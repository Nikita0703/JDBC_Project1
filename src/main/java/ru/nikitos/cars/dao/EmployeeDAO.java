package ru.nikitos.cars.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Component
public class EmployeeDAO {
    @Autowired
    private Connection connection;

    //public EmployeeDAO(Connection connection) {
      //  this.connection = connection;
  //  }

    public void addEmployee(Employee employee) throws SQLException {
       // String sql = "INSERT INTO employee (name, surname, salary, department) VALUES (?, ?, ?, ?)";
        String sql = "INSERT INTO employee_full (name, surname, salary, department, car_id,house_id) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSurname());
        statement.setInt(3, employee.getSalary());
        statement.setString(4, employee.getDepartment());
        statement.setInt(5, employee.getCarId());
        statement.setInt(6, employee.getHouse_id());
        statement.executeUpdate();
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
    }

    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee_full WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
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
            return employee;
        }
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
        return employees;
    }





}
