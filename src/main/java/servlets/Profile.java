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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static servlets.ServletConst.*;

@Slf4j
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

        Pattern p = Pattern.compile("/?Profile/(\\d+)");
        Matcher m = p.matcher(url.trim());

        if (!m.matches())
            resp.sendRedirect("/WEB-INF/auth/error");

        Optional<Person> user = personDao
                .getEntity(Integer.parseInt(m.group(1)));
        if (user.isPresent()) {
            req.setAttribute(USER, user.get());
            session.setAttribute(URL, url);
            int person_id = ((Person) session.getAttribute(PERSON)).getId();
            int user_id = user.get().getId();
            log.info("person id - " + person_id + "; requested id - " + user_id);
            req.setAttribute(FRIEND_STATUS, relationDao.getRelation(person_id, user_id));
            req.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(req, resp);
        } else {
            log.warn("user not found");
            resp.sendRedirect("/WEB-INF/auth/error/");
        }
    }
}