package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface CarRepository extends CrudRepository<Car, Long> {
    
    List<Car> findByBrand(String brand);
    
    List<Car> findByColor(String color);
    
    List<Car> findByModelYear(int modelYear);
    
    List<Car> findByBrandAndModel(String brand, String model);
    
    List<Car> findByBrandOrColor(String brand, String color);
    
    List<Car> findByBrandOrderByModelYearAsc(String brand);
    
    @Query("select c from Car c where c.brand = ?1")
    List<Car> findByBrandWithQuery(String brand);
    
    @Query("select c from Car c where c.brand like %?1")
    List<Car> findByBrandEndsWith(String brand);
}





