package servlets;

import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static servlets.ServletConst.*;


@Log
@WebServlet("/Login")
public class Login extends ServletWrapper {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(() -> "Login doGet");
        req.getSession(true).setAttribute("lang", "ru");
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(() -> "Login doPost");
        HttpSession session = req.getSession();
        Map<String, String[]> map = req.getParameterMap();
        Optional<Person> person = personDao.isRegistered(map.get("username")[0], map.get("password")[0]);
        if (person.isPresent()) {
            session.setAttribute(PERSON, person.get());
            log.info("/Profile/" + person.get().getId());
            resp.sendRedirect("/Profile/" + person.get().getId());
        } else {
            req.setAttribute("error", "err");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }
}
