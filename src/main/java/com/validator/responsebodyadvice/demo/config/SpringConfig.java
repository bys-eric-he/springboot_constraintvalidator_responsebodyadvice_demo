package com.validator.responsebodyadvice.demo.config;

import com.validator.responsebodyadvice.demo.response.ResponseResultInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * WebMvcConfigurerAdapter，这个类的作用是进行SpringMVC的一些配置
 * 等同于继承WebMvcConfigurationSupport, 如果项目中同时存在WebMvcConfigurationSupport的派生类时
 * WebMvcConfigurerAdapter 的派生类不会执行，优先使用WebMvcConfigurationSupport的派生类
 * 因为WebMvcConfigurerAdapter过期，使用WebMvcConfigurationSupport代替WebMvcConfigurerAdapter
 */
@Configuration
public class SpringConfig extends WebMvcConfigurationSupport {

    private static Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseResultInterceptor())
                // 拦截配置
                .addPathPatterns("/**")
                // 排除配置
                .excludePathPatterns("/error", "/login**");
        super.addInterceptors(registry);
        logger.info("SpringConfig Add ResponseResultInterceptor.....");
    }

    /**
     * 配置解决SwaggerUI访问404错误的问题
     * 如果继承了WebMvcConfigurationSupport需要重新指定静态资源
     *
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}