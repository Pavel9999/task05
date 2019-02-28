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


//Отменяет аренду пользователя пользователь

@WebServlet(urlPatterns = {"/cancel_rent_client"})
public class CancelRentServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession(true).setAttribute("local",
                req.getParameter("local"));

        int id_car = Integer.valueOf(req.getParameter("car_id"));
        RentcarDAO.rentCancel(id_car);


        String email = (String)getServletContext().getAttribute("email");
        String password = (String)getServletContext().getAttribute("password");
        User newClient = RentcarDB.selectUser(email, password);
        List<Car> cars = RentcarDB.selectRentedCars(email);

        req.setAttribute("acc_type", "client");
        req.setAttribute("req_type", "rented");
        req.setAttribute("cars", cars);

        req.setAttribute("first_name", newClient.getFirst_name());
        req.setAttribute("second_name", newClient.getSecond_name());
        req.setAttribute("last_name", newClient.getLast_name());

        req.getRequestDispatcher("WEB-INF/pages/client_cars_in_rent.jsp").forward(req, resp);
    }
}
