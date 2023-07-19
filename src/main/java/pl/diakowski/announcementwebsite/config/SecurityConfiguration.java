package pl.diakowski.announcementwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
    /**
     * Method created to configure access to the website.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                .requestMatchers("/").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/style/**").permitAll()
                .requestMatchers("/category").permitAll()
                .requestMatchers("/announcement").permitAll()
                .requestMatchers("/change-password").authenticated()
                .requestMatchers("/login").anonymous()
//                .requestMatchers(HttpMethod.)
                .requestMatchers("/register").anonymous()
                .requestMatchers("/not-found").permitAll()
                .requestMatchers("/403").permitAll());
        http.exceptionHandling(exceptionHandler -> exceptionHandler
                .accessDeniedPage("/403"));
//                .authenticationEntryPoint((request, response, authException) -> {}));
        http.formLogin(login -> login.loginPage("/login")
                .loginProcessingUrl("/login"));
        http.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/"));
//        http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
//        http.headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
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
