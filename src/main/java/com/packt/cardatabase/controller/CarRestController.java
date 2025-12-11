package com.packt.cardatabase.controller;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import com.packt.cardatabase.dto.CarDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Management", description = "API for managing cars")
public class CarRestController {
    
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;
    
    @Autowired
    public CarRestController(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }
    
    @Operation(summary = "Get all cars", description = "Retrieve a list of all cars")
    @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully")
    @GetMapping
    public List<CarDto> getAllCars() {
        return ((List<Car>) carRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Operation(summary = "Get car by ID", description = "Retrieve a car by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car found"),
        @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            return ResponseEntity.ok(convertToDto(car.get()));
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Create new car", description = "Create a new car")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Car created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Owner not found (if ownerId is provided)")
    })
    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setColor(carDto.getColor());
        car.setRegistrationNumber(carDto.getRegistrationNumber());
        car.setModelYear(carDto.getModelYear());
        car.setPrice(carDto.getPrice());
        
        // Set owner if ownerId is provided
        if (carDto.getOwnerId() != null) {
            Optional<Owner> owner = ownerRepository.findById(carDto.getOwnerId());
            if (owner.isPresent()) {
                car.setOwner(owner.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        
        Car savedCar = carRepository.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedCar));
    }
    
    @Operation(summary = "Update car", description = "Update an existing car's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Car updated successfully"),
        @ApiResponse(responseCode = "404", description = "Car or Owner not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id,
            @RequestBody CarDto carDto) {
        Optional<Car> carOptional = carRepository.findById(id);
        
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setBrand(carDto.getBrand());
            car.setModel(carDto.getModel());
            car.setColor(carDto.getColor());
            car.setRegistrationNumber(carDto.getRegistrationNumber());
            car.setModelYear(carDto.getModelYear());
            car.setPrice(carDto.getPrice());
            
            // Update owner if ownerId is provided
            if (carDto.getOwnerId() != null) {
                Optional<Owner> owner = ownerRepository.findById(carDto.getOwnerId());
                if (owner.isPresent()) {
                    car.setOwner(owner.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                car.setOwner(null);
            }
            
            Car updatedCar = carRepository.save(car);
            return ResponseEntity.ok(convertToDto(updatedCar));
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Delete car", description = "Delete a car by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Car deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(
            @Parameter(description = "Car ID", required = true) @PathVariable Long id) {
        Optional<Car> car = carRepository.findById(id);
        
        if (car.isPresent()) {
            carRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Helper method
    private CarDto convertToDto(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setColor(car.getColor());
        dto.setRegistrationNumber(car.getRegistrationNumber());
        dto.setModelYear(car.getModelYear());
        dto.setPrice(car.getPrice());
        
        if (car.getOwner() != null) {
            dto.setOwnerId(car.getOwner().getOwnerid());
            dto.setOwnerName(car.getOwner().getFirstname() + " " + car.getOwner().getLastname());
        }
        
        return dto;
    }
}


