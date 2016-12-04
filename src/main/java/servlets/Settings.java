package servlets;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@WebServlet("/Settings")
public class Settings extends ServletWrapper {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Go to - > settings.jsp");
        Person person = (Person) req.getSession().getAttribute(PERSON);
        req.setAttribute("day", (person.getDob() == null) ? "" : person.getDob().getDayOfMonth());
        req.setAttribute("month", (person.getDob() == null) ? "" : person.getDob().getMonthValue());
        req.setAttribute("year", (person.getDob() == null) ? "" : person.getDob().getYear());
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

        LocalDate dob = null;
        try {
            int day = Integer.parseInt(req.getParameter("day"));
            int month = Integer.parseInt(req.getParameter("month"));
            int year = Integer.parseInt(req.getParameter("year"));

            dob = LocalDate.of(year, month, day);
        } catch (DateTimeException | NumberFormatException e) {
            log.error("Failed changing date", e);
        }

        HttpSession session = req.getSession();
        Person person = (Person) session.getAttribute(PERSON);

        Person personNew = Person.builder()
                .id(person.getId())
                .firstName(firstName.equals("") ? person.getFirstName() : firstName)
                .lastName(lastName.equals("") ? person.getLastName() : lastName)
                .telephone(telephone)
                .email(person.getEmail())
                .dob(dob)
                .address(address)
                .info(info)
                .build();
        session.setAttribute(PERSON, personNew);
        if (!password.equals(""))
            personNew.setPassword(password);
        personDao.update(personNew);
        resp.sendRedirect("/Profile/" + person.getId());
    }
}
