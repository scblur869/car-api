package com.bmw.baseapi;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 *
 * @author brad.ross
 */
@Data
@Entity
class Car {
  private @Id @GeneratedValue Long id;
  private String vin;
  private String make;
  private String model;
  private String year;
  private String color;

  
  Car() {}
  
  Car(String vin, String make, String model, String year, String color) {
      this.vin = vin;
      this.make = make;
      this.model = model;
      this.year = year;
      this.color = color;
  }
  
}
