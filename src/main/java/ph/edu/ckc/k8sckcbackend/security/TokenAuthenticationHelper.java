package ph.edu.ckc.k8sckcbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.util.WebUtils;
import ph.edu.ckc.k8sckcbackend.config.ConfigurableApplicationContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

class TokenAuthenticationHelper {

    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes
    private static final String SECRET = "ThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecretThisIsASecret";
    private static final String COOKIE_BEARER = "COOKIE-BEARER";


    private TokenAuthenticationHelper() {
        throw new IllegalStateException("Utility class");
    }


    static void addAuthentication(HttpServletResponse res, Authentication auth) {

        AssymetricSecurity assymetricSecurity = ConfigurableApplicationContext.getBean(AssymetricSecurity.class);

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwt = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(assymetricSecurity.getPrivateKeys())
                .compact();
        Cookie cookie = new Cookie(COOKIE_BEARER, jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    static Authentication getAuthentication(HttpServletRequest request) {

        AssymetricSecurity assymetricSecurity =  ConfigurableApplicationContext.getBean(AssymetricSecurity.class);

        Cookie cookie = WebUtils.getCookie(request, COOKIE_BEARER);
        String token = cookie != null ? cookie.getValue() : null;

        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(assymetricSecurity.getPublicKeys())
                    .parseClaimsJws(token)
                    .getBody();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("authorities").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            String userName = claims.getSubject();
            return userName != null ? new UsernamePasswordAuthenticationToken(userName, null, authorities) : null;
        }
        return null;
    }


}
