package servlets;

import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.h2.util.StringUtils.isNumber;
import static servlets.ServletConst.SEARCH_RESULT;

@WebServlet("/Search")
public class Search extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String mask = req.getParameter("mask");
        //todo поиск по всем данным
        if (mask == null || mask.equals("")) {
            req.setAttribute(SEARCH_RESULT, personDao.getAll());
        } else if (isNumber(mask)) {
            Optional<Person> person = personDao.getEntity(Integer.parseInt(mask));
                req.setAttribute(SEARCH_RESULT, person.isPresent() ?
                        Collections.singletonList(person.get()) : emptyList());
        } else {
            Optional<Person> person = personDao.getByEmail(mask);
                req.setAttribute(SEARCH_RESULT, person.isPresent() ?
                        Collections.singletonList(person.get()) : emptyList());
          }

        req.getRequestDispatcher("/user/results.jsp").forward(req, resp);

    }
}
