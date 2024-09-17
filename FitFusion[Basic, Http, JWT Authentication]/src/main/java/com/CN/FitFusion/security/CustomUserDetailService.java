package com.CN.FitFusion.security;

import com.CN.FitFusion.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * Constructor to initialize the repository.
	 */
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Loads user-specific data by username.
	 * <p>
	 * This method is called by Spring Security during authentication to retrieve user details.
	 * It queries the user repository to find a user by their email address.
	 * </p>
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find user by email and throw exception if not found
		return this.userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
	}
}

