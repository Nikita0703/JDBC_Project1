package ru.nikitos.cars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikitos.cars.dao.*;
import ru.nikitos.cars.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private Emps_ProjectsDAO empsProjectsDAO;


    public void addEmployee(Employee employee, Car car, House house, List<Pet> pets, List<Project> projects) throws SQLException {

        carDAO.addCar(car);
        List<Car> cars = carDAO.getAllCars();
        int idCar = 0;
        for (Car caro : cars) {
            if (car.getModel().equals(caro.getModel()) && car.getYear() == caro.getYear()) {
                idCar = caro.getId();
            }
        }
        houseDAO.addHouse(house);
        List<House> houses = houseDAO.getAllHousess();
        int idHouse = 0;
        for (House h : houses) {
            if (house.getAdress().equals(h.getAdress()) &&
                    house.getFlour()== h.getFlour() &&
                      house.getFlat()==h.getFlat()
            ) {
                idHouse = h.getId();
            }
        }
        employee.setCarId(idCar);
        employee.setHouse_id(idHouse);
        employeeDAO.addEmployee(employee);

        List<Employee> employees = employeeDAO.getAllEmployees();
        int idEmpl = 0;

        for(Employee emp :employees){
            if(emp.getName().equals(employee.getName())&&
                    emp.getSurname().equals(employee.getSurname())&&
                       emp.getDepartment().equals(employee.getDepartment())&&
                          emp.getSalary()==employee.getSalary()&&
                             emp.getCar_id()==employee.getCar_id()&&
                                emp.getHouse_id()==employee.getHouse_id()
            ){
               idEmpl = emp.getId();
            }
        }

        for (Pet pet :pets){
            pet.setEmployee_id(idEmpl);
            petDAO.addPet(pet);
        }

        for (Project project:projects){
            projectDAO.addProject(project);
        }

        List<Project> projectList = projectDAO.getAllProjects();
        for (Project project:projectList){
            for (Project pr :projects){
                if (pr.getTitle().equals(project.getTitle())
                     && pr.getYear()==project.getYear()){
                    Emps_Projects empsProjects = new Emps_Projects();
                    empsProjects.setEmployee_id(idEmpl);
                    empsProjects.setProjects_id(project.getId());
                    empsProjectsDAO.addEmpProjectIds(empsProjects);
                }
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

            List<Project> Projects = new ArrayList<>();
            List<Emps_Projects> empsProjects = empsProjectsDAO.getAllEmpProjectIds();
            for (Emps_Projects emp_project :empsProjects){
                if(emp_project.getEmployee_id()==e.getId()){
                    int idProject = emp_project.getProjects_id();
                    Project project = projectDAO.getProjectById(idProject);
                    Projects.add(project);
                }
            }

            System.out.print("Projects [");
            for (Project project:Projects){
                System.out.print(" {title - "+ project.getTitle()+ " year - "+project.getYear() + " }");
            }
            System.out.println(" ]");
        }
    }

    public void deleteEmployee(int id) throws SQLException {
        Employee employee = employeeDAO.getEmployee(id);
        int carId = employee.getCarId();
        int houseId = employee.getHouse_id();

        List<Emps_Projects> empsProjects = empsProjectsDAO.getAllEmpProjectIds();
        for (Emps_Projects empPr :empsProjects){
            if (empPr.getEmployee_id() == id ){
                int idProject = empPr.getProjects_id();
                empsProjectsDAO.deleteEmpProjectIds(empPr.getId());
                projectDAO.deleteProject(idProject);
            }
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
        employee.setId(id);
        int carId = employee.getCarId();
        int houseId = employee.getHouse_id();
        employeeDAO.updateEmployee(employee,id);
        carDAO.updateCar(car);
        houseDAO.updateHouse(house);

        int newLength = pets.size();
        int priviousLength = 0;

        List<Pet> petslist = petDAO.getAllPets();
        for (Pet pt:petslist){
            if (pt.getEmployee_id()==employee.getId()){
                priviousLength+=1;
            }
        }

       if (priviousLength>newLength){
           int difference = priviousLength - newLength;
           for (int i = 0;i<difference;i++){
               for (Pet pt:petslist){
                   if (pt.getEmployee_id()==employee.getId()){
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

       if(priviousLength<newLength){
           int diference = newLength-priviousLength;
           for(int i = 0;i<diference;i++){
               Pet pet = new Pet("","");
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

       List<Project> projectslist = projectDAO.getAllProjects();
       int newLengthProject = projects.size();
       int priviousLengthProject = 0;
       List<Emps_Projects> empsProjects = empsProjectsDAO.getAllEmpProjectIds();

       for (Emps_Projects empPr: empsProjects){
           if (empPr.getEmployee_id()==employee.getId()){
               priviousLengthProject+=1;
           }
       }

       if(priviousLengthProject>newLengthProject){
           int difference = priviousLength - newLengthProject;
           for (int i = 0;i<difference;i++){
               for (Emps_Projects empPr: empsProjects){
                   if (empPr.getEmployee_id()==employee.getId()){
                       int projectId = empPr.getProjects_id();
                       empsProjectsDAO.deleteEmpProjectIds(empPr.getId());
                       projectDAO.deleteProject(projectId);
                       break;
                   }
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
                   Emps_Projects employeeProjects = new Emps_Projects(employee.getId(),projectId+i);
                    empsProjectsDAO.addEmpProjectIds(employeeProjects);
                }

        }

        List<Project> lastprojectlist= projectDAO.getAllProjects();
        List<Emps_Projects> empsProjects1 = empsProjectsDAO.getAllEmpProjectIds();
        for (Project project : projects) {
            for (Emps_Projects pr: empsProjects1) {
                boolean Flag = false;
                if (pr.getEmployee_id()== employee.getId()) {
                    for (Project project1 : projects) {
                        Project currentProject = projectDAO.getProjectById(pr.getProjects_id());
                        if (project1.getTitle().equals(currentProject.getTitle())
                                && project1.getYear()==(currentProject.getYear())) {
                            Flag = true;
                        }
                    }
                    if (Flag == false) {
                        project.setId(pr.getProjects_id());
                        projectDAO.updateProject(project);
                    }
                }
                if (Flag == false) {
                    break;
                }
            }
        }

    }
}
