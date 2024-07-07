package kr.co.polycube.backendtest.Component;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SpecialCharacterFilter extends OncePerRequestFilter {
	
	private static final Pattern ALLOWED_CHARACTERS_PATTERN = Pattern.compile("^[a-zA-Z0-9/?=&;:_\\\\.]+$");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        if (!isValidRequestURI(requestURI) || !isValidQueryString(queryString)) {
        	
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "? & = : //를 제외힌 특수문자를 URL에 입력할 수 없습니다.");
            
            return;
            
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidRequestURI(String requestURI) {
    	
        return ALLOWED_CHARACTERS_PATTERN.matcher(requestURI).matches();
        
    }

    private boolean isValidQueryString(String queryString) {
    	
        if (queryString == null) {
        	
            return true; // Query string이 없는 경우는 허용
            
        }
        
        return ALLOWED_CHARACTERS_PATTERN.matcher(queryString).matches();
        
    }
}
