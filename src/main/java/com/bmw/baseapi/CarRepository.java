/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmw.baseapi;
import java.util.List;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author brad.ross
 */
public interface CarRepository extends JpaRepository<Car, Long>  {
    
        @Query("SELECT car from Car car where car.year = :year")
    public List<Car> findByYear(@Param("year") String year);
    
            @Query("SELECT car from Car car where car.model = :model")
    public List<Car> findByModel(@Param("model") String model);
}
