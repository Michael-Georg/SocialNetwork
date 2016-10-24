package servlets.filters;

import Dao.PersonDao;
import models.Person;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    private static final String KEY = "key";
    private PersonDao personDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        personDao = (PersonDao) context.getAttribute("personDao");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute(KEY) != null)
            chain.doFilter(request, response);
        Map<String, String[]> map = request.getParameterMap();
        if (map.containsKey("username") && map.containsKey("password")){
            Person person = personDao.isRegistered(map.get("username")[0], map.get("password")[0]);
            if (person != null) {
                session.setAttribute(KEY, person);
                chain.doFilter(request, response);
            } else
                request.getRequestDispatcher("/error.html").forward(request, response);
        } else
            request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}