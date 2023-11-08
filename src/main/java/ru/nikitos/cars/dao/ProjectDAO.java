package ru.nikitos.cars.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Car;
import ru.nikitos.cars.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProjectDAO {
    @Autowired
    private Connection connection;
    public int addProject(Project project) throws SQLException {
        int id = 0;
        String sql = "INSERT INTO projects_for_empl (title, year) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
          statement.setString(1, project.getTitle());
          statement.setInt(2, project.getYear());
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


    public Project getProjectById(int id) throws SQLException {
        String sql = "SELECT * FROM projects_for_empl WHERE id = ?";
        Project project = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            project = null;
            if (resultSet.next()) {
                project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setYear(resultSet.getInt("year"));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public List<Project> getAllProjects() throws SQLException {
        String sql = "SELECT * FROM projects_for_empl";
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setYear(resultSet.getInt("year"));
                projects.add(project);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        statement.close();
    }


    public void deleteProject(int id) throws SQLException {
        String sql = "DELETE FROM car_for_empl WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }
}




