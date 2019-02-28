package by.com.epam.task05.entity;

import by.com.epam.task05.service.RentcarDB;

public class Contract {
    int id;
    float all_price;
    int id_manager;
    int id_client;
    String manager;
    String client;
    int id_car;
    String state;

    String date_start;
    String date_finish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAll_price() {
        return all_price;
    }

    public void setAll_price(float all_price) {
        this.all_price = all_price;
    }

    public int getId_manager() {
        return id_manager;
    }

    public void setId_manager(int id_manager) {
        this.id_manager = id_manager;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_finish() {
        return date_finish;
    }

    public void setDate_finish(String date_finish) {
        this.date_finish = date_finish;
    }

    public void Init()
    {
        manager = "";
        client = "";

        if (id_manager != 0)
        {
            manager = RentcarDB.selectUser(id_manager).getFull_name();
        }
        if (id_client != 0)
        {
            client = RentcarDB.selectUser(id_client).getFull_name();
        }

    }
}
