package com.packt.cardatabase.service;

import org.springframework.stereotype.Service;

@Service
public class CarDatabaseService implements CarService {
    
    @Override
    public void startCar() {
        System.out.println("Starting car from database service...");
    }
    
    public String getAllCars() {
        return "All cars from database";
    }
}
