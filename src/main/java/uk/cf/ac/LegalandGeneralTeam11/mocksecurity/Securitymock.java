package uk.cf.ac.LegalandGeneralTeam11.mocksecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Securitymock {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/images/**",
            "/",
            "/403",
            "/css/**",
            "static/**",
            "legal.png",
            "legal_and_general.png",
            "/review/**",
            "favicon-32x32.png",
            "/icons/**",
            "logso.png",
            "/404/","/logout/",
            "/activate/**",
            "/csp-report-endpoint",
            "/reset-password/**",
            "/forgot-password/**",
            "/password/**",
            "/home"
    };

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers(headersConfigurer -> headersConfigurer
                        .contentSecurityPolicy(csp -> csp.policyDirectives("form-action 'self'; report-uri /csp-report-endpoint"))
                        .frameOptions(frame -> frame.sameOrigin())
                        .frameOptions(frame -> frame.deny())
                        .frameOptions(frame -> frame.disable())
                        //.httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
                )

                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers("css/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my_info").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/form/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/domain/**").hasRole("ADMIN")
                        .requestMatchers("/account/**").hasAnyRole( "USER")
                       // .requestMatchers("/review/**").hasRole("USER")
                        .requestMatchers("/self-assessment/**").hasAnyRole( "USER", "ADMIN")
                        .requestMatchers("/accept/**").hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/accept/**")).hasRole("ADMIN")
                        .requestMatchers("get_reviewers/**").hasRole("USER")
                        .requestMatchers("/submit_reviewers/**").hasRole("USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/submit_reviewers/**")).hasRole("USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/domain/**")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/addDomain/**")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/activate/**")).hasRole("ADMIN")

                        .requestMatchers("/account/**").hasAnyRole( "USER")
                        .requestMatchers("/accept/**").hasAnyRole("ADMIN")
                        .anyRequest().hasRole("ADMIN")
                )





                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority auth : authentication.getAuthorities()) {
                                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin");
                                } else if (auth.getAuthority().equals("ROLE_USER")) {
                                    response.sendRedirect("/account");
                                }
                            }
                        })
                        .failureUrl("/login?error=true")
                )



                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )

                .logout((l) -> l.permitAll().logoutSuccessUrl("/login"))


                .exceptionHandling(access -> access.accessDeniedPage("/403"));



        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());

        return provider;
    }
    @Bean
    UserDetailsService userDetailsService() {

        JdbcDaoImpl jdbcUserDetails = new JdbcDaoImpl();
        jdbcUserDetails.setDataSource(dataSource);
        jdbcUserDetails.setUsersByUsernameQuery("select email, password, enabled from users where email=?");
        jdbcUserDetails.setAuthoritiesByUsernameQuery("select email, name from users u join roles r on r.role_id = u.role_id where email=?");
        return jdbcUserDetails;
    }


}