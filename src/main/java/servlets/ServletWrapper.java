package servlets;

import Dao.PersonDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static servlets.ServletConst.*;


public class ServletWrapper extends HttpServlet {
    protected PersonDao personDao;

    @Override
    public void init() throws ServletException {
        personDao = (PersonDao) getServletContext().getAttribute(PERSON_DAO);
    }
}
