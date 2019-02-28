package by.com.epam.task05.controller;

import by.com.epam.task05.dao.RentcarDAO;
import by.com.epam.task05.entity.Car;
import by.com.epam.task05.entity.User;
import by.com.epam.task05.service.RentcarDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



//Формирует таблицу в зависимости от типа юзера и типа запроса
@WebServlet(urlPatterns = {"/cars"})
public class CarsServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession(true).setAttribute("local",
                req.getParameter("local"));

        String acc_type = req.getParameter("acc_type");
        switch (acc_type)
        {
            case "guest":{
                getGuestRequest(req, resp);
            }break;
            case "client":{
                getClientRequest(req, resp);
            }break;
            case "manager":{
                getManagerRequest(req, resp);
            }break;
            default: break;
        }
    }


    //обработка запроса гостья
    void getGuestRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String req_type = req.getParameter("req_type");

        switch (req_type) {
            case "all":{
                getGuestAllRequest(req, resp);
            }break;
            case "free":{
                getGuestFreeRequest(req, resp);
            }break;
            default: break;
        }
    }
//обработка запроса пользователя
    void getClientRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String req_type = req.getParameter("req_type");

        switch (req_type) {
            case "all":{
                getClientAllRequest(req, resp);
            }break;
            case "free":{
                getClientFreeRequest(req, resp);
            }break;
            case "rented":{
                getClientRentedRequest(req, resp);
            }break;
            default: break;
        }
    }
//обработка запроса менеджера

    void getManagerRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String req_type = req.getParameter("req_type");

        switch (req_type) {
            case "unconfirmed":{
                getManagerUnconfirmedRequest(req, resp);
            }break;
            case "rented":{
                getManagerRentedRequest(req, resp);
            }break;
            default: break;
        }
    }
//роль гостья выводит все машины
    void getGuestAllRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> cars = RentcarDAO.SelectAllCars();

        req.setAttribute("acc_type", "guest");
        req.setAttribute("req_type", "all");
        req.setAttribute("cars", cars);

        req.getRequestDispatcher("WEB-INF/pages/guest_cars.jsp").forward(req, resp);
    }
//роль гость выводит свободные машины
    void getGuestFreeRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> cars = RentcarDAO.SelectFreeCars();

        req.setAttribute("acc_type", "guest");
        req.setAttribute("req_type", "free");
        req.setAttribute("cars", cars);

        req.getRequestDispatcher("WEB-INF/pages/guest_cars.jsp").forward(req, resp);
    }


//добавляет атрибуты ФИО
    HttpServletRequest packClientData(HttpServletRequest req)
    {
        String email = (String)getServletContext().getAttribute("email");
        String password = (String)getServletContext().getAttribute("password");
        User newClient = RentcarDB.selectUser(email, password);


        req.setAttribute("first_name", newClient.getFirst_name());
        req.setAttribute("second_name", newClient.getSecond_name());
        req.setAttribute("last_name", newClient.getLast_name());

        return req;
    }
//тип пользователя регистр пользователь все машины
    void getClientAllRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> cars = RentcarDAO.SelectAllCars();

        req.setAttribute("acc_type", "client");
        req.setAttribute("req_type", "all");
        req.setAttribute("cars", cars);

        req = packClientData(req);

        req.getRequestDispatcher("WEB-INF/pages/client_cars.jsp").forward(req, resp);
    }
//тип пользователь регист свободные машины
    void getClientFreeRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> cars = RentcarDAO.SelectFreeCars();

        req.setAttribute("acc_type", "client");
        req.setAttribute("req_type", "free");
        req.setAttribute("cars", cars);

        req = packClientData(req);

        req.getRequestDispatcher("WEB-INF/pages/client_cars.jsp").forward(req, resp);
    }
//тип регистрированный пользователь машины кот. будут арендованы
    void getClientRentedRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = (String)getServletContext().getAttribute("email");
        List<Car> cars = RentcarDAO.SelectClientCars(email);

        req.setAttribute("acc_type", "client");
        req.setAttribute("req_type", "rented");
        req.setAttribute("cars", cars);

        req = packClientData(req);

        req.getRequestDispatcher("WEB-INF/pages/client_cars_in_rent.jsp").forward(req, resp);
    }

//тип менеджер все неподтв контракты
    void getManagerUnconfirmedRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Car> cars = RentcarDAO.SelectUnconfirmedCars();

        req.setAttribute("acc_type", "manager");
        req.setAttribute("req_type", "unconfirmed");
        req.setAttribute("cars", cars);

        req = packClientData(req);

        req.getRequestDispatcher("WEB-INF/pages/manager_cars.jsp").forward(req, resp);
    }
//тип менеджер все активные контракты  его
    void getManagerRentedRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = (String)getServletContext().getAttribute("email");
        String password = (String)getServletContext().getAttribute("password");
        User newUser = RentcarDB.selectUser(email, password);

        List<Car> cars = RentcarDAO.SelectManagerCars(newUser.getId());

        req.setAttribute("acc_type", "manager");
        req.setAttribute("req_type", "rented");
        req.setAttribute("cars", cars);

        req = packClientData(req);

        req.getRequestDispatcher("WEB-INF/pages/manager_cars.jsp").forward(req, resp);
    }



}
