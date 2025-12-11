package com.packt.cardatabase.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Car information")
public class CarDto {
    
    @Schema(description = "Car ID", example = "1")
    private Long id;
    
    @Schema(description = "Car brand", example = "Ford", required = true)
    private String brand;
    
    @Schema(description = "Car model", example = "Mustang", required = true)
    private String model;
    
    @Schema(description = "Car color", example = "Red")
    private String color;
    
    @Schema(description = "Registration number", example = "ADF-1121")
    private String registrationNumber;
    
    @Schema(description = "Model year", example = "2023")
    private int modelYear;
    
    @Schema(description = "Price in USD", example = "59000")
    private int price;
    
    @Schema(description = "Owner ID", example = "1")
    private Long ownerId;
    
    @Schema(description = "Owner full name", example = "John Johnson")
    private String ownerName;
    
    // Constructors
    public CarDto() {}
    
    public CarDto(String brand, String model, String color, String registrationNumber, int modelYear, int price, Long ownerId) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.modelYear = modelYear;
        this.price = price;
        this.ownerId = ownerId;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    
    public int getModelYear() { return modelYear; }
    public void setModelYear(int modelYear) { this.modelYear = modelYear; }
    
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}



