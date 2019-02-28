package by.com.epam.task05.entity;

import by.com.epam.task05.service.RentcarDB;

public class Car
{
    int id;
    String brand;
    String model;
    String year;
    String mkp;
    String color;
    String horsepower;
    String engine_size;
    String rental;
    int contract_id;
    String miliage;
    float price;

    String contract_state;
    String full_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMkp() {
        return mkp;
    }

    public void setMkp(String mkp) {
        this.mkp = mkp;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(String horsepower) {
        this.horsepower = horsepower;
    }

    public String getEngine_size() {
        return engine_size;
    }

    public void setEngine_size(String engine_size) {
        this.engine_size = engine_size;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public String getMiliage() {
        return miliage;
    }

    public void setMiliage(String miliage) {
        this.miliage = miliage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getContract_state() {
        return contract_state;
    }


    public String getFull_name() {
        return full_name;
    }

    public void InitCar()
    {
        contract_state = "free";
        if (contract_id != 0)
        {
            contract_state = RentcarDB.selectContract(contract_id).getState();
        }

        full_name = "";
        full_name +=brand + " ";
        full_name +=model + " ";
        full_name +=color;
    }
}
