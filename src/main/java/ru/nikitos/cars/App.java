package ru.nikitos.cars;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.nikitos.cars.configuration.AppConfig;
import ru.nikitos.cars.entity.*;
import ru.nikitos.cars.service.EmployeeService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class App
{

    public static void main( String[] args )  {


        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            EmployeeService employeeService = context.getBean("employeeService",EmployeeService.class);

            final String POSSIBLE_ANSWER_ADD = "добавить";
            final String POSSIBLE_ANSWER_SHOW = "вывести всех";
            final String POSSIBLE_ANSWER_DELETE = "удалить";
            final String POSSIBLE_ANSWER_UPDATE = "обновить";

            Scanner scanner = new Scanner(System.in);

            int id;
            String employeeName;
            String employeeSurname;
            int employeeSalary;
            String employeeDepartment;

            String carModel;
            int carMade;

            String houseAdress;
            int houseFlour;
            int houseFlat;

            String petVid;
            String petName;

            String projectTitle;
            int projectYear;

            int numberOfPets;
            int numberOfProjects;

            Employee employee;
            Car car;
            House house;
            List<Pet> pets = new ArrayList<>();
            List<Project> projects = new ArrayList<>();

            String answerToDoing;

            while (true) {
                System.out.print("Что будем делать (добавить,вывести всех,удалить,обновить): ");
                answerToDoing = scanner.nextLine();
                switch (answerToDoing) {
                    case POSSIBLE_ANSWER_ADD:
                        System.out.print("Введите имя: ");
                        employeeName = scanner.nextLine();
                        System.out.print("Введите фамилию: ");
                        employeeSurname = scanner.nextLine();
                        System.out.print("Введите зп: ");
                        employeeSalary = scanner.nextInt();
                        System.out.print("Введите департамент: ");
                        employeeDepartment = scanner.nextLine();

                         employee = new Employee(employeeName,employeeSurname,employeeSalary,employeeDepartment);

                        System.out.print("Введите модель машины: ");
                        carModel = scanner.nextLine();
                        System.out.print("Введите год машины: ");
                        carMade = scanner.nextInt();

                         car = new Car(carModel,carMade);

                        System.out.print("Введите адресс: ");
                        houseAdress = scanner.nextLine();
                        System.out.print("Введите этаж: ");
                        houseFlour = scanner.nextInt();
                        System.out.print("Введите квартиру: ");
                        houseFlat = scanner.nextInt();

                        house = new House(houseAdress,houseFlour,houseFlat);

                        System.out.print("Введите количество питомцев: ");
                        numberOfPets = scanner.nextInt();

                        for (int i = 0;i<numberOfPets;i++){
                            System.out.print("Введите вид: ");
                            petVid = scanner.nextLine();
                            System.out.print("Введите имя: ");
                            petName = scanner.nextLine();
                            Pet pet = new Pet(petVid,petName);
                            pets.add(pet);
                        }

                        System.out.print("Введите количество проектов: ");
                        numberOfProjects = scanner.nextInt();

                        for (int i = 0;i<numberOfProjects;i++){
                            System.out.print("Введите назвагие: ");
                            projectTitle = scanner.nextLine();
                            System.out.print("Введите год: ");
                            projectYear = scanner.nextInt();
                            Project project = new Project(projectTitle,projectYear);
                            projects.add(project);
                        }

                        employeeService.addEmployee(employee,car,house,pets,projects);

                        break;

                    case POSSIBLE_ANSWER_SHOW:
                        System.out.println("Список сотрудников");
                        employeeService.printAllEmployees();
                        break;

                    case POSSIBLE_ANSWER_DELETE:
                        System.out.println("Введите id сотрудника,которого желаете удалить");
                        id  = scanner.nextInt();
                        employeeService.deleteEmployee(id);
                        break;

                    case POSSIBLE_ANSWER_UPDATE:
                        System.out.println("Введите id сотрудника,которого желаете обновить");
                        id  = scanner.nextInt();
                        System.out.print("Введите имя: ");
                        employeeName = scanner.nextLine();
                        System.out.print("Введите фамилию: ");
                        employeeSurname = scanner.nextLine();
                        System.out.print("Введите фамилию: ");
                        employeeSalary = scanner.nextInt();
                        System.out.print("Введите департамент: ");
                        employeeDepartment = scanner.nextLine();

                        employee = new Employee(employeeName,employeeSurname,employeeSalary,employeeDepartment);

                        System.out.print("Введите модель машины: ");
                        carModel = scanner.nextLine();
                        System.out.print("Введите год машины: ");
                        carMade = scanner.nextInt();

                        car = new Car(carModel,carMade);

                        System.out.print("Введите адресс: ");
                        houseAdress = scanner.nextLine();
                        System.out.print("Введите этаж: ");
                        houseFlour = scanner.nextInt();
                        System.out.print("Введите квартиру: ");
                        houseFlat = scanner.nextInt();

                        house = new House(houseAdress,houseFlour,houseFlat);

                        System.out.print("Введите количество питомцев: ");
                        numberOfPets = scanner.nextInt();

                        for (int i = 0;i<numberOfPets;i++){
                            System.out.print("Введите вид: ");
                            petVid = scanner.nextLine();
                            System.out.print("Введите имя: ");
                            petName = scanner.nextLine();
                            Pet pet = new Pet(petVid,petName);
                            pets.add(pet);
                        }

                        System.out.print("Введите количество проектов: ");
                        numberOfProjects = scanner.nextInt();

                        for (int i = 0;i<numberOfProjects;i++){
                            System.out.print("Введите назвагие: ");
                            projectTitle = scanner.nextLine();
                            System.out.print("Введите год: ");
                            projectYear = scanner.nextInt();
                            Project project = new Project(projectTitle,projectYear);
                            projects.add(project);
                        }

                        employeeService.updateEmployee(employee,car,house,pets,projects,id);
                        break;



                        default:
                        System.out.println("Я вас не понял попробуйте ещё раз");
                }
            }


        } catch (SQLException e) {
                    e.printStackTrace();
            }

        }



   }

