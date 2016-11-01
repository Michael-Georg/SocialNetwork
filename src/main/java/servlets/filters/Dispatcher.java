package servlets.filters;

import lombok.extern.java.Log;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static servlets.ServletConst.URL;

@Log
//@WebFilter("/*")
public class Dispatcher extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();
        log.info(url);

        Pattern p = Pattern.compile("/?Profile/(\\d+)");
        Matcher m = p.matcher(url);
        url = m.matches() ? url : "/";
        request.getSession(true).setAttribute(URL, url);
        chain.doFilter(request, response);

    }
}

