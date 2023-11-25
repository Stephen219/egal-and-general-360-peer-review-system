package uk.cf.ac.LegalandGeneralTeam11.mocksecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Securitymock {
    public static final String[] ENDPOINTS_WHITELIST = {
            "/images/**",
            "/",
            "/403",
            "/order/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            //
            Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        // .requestMatchers( "/order/**").hasRole("USER")
                        .requestMatchers("/kitchen/**").hasRole("COOK")
                        //.requestMatchers(("/account/**")).hasRole("USER")
                        //.requestMatchers("/order/**").hasRole("ADMIN")


                        .anyRequest().hasRole("ADMIN"))

                // require authentication for all other requests
                //.anyRequest().authenticated())//hasRole("USER") // require authentication for all other requests

                //.csrf().disable() // i have removed the above line to disable cross site request forgery  .it is when i wanted to implement logout
                .formLogin(form -> form
                        .loginPage("/login").
                        permitAll()
                        .defaultSuccessUrl("/account", true)
                        .failureUrl("/login?error=true")

                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )


                .exceptionHandling((e) -> e.accessDeniedPage("/403"));


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(user,admin);
    }

}