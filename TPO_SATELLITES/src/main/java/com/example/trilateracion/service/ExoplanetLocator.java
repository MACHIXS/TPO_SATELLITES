package com.example.trilateracion.service;

import java.util.List;

public class ExoplanetLocator {

    public static double[] calculateExoplanetLocation(List<Double> distances, List<double[]> satelliteLocations) {
        double sumX = 0.0;
        double sumY = 0.0;

        for (int i = 0; i < satelliteLocations.size(); i++) {
            double[] location = satelliteLocations.get(i);
            double weight = distances.get(i);
            sumX += location[0] * weight;
            sumY += location[1] * weight;
        }

        double totalWeight = distances.stream().mapToDouble(Double::doubleValue).sum();
        return new double[]{sumX / totalWeight, sumY / totalWeight};
    }
}
