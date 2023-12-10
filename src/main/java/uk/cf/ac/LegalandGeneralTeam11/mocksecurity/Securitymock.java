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
            "/review/**",
            "/domain"
    };

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()



                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers("css/**").permitAll()
                        .requestMatchers("/form/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/account/**").hasAnyRole( "USER")
                       // .requestMatchers("/review/**").hasRole("USER")
                        .requestMatchers("/self-assessment/**").hasAnyRole( "USER", "ADMIN")
                        .requestMatchers("/accept/**").hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/accept/**")).hasRole("ADMIN")
                        .requestMatchers("get_reviewers/**").hasRole("USER")
                        .requestMatchers("/submit_reviewers/**").hasRole("USER")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/submit_reviewers/**")).hasRole("USER")

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

//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//    }

    @Bean
    UserDetailsService userDetailsService() {

        //we can replace this with another implementation of UserDetailsService.
        //that could use JPA to access the DB, or use LDAP instead.
        //quite often, Spring will provide default implementations. Read before writing!
        //The user details service interface provides a method to get a user by username.
        //That user will contain the authorities. With that object graph, Spring Security can do the rest.

        JdbcDaoImpl jdbcUserDetails = new JdbcDaoImpl();
        jdbcUserDetails.setDataSource(dataSource);
        jdbcUserDetails.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        jdbcUserDetails.setAuthoritiesByUsernameQuery("select username, authority from user_authorities where username=?");
        return jdbcUserDetails;
    }


}