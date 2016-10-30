package servlets;

import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static servlets.ServletConst.*;

@Log
@WebServlet("/Profile/*")
public class Profile extends ServletWrapper {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String url = req.getRequestURI();
        log.info(url);

        Pattern p = Pattern.compile("/?Profile/(\\d+)");
        Matcher m = p.matcher(url.trim());

        if (!m.matches())
            resp.sendRedirect("/auth/error");

        Optional<Person> user = personDao
                .getEntity(Integer.parseInt(m.group(1)));
        if (user.isPresent()) {
            req.setAttribute(USER, user.get());
            session.setAttribute(URL, url);
            req.getRequestDispatcher("/user/profile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/auth/error/");
//            Object person = session.getAttribute(PERSON);
//            req.setAttribute(USER, person);
//            url = "/Profile/" + ((Person) person).getId();
        }

    }
}