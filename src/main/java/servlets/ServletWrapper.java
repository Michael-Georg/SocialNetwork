package servlets;

import Dao.RelationDao;
import Dao.PersonDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static servlets.ServletConst.*;


public class ServletWrapper extends HttpServlet {
    protected PersonDao personDao;
    protected RelationDao relationDao;

    @Override
    public void init() throws ServletException {

        personDao = (PersonDao) getServletContext().getAttribute(PERSON_DAO);
        relationDao = (RelationDao) getServletContext().getAttribute(FRIENDS_DAO);
    }
}
