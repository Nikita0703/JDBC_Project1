package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.House;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HouseDAO {

    @Autowired
    private Connection connection;

    //public HouseDAO(Connection connection) {
        //this.connection = connection;
   // }
    public void addHouse(House house) throws SQLException {
        String sql = "INSERT INTO house_for_empl (adress, flour,flat) VALUES (?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, house.getAdress());
        statement.setInt(2, house.getFlour());
        statement.setInt(3, house.getFlat());
        statement.executeUpdate();
    }


    public House getHouseById(int id) throws SQLException {
        String sql = "SELECT * FROM house_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        House house = null;
        if (resultSet.next()) {
            house = new House();
            house.setId(resultSet.getInt("id"));
            house.setAdress(resultSet.getString("adress"));
            house.setFlour(resultSet.getInt("flour"));
            house.setFlat(resultSet.getInt("flat"));
        }
        return house;
    }

    public List<House> getAllHousess() throws SQLException {
        String sql = "SELECT * FROM house_for_empl";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<House> houses = new ArrayList<>();
        while (resultSet.next()) {
            House house = new House();
            house.setId(resultSet.getInt("id"));
            house.setAdress(resultSet.getString("adress"));
            house.setFlour(resultSet.getInt("flour"));
            house.setFlat(resultSet.getInt("flat"));
            houses.add(house);
        }

        return houses;
    }


    public void updateHouse(House house) throws SQLException {
        String sql = "UPDATE house_for_empl SET adress = ?, flour = ?, flat = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, house.getAdress());
        statement.setInt(2, house.getFlour());
        statement.setInt(3, house.getFlour());
        statement.setInt(4, house.getId());
        statement.executeUpdate();
    }


    public void deleteHouse(int id) throws SQLException {
        String sql = "DELETE FROM house_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}




