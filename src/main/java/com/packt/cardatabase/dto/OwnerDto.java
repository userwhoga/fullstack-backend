package com.packt.cardatabase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Owner information")
public class OwnerDto {
    
    @Schema(description = "Owner ID", example = "1")
    private Long ownerId;
    
    @Schema(description = "First name", example = "John", required = true)
    private String firstname;
    
    @Schema(description = "Last name", example = "Johnson", required = true)
    private String lastname;
    
    @Schema(description = "List of owned car IDs")
    private List<Long> carIds;
    
    @Schema(description = "Number of owned cars", example = "2")
    private int carCount;
    
    // Constructors
    public OwnerDto() {}
    
    public OwnerDto(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    // Getters and Setters
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    
    public List<Long> getCarIds() { return carIds; }
    public void setCarIds(List<Long> carIds) { this.carIds = carIds; }
    
    public int getCarCount() { return carCount; }
    public void setCarCount(int carCount) { this.carCount = carCount; }
}



