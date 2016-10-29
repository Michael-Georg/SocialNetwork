package servlets;

import Dao.PersonDao;
import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Log
@WebServlet("/Login")
public class Login extends HttpServlet {
    private PersonDao personDao;
    private static final String KEY = "person";

    @Override
    public void init() throws ServletException {
        personDao = (PersonDao) getServletContext().getAttribute("personDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
//        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(() -> "Login");
        HttpSession session = req.getSession(true);
        Map<String, String[]> map = req.getParameterMap();

        Optional<Person> person = personDao.isRegistered(map.get("username")[0], map.get("password")[0]);
        if (person.isPresent()) {
            session.setAttribute(KEY, person.get());
            session.setAttribute("lang", "en");
            resp.sendRedirect("/Profile/" + person.get().getId());
        } else {
            req.setAttribute("errorMsg", "TRY AGAIN");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }

    }
}
