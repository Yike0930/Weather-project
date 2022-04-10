package com.example.details.config;

import com.example.details.filter.CorrelationHeaderFilter;
import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class CorrelationHeaderFilterConfig {

    @Bean
    public FilterRegistrationBean<CorrelationHeaderFilter> servletRegistrationBean() {
        final FilterRegistrationBean<CorrelationHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        final CorrelationHeaderFilter filter = new CorrelationHeaderFilter();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}