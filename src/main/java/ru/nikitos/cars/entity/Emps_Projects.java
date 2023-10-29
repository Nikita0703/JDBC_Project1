package ru.nikitos.cars.entity;

public class Emps_Projects {

    int id;

    int employee_id;

    int projects_id;

    public int getId() {
        return id;
    }

    public Emps_Projects(int employee_id, int projects_id) {
        this.employee_id = employee_id;
        this.projects_id = projects_id;
    }

    public Emps_Projects(){};

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getProjects_id() {
        return projects_id;
    }

    public void setProjects_id(int projects_id) {
        this.projects_id = projects_id;
    }
}
