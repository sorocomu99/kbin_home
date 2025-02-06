package com.kbin.inno.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//public class WebConfig implements WebMvcConfigurer {
public class WebConfig extends WebMvcConfigurerAdapter {

    private final MenuInterceptor menuInterceptor;

    public WebConfig(MenuInterceptor menuInterceptor) {
        this.menuInterceptor = menuInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/kbinnovationhub/upload/**").addResourceLocations("file:///D:/fsfile/dev_kbinnovation/");
		
        //registry.addResourceHandler("/summernoteimages/**" , "/upload/**")
    	registry.addResourceHandler("/summernoteimages/**")
    	        .addResourceLocations("file:///D:/fsifle/dev_kbinnovation/");
                //.addResourceLocations("file:/fsfile/dev_kbinnovation/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(menuInterceptor)
                .addPathPatterns("/starters/**"
                        , "/nurture/**"
                        , "/error/**"
                        , "/community/**"
                        , "/"
                        , "/startup/**")
        ;
    }
}