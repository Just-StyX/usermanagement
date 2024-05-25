package jsl.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jsl.security.messaging.EmailProducer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationLoggingFilter implements Filter {
    private final EmailProducer emailProducer;

    public AuthenticationLoggingFilter(EmailProducer emailProducer) {
        this.emailProducer = emailProducer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var requestId = httpRequest.getHeader("Request-Id");
        emailProducer.sendMessage("Successfully authenticated request with id " + requestId);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
