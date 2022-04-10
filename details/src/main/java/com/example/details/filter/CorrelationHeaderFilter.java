package com.example.details.filter;

import com.example.details.controller.WeatherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CorrelationHeaderFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String currentCorrId = httpServletRequest.getHeader(RequestCorrelation.CORRELATION_ID);

        if (!currentRequestIsAsyncDispatcher(httpServletRequest)) {
            if (currentCorrId == null) {
                currentCorrId = UUID.randomUUID().toString();
                logger.info("No correlationId found in Header. Generated : " + currentCorrId);
            } else {
                logger.info("Found correlationId in Header : " + currentCorrId);
            }

            RequestCorrelation.setId(currentCorrId);
        }

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    private boolean currentRequestIsAsyncDispatcher(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getDispatcherType().equals(DispatcherType.ASYNC);
    }
}
