package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class CarDAO {
    @Autowired
    private Connection connection;

   // public CarDAO(Connection connection) {
      //  this.connection = connection;
   // }
    public int addCar(Car car) throws SQLException {
        int id = 0;
        String sql = "INSERT INTO car_for_empl (model, made) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, car.getModel());
        statement.setInt(2, car.getYear());
        statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
                System.out.println("Inserted object with id: " + id);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        statement.close();
        return id;
    }


    public Car getCarById(int id) throws SQLException {
        String sql = "SELECT * FROM car_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Car car = null;
        if (resultSet.next()) {
            car = new Car();
            car.setId(resultSet.getInt("id"));
            car.setModel(resultSet.getString("model"));
            car.setYear(resultSet.getInt("made"));
        }
       resultSet.close();
        statement.close();
        return car;


    }

    public List<Car> getAllCars() throws SQLException {
        String sql = "SELECT * FROM car_for_empl";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Car> cars = new ArrayList<>();
        while (resultSet.next()) {
            Car car = new Car();
            car.setId(resultSet.getInt("id"));
            car.setModel(resultSet.getString("model"));
            car.setYear(resultSet.getInt("made"));
            cars.add(car);
        }
        resultSet.close();
        statement.close();
        return cars;
    }



    public void updateCar(Car car) throws SQLException {
        String sql = "UPDATE car_for_empl SET model = ?, made = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, car.getModel());
        statement.setInt(2, car.getYear());
        statement.setInt(3, car.getId());
        statement.executeUpdate();
        statement.close();
    }


    public void deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM car_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }
}


