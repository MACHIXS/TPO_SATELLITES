package com.example.trilateracion.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Satellite {
    private String name;
    private double distance;
    private List<String> message;
    private Location location;

}
