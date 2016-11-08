package servlets;

import lombok.extern.java.Log;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Log
@WebServlet("/Upload")
@MultipartConfig
public class Upload extends ServletWrapper{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user/upload.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        int id = ((Person) req.getSession().getAttribute(PERSON)).getId();
        log.info("/content/");
        filePart.write(getServletContext().getRealPath("/images/") + id + ".jpg" );

        resp.sendRedirect("/Profile/" + id);

    }
}
