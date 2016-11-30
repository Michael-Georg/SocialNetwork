package servlets;

import lombok.extern.slf4j.Slf4j;
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
import static servlets.ServletConst.URL;
@Slf4j
@WebServlet("/Search")
public class Search extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String mask = req.getParameter("mask");
        String url = req.getRequestURI();
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
//        req.setAttribute(OPTIONAL_BUTTON, "add");
        session.setAttribute(URL, url);
        log.info("URL : {}", url);
        req.getRequestDispatcher("/user/results.jsp").forward(req, resp);


    }
}
