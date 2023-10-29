package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.Emps_Projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class Emps_ProjectsDAO {
    @Autowired
    private Connection connection;
    public void addEmpProjectIds(Emps_Projects empsProjects) throws SQLException {
        String sql = "INSERT INTO emps_projects (employee_id, project_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, empsProjects.getEmployee_id());
        statement.setInt(2, empsProjects.getProjects_id());
        statement.executeUpdate();
    }


    public Emps_Projects getEmpProjectIdsById(int id) throws SQLException {
        String sql = "SELECT * FROM emps_projects WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Emps_Projects empsProjects = null;
        if (resultSet.next()) {
            empsProjects = new Emps_Projects();
            empsProjects.setId(resultSet.getInt("id"));
            empsProjects.setEmployee_id(resultSet.getInt("employee_id"));
            empsProjects.setProjects_id(resultSet.getInt("project_id"));
        }
        return empsProjects;
    }

    public List<Emps_Projects> getAllEmpProjectIds() throws SQLException {
        String sql = "SELECT * FROM emps_projects";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Emps_Projects> empsProjects = new ArrayList<>();
        while (resultSet.next()) {
            Emps_Projects empsProject = new Emps_Projects();
            empsProject.setId(resultSet.getInt("id"));
            empsProject.setEmployee_id(resultSet.getInt("employee_id"));
            empsProject.setProjects_id(resultSet.getInt("project_id"));
            empsProjects.add(empsProject);
        }
        return empsProjects;
    }


    public void updateCar(Car car) throws SQLException {
        String sql = "UPDATE car_for_empl SET model = ?, made = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, car.getModel());
        statement.setInt(2, car.getYear());
        statement.setInt(3, car.getId());
        statement.executeUpdate();
    }


    public void deleteEmpProjectIds(int id) throws SQLException {
        String sql = "DELETE FROM emps_projects WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}




