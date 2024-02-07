package br.com.fstmkt.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtEntryPoint jwtEntryPoint;
    private final SecurityFilter securityFilter;

    public SecurityConfig(JwtEntryPoint jwtEntryPoint, SecurityFilter securityFilter) {
        this.jwtEntryPoint = jwtEntryPoint;
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(jwtEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/v1/auth/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.PUT, "api/v1/usuario/senha")).permitAll()
                        .requestMatchers("/api/v1/usuario/listar").hasAnyAuthority("ADMIN", "CLIENTE", "VENDEDOR")
                        .requestMatchers("/api/v1/usuario/perfil").hasAnyAuthority("ADMIN", "CLIENTE", "VENDEDOR")
                        .requestMatchers("api/v1/usuario/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/usuario/cliente/**").hasRole("CLIENTE")
                        .requestMatchers("api/v1/usuario/vendedor/**").hasRole("VENDEDOR")
                        .requestMatchers("/api/v1/usuario/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/vendas/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                        .requestMatchers("/api/v1/produtos/**").hasAnyAuthority("ADMIN", "VENDEDOR")
                        .requestMatchers("/api/v1/carrinho/**").hasAnyAuthority("VENDEDOR", "CLIENTE")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
