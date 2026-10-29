package ro.uvt.info.designpatternslab.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        log.info("BEFORE processing: {} {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);   // aici cererea merge mai departe în lanț, către controller
        log.info("AFTER processing: {} {} -> status {}", req.getMethod(), req.getRequestURI(), res.getStatus());
    }
}