package me.longli.demo.configuration;

import com.alibaba.druid.support.http.StatViewFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<StatViewFilter> druidFilter() {
        FilterRegistrationBean<StatViewFilter> regBean = new FilterRegistrationBean<>();
        regBean.setFilter(new StatViewFilter());
        regBean.addUrlPatterns("/druid/*");
        regBean.setOrder(1);
        return regBean;
    }
}
