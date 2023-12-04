package com.example.trilateracion.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TopSecretResponse {
    private Location exoplanetLocation;
    private List<String> reconstructedMessage;
    private String exoplanetName;
    private double exoplanetRadius;
    private double exoplanetMass;
    private String secretMessage;
}
