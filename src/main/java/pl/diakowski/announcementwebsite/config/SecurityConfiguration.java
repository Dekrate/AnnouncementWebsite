package pl.diakowski.announcementwebsite.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcherRegistry ->
                requestMatcherRegistry.anyRequest().permitAll());
        http.formLogin(login -> login.loginPage("/login"));
        http.logout(logout ->
                logout.logoutSuccessUrl("/"));
        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
        http.headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

    /**
     * @return PasswordEncoder, which is used in ClientService.<br />
     * The method was used here to create a bean of PasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
