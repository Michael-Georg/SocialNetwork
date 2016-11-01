package servlets;

import lombok.extern.java.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Log
@WebServlet("/SignOut")
public class SingOut extends ServletWrapper{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute(PERSON);
        log.info("Person sign out {}");
        resp.sendRedirect("/Login");
    }
}
