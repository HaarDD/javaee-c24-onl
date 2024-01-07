package by.teachmeskills.lesson41.config;

import by.teachmeskills.lesson41.config.converters.StringToAuthorDtoConverter;
import by.teachmeskills.lesson41.logging.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("by.teachmeskills.lesson41")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    HandlerInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    @Value("${logging.interceptor.enabled}")
    private boolean isLoggingInterceptorEnabled;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new StringToAuthorDtoConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Добавлен переключатель в application.yaml
        if(isLoggingInterceptorEnabled){
            registry.addInterceptor(loggingInterceptor());
        }
    }

    // Логирование request`ов
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
