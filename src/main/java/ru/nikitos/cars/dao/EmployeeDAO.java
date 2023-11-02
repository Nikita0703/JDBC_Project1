package ru.nikitos.cars.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.entity.Employee;
import ru.nikitos.cars.entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class EmployeeDAO {
    @Autowired
    private Connection connection;

    @Autowired
    private ProjectDAO projectDAO;


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
        for (Project project: employee.getProjects()){
            int project_id = projectDAO.addProject(project);
            String sqlManyToMany = "INSERT INTO emps_projects (employee_id, project_id) VALUES (?, ?)";
            PreparedStatement statementManyToMany = connection.prepareStatement(sqlManyToMany);
            statementManyToMany.setInt(1, id);
            statementManyToMany.setInt(2, project_id);
            statementManyToMany.executeUpdate();
            statementManyToMany.close();
        }
        statement.close();

        return id;
    }


    public void deleteEmployee(int id) throws SQLException {
        String sql = "DELETE FROM employee_full WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        String sqlManyToMany = "DELETE FROM emps_projects WHERE employee_id = ?";
        PreparedStatement statementManyToMany = connection.prepareStatement(sqlManyToMany);
        statementManyToMany.setInt(1, id);
        statementManyToMany.executeUpdate();
        statementManyToMany.close();
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

            String sqlManyToMany = "SELECT project_id  FROM emps_projects WHERE employee_id = ?";
            PreparedStatement statementManyToMany = connection.prepareStatement(sqlManyToMany);
            statementManyToMany.setInt(1,employee.getId());
            ResultSet resultSetManyToMany = statementManyToMany.executeQuery();
            List<Integer> projects_id = new ArrayList<>();
            while (resultSetManyToMany.next()) {
                int projectId = resultSet.getInt("project_id");
                projects_id.add(projectId);
            }
            for(Integer i :projects_id){
                Project project = projectDAO.getProjectById(i);
                employee.getProjects().add(project);
            }

            statementManyToMany.close();
            resultSetManyToMany.close();
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
            String sqlManyToMany = "SELECT project_id  FROM emps_projects WHERE employee_id = ?";
            PreparedStatement statementManyToMany = connection.prepareStatement(sqlManyToMany);
            statementManyToMany.setInt(1,employee.getId());
            ResultSet resultSetManyToMany = statementManyToMany.executeQuery();
            List<Integer> projects_id = new ArrayList<>();
            while (resultSetManyToMany.next()) {
                int projectId = resultSetManyToMany.getInt("project_id");
                projects_id.add(projectId);
            }
            for(Integer i :projects_id){
                Project project = projectDAO.getProjectById(i);
                employee.getProjects().add(project);
            }

            statementManyToMany.close();
            resultSetManyToMany.close();
            employees.add(employee);
        }

        resultSet.close();
        statement.close();
        return employees;
    }


    public void updateEmployee(Employee employee, int id,List<Project> projects) throws SQLException {
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

        String sqlManyToMany = "SELECT project_id  FROM emps_projects WHERE employee_id = ?";
        PreparedStatement statementManyToMany = connection.prepareStatement(sqlManyToMany);
        statementManyToMany.setInt(1,employee.getId());
        ResultSet resultSetManyToMany = statementManyToMany.executeQuery();
        List<Integer> projects_id = new ArrayList<>();
        while (resultSetManyToMany.next()) {
            int projectId = resultSetManyToMany.getInt("project_id");
            projects_id.add(projectId);
        }
        for(Integer i :projects_id){
            Project project = projectDAO.getProjectById(i);
            employee.getProjects().add(project);
        }

        int newLengthProject = projects.size();


        int priviousLengthProject = projects_id.size();

        if(priviousLengthProject>newLengthProject){
            int difference = priviousLengthProject - newLengthProject;
            for (int i = 0;i<difference;i++){
                for (Integer index: projects_id){
                    String sqlManyToManyDel  = "DELETE FROM emps_projects WHERE employee_id = ? AND project_id = ? ";
                    PreparedStatement statementManyToManyDel = connection.prepareStatement(sqlManyToManyDel);
                    statementManyToManyDel.setInt(1, id);
                    statementManyToManyDel.setInt(2,index);
                    statement.executeUpdate();
                    statementManyToManyDel.close();
                        projectDAO.deleteProject(index);
                        projects_id.remove(index);
                       break;

                }
            }

        }

        if(priviousLengthProject<newLengthProject){
            int projectId = 0;
            boolean Flag = false;
            int difference = newLengthProject-priviousLengthProject;
            for (int i = 0; i < difference; i++) {
                Project newProject = new Project("new_project ", 0);
                projectDAO.addProject(newProject);
                if (Flag == false) {
                    List<Project> projectslist1 = projectDAO.getAllProjects();
                    for (Project project : projectslist1) {
                        if (newProject.getTitle().equals(project.getTitle())
                                && newProject.getYear() == project.getYear()) {
                            projectId = project.getId();
                            Flag = true;
                        }
                    }
                }
                String sqlManyToManyAdd = "INSERT INTO emps_projects (employee_id, project_id) VALUES (?, ?)";
                PreparedStatement statementManyToManyAdd = connection.prepareStatement(sqlManyToManyAdd);
                statementManyToManyAdd.setInt(1, employee.getId());
                statementManyToManyAdd.setInt(2, projectId+i);
                projects_id.add(projectId+i);
                statementManyToManyAdd.executeUpdate();
                statementManyToManyAdd.close();
            }

        }

        List<Project> lastprojectlist= projectDAO.getAllProjects();
           int i = 0;
           for (Project project:projects) {
                project.setId(projects_id.get(i++));
                projectDAO.updateProject(project);
            }

    }


}
