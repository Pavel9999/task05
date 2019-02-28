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

@WebServlet(urlPatterns = {"/update_rent_manager"})
public class UpdateRentManagerServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession(true).setAttribute("local",
                req.getParameter("local"));

        String acc_type = req.getParameter("acc_type");
        String req_type = req.getParameter("req_type");
        int id_car = Integer.valueOf(req.getParameter("car_id"));
        String email = (String)getServletContext().getAttribute("email");
        String password = (String)getServletContext().getAttribute("password");
        User newUser = RentcarDAO.GetUser(email, password);

        if (!acc_type.equals("manager")) return;

        switch (req_type)
        {
            case "confirm":{
                RentcarDAO.rentConfirm(id_car, newUser.getId());
            }break;
            case "close":{
                RentcarDAO.rentFinish(id_car);
            }break;
            case "cancel":{
                RentcarDAO.rentCancel(id_car);
            }break;
            default: break;
        }

        req.setAttribute("acc_type", "manager");
        req.setAttribute("req_type", "rented");
        List<Car> cars = RentcarDB.selectManagerCars(newUser.getId());
        req.setAttribute("cars", cars);

        req.setAttribute("first_name", newUser.getFirst_name());
        req.setAttribute("second_name", newUser.getSecond_name());
        req.setAttribute("last_name", newUser.getLast_name());

        req.getRequestDispatcher("WEB-INF/pages/manager_cars.jsp").forward(req, resp);
    }
}
