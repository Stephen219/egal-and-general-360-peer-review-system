package uk.cf.ac.LegalandGeneralTeam11.mocksecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class Securitymock {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/images/**",
            "/",
            "/403"
    };

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers("/form/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/account/**").hasAnyRole( "USER")
                )



                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority auth : authentication.getAuthorities()) {
                                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/home");
                                } else if (auth.getAuthority().equals("ROLE_USER")) {
                                    response.sendRedirect("/account");
                                }
                            }
                        })
                        .failureUrl("/login?error=true")
                )

                .logout((l) -> l.permitAll().logoutSuccessUrl("/home"))

                .exceptionHandling().accessDeniedPage("/403");

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
