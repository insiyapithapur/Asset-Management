package com.elecon.asset_mgt.Config;

import com.elecon.asset_mgt.utils.jwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

  private final jwtService jwtservice;

  public SecurityConfiguration(jwtService jwtservice) {
    this.jwtservice = jwtservice;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    System.out.println("security");
    http.csrf(csrf -> csrf.disable())
      .authorizeRequests(authorizeRequests -> authorizeRequests
        .requestMatchers("/api/v1/employee/login","/api/v1/employee/CreateEmployee/","/api/v1/employee/registerEmployee/**","/api/v1/AssetRequest/addStatus").permitAll()
        .anyRequest().authenticated()
      )
      .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .addFilterBefore(new JwtAuthenticationFilter(jwtservice), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authentication -> {
      try {
        return authenticationManager().authenticate(authentication);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}


