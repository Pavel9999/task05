package by.com.epam.task05.controller;

import by.com.epam.task05.dao.RentcarDAO;
import by.com.epam.task05.service.RentcarDB2;
import by.com.epam.task05.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



//
@WebServlet(urlPatterns = {"/finish_singup"})
public class FinishSingupServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession(true).setAttribute("local",
                req.getParameter("local"));


        String first_name = req.getParameter("first_name");
        String second_name = req.getParameter("second_name");
        String last_name = req.getParameter("last_name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (RentcarDAO.CheckEmail(email))
        {
            User newClient = new User();
            newClient.setFirst_name(first_name);
            newClient.setSecond_name(second_name);
            newClient.setLast_name(last_name);
            newClient.setEmail(email);
            newClient.setEmail(password);

            RentcarDB2.insertUser(newClient);

            req.setAttribute("message_text", "Регистрация завершена");

            req.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(req, resp);
        }
        else
        {
            req.setAttribute("message_text", "Email уже занят");


            req.setAttribute("first_name_fill", first_name);
            req.setAttribute("second_name_fill", second_name);
            req.setAttribute("last_name_fill", last_name);
            req.setAttribute("email_fill", email);
            req.setAttribute("password_fill", password);

            req.getRequestDispatcher("WEB-INF/pages/singup.jsp").forward(req, resp);
        }



    }
}
