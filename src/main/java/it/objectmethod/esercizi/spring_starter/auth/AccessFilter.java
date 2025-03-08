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
import java.util.Objects;

import static java.util.Collections.emptyList;

@Component
@Order(1)
public class AccessFilter extends OncePerRequestFilter {
    private static final String AUTH_ENDPOINT = "/api/auth";

    private final JwtTokenProvider jwtTokenProvider;

    private static final Map<String, String> ROLE_PATH_MAPPING = Map.of(
            "2", "/api/actor",
            "3", "/api/film",
            "4", "/api/director"
    );

    public AccessFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        /*
         * Used by front-ent to make sure that backend is working (operation of pre-flight)
         * A preflight request is an automatic browser initiated OPTIONS request that takes
         * occurs before certain cors-origin requests to ensure that backend/server is working,
         * so that server accepts the upcoming request method, header and credentials.
         */
        String requestURI = request.getRequestURI() != null ? request.getRequestURI() : "";
        String httpMethod = request.getMethod();

        if (httpMethod.equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (httpMethod.equalsIgnoreCase("GET") || requestURI.startsWith(AUTH_ENDPOINT)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = request.getHeader("Authorization");
        String roleClaim = "[]";

        if (!Objects.isNull(jwtToken) && !jwtToken.isEmpty()) {
            roleClaim = jwtTokenProvider.extractRoleFromClaims(jwtToken) ==
                        null ? "[]" : jwtTokenProvider.extractRoleFromClaims(jwtToken);
        }

        List<String> userRoles = roleClaim != null ?
                Arrays.asList(roleClaim.split(",")) : emptyList();

        System.out.println("roles -> " + roleClaim);
        if (!requestURI.startsWith(AUTH_ENDPOINT)) {
            if (Objects.isNull(jwtToken) || !isAuthenticated(jwtToken) || Objects.requireNonNull(roleClaim).equalsIgnoreCase("[]")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        boolean isAllowed = userRoles.contains("1") ||  // Admin role
                            userRoles.stream()
                                    .anyMatch(r -> ROLE_PATH_MAPPING.containsKey(r) &&
                                                   requestURI.startsWith(ROLE_PATH_MAPPING.get(r)));

        if (isAllowed) {
            filterChain.doFilter(request, response);
        } else {
            throw new UnauthorizedException("User is not allowed to perform this operations", HttpStatus.FORBIDDEN);
        }
    }

    private Boolean isAuthenticated(final String jwtToken) {
        if (!jwtTokenProvider.isValid(jwtToken) || jwtTokenProvider.isTokenExpired(jwtToken))
            throw new UnauthorizedException("User is not authenticated", HttpStatus.UNAUTHORIZED);

        return true;
    }
}
