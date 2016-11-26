package servlets;

import lombok.extern.slf4j.Slf4j;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static servlets.ServletConst.*;


@Slf4j
@WebServlet("/Login")
public class Login extends ServletWrapper {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("awe {} qwe", 123);
        log.debug("debug");
        log.error("error");
        log.warn("error");
        HttpSession session = req.getSession(true);
        Optional<Person> person = Optional.ofNullable((Person) session.getAttribute(PERSON));
        if (person.isPresent())
            resp.sendRedirect("/Profile/" + person.get().getId());
        else {
            session.setAttribute(URL, "/Login");
            if (session.getAttribute(LANG) == null)
                session.setAttribute(LANG, "en");
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.info(() -> "Login doPost");
        log.info("qweqweqwe");
        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
//        if (email != "" && password != "") {
            Optional<Person> person = personDao.getByEmailAndPass(email, password);
            if (person.isPresent()) {
                session.setAttribute(PERSON, person.get());
                resp.sendRedirect("/Profile/" + person.get().getId());
                return;
            }
//        }
        req.setAttribute(ERROR, "err");
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }
}
