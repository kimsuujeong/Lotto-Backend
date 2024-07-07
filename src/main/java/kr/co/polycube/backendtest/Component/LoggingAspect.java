package kr.co.polycube.backendtest.Component;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

	@Before("execution(* kr.co.polycube.backendtest.Controller.UserController.*(..))")
	public void logRequest() {
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
		if (attributes != null) {
            
			HttpServletRequest request = attributes.getRequest();
            logger.info("Client Agent: " + request.getHeader("User-Agent"));
            
        } else {
        	
            logger.warning("ServletRequestAttributes is null");
            
        }
	}

}