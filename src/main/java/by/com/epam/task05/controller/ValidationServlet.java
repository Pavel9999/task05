package by.com.epam.task05.controller;

import by.com.epam.task05.dao.RentcarDAO;
import by.com.epam.task05.entity.Car;
import by.com.epam.task05.entity.User;
import by.com.epam.task05.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = {"/validation"})
public class ValidationServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession(true).setAttribute("local",
                req.getParameter("local"));

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (RentcarDAO.CheckUser(email, password))
            AuthorizeUser(req, resp);
        else ErrorPage(req, resp);
    }


    void AuthorizeUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        getServletContext().setAttribute("email", email);
        getServletContext().setAttribute("password", password);

        User newUser = RentcarDAO.GetUser(email, password);
        List<Car> cars = new LinkedList<>();


        if (newUser.getRole() == UserRole.CLIENT)
        {
            req.setAttribute("acc_type", "client");
            req.setAttribute("req_type", "all");
            cars = RentcarDAO.SelectAllCars();
            req.setAttribute("cars", cars);

            req.setAttribute("first_name", newUser.getFirst_name());
            req.setAttribute("second_name", newUser.getSecond_name());
            req.setAttribute("last_name", newUser.getLast_name());

            req.getRequestDispatcher("WEB-INF/pages/client_cars.jsp").forward(req, resp);
        }
        if (newUser.getRole() == UserRole.MANAGER)
        {
            req.setAttribute("acc_type", "manager");
            req.setAttribute("req_type", "rented");
            cars = RentcarDAO.SelectManagerCars(newUser.getId());
            req.setAttribute("cars", cars);

            req.setAttribute("first_name", newUser.getFirst_name());
            req.setAttribute("second_name", newUser.getSecond_name());
            req.setAttribute("last_name", newUser.getLast_name());

            req.getRequestDispatcher("WEB-INF/pages/manager_cars.jsp").forward(req, resp);
        }
        if (newUser.getRole() == UserRole.ADMIN)
        {
            req.setAttribute("acc_type", "ADMIN");
            req.setAttribute("req_type", "all");
            cars = RentcarDAO.SelectAllCars();
            req.setAttribute("cars", cars);

            req.setAttribute("first_name", newUser.getFirst_name());
            req.setAttribute("second_name", newUser.getSecond_name());
            req.setAttribute("last_name", newUser.getLast_name());

            req.getRequestDispatcher("WEB-INF/pages/client_cars.jsp").forward(req, resp);
        }
    }

    void ErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("message_text", "Wrong login or password!");
        req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
    }
}
