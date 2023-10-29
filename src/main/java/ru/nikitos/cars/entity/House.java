package ru.nikitos.cars.entity;

public class House {
  private int id;
  private String adress;
  private int flour;

  private int flat;

    public House(String adress, int flour, int flat) {
        this.adress = adress;
        this.flour = flour;
        this.flat = flat;
    }

    public House(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getFlour() {
        return flour;
    }

    public void setFlour(int flour) {
        this.flour = flour;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }
}
