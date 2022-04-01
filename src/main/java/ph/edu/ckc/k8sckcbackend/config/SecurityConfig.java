package ph.edu.ckc.k8sckcbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ph.edu.ckc.k8sckcbackend.security.*;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationAuthenticationEntryPoint authenticationEntryPoint;
    private final ApplicationAccessDeniedHandler accessDeniedHandler;
    private final UserDetailsServiceDefault userDetailsServiceDefault;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:4000", "https://ckc.edu.ph", "https://www.ckc.edu.ph"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().ignoringAntMatchers("/login").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                .and()
/*                .headers().frameOptions().sameOrigin().and()*/
/*
                */
/*            .and()*/
/*                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()*/
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/menu/**").hasAnyAuthority( "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/auth/menu/**").hasAnyAuthority( "ADMIN")
                .antMatchers(HttpMethod.PUT, "/auth/menu/**").hasAnyAuthority( "ADMIN")
                .antMatchers("/auth/account/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/auth/**").hasAuthority( "ADMIN")
                .antMatchers(HttpMethod.PUT,"/auth/**").hasAuthority( "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/auth/**").hasAuthority( "ADMIN")

                .antMatchers(HttpMethod.GET,"/auth/**").permitAll()
                .antMatchers("/h2/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceDefault).passwordEncoder(passwordEncoder());
    }


}
