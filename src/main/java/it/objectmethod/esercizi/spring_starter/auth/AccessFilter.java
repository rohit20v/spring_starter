package it.objectmethod.esercizi.spring_starter.auth;

import it.objectmethod.esercizi.spring_starter.controller.controllerAdvice.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

@Component
@Order(1)
public class AccessFilter extends OncePerRequestFilter {
    private static final String AUTH_ENDPOINT = "/api/auth";
    private static final String ADMIN_ROLE = "1";
    private static final Map<String, String> ROLE_PATH_MAPPING = Map.of(
            "2", "/api/actor",
            "3", "/api/film",
            "4", "/api/director"
    );

    private final JwtTokenProvider jwtTokenProvider;

    public AccessFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /*
         * [Original Comment Preserved]
         * Used by front-end to make sure that backend is working (operation of pre-flight)
         * A preflight request is an automatic browser initiated OPTIONS request that
         * occurs before certain cors-origin requests to ensure that backend/server is working,
         * so that server accepts the upcoming request method, header and credentials.
         */
        if (isPreflightRequest(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = getJwtToken(request);
        authenticate(jwtToken);

        List<String> userRoles = extractUserRoles(jwtToken);

        authorizeAccess(request, userRoles);

        filterChain.doFilter(request, response);
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String requestURI = request.getRequestURI() != null ? request.getRequestURI() : "";
        return "GET".equalsIgnoreCase(request.getMethod()) || requestURI.startsWith(AUTH_ENDPOINT);
    }

    private String getJwtToken(HttpServletRequest request) {
        final String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new UnauthorizedException("Missing authentication token", HttpStatus.UNAUTHORIZED);
        }
        return jwtToken;
    }

    private void authenticate(String jwtToken) {
        if (!jwtTokenProvider.isValid(jwtToken) || jwtTokenProvider.isTokenExpired(jwtToken)) {
            throw new UnauthorizedException("User is not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    private List<String> extractUserRoles(String jwtToken) {
        String roleClaim = jwtTokenProvider.extractRoleFromClaims(jwtToken);
        System.out.println("roles -> " + roleClaim);
        return (roleClaim != null && !roleClaim.isEmpty()) ?
                Arrays.asList(roleClaim.split(",")) :
                emptyList();
    }

    private void authorizeAccess(HttpServletRequest request, List<String> userRoles) {
        String requestURI = request.getRequestURI() != null ? request.getRequestURI() : "";

        if (userRoles.contains(ADMIN_ROLE)) {
            return;
        }

        boolean hasAccess = userRoles.stream()
                .anyMatch(role -> ROLE_PATH_MAPPING.containsKey(role) &&
                                  requestURI.startsWith(ROLE_PATH_MAPPING.get(role)));

        if (!hasAccess) {
            throw new UnauthorizedException("User is not allowed to perform this operation", HttpStatus.FORBIDDEN);
        }
    }
}