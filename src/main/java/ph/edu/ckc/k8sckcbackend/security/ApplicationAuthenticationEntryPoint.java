package ph.edu.ckc.k8sckcbackend.security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Component
public class ApplicationAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.flush();
        printWriter.close();
    }
}
/*
*   HashMap<String, String> map = new HashMap<>(2);
         map.put("uri", request.getRequestURI());
         map.put("msg", "Authentication failed");
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         response.setCharacterEncoding("utf-8");
         response.setContentType(MediaType.APPLICATION_JSON_VALUE);
         ObjectMapper objectMapper = new ObjectMapper();
         String resBody = objectMapper.writeValueAsString(map);
         PrintWriter printWriter = response.getWriter();
         printWriter.print(resBody);
         printWriter.flush();
         printWriter.close();
         *
* */
