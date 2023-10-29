package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class CarDAO {
    @Autowired
    private Connection connection;

   // public CarDAO(Connection connection) {
      //  this.connection = connection;
   // }
    public void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO car_for_empl (model, made) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, car.getModel());
        statement.setInt(2, car.getYear());
        statement.executeUpdate();
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
        return cars;
    }


    public void updateCar(Car car) throws SQLException {
        String sql = "UPDATE car_for_empl SET model = ?, made = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, car.getModel());
        statement.setInt(2, car.getYear());
        statement.setInt(3, car.getId());
        statement.executeUpdate();
    }


    public void deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM car_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}


