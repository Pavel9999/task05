package by.com.epam.task05.dao;

import by.com.epam.task05.entity.Car;
import by.com.epam.task05.entity.Contract;
import by.com.epam.task05.entity.User;
import by.com.epam.task05.service.RentcarDB;
import by.com.epam.task05.service.RentcarDB2;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class RentcarDAO
{
    public static boolean CheckUser(String email, String password)
    {
        List<Pair <String, String>> pairs = new LinkedList<>();
        Pair<String, String> emailPair = new Pair<>("e-mail", email);
        Pair<String, String> passPair = new Pair<>("password", password);
        pairs.add(emailPair);
        pairs.add(passPair);

        List<User> users = RentcarDB2.selectUsers(pairs);

        if (users.size() == 0) return false;
        else return true;
    }

    public static User GetUser(String email, String password)
    {
        List<Pair <String, String>> pairs = new LinkedList<>();
        Pair<String, String> emailPair = new Pair<>("e-mail", email);
        Pair<String, String> passPair = new Pair<>("password", password);
        pairs.add(emailPair);
        pairs.add(passPair);

        List<User> users = RentcarDB2.selectUsers(pairs);

        if (users.size() == 0) return null;
        else return users.get(0);
    }

    public static List<Car> SelectAllCars()
    {
        List<Car> cars = RentcarDB2.selectCars();
        return cars;
    }

    public static List<Car> SelectFreeCars()
    {
        Pair <String, String> pair = new Pair<>("contract_id", "0");

        List<Car> cars = RentcarDB2.selectCars(pair);
        return cars;
    }

    public static List<Car> SelectClientCars(String email)
    {
        Pair <String, String> pair = new Pair<>("rental", email);

        List<Car> cars = RentcarDB2.selectCars(pair);
        return cars;
    }

    public static List<Contract> SelectUnconfirmedContracts()
    {
        Pair <String, String> pair = new Pair<>("contract_state", "unconfirmed");

        List<Contract> contracts = RentcarDB2.selectContracts(pair);
        return contracts;
    }

    public static List<Car> SelectUnconfirmedCars()
    {
        List<Contract> contracts = SelectUnconfirmedContracts();

        List<Car> cars = new LinkedList<>();

        for (int i = 0; i < contracts.size(); i++)
        {
            Car newCar = RentcarDB2.selectCar(contracts.get(i).getId_car());
            cars.add(newCar);
        }

        return cars;
    }



    public static List<Car> SelectManagerCars(int id)
    {
        Pair<String, String> id_contact = new Pair<>("id_manager", String.valueOf(id));
        List<Contract> contracts = RentcarDB2.selectContracts(id_contact);

        List<Car> cars = new LinkedList<>();
        for (int i = 0; i < contracts.size(); i++)
        {
            if (contracts.get(i).getState().equals("confirmed"))
            {
                Car newCar = RentcarDB2.selectCar(contracts.get(i).getId_car());
                cars.add(newCar);
            }
        }
        return cars;
    }

    public static boolean CheckEmail(String email)
    {
        String sqlRequest = "SELECT * FROM user WHERE `e-mail`='" + email + "';";

        Pair<String, String> email_pair = new Pair<>("mail", email);

        List<User> users = RentcarDB2.selectUsers(email_pair);

        if (users.size() > 0)
            return false;
        else
            return true;
    }

    public static void rentStart(int id_car, int id_client, int days)
    {
        Car car = RentcarDB2.selectCar(id_car);
        User user = RentcarDB2.selectUser(id_client);

        Contract contract = new Contract();
        contract.setAll_price(car.getPrice() * days);
        contract.setId_manager(0);
        contract.setId_client(id_client);
        contract.setId_car(id_car);
        contract.setState("unconfirmed");

        int id_contract = RentcarDB2.insertContract(contract);

        List<Pair <String, String>> pairs = new LinkedList<>();
        Pair<String, String> emailPair = new Pair<>("rental", user.getEmail());
        Pair<String, String> passPair = new Pair<>("contract_id", String.valueOf(id_contract));
        pairs.add(emailPair);
        pairs.add(passPair);

        List<Pair <String, String>> params = new LinkedList<>();
        Pair<String, String> paramPair = new Pair<>("id_car", String.valueOf(id_car));
        params.add(paramPair);

        RentcarDB2.UpdateTable("car", pairs, params);

    }

    public static void rentCancel(int id_car) {
        Car car = RentcarDB2.selectCar(id_car);


        int id_contract = car.getContract_id();
        List<Pair <String, String>> contract_values = new LinkedList<>();
        Pair<String, String> cnt_value_pair = new Pair<>("contract_state", "canceled");
        contract_values.add(cnt_value_pair);
        List<Pair <String, String>> contract_params = new LinkedList<>();
        Pair<String, String> cnt_param_pair = new Pair<>("id_contract", String.valueOf(id_contract));
        contract_params.add(cnt_param_pair);
        RentcarDB2.UpdateTable("contract", contract_values, contract_params);

        List<Pair <String, String>> car_values = new LinkedList<>();
        Pair<String, String> car_value_pair1 = new Pair<>("rental", "free");
        Pair<String, String> car_value_pair2 = new Pair<>("contract_id", "0");
        car_values.add(car_value_pair1);
        car_values.add(car_value_pair2);
        List<Pair <String, String>> car_params = new LinkedList<>();
        Pair<String, String> car_param_pair = new Pair<>("id_car", String.valueOf(id_car));
        car_params.add(car_param_pair);
        RentcarDB2.UpdateTable("car", car_values, car_params);
    }

    public static void rentConfirm(int id_car, int id_manager)
    {
        Car car = RentcarDB.selectCar(id_car);
        int id_contract = car.getContract_id();

        List<Pair <String, String>> contract_values = new LinkedList<>();
        Pair<String, String> cnt_value_pair1 = new Pair<>("contract_state", "confirmed");
        Pair<String, String> cnt_value_pair2 = new Pair<>("id_manager", String.valueOf(id_manager));
        contract_values.add(cnt_value_pair1);
        contract_values.add(cnt_value_pair2);
        List<Pair <String, String>> contract_params = new LinkedList<>();
        Pair<String, String> cnt_param_pair = new Pair<>("id_contract", String.valueOf(id_contract));
        contract_params.add(cnt_param_pair);
        RentcarDB2.UpdateTable("contract", contract_values, contract_params);
    }

    public static void rentFinish(int id_car) {
        Car car = RentcarDB2.selectCar(id_car);

        int id_contract = car.getContract_id();
        List<Pair <String, String>> contract_values = new LinkedList<>();
        Pair<String, String> cnt_value_pair = new Pair<>("contract_state", "finished");
        contract_values.add(cnt_value_pair);
        List<Pair <String, String>> contract_params = new LinkedList<>();
        Pair<String, String> cnt_param_pair = new Pair<>("id_contract", String.valueOf(id_contract));
        contract_params.add(cnt_param_pair);
        RentcarDB2.UpdateTable("contract", contract_values, contract_params);

        List<Pair <String, String>> car_values = new LinkedList<>();
        Pair<String, String> car_value_pair1 = new Pair<>("rental", "free");
        Pair<String, String> car_value_pair2 = new Pair<>("contract_id", "0");
        car_values.add(car_value_pair1);
        car_values.add(car_value_pair2);
        List<Pair <String, String>> car_params = new LinkedList<>();
        Pair<String, String> car_param_pair = new Pair<>("id_car", String.valueOf(id_car));
        car_params.add(car_param_pair);
        RentcarDB2.UpdateTable("car", car_values, car_params);
    }

}
