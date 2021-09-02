package ru.sirius.natayarik.ft.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import ru.sirius.natayarik.ft.services.CurrentUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author Natalia Nikonova
 */
public class LoginInterceptor implements HandlerInterceptor {
   private final CurrentUserService currentUserService;
   private static final String USER_KEY = "name";

   public LoginInterceptor(CurrentUserService currentUserService) {
      this.currentUserService = currentUserService;
   }

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      Enumeration<String> headers = request.getHeaderNames();
      while (headers.hasMoreElements()) {
         String headerName = headers.nextElement();
         if (headerName.equals(USER_KEY)) {
            currentUserService.setUser(request.getHeader(headerName));
         }
      }
      return true;
   }
}
