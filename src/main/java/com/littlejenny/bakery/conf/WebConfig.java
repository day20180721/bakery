package com.littlejenny.bakery.conf;

import com.littlejenny.bakery.interceptor.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("*")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost", "http://localhost","http://day20180721-work.ddns.net","http://day20180721.ddns.net", "https://web.postman.co/")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
            setSupportedMediaTypes(mediaTypes);// tag6
        }
    }
    @Autowired
    LoginInterceptorImpl loginInterceptor;
    @Autowired
    BusinessPageInterceptor businessPageInterceptor;
    @Autowired
    GenericModelParameterInterceptor genericModelParameterInterceptor;
    @Autowired
    PreventJoinToRootLoginPageWhenLoginedInterceptor preventJoinToRootLoginPageWhenLoginedInterceptor;
    @Autowired
    PutUserToRequestWhenLoginedInterceptor putUserToRequestWhenLoginedInterceptor;
    @Value("${business-prefix-value}")
    private String buisnessPrefix;
    @Value("${customer-prefix-value}")
    private String customerPrefix;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns(customerPrefix + "/**");
        registry.addInterceptor(businessPageInterceptor).addPathPatterns(buisnessPrefix + "/**");
        registry.addInterceptor(genericModelParameterInterceptor).addPathPatterns("/**");
        registry.addInterceptor(preventJoinToRootLoginPageWhenLoginedInterceptor).addPathPatterns("/oauth/root");
        registry.addInterceptor(putUserToRequestWhenLoginedInterceptor).addPathPatterns("/shop");
    }
}
