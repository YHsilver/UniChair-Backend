package fudan.se.lab2.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Write your code to make this filter works.
 *
 * @author LBW
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenUtil tokenUtil;

    @Autowired
    public JwtRequestFilter(JwtTokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ignore the check of token if the request is a login or register
        if (request.getRequestURI().equals("/register") || request.getRequestURI().equals("/login")) {
            System.out.println("[URI]: " + request.getRequestURI() + " passed");
            filterChain.doFilter(request, response);
            return;
        }
        // get token from the request header and check whether it exists
        String token = request.getHeader("token");
        //System.out.println("[URI]: " + request.getRequestURI() + " [token]: " + token);
        if (token == null) {
            response.sendRedirect("http://localhost:80/login");
            return;
        }
        // check whether the token is
        String username = null;
        try {
            username = tokenUtil.getUsernameFromToken(token);
        } catch (ExpiredJwtException ex) {
            // TODO logger
            System.out.println("[" + ex.getClaims().getSubject() + "] token out of time");
            response.sendRedirect("http://localhost:80/login");
            return;
        }
        System.out.println("[" + username + "] token access");
        // green light
        filterChain.doFilter(request, response);
    }

    private void printRequestHeader(HttpServletRequest request) {
        System.out.println("-----------------------------");
        Enumeration<String> requestHeaderNamesEnumeration = request.getHeaderNames();
        while (requestHeaderNamesEnumeration.hasMoreElements()) {
            String elementName = requestHeaderNamesEnumeration.nextElement();
            System.out.println("[name]: " + elementName + " [value]: " + request.getHeader(elementName));
        }
        System.out.println("-----------------------------");

    }
}