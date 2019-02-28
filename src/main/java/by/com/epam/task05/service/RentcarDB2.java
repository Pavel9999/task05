package by.com.epam.task05.service;

import by.com.epam.task05.entity.Car;
import by.com.epam.task05.entity.Contract;
import by.com.epam.task05.entity.User;
import by.com.epam.task05.entity.UserRole;
import javafx.util.Pair;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RentcarDB2 {

    final static String url = "jdbc:mysql://localhost:3306/rentcar?serverTimezone=Europe/Moscow&useSSL=false";
    final static String username = "root";
    final static String password = "1234";

    public static int insertUser(User client) {
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO client (`id_client`, `firstname`, `surname`, `lastname`, `e-mail`) Values (?, ?, ?, ? ,?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    int id_user = getNextUserID();
                    preparedStatement.setInt(1, getNextUserID());
                    preparedStatement.setString(2, client.getFirst_name());
                    preparedStatement.setString(3, client.getSecond_name());
                    preparedStatement.setString(4, client.getLast_name());
                    preparedStatement.setString(5, client.getEmail());
                    preparedStatement.setString(6, client.getPassword());
                    preparedStatement.executeUpdate();
                    return id_user;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return 0;
    }

    public static int insertContract(Contract contract)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO contract (`id_contract`, `all_price`, `id_manager`, `id_client`, `id_car`, `contract_state`, `date_start`, `date_finish`) Values (?, ?, ?, ? ,?, ?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){

                    int id_contract = getNextContractID();
                    preparedStatement.setInt(1, getNextContractID());
                    preparedStatement.setFloat(2, contract.getAll_price());
                    preparedStatement.setInt(3, contract.getId_manager());
                    preparedStatement.setInt(4, contract.getId_client());
                    preparedStatement.setInt(5, contract.getId_car());
                    preparedStatement.setString(6, contract.getState());
                    preparedStatement.setDate(7, new java.sql.Date(System.currentTimeMillis()));
                    preparedStatement.setDate(8, new java.sql.Date(System.currentTimeMillis()));

                    preparedStatement.executeUpdate();
                    return  id_contract;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static User selectUser(int user_id)
    {
        User newUser = null;
        Pair<String, String> param = new Pair<>("id_user", String.valueOf(user_id));
        String sqlRequest = formSelectRequest("user", param);

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){
                    newUser = getUser(resultSet);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return newUser;
    }

    public static Contract selectContract(int contract_id)
    {
        Contract contract = null;
        Pair<String, String> param = new Pair<>("id_contract", String.valueOf(contract_id));
        String sqlRequest = formSelectRequest("contract", param);

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){
                    contract = getContract(resultSet);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return contract;
    }

    public static Car selectCar(int car_id)
    {
        Car car = null;
        Pair<String, String> param = new Pair<>("id_car", String.valueOf(car_id));
        String sqlRequest = formSelectRequest("car", param);

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){
                    car = getCar(resultSet);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return car;
    }


    public static List<User> selectUsers()
    {
        String sqlRequest = formSelectRequest("user");
        List<User> users = selectUsers(sqlRequest);

        return users;
    }


    public static List<User> selectUsers(Pair<String, String> values)
    {
        String sqlRequest = formSelectRequest("user", values);
        List<User> users = selectUsers(sqlRequest);

        return users;
    }

    public static List<User> selectUsers(List<Pair<String, String>> values)
    {
        String sqlRequest = formSelectRequest("user", values);

        List<User> users = selectUsers(sqlRequest);

        return users;
    }

    static List<User> selectUsers(String sqlRequest)
    {
        List<User> users = new LinkedList<>();
        try{
            Connection conn = DBConnection.createConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while(resultSet.next()){
                User newUser = getUser(resultSet);
                users.add(newUser);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return users;
    }

    public static List<Contract> selectContracts()
    {

        String sqlRequest = formSelectRequest("contract");
        List<Contract> contracts = selectContracts(sqlRequest);

        return contracts;
    }

    public static List<Contract> selectContracts(Pair<String, String> values)
    {

        String sqlRequest = formSelectRequest("contract", values);
        List<Contract> contracts = selectContracts(sqlRequest);

        return contracts;
    }

    public static List<Contract> selectContracts(List<Pair<String, String>> values)
    {

        String sqlRequest = formSelectRequest("contract", values);
        List<Contract> contracts = selectContracts(sqlRequest);

        return contracts;
    }

    public static List<Contract> selectContracts(String sqlRequest)
    {
        List<Contract> contracts = new LinkedList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){
                    Contract newContract = getContract(resultSet);
                    contracts.add(newContract);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return contracts;
    }

    public static List<Car> selectCars()
    {
        String sqlRequest = formSelectRequest("car");
        List<Car> cars = selectCars(sqlRequest);

        return cars;
    }

    public static List<Car> selectCars(Pair<String, String> values)
    {
        String sqlRequest = formSelectRequest("car", values);
        List<Car> cars = selectCars(sqlRequest);

        return cars;
    }

    public static List<Car> selectCars(List<Pair<String, String>> values)
    {
        String sqlRequest = formSelectRequest("car", values);
        List<Car> cars = selectCars(sqlRequest);

        return cars;
    }

    public static List<Car> selectCars(String sqlRequest)
    {
        List<Car> cars = new LinkedList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){
                    Car newCar = getCar(resultSet);
                    cars.add(newCar);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return cars;
    }

    public static void UpdateTable(String table, Pair<String, String> newValue)
    {
        String sqlRequest = formUpdateRequest(table, newValue);
        UpdateTable(sqlRequest);
    }

    public static void UpdateTable(String table, List<Pair<String, String>> newValues)
    {
        String sqlRequest = formUpdateRequest(table, newValues);
        UpdateTable(sqlRequest);
    }

    public static void UpdateTable(String table, List<Pair<String, String>> newValues, List<Pair<String, String>> params)
    {
        String sqlRequest = formUpdateRequest(table, newValues, params);
        UpdateTable(sqlRequest);
    }

    public static void UpdateTable(String sqlRequest)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                Statement statement = conn.createStatement();

                statement.executeUpdate(sqlRequest);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    static int getNextUserID()
    {
        int id = 0;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT COUNT(*) FROM user";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if (resultSet.next()){
                    id = resultSet.getInt(1) + 1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return id;
    }

    static int getNextCarID()
    {
        int id = 0;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT COUNT(*) FROM car";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if (resultSet.next()){
                    id = resultSet.getInt(1) + 1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return id;
    }

    static int getNextContractID()
    {
        int id = 0;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT COUNT(*) FROM contract";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if (resultSet.next()){
                    id = resultSet.getInt(1) + 1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return id;
    }

    static User getUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        int role = resultSet.getInt(2);
        String first_name = resultSet.getString(3);
        String second_name = resultSet.getString(4);
        String last_name = resultSet.getString(5);
        String _mail = resultSet.getString(6);
        String _password = resultSet.getString(7);


        User user = new User();
        user.setId(id);
        user.setRole(UserRole.fromInteger(role));
        user.setFirst_name(first_name);
        user.setSecond_name(second_name);
        user.setLast_name(last_name);
        user.setEmail(_mail);
        user.setPassword(_password);

        return user;
    }

    static Contract getContract(ResultSet resultSet) throws SQLException {
        Contract contract = new Contract();

        int _id = resultSet.getInt(1);
        float price = resultSet.getFloat(2);
        int managerID = resultSet.getInt(3);
        int clientID = resultSet.getInt(4);
        int id_car =  resultSet.getInt(5);
        String state = resultSet.getString(6);

        contract.setId(_id);
        contract.setAll_price(price);
        contract.setId_manager(managerID);
        contract.setId_client(clientID);
        contract.setId_car(id_car);
        contract.setState(state);
        contract.Init();

        return contract;
    }

    static Car getCar(ResultSet resultSet) throws SQLException {
        Car newCar = new Car();

        int id = resultSet.getInt(1);
        String brand = resultSet.getString(2);
        String model = resultSet.getString(3);
        String year = resultSet.getString(4);
        String mkp = resultSet.getString(5);
        String color = resultSet.getString(6);
        String horsePower = resultSet.getString(7);
        String engineSize = resultSet.getString(8);
        String rental = resultSet.getString(9);
        int rental_id = resultSet.getInt(10);
        String miliage = resultSet.getString(11);
        float price = resultSet.getFloat(12);

        newCar.setId(id);
        newCar.setBrand(brand);
        newCar.setModel(model);
        newCar.setYear(year);
        newCar.setMkp(mkp);
        newCar.setColor(color);
        newCar.setHorsepower(horsePower);
        newCar.setEngine_size(engineSize);
        newCar.setRental(rental);
        newCar.setContract_id(rental_id);
        newCar.setMiliage(miliage);
        newCar.setPrice(price);
        newCar.InitCar();

        return newCar;
    }

    static String formSelectRequest(String table)
    {
        String sqlRequest = "SELECT * FROM " + table;

        return sqlRequest;
    }

    static String formSelectRequest(String table, Pair<String, String> param)
    {
        List<Pair<String, String>> paramsList = new LinkedList<>();
        paramsList.add(param);
        return formSelectRequest(table, paramsList);
    }

    static String formSelectRequest(String table, List<Pair<String, String>> params)
    {
        String sqlRequest = "SELECT * FROM " + table;

        if (params.size() > 0)
        {
            sqlRequest += " WHERE ";

            for (int i = 0; i < params.size(); i++)
            {
                if (i >= 1) sqlRequest+= " AND ";

                sqlRequest+="`" + params.get(i).getKey() + "`=";
                sqlRequest+="'" + params.get(i).getValue() + "'";
            }
        }

        return sqlRequest;
    }

    static String formUpdateRequest(String table, Pair<String, String> newValue)
    {
        List<Pair<String, String>> newValues = new LinkedList<>();
        newValues.add(newValue);
        return formUpdateRequest(table, newValues);
    }

    static String formUpdateRequest(String table, List<Pair<String, String>> newValues)
    {
        String sqlRequest = "UPDATE " + table + " SET ";

        for (int i = 0; i < newValues.size(); i++)
        {
            if (i >= 1) sqlRequest+= ", ";

            sqlRequest+="`" + newValues.get(i).getKey() + "`=";
            sqlRequest+="'" + newValues.get(i).getValue() + "'";
        }

        return sqlRequest;
    }

    static String formUpdateRequest(String table, List<Pair<String, String>> newValues, List<Pair<String, String>> params)
    {
        String sqlRequest = "UPDATE " + table + " SET ";

        for (int i = 0; i < newValues.size(); i++)
        {
            if (i >= 1) sqlRequest+= ", ";

            sqlRequest+="`" + newValues.get(i).getKey() + "`=";
            sqlRequest+="'" + newValues.get(i).getValue() + "'";
        }
        if (params.size() > 0)
        {
            sqlRequest += " WHERE ";

            for (int i = 0; i < params.size(); i++)
            {
                if (i >= 1) sqlRequest+= " AND ";

                sqlRequest+="`" + params.get(i).getKey() + "`=";
                sqlRequest+="'" + params.get(i).getValue() + "'";
            }
        }

        return sqlRequest;
    }
}