package priv.ymqm.housing.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import priv.ymqm.housing.common.interceptor.LoginInterceptor;
import priv.ymqm.housing.common.interceptor.PermissionInterceptor;

/**
 * @author chenhonnian
 * @since 2020/03/22
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        String[] excludeResourcePaths = {"/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**", "/api-docs/**"};
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/loginController/**")
                .excludePathPatterns(excludeResourcePaths);

        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/loginController/**")
                .excludePathPatterns(excludeResourcePaths);
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
