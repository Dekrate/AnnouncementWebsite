package pl.diakowski.announcementwebsite.config;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.diakowski.announcementwebsite.web.AnnouncementController;


/**
 * Class created to configure security of the website.
 */
@Configuration
public class SecurityConfiguration {
    /**
     * Method created to configure access to the website.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/client/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/static/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/index")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/style/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/category")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/announcement")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/add-announcement")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/login")).anonymous()
                .requestMatchers(new AntPathRequestMatcher("/register")).anonymous()
                .requestMatchers(new AntPathRequestMatcher("/403")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/error")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/activation")).permitAll()
                .anyRequest().permitAll());
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
     * @return {@link DelegatingPasswordEncoder}, which is used in ClientService.<br />
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * @return {@link PolicyFactory}, which is used in {@link AnnouncementController} to allow some HTML tags.<br />
     */
    @Bean
    PolicyFactory policyFactory() {
        return Sanitizers.BLOCKS.and(Sanitizers.STYLES).and(Sanitizers.LINKS).and(Sanitizers.FORMATTING);
    }
}
