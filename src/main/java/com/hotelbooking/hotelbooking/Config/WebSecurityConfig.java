// package com.hotelbooking.hotelbooking.Config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// // import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// // import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// // import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// // import org.springframework.security.core.userdetails.User;
// // import org.springframework.security.core.userdetails.UserDetails;
// // import org.springframework.security.core.userdetails.UserDetailsService;
// // import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// // import org.springframework.security.crypto.password.PasswordEncoder;
// // import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// // import org.springframework.security.web.SecurityFilterChain;

// import com.hotelbooking.hotelbooking.Service.AccountService;

// @Configuration
// @EnableWebSecurity
// public class WebSecurityConfig {

//         @Autowired
//         AccountService accountService;

//         @Bean
//         public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//                 http
//                                 .csrf(csrf -> csrf
//                                                 .ignoringRequestMatchers("/check-phoneNumber", "/validateOtp",
//                                                                 "/addAccount", "/login"))
//                                 .authorizeHttpRequests((requests) -> requests
//                                                 .requestMatchers("/register", "/contact", "/assets/**", "/login",
//                                                                 "/index")
//                                                 .permitAll()
//                                                 .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                                                 .requestMatchers("/check-phoneNumber", "/validateOtp", "/addAccount",
//                                                                 "/login")
//                                                 .permitAll()
//                                                 .anyRequest().authenticated())
//                                 .formLogin((form) -> form
//                                                 .loginPage("/login")
//                                                 .successForwardUrl("/index")
//                                                 .permitAll())
//                                 .logout((logout) -> logout.permitAll());


//                 return http.build();
//         }

   
// }
