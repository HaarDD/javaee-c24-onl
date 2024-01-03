package by.teachmeskills.lesson39.config;

import by.teachmeskills.lesson39.config.converters.StringToAuthorDtoConverter;
import by.teachmeskills.lesson39.logging.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("by.teachmeskills.lesson39")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    ViewResolver viewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setPrefix("/WEB-INF/pages/");
        internalResourceViewResolver.setContentType("text/html;charset=UTF-8");
        return internalResourceViewResolver;
    }

    @Bean
    HandlerInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new StringToAuthorDtoConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Отключено для чистых логов
        //registry.addInterceptor(loggingInterceptor());
    }
}
