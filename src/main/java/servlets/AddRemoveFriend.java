package servlets;

import lombok.extern.java.Log;
import models.Person;
import models.Relation;
import models.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Log
@WebServlet("/AddRemoveFriend")
public class AddRemoveFriend extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int status = Integer.parseInt(req.getParameter("status"));
        int person_id = ((Person) req.getSession().getAttribute(PERSON)).getId();
        int friend_id = Integer.parseInt(req.getParameter("user_id"));
        log.info("Person id - " + person_id + "friend id - " + friend_id);
        Relation relation = new Relation(person_id, friend_id, Status.FOLLOW);
        if (status == 0)
            relationDao.remove(relation);
        if (status == 1)
            relationDao.add(relation);
        resp.sendRedirect("/Profile/" + friend_id);

    }
}
