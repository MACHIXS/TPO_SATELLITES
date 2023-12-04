package com.example.trilateracion.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Distance {
    private double value;
    private String unit;

    public Distance(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }


    public String getFormattedDistance() {
        return String.format("%.2f %s", value, unit);
    }

    @Override
    public String toString() {
        return "Distance{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
