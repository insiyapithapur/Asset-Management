package com.elecon.asset_mgt.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.elecon.asset_mgt.utils.jwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final jwtService jwtservice;
    @Autowired
    public JwtAuthenticationFilter(jwtService jwtservice) {
        this.jwtservice = jwtservice;
    }

    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        System.out.println("authheader "+authHeader);

        if (authHeader == null || authHeader.startsWith("Bearer ") && request.getRequestURI().equals("/api/v1/employee/login")) {
            System.out.println("in if");
            System.out.println(authHeader == null || !authHeader.startsWith("Bearer ") && !request.getRequestURI().equals("/api/v1/employee/login"));
            System.out.println(authHeader == null);
//        System.out.println(!authHeader.startsWith("Bearer "));
//        System.out.println(!request.getRequestURI().equals("/api/v1/employee/login"));
            filterChain.doFilter(request, response);
            System.out.println("filterchain");
            return;
        }
        System.out.println("after if");

        final String token = authHeader.substring(7);
        System.out.println("token"+token);

        try {
            // Change this to your secret key
            String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
            System.out.println(jwtservice.isTokenValid(token));
            if (jwtservice.isTokenValid(token)) {
                System.out.println("in if of filter");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

            String employeeName = claims.get("sub", String.class);
            System.out.println(employeeName);
            String role = claims.get("user_role", String.class);
            String employeeCode = claims.get("employeeCode", String.class);
            request.setAttribute("userRole", role);
            // Here, you may want to load user details from your database based on the extracted information
            UserDetails userDetails = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    System.out.println("granted authority");
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));
                    return authorities;
                }

                @Override
                public String getPassword() {
                    return null; // Do not expose password
                }

                @Override
                public String getUsername() {
                    return employeeName;
                }

                // Implement other methods as required

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
            System.out.println("before" + (employeeName != null));
            System.out.println("before" + (SecurityContextHolder.getContext().getAuthentication() == null));

            if (employeeName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("in if employeename != null");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        employeeCode, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT expired: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Error processing JWT: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Error processing JWT: " + e.getMessage());
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("in getauth");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        System.out.println("out getauth");
        filterChain.doFilter(request, response);
        System.out.println("filter");
    }
}