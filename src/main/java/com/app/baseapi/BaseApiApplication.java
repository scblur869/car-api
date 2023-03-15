package com.app.baseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@SpringBootApplication
public class BaseApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApiApplication.class, args);
	}
        @Bean
        CommandLineRunner runner(CarFetchService carFetchService) {
            
            return args -> {
                ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Car>> typeReference = new TypeReference<List<Car>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/cars.json");
			try {
				List<Car> cars = mapper.readValue(inputStream,typeReference);
				carFetchService.save(cars);
				System.out.println("cars loaded!");
			} catch (IOException e){
				System.out.println("Unable to load cars data: " + e.getMessage());
			}
	    };
            }
        }

