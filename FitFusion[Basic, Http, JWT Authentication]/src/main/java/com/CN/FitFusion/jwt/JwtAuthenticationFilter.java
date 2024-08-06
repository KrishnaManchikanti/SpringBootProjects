package com.CN.FitFusion.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtAuthenticationHelper jwtHelper;

	@Autowired
	UserDetailsService userDetailsService;

	/**
	 * Processes each HTTP request to extract and validate the JWT token.
	 * <p>
	 * - Extracts the JWT token from the "Authorization" header.
	 * - Validates the token and retrieves user details.
	 * - Sets the authentication in the security context if the token is valid.
	 * - Continues the filter chain to handle the request.
	 * </p>
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestHeader = request.getHeader("Authorization");

		// Initialize variables
		String username = null;
		String token = null;

		// Check if the header is present and starts with "Bearer"
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
			// Extract token from header
			token = requestHeader.substring(7);

			// Get username from token
			username = jwtHelper.getUsernameFromToken(token);

			// If username is found and no authentication is set in the context
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// Load user details
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// Check if the token is not expired
				if (!jwtHelper.isTokenExpired(token)) {
					// Create authentication token
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());

					// Set additional details
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					// Set authentication in security context
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		// Continue with the filter chain
		filterChain.doFilter(request, response);
	}
}

