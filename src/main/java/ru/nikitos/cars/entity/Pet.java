package ru.nikitos.cars.entity;

public class Pet {
    private int id;

    private String vid;

    private String name;

    private int employee_id;

    public Pet(String vid, String name) {
        this.vid = vid;
        this.name = name;
    }

    public Pet(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
