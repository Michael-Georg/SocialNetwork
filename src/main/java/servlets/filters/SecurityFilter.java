package servlets.filters;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static servlets.ServletConst.PERSON;

@Slf4j
@WebFilter({"/", "/Profile/*", "/Settings", "/Friends", "/Upload"})
public class SecurityFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute(PERSON) != null)
            chain.doFilter(request, response);
        else {
            response.sendRedirect("/Login");
        }
    }
}