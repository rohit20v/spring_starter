package it.objectmethod.esercizi.spring_starter.util;

import it.objectmethod.esercizi.spring_starter.auth.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AccessFilter extends OncePerRequestFilter {
    private static final String POST = "POST";
    private static final String AUTH_ENDPOINT = "/api/auth";

    private final JwtTokenProvider jwtTokenProvider;

    public AccessFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String jwtToken = request.getHeader("Authorization");
        String requestURI = request.getRequestURI() != null ? request.getRequestURI() : "";

        if (!requestURI.startsWith(AUTH_ENDPOINT)) {
            if (Objects.isNull(jwtToken) || !isAuthenticated(jwtToken)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Boolean isAuthenticated(final String jwtToken) {
        return jwtTokenProvider.isValid(jwtToken);
    }
}
