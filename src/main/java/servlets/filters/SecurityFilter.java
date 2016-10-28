package servlets.filters;

import lombok.extern.java.Log;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log
@WebFilter("/*")
public class SecurityFilter extends HttpFilter {

    private static final String KEY = "person";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        log.info(() -> "Security Filter" + request.getRequestURI()  + " ");
        if (session.getAttribute(KEY) != null)
            chain.doFilter(request, response);
        else
            request.getRequestDispatcher("Login").forward(request, response);
    }
}