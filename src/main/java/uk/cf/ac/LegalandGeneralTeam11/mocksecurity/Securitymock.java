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
            "legal.png",
            "/order/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            //
            Exception {
        http
                .authorizeHttpRequests(request -> request
//
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers("/form/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/account/**").hasAnyRole( "USER"))
                .formLogin(form -> form
                        .loginPage("/login").
                        permitAll()
                        .defaultSuccessUrl("/account", true)
                        .defaultSuccessUrl("/admin", true)
                        //currently the admin url is not working
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