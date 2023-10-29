package ru.nikitos.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.configuration.AppConfig;
import ru.nikitos.cars.entity.Car;
@Component
public class CarService {
    @Autowired
     Car car;

  public void print(){
      System.out.println("model - "+car.getModel()+ "year-"+car.getYear());
  }
}
