/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmw.baseapi;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author brad.ross
 */
@Service
public class CarFetchService {
    private CarRepository carRepository;
    
    public CarFetchService(CarRepository carRespository) {
        this.carRepository = carRespository;
    }
        
    public Iterable<Car> list() {
        return carRepository.findAll();
    }
    public Iterable<Car> byYear(String year) {
        return carRepository.findByYear(year);
    }
    
        public Iterable<Car> byModelName(String model) {
        return carRepository.findByModel(model);
    }
        
    public void save(List<Car> cars) {
        carRepository.saveAll(cars);
    }
    
    public Car newCar(Car car) {
        return carRepository.save(car);
    }
    
   
}
