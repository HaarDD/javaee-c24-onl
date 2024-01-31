package by.teachmeskills.lesson41.config;

import by.teachmeskills.lesson41.logging.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("by.teachmeskills.lesson41")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    HandlerInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }


    @Value("${logging.interceptor.enabled}")
    private boolean isLoggingInterceptorEnabled;

    @Value("${storage.book_files_path}")
    private String bookFilesStoragePath;

    @Bean
    public String bookFilesStoragePath() {
        return bookFilesStoragePath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (isLoggingInterceptorEnabled) {
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
