package servlets.filters;

import lombok.extern.java.Log;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static servlets.ServletConst.URL;

@Log
//@WebFilter("/*")
public class Dispatcher extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session =  request.getSession(true);
        String url = request.getRequestURI();
        if  (!url.contains("Location"))
            session.setAttribute(URL, url);
    log.info("requested URL: " + url);
    chain.doFilter(request, response);
    }
}

