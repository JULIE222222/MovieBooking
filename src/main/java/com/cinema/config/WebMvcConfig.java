package com.cinema.config;

import com.cinema.utils.MyPath;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        //외부에 있는 폴더를 내프로젝트에서 찾을 수 있도록 세팅함
        registry.addResourceHandler("/upload/**")
                // /upload 경로를 적으면 그 경로를
                .addResourceLocations("file:///"+ MyPath.IMAGEPATH)
                .setCachePeriod(60*10*6)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
