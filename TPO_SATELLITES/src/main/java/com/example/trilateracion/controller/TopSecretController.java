package com.example.trilateracion.controller;

import com.example.trilateracion.model.*;
import com.example.trilateracion.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topsecret")
public class TopSecretController {

    @Value("${exoplanet.name:DefaultExoplanet}")
    private String exoplanetName;

    @Value("${exoplanet.radius:0.0}")
    private double exoplanetRadius;

    @Value("${exoplanet.mass:0.0}")
    private double exoplanetMass;

    @Value("${secret.message:DefaultSecretMessage}")
    private String secretMessage;

    @PostMapping
    public ResponseEntity<TopSecretResponse> processTopSecret(@RequestBody TopSecretRequest request) {
        try {
            List<Double> distances = request.getSatellites().stream().map(Satellite::getDistance).toList();
            List<double[]> locations = request.getSatellites().stream()
                    .map(satellite -> new double[]{satellite.getLocation().getX(), satellite.getLocation().getY()})
                    .toList();

            double[] exoplanetLocation = ExoplanetLocator.calculateExoplanetLocation(distances, locations);

            List<List<String>> interferedMessages = request.getSatellites().stream()
                    .map(Satellite::getMessage)
                    .toList();
            List<String> reconstructedMessage = MessageReconstructor.reconstructMessage(interferedMessages);

            TopSecretResponse response = new TopSecretResponse(
                    new Location(exoplanetLocation[0], exoplanetLocation[1]),
                    reconstructedMessage,
                    exoplanetName,
                    exoplanetRadius,
                    exoplanetMass,
                    secretMessage
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/exoplanet-info")
    public ExoplanetInfo getExoplanetInfo() {
        return new ExoplanetInfo(exoplanetName, exoplanetRadius, exoplanetMass);
    }

    @PutMapping("/update-message")
    public ResponseEntity<String> updateSecretMessage(@RequestBody String newMessage) {
        secretMessage = newMessage;
        return ResponseEntity.ok("Mensaje secreto actualizado con éxito");
    }
}
