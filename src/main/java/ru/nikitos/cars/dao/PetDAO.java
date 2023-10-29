package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class PetDAO {
    @Autowired
    private Connection connection;

    //public PetDAO(Connection connection) {
       // this.connection = connection;
   // }
    public void addPet(Pet pet) throws SQLException {
        String sql = "INSERT INTO pets_for_empl (vid, petname, employee_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pet.getVid() );
        statement.setString(2, pet.getName());
        statement.setInt(3,pet.getEmployee_id());
        statement.executeUpdate();
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
        return pets;

    }


    public void updatePet(Pet pet) throws SQLException {
        String sql = "UPDATE pets_for_empl SET vid = ?, petname = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, pet.getVid());
        statement.setString(2, pet.getName());
        statement.setInt(3, pet.getId());
        statement.executeUpdate();
    }


    public void deletePet(int id) throws SQLException {
        String sql = "DELETE FROM pets_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}





