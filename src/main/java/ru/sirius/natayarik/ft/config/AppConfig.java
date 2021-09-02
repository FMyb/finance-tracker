package ru.sirius.natayarik.ft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.sirius.natayarik.ft.interceptor.LoginInterceptor;
import ru.sirius.natayarik.ft.services.CurrentUserService;

/**
 * @author Natalia Nikonova
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {
   private final CurrentUserService currentUserService;

   public AppConfig(CurrentUserService currentUserService) {
      this.currentUserService = currentUserService;
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new LoginInterceptor(currentUserService));
   }
}
