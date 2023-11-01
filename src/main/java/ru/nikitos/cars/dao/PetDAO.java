package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PetDAO {
    @Autowired
    private Connection connection;

    //public PetDAO(Connection connection) {
       // this.connection = connection;
   // }
    public int addPet(Pet pet) throws SQLException {
        int id;
        String sql = "INSERT INTO pets_for_empl (vid, petname, employee_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, pet.getVid() );
        statement.setString(2, pet.getName());
        statement.setInt(3,pet.getEmployee_id());
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


    public Pet getPetById(int id) throws SQLException {
        String sql = "SELECT * FROM pets_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Pet pet= null;
        if (resultSet.next()) {
            pet = new Pet();
            pet.setId(resultSet.getInt("id"));
            pet.setVid(resultSet.getString("vid"));
            pet.setName(resultSet.getString("petname"));
            pet.setEmployee_id(resultSet.getInt("employee_id"));
        }
        resultSet.close();
        statement.close();
        return pet;
    }

    public List<Pet> getAllPets() throws SQLException {
        String sql = "SELECT * FROM pets_for_empl";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Pet> pets = new ArrayList<>();
        while (resultSet.next()) {
            Pet pet = new Pet();
            pet.setId(resultSet.getInt("id"));
            pet.setVid(resultSet.getString("vid"));
            pet.setName(resultSet.getString("petname"));
            pet.setEmployee_id(resultSet.getInt("employee_id"));
            pets.add(pet);
        }
        resultSet.close();
        statement.close();
        return pets;

    }


    public void updatePet(Pet pet) throws SQLException {
        String sql = "UPDATE pets_for_empl SET vid = ?, petname = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pet.getVid());
        statement.setString(2, pet.getName());
        statement.setInt(3, pet.getId());
        statement.executeUpdate();
        statement.close();
    }


    public void deletePet(int id) throws SQLException {
        String sql = "DELETE FROM pets_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }
}





