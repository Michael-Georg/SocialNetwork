package servlets;

import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

import static servlets.ServletConst.PERSON;
import static servlets.ServletConst.URL;

@Log
@WebServlet("/Settings")
public class Settings extends ServletWrapper {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info(() -> " - > settings.jsp");
        req.getSession().setAttribute(URL, "/Settings");
        req.getRequestDispatcher("/WEB-INF/user/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");
        String info = req.getParameter("info");
        String password = req.getParameter("password");
        int day = Integer.parseInt(req.getParameter("day"));
        int month = Integer.parseInt(req.getParameter("month"));
        int year = Integer.parseInt(req.getParameter("year"));

        LocalDate dob = null;
        try {
            dob = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            log.warning("Date didn't change");
        }

        HttpSession session = req.getSession();
        Integer id = ((Person) session.getAttribute(PERSON)).getId();
        String email = ((Person) session.getAttribute(PERSON)).getEmail();

        Person person = Person.builder()   //TODO доделать обработку вводимых данных
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .telephone(telephone)
                .email(email)
                .dob(dob)
                .address(address)
                .info(info)
                .build();
        session.setAttribute(PERSON, person);

        person.setPassword(password);
        personDao.update(person);
        resp.sendRedirect("/Profile/" + id);
    }
}
