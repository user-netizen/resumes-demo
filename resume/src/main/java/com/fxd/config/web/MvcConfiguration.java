package com.fxd.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;

/**
 * 开启Mvc，自动注入Spring容器。
 * WebMvcConfigurationSupport:配置视图解析器
 * 当一个类实现接口（ApplicationContextAware）之后，
 * 这个类就可以方便获得ApplicationContext的所有bean
 */
@EnableWebMvc
@Configuration
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    //Spring容器
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index");
    }

    /**
     * 静态资源配置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        //本地图片http://localhost:8080/resumes/upload/resumes/4/2019100522080116076.jpg
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:///Users/zhangtao/upload/");
        //服务器路径
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///home/work/upload/");

    }

    /**
     * 定义默认的请求处理器
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 创建viewResolver 定义视图解析器
     */
    @Bean
    public ViewResolver createViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        设置Spring容器
        viewResolver.setApplicationContext(this.applicationContext);
//        取消缓存
        viewResolver.setCache(false);
//        设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
//        viewResolver.setPrefix("/");
//        设置解析的后缀
        viewResolver.setSuffix(".html");
//        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * 文件上传解析器
     */
//    @Bean
//    public CommonsMultipartResolver createMultipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setDefaultEncoding("utf-8");
//        multipartResolver.setMaxUploadSize(20971520);
//        multipartResolver.setMaxInMemorySize(20971520);
//        return multipartResolver;
//    }

    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fontcolor;
    @Value("${kaptcha.testproducer.width}")
    private String width;
    @Value("${kaptcha.textproducer.char.string}")
    private String charString;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.size}")
    private String fontsize;
    @Value("${kaptcha.noise.color}")
    private String noiseColor;
    @Value("${kaptcha.textproducer.char.length}")
    private String charLength;
    @Value("${kaptcha.textproducer.font.names}")
    private String fontNames;

    /**
     * 配置kapthca验证码
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws ServletException {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
        servlet.addInitParameter("kaptcha.border", border);
        servlet.addInitParameter("kaptcha.textproducer.font.color", fontcolor);
        servlet.addInitParameter("kaptcha.testproducer.width", width);
        servlet.addInitParameter("kaptcha.textproducer.char.string", charString);
        servlet.addInitParameter("kaptcha.image.height", height);
        servlet.addInitParameter("kaptcha.textproducer.font.size", fontsize);
        servlet.addInitParameter("kaptcha.noise.color", noiseColor);
        servlet.addInitParameter("kaptcha.textproducer.char.length", charLength);
        servlet.addInitParameter("kaptcha.textproducer.font.names", fontNames);
        return servlet;
    }
}
