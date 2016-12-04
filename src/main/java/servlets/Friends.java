package servlets;

import lombok.extern.slf4j.Slf4j;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static servlets.ServletConst.*;
@Slf4j
@WebServlet({"/BlackList", "/Following", "/Followers"})
public class Friends extends ServletWrapper {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Person person = (Person) session.getAttribute(PERSON);
        String url = req.getRequestURI();
        switch (url.substring(1)) {
            case "BlackList": {
                req.setAttribute(SEARCH_RESULT, relationDao.ignoreList(person.getId()));
                req.setAttribute(SEARCH_RESULT_MSG, "blackList");
                req.setAttribute(OPTIONAL_BUTTON_NAME, "remove");
                break;
            }
            case "Followers": {
                req.setAttribute(SEARCH_RESULT, relationDao.followersList(person.getId()));
                req.setAttribute(SEARCH_RESULT_MSG, "followers");
                req.setAttribute(OPTIONAL_BUTTON_NAME, "block");
                break;
            }
            case "Following": {
                req.setAttribute(SEARCH_RESULT, relationDao.followingList(person.getId()));
                req.setAttribute(SEARCH_RESULT_MSG, "following");
                req.setAttribute(OPTIONAL_BUTTON_NAME, "unfollow");
                break;
            }
        }
        log.info("Set url to session attribute{} ", url);
        session.setAttribute(URL, url);
        req.getRequestDispatcher("/WEB-INF/user/results.jsp").forward(req, resp);
    }
}
