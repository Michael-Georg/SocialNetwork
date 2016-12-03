package servlets;

import lombok.extern.slf4j.Slf4j;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Slf4j
@WebServlet("/Upload")
@MultipartConfig
public class Upload extends ServletWrapper{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/user/upload.jsp").forward(req, resp);
        log.info("forwarding to upload.jsp");
    }
    //TODO Need to create external storage for content
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        int id = ((Person) req.getSession().getAttribute(PERSON)).getId();
        filePart.write(getServletContext().getRealPath("/images/") + id + ".jpg" );
        log.info("avatar upload");
        resp.sendRedirect("/Profile/" + id);

    }
}
