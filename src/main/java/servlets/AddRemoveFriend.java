package servlets;

import lombok.extern.slf4j.Slf4j;
import models.Person;
import models.Relation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static servlets.ServletConst.PERSON;
import static servlets.ServletConst.URL;

@Slf4j
@WebServlet("/AddRemoveFriend")
public class AddRemoveFriend extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String url = (String) Optional.ofNullable(session.getAttribute(URL)).orElse("/");
        int status = Integer.parseInt(req.getParameter("status"));
        int person_id = ((Person) req.getSession().getAttribute(PERSON)).getId();
        int user_id = Integer.parseInt(req.getParameter("user_id"));
        switch (status) {
            //remove from blacklist
            case -1 : {
                relationDao.remove(new Relation(user_id, person_id, status));
                break;
            }
            //unfollow
            case 0: {
                relationDao.remove(new Relation(person_id, user_id, status));
                break;
            }
            //block user
            case 1: {
                relationDao.update(new Relation(person_id, user_id, status));
                break;
            }
            //follow user
            case 2: {
                relationDao.add(new Relation(person_id, user_id, status));
                break;
            }
        }
        log.info("Relation status {}; redirected URL {}",status, url);
        resp.sendRedirect(url);
    }
}
