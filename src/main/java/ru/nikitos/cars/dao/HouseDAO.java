package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.House;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class HouseDAO {

    @Autowired
    private Connection connection;

    //public HouseDAO(Connection connection) {
        //this.connection = connection;
   // }
    public int addHouse(House house) throws SQLException {
        int id = 0;
        String sql = "INSERT INTO house_for_empl (adress, flour,flat) VALUES (?, ?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
          statement.setString(1, house.getAdress());
          statement.setInt(2, house.getFlour());
          statement.setInt(3, house.getFlat());
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
       }
        catch (SQLException e) {
        e.printStackTrace();
       }
        return id;
    }


    public House getHouseById(int id) throws SQLException {
        String sql = "SELECT * FROM house_for_empl WHERE id = ?";
        House house = new House();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            house = null;
            if (resultSet.next()) {
                house.setId(resultSet.getInt("id"));
                house.setAdress(resultSet.getString("adress"));
                house.setFlour(resultSet.getInt("flour"));
                house.setFlat(resultSet.getInt("flat"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return house;
    }

    public List<House> getAllHousess() throws SQLException {
        String sql = "SELECT * FROM house_for_empl";
        List<House> houses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                House house = new House();
                house.setId(resultSet.getInt("id"));
                house.setAdress(resultSet.getString("adress"));
                house.setFlour(resultSet.getInt("flour"));
                house.setFlat(resultSet.getInt("flat"));
                houses.add(house);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        statement.close();
    }


    public void deleteHouse(int id) throws SQLException {
        String sql = "DELETE FROM house_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }
}




