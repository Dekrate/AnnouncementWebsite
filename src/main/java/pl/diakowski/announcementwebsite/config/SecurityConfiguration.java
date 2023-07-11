package pl.diakowski.announcementwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll());
        http.formLogin(login -> login.loginPage("/login"));
        http.logout(httpSecurityLogoutConfigurer ->
                httpSecurityLogoutConfigurer.logoutSuccessUrl("/"));
        return http.build();
    }

    /**
     * @return PasswordEncoder which is used in ClientService.<br />
     * The method was used here to create a bean of PasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
