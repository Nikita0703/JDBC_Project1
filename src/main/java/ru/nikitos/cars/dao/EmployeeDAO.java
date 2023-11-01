package ru.nikitos.cars.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class EmployeeDAO {
    @Autowired
    private Connection connection;

    //public EmployeeDAO(Connection connection) {
      //  this.connection = connection;
  //  }

    public int addEmployee(Employee employee) throws SQLException {
       // String sql = "INSERT INTO employee (name, surname, salary, department) VALUES (?, ?, ?, ?)";
        int id = 0;
        String sql = "INSERT INTO employee_full (name, surname, salary, department, car_id,house_id) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSurname());
        statement.setInt(3, employee.getSalary());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, employee.getCarId());
        statement.setInt(6, employee.getHouse_id());
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


    public void updateEmployee(Employee employee, int id) throws SQLException {
        String sql = "UPDATE employee_full SET name = ?, surname = ?, salary = ?, department = ?, car_id = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSurname());
        statement.setInt(3, employee.getSalary());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, employee.getCarId());
        statement.setInt(6, id);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee_full WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
    }

    public Employee getEmployee(int id) throws SQLException {
        String sql = "SELECT * FROM employee_full WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Employee employee = new Employee();
             employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setSurname(resultSet.getString("surname"));
            employee.setSalary(resultSet.getInt("salary"));
            employee.setDepartment(resultSet.getString("department"));
            employee.setCarId(resultSet.getInt("car_id"));
            employee.setHouse_id(resultSet.getInt("house_id"));
            resultSet.close();
            statement.close();
            return employee;
        }
        resultSet.close();
        statement.close();
        return null;
    }
    public List<Employee> getAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee_full";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setSurname(resultSet.getString("surname"));
            employee.setSalary(resultSet.getInt("salary"));
            employee.setDepartment(resultSet.getString("department"));
            employee.setCarId(resultSet.getInt("car_id"));
            employee.setHouse_id(resultSet.getInt("house_id"));
            employees.add(employee);
        }

        resultSet.close();
        statement.close();
        return employees;
    }





}
