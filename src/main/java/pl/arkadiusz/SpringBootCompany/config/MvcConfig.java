package pl.arkadiusz.SpringBootCompany.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/logoutPage").setViewName("logout");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/Homepage").setViewName("homepage");
        registry.addViewController("/userHomepage").setViewName("homepage");
    }
}