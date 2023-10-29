package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProjectDAO {
    @Autowired
    private Connection connection;
    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO projects_for_empl (title, year) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, project.getTitle());
        statement.setInt(2, project.getYear());
        statement.executeUpdate();
    }


    public Project getProjectById(int id) throws SQLException {
        String sql = "SELECT * FROM projects_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Project project = null;
        if (resultSet.next()) {
            project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setTitle(resultSet.getString("title"));
            project.setYear(resultSet.getInt("year"));
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
        String sql = "SELECT * FROM projects_for_empl";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Project> projects = new ArrayList<>();
        while (resultSet.next()) {
           Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setTitle(resultSet.getString("title"));
            project.setYear(resultSet.getInt("year"));
            projects.add(project);
        }
        return projects;
    }


    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE projects_for_empl SET title = ?, year = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, project.getTitle());
        statement.setInt(2, project.getYear());
        statement.setInt(3, project.getId());
        statement.executeUpdate();
    }


    public void deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM car_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}




