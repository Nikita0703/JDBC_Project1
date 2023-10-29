package ru.nikitos.cars.entity;

public class Project {
    int id;
    String title;

    int year;

    public Project(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Project(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
