package by.com.epam.task05.service;

import by.com.epam.task05.entity.Car;
import by.com.epam.task05.entity.Contract;
import by.com.epam.task05.entity.User;
import by.com.epam.task05.entity.UserRole;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RentcarDB
{

    final static String url = "jdbc:mysql://localhost:3306/rentcar?serverTimezone=Europe/Moscow&useSSL=false";
    final static String username = "root";
    final static String password = "1234";

    public static int insertUser(User client)
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO client (`id_client`, `firstname`, `surname`, `lastname`, `e-mail`) Values (?, ?, ?, ? ,?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, getNextUserID());
                    preparedStatement.setString(2, client.getFirst_name());
                    preparedStatement.setString(3, client.getSecond_name());
                    preparedStatement.setString(4, client.getLast_name());
                    preparedStatement.setString(5, client.getEmail());
                    preparedStatement.setString(6, client.getPassword());

                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
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

                    preparedStatement.setInt(1, getNextContractID());
                    preparedStatement.setFloat(2, contract.getAll_price());
                    preparedStatement.setInt(3, contract.getId_manager());
                    preparedStatement.setInt(4, contract.getId_client());
                    preparedStatement.setInt(5, contract.getId_car());
                    preparedStatement.setString(6, contract.getState());
                    preparedStatement.setDate(7, new java.sql.Date(System.currentTimeMillis()));
                    preparedStatement.setDate(8, new java.sql.Date(System.currentTimeMillis()));

                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return 0;
    }

    public static List<User> selectUsers()
    {
        List<User> clients = new LinkedList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                while(resultSet.next()){

                    int id = resultSet.getInt(1);
                    String first_name = resultSet.getString(2);
                    String second_name = resultSet.getString(3);
                    String last_name = resultSet.getString(4);
                    String mail = resultSet.getString(5);
                    String password = resultSet.getString(6);

                    User newClient = new User();
                    newClient.setId(id);
                    newClient.setFirst_name(first_name);
                    newClient.setSecond_name(second_name);
                    newClient.setLast_name(last_name);
                    newClient.setEmail(mail);
                    newClient.setPassword(password);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return clients;
    }

    public static User selectUser(int user_id)
    {
        User newClient = null;
        String idString = "`id_user`='"+user_id+"'";
        String sqlRequest = "SELECT * FROM user WHERE "+idString;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){

                    int id = resultSet.getInt(1);
                    int role = resultSet.getInt(2);
                    String first_name = resultSet.getString(3);
                    String second_name = resultSet.getString(4);
                    String last_name = resultSet.getString(5);
                    String _mail = resultSet.getString(6);
                    String _password = resultSet.getString(7);

                    newClient = new User();
                    newClient.setId(id);
                    newClient.setRole(UserRole.fromInteger(role));
                    newClient.setFirst_name(first_name);
                    newClient.setSecond_name(second_name);
                    newClient.setLast_name(last_name);
                    newClient.setEmail(_mail);
                    newClient.setPassword(_password);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return newClient;
    }


    public static User selectUser(String _mail, String _password)
    {
        User newClient = null;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String mailString = "`e-mail`='"+_mail+"'";
                String passwordString = "`password`='"+_password+"'";
                String sqlRequest = "SELECT * FROM user WHERE "+mailString+" AND " + passwordString;
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){

                    int id = resultSet.getInt(1);
                    int role = resultSet.getInt(2);
                    String first_name = resultSet.getString(3);
                    String second_name = resultSet.getString(4);
                    String last_name = resultSet.getString(5);


                    newClient = new User();
                    newClient.setId(id);
                    newClient.setRole(UserRole.fromInteger(role));
                    newClient.setFirst_name(first_name);
                    newClient.setSecond_name(second_name);
                    newClient.setLast_name(last_name);
                    newClient.setEmail(_mail);
                    newClient.setPassword(_password);

                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return newClient;
    }

    public static Contract selectContract(int contract_id)
    {
        Contract contract = new Contract();
        String sqlRequest = "SELECT * FROM contract WHERE `id_contract`='"+String.valueOf(contract_id)+"'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if(resultSet.next()){
                    contract = contractFromResultset(resultSet);
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
        Car car = new Car();
        String sqlRequest = "SELECT * FROM car WHERE `id_car`='" + String.valueOf(car_id) + "'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){

                    car = carFromResultset(resultSet);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return car;
    }

    public static List<Car> selectAllCars()
    {
        List<Car> cars = new LinkedList<>();
        String sqlRequest = "SELECT * FROM car";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){

                    Car newCar = carFromResultset(resultSet);

                    cars.add(newCar);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return cars;

    }

    public static List<Car> selectFreeCars()
    {
        List<Car> cars = new LinkedList<>();
        String sqlRequest = "SELECT * FROM car WHERE `contract_id`='0'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){

                    Car newCar = carFromResultset(resultSet);

                    cars.add(newCar);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return cars;
    }

    public static List<Car> selectRentedCars(String mail)
    {
        List<Car> cars = new LinkedList<>();
        String sqlRequest = "SELECT * FROM car WHERE `rental`='" + mail+"'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){

                    Car newCar = carFromResultset(resultSet);

                    cars.add(newCar);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return cars;
    }

    public static List<Contract> selectUnconfirmedContracts()
    {
        List<Contract> contracts = new LinkedList<>();

        String sqlRequest = "SELECT * FROM contract WHERE `contract_state`='unconfirmed'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){
                    Contract newContract = contractFromResultset(resultSet);
                    contracts.add(newContract);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return contracts;
    }


    public static List<Contract> selectManagerContracts(int id)
    {
        List<Contract> contracts = new LinkedList<>();

        String sqlRequest = "SELECT * FROM contract WHERE `id_manager`='" + id+"' AND `contract_state`='confirmed'";

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while(resultSet.next()){
                    Contract newContract = contractFromResultset(resultSet);
                    contracts.add(newContract);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return contracts;
    }

    public static List<Car> selectUnconfirmedCars()
    {
        List<Contract> contracts = selectUnconfirmedContracts();

        List<Car> cars = new LinkedList<>();

        for (int i = 0; i < contracts.size(); i++)
        {
            Car newCar = selectCar(contracts.get(i).getId_car());
            cars.add(newCar);
        }

        return cars;

    }

    public static List<Car> selectManagerCars(int id)
    {
        List<Contract> contracts = selectManagerContracts(id);

        List<Car> cars = new LinkedList<>();

        for (int i = 0; i < contracts.size(); i++)
        {
            if (contracts.get(i).getState().equals("confirmed"))
            {
                Car newCar = selectCar(contracts.get(i).getId_car());
                cars.add(newCar);
            }
        }

        return cars;
    }

    static Car carFromResultset(ResultSet resultSet) throws SQLException {
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

    static Contract contractFromResultset(ResultSet resultSet) throws SQLException {
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

    static int getNextUserID()
    {
        int id = 1;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT * FROM user";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while (resultSet.next()){
                    id++;
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
        int id = 1;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT * FROM car";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while (resultSet.next()){
                    id++;
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
        int id = 1;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT * FROM contract";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                while (resultSet.next()){
                    id++;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return id;
    }

    public static void rentStart(int id_car, int id_client, int days)
    {
        Car car = RentcarDB.selectCar(id_car);
        User user = RentcarDB.selectUser(id_client);
        Contract contract = new Contract();

        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                contract.setId(RentcarDB.getNextContractID());
                contract.setAll_price(car.getPrice() * days);
                contract.setId_manager(0);
                contract.setId_client(id_client);
                contract.setId_car(id_car);
                contract.setState("unconfirmed");
                RentcarDB.insertContract(contract);

                Statement statement = conn.createStatement();
                String rental = "`rental`='" + user.getEmail() + "', ";
                String contract_id = "`contract_id`='" + contract.getId() + "'";
                String sqlRequest = "UPDATE car SET " + rental + contract_id + " WHERE `id_car`='" + id_car + "';";
                statement.executeUpdate(sqlRequest);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void rentCancel(int id_car)
    {
        Car car = RentcarDB.selectCar(id_car);

        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                int id_contract = car.getContract_id();
                Statement statement1 = conn.createStatement();
                String sqlRequest1 = "UPDATE contract SET `contract_state`='canceled' WHERE `id_contract`='" + id_contract + "'";
                statement1.executeUpdate(sqlRequest1);

                Statement statement2 = conn.createStatement();
                String sqlRequest2 = "UPDATE car SET `rental`='free', `contract_id`='0' WHERE `id_car`='" + id_car + "'";
                statement2.executeUpdate(sqlRequest2);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void rentConfirm(int id_car, int id_manager)
    {
        Car car = RentcarDB.selectCar(id_car);

        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                int id_contract = car.getContract_id();
                Statement statement = conn.createStatement();
                String idManager = "`id_manager`='" + id_manager + "'";
                String sqlRequest = "UPDATE contract SET `contract_state`='confirmed', " + idManager + " WHERE `id_contract`='" + id_contract + "'";
                statement.executeUpdate(sqlRequest);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void rentFinish(int id_car)
    {
        Car car = RentcarDB.selectCar(id_car);

        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                int id_contract = car.getContract_id();
                Statement statement1 = conn.createStatement();
                String sqlRequest1 = "UPDATE contract SET `contract_state`='finished' WHERE `id_contract`='" + id_contract + "'";
                statement1.executeUpdate(sqlRequest1);

                Statement statement2 = conn.createStatement();
                String sqlRequest2 = "UPDATE car SET `rental`='free', `contract_id`='0' WHERE `id_car`='" + id_car + "'";
                statement2.executeUpdate(sqlRequest2);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }


    public static boolean checkEmail(String email)
    {
        boolean result = true;

        try{
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){


                Statement statement = conn.createStatement();
                String sqlRequest = "SELECT * FROM user WHERE `e-mail`='" + email + "';";
                ResultSet resultSet = statement.executeQuery(sqlRequest);
                if (resultSet.next()){
                    result = false;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return result;
    }
}
