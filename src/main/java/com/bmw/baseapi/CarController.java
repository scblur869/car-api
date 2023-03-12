/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmw.baseapi;

import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 *
 * @author brad.ross
 */
@RestController
@RequestMapping("/cars")
public class CarController {
    private CarFetchService carFetchService;
    public CarController(CarFetchService carFetchService){
        this.carFetchService = carFetchService;
    }
    
    @GetMapping("/list")
    public Iterable<Car> list() {
        return carFetchService.list();
    }
    
    @GetMapping("/by-year/{year}")
    public Iterable<Car> byYear(@PathVariable String year) {
        return carFetchService.byYear(year);
    }
    
        @GetMapping("/by-model/{model}")
    public Iterable<Car> byModel(@PathVariable String model) {
        return carFetchService.byModelName(model);
    }
    
    @PostMapping(path = "/new-car", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json")
	public Car createCar(@RequestBody Car car) {
		carFetchService.newCar(car);
		return car;
	}
  
    @PostMapping("/add-car")
	public ResponseEntity<Void> addNewVehicle(
			@RequestBody Car car) {

		Car carResult = carFetchService.newCar(car);

		if (carResult == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
				"/").buildAndExpand().toUri();

		return ResponseEntity.created(location).build();
	}    
}
