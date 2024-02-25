package com.example.supermercadodawonline.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

@Configuration
public class DatabaseWebSecurity  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select DNI, pw, activo from cliente where DNI=?");
        users.setAuthoritiesByUsernameQuery("SELECT DNI, rol FROM cliente WHERE DNI=?");
        return users;
    }

    // Filtros por URL.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/templates/imagenes/**").permitAll()
                .requestMatchers("/", "/proyectos", "/login", "/signup").permitAll()
//                .requestMatchers("/categorias").hasAuthority("ADMINISTRADOR")
//                .requestMatchers("/pedido").hasAuthority("ADMINISTRADOR")
//                .requestMatchers("/nuevo").hasAuthority("ADMINISTRADOR")
                .anyRequest().authenticated());
        http.formLogin(formLogin -> formLogin.loginPage("/login").permitAll());
        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll());
        http.exceptionHandling((exception)-> exception.accessDeniedPage("/denegado"));

        return http.build();
    }
}

