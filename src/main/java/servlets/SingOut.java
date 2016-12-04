package servlets;

import lombok.extern.slf4j.Slf4j;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Slf4j
@WebServlet("/SignOut")
public class SingOut extends ServletWrapper{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Person person = (Person) req.getSession().getAttribute(PERSON);
        log.info("Person sign out {}", person.getId());
        req.getSession().removeAttribute(PERSON);
        resp.sendRedirect("/Login");
    }
}
