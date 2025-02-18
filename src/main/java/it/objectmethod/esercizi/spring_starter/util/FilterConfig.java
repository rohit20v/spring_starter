package it.objectmethod.esercizi.spring_starter.util;


import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FilterConfig {
    private final AccessFilter accessFilter;

    public FilterConfig(AccessFilter accessFilter) {
        this.accessFilter = accessFilter;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0);
        return registrationBean;
    }

    /**
     * Registers CustomFilter with the Spring context.
     *
     * @return FilterRegistrationBean for CustomFilter
     */
    @Bean
    public FilterRegistrationBean<AccessFilter> accessFilterRegistrationBean() {
        FilterRegistrationBean<AccessFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(accessFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
