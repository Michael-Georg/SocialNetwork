package servlets;

import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static servlets.ServletConst.*;

@Log
@WebServlet("/Friends")
public class Friends extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Person person = (Person) session.getAttribute(PERSON);
        req.setAttribute(SEARCH_RESULT, friendsDao.getAll(person.getId()));
        session.setAttribute(URL, "/Friends");
        req.getRequestDispatcher("/user/results.jsp").forward(req, resp);
    }
}
