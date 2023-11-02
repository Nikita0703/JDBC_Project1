package ru.nikitos.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ru.nikitos.cars.configuration.AppConfig;
import ru.nikitos.cars.dao.*;
import ru.nikitos.cars.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class EmployeeService {
    @Autowired
    private CarDAO carDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private HouseDAO houseDAO;

    @Autowired
    private PetDAO petDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private Connection connection;

    public void addEmployee(Employee employee, Car car, House house, List<Pet> pets, List<Project> projects) throws SQLException {

        try  {

            connection.setAutoCommit(false);

            int idCar = carDAO.addCar(car);
            int idHouse = houseDAO.addHouse(house);

            employee.setCarId(idCar);
            employee.setHouse_id(idHouse);

            employee.setProjects(projects);
            int idEmpl = employeeDAO.addEmployee(employee);

            for (Pet pet : pets) {
                pet.setEmployee_id(idEmpl);
                petDAO.addPet(pet);
            }

            connection.commit();

        }catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void printAllEmployees() throws SQLException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee e : employees) {
            System.out.print("Id - "+e.getId()+" Name - "+e.getName() + " Surname - " + e.getSurname() + " Salary - " + e.getSalary() + " Department - " + e.getDepartment()+ " ");
            int idCar = e.getCarId();
            Car car = carDAO.getCarById(idCar);
            System.out.print("Car{ model -  "+car.getModel()+ " year - "+car.getYear()+ "} ");
            int idHouse = e.getHouse_id();
            House house = houseDAO.getHouseById(idHouse);

            System.out.print("House{ adress - "+house.getAdress()+" flour - "+house.getFlour()+" flat - "+house.getFlat()+ "}");
            List<Pet> pets = petDAO.getAllPets();
            List<Pet> resPet  = new ArrayList<>();
            for (Pet pet :pets){
                if (pet.getEmployee_id()==e.getId()){
                    resPet.add(pet);
                }
            }

            System.out.print("Pets [");
            for (Pet pet :resPet){
                System.out.print(" {vid - "+ pet.getVid()+ " name - "+ pet.getName() + " }");
            }
            System.out.print(" ]");


            System.out.print("Projects [");
            for (Project project: e.getProjects()){
                System.out.print(" {title - "+ project.getTitle()+ " year - "+project.getYear() + " }");
            }
            System.out.println(" ]");
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        Employee employee = employeeDAO.getEmployee(id);
        int carId = employee.getCarId();
        int houseId = employee.getHouse_id();

       List<Project> projects = projectDAO.getAllProjects();

       for(Project project: projects){
           projectDAO.deleteProject(project.getId());
       }
        List<Pet> pets = petDAO.getAllPets();
        for (Pet pet :pets){
            if (pet.getEmployee_id()==id){
                petDAO.deletePet(pet.getId());
            }
        }

        employeeDAO.deleteEmployee(id);
        carDAO.deleteCar(carId);
        houseDAO.deleteHouse(houseId);

    };

    public void updateEmployee(Employee employee, Car car, House house, List<Pet> pets, List<Project> projects,int id) throws SQLException {
       try {

           connection.setAutoCommit(false);

           employee.setId(id);
           int carId = employee.getCarId();
           int houseId = employee.getHouse_id();
           employeeDAO.updateEmployee(employee, id, projects);
           carDAO.updateCar(car);
           houseDAO.updateHouse(house);

           int newLength = pets.size();
           int priviousLength = 0;

           List<Pet> petslist = petDAO.getAllPets();
           for (Pet pt : petslist) {
               if (pt.getEmployee_id() == employee.getId()) {
                   priviousLength += 1;
               }
           }

           if (priviousLength > newLength) {
               int difference = priviousLength - newLength;
               for (int i = 0; i < difference; i++) {
                   for (Pet pt : petslist) {
                       if (pt.getEmployee_id() == employee.getId()) {
                           petDAO.deletePet(pt.getId());
                           break;
                       }
                   }
               }
               for (Pet pet : pets) {
                   for (Pet pt : petslist) {
                       boolean Flag = false;
                       if (pt.getEmployee_id() == employee.getId()) {
                           for (Pet pet1 : pets) {
                               if (pt.getName().equals(pet1.getName())
                                       && pt.getVid().equals(pet1.getVid())) {
                                   Flag = true;
                               }
                           }
                           if (Flag == false) {
                               pet.setId(pt.getId());
                               petDAO.updatePet(pet);
                           }
                       }
                       if (Flag == false) {
                           break;
                       }
                   }
               }

           }


           if (priviousLength == newLength) {
               for (Pet pet : pets) {
                   for (Pet pt : petslist) {
                       boolean Flag = false;
                       if (pt.getEmployee_id() == employee.getId()) {
                           for (Pet pet1 : pets) {
                               if (pt.getName().equals(pet1.getName())
                                       && pt.getVid().equals(pet1.getVid())) {
                                   Flag = true;
                               }
                           }
                           if (Flag == false) {
                               pet.setId(pt.getId());
                               petDAO.updatePet(pet);
                           }
                       }
                       if (Flag == false) {
                           break;
                       }
                   }
               }
           }

           if (priviousLength < newLength) {
               int diference = newLength - priviousLength;
               for (int i = 0; i < diference; i++) {
                   Pet pet = new Pet("", "");
                   pet.setEmployee_id(employee.getId());
                   petDAO.addPet(pet);
               }
               for (Pet pet : pets) {
                   for (Pet pt : petslist) {
                       boolean Flag = false;
                       if (pt.getEmployee_id() == employee.getId()) {
                           for (Pet pet1 : pets) {
                               if (pt.getName().equals(pet1.getName())
                                       && pt.getVid().equals(pet1.getVid())) {
                                   Flag = true;
                               }
                           }
                           if (Flag == false) {
                               pet.setId(pt.getId());
                               petDAO.updatePet(pet);
                           }
                       }
                       if (Flag == false) {
                           break;
                       }
                   }
               }
           }
       }catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
