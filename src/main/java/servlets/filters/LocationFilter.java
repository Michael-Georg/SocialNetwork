package servlets.filters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/location")
public class LocationFilter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Optional<String> lang = Optional.ofNullable(session.getAttribute("lang").toString());
        if (lang.isPresent())
            session.setAttribute("lang",
                    lang.get().equals("ru") ? "en" : "ru" );
        resp.sendRedirect("Profile");
    }

}
