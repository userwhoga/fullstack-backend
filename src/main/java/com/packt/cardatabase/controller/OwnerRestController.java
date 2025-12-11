package com.packt.cardatabase.controller;

import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import com.packt.cardatabase.dto.OwnerDto;
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
@RequestMapping("/api/owners")
@Tag(name = "Owner Management", description = "API for managing car owners")
public class OwnerRestController {
    
    private final OwnerRepository ownerRepository;
    
    @Autowired
    public OwnerRestController(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    @Operation(summary = "Get all owners", description = "Retrieve a list of all car owners")
    @ApiResponse(responseCode = "200", description = "List of owners retrieved successfully")
    @GetMapping
    public List<OwnerDto> getAllOwners() {
        return ((List<Owner>) ownerRepository.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Operation(summary = "Get owner by ID", description = "Retrieve an owner by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Owner found"),
        @ApiResponse(responseCode = "404", description = "Owner not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(
            @Parameter(description = "Owner ID", required = true) @PathVariable Long id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            return ResponseEntity.ok(convertToDto(owner.get()));
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Create new owner", description = "Create a new car owner")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Owner created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setFirstname(ownerDto.getFirstname());
        owner.setLastname(ownerDto.getLastname());
        
        Owner savedOwner = ownerRepository.save(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(savedOwner));
    }
    
    @Operation(summary = "Update owner", description = "Update an existing owner's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Owner updated successfully"),
        @ApiResponse(responseCode = "404", description = "Owner not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(
            @Parameter(description = "Owner ID", required = true) @PathVariable Long id,
            @RequestBody OwnerDto ownerDto) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        
        if (ownerOptional.isPresent()) {
            Owner owner = ownerOptional.get();
            owner.setFirstname(ownerDto.getFirstname());
            owner.setLastname(ownerDto.getLastname());
            
            Owner updatedOwner = ownerRepository.save(owner);
            return ResponseEntity.ok(convertToDto(updatedOwner));
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Delete owner", description = "Delete an owner by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Owner not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(
            @Parameter(description = "Owner ID", required = true) @PathVariable Long id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        
        if (owner.isPresent()) {
            ownerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Helper method to convert Owner entity to DTO
    private OwnerDto convertToDto(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setOwnerId(owner.getOwnerid());
        dto.setFirstname(owner.getFirstname());
        dto.setLastname(owner.getLastname());
        
        if (owner.getCars() != null) {
            dto.setCarIds(owner.getCars().stream()
                    .map(car -> car.getId())
                    .collect(Collectors.toList()));
            dto.setCarCount(owner.getCars().size());
        } else {
            dto.setCarCount(0);
        }
        
        return dto;
    }
}

