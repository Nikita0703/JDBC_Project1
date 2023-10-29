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

            final String POSSIBLE_ANSWER1 = "добавить";
            final String POSSIBLE_ANSWER2 = "вывести всех";
            final String POSSIBLE_ANSWER3 = "удалить";
            final String POSSIBLE_ANSWER4 = "обновить";

            Scanner scanner = new Scanner(System.in);

            int id;
            String employeeName;
            String employeeSurname;
            int employeeSalary;
            String employeeDepartment;

            String CarModel;
            int CarMade;

            String HouseAdress;
            int HouseFlour;
            int HouseFlat;

            String PetVid;
            String PetName;

            String ProjectTitle;
            int ProjectYear;

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
                    case POSSIBLE_ANSWER1:
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
                        CarModel = scanner.nextLine();
                        System.out.print("Введите год машины: ");
                        CarMade = scanner.nextInt();

                         car = new Car(CarModel,CarMade);

                        System.out.print("Введите адресс: ");
                        HouseAdress = scanner.nextLine();
                        System.out.print("Введите этаж: ");
                        HouseFlour = scanner.nextInt();
                        System.out.print("Введите квартиру: ");
                        HouseFlat = scanner.nextInt();

                        house = new House(HouseAdress,HouseFlour,HouseFlat);

                        System.out.print("Введите количество питомцев: ");
                        numberOfPets = scanner.nextInt();

                        for (int i = 0;i<numberOfPets;i++){
                            System.out.print("Введите вид: ");
                            PetVid = scanner.nextLine();
                            System.out.print("Введите имя: ");
                            PetName = scanner.nextLine();
                            Pet pet = new Pet(PetVid,PetName);
                            pets.add(pet);
                        }

                        System.out.print("Введите количество проектов: ");
                        numberOfProjects = scanner.nextInt();

                        for (int i = 0;i<numberOfProjects;i++){
                            System.out.print("Введите назвагие: ");
                            ProjectTitle = scanner.nextLine();
                            System.out.print("Введите год: ");
                            ProjectYear = scanner.nextInt();
                            Project project = new Project(ProjectTitle,ProjectYear);
                            projects.add(project);
                        }

                        employeeService.addEmployee(employee,car,house,pets,projects);

                        break;

                    case POSSIBLE_ANSWER2:
                        System.out.println("Список сотрудников");
                        employeeService.printAllEmployees();
                        break;

                    case POSSIBLE_ANSWER3:
                        System.out.println("Введите id сотрудника,которого желаете удалить");
                        id  = scanner.nextInt();
                        employeeService.deleteEmployee(id);
                        break;

                    case POSSIBLE_ANSWER4:
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
                        CarModel = scanner.nextLine();
                        System.out.print("Введите год машины: ");
                        CarMade = scanner.nextInt();

                        car = new Car(CarModel,CarMade);

                        System.out.print("Введите адресс: ");
                        HouseAdress = scanner.nextLine();
                        System.out.print("Введите этаж: ");
                        HouseFlour = scanner.nextInt();
                        System.out.print("Введите квартиру: ");
                        HouseFlat = scanner.nextInt();

                        house = new House(HouseAdress,HouseFlour,HouseFlat);

                        System.out.print("Введите количество питомцев: ");
                        numberOfPets = scanner.nextInt();

                        for (int i = 0;i<numberOfPets;i++){
                            System.out.print("Введите вид: ");
                            PetVid = scanner.nextLine();
                            System.out.print("Введите имя: ");
                            PetName = scanner.nextLine();
                            Pet pet = new Pet(PetVid,PetName);
                            pets.add(pet);
                        }

                        System.out.print("Введите количество проектов: ");
                        numberOfProjects = scanner.nextInt();

                        for (int i = 0;i<numberOfProjects;i++){
                            System.out.print("Введите назвагие: ");
                            ProjectTitle = scanner.nextLine();
                            System.out.print("Введите год: ");
                            ProjectYear = scanner.nextInt();
                            Project project = new Project(ProjectTitle,ProjectYear);
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

