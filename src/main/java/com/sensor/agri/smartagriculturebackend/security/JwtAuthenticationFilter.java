package com.sensor.agri.smartagriculturebackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");



        if(authHeader == null || !authHeader.startsWith("Bearer")) {

            filterChain.doFilter(request,response);
            return;
        }

//        System.out.println(authHeader);
        final String jwt = authHeader.substring(7);
        final String email = jwtService.extractUsername(jwt);
//        System.out.println("email"+email);

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        if(email !=null  && authentication == null) {
            //Authenticate
            UserDetails userDetails
                    = customUserDetailsService.loadUserByUsername(email);
            if (userDetails instanceof CustomUserDetails customUserDetails) {
                System.out.println("CustomUserDetails email: " + customUserDetails.getEmail());
            }else{
                System.out.println("Not CustomUserDetails email: " );
            }


//            System.out.println("userDetails"+userDetails.getClass().getName());

            if(jwtService.isTokenValid(jwt,userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                System.out.println(authenticationToken);
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
