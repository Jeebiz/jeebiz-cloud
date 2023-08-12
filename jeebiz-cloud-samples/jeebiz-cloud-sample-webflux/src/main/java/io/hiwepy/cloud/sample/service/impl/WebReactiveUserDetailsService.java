package io.hiwepy.cloud.sample.service.impl;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import reactor.core.publisher.Mono;

public class WebReactiveUserDetailsService implements ReactiveUserDetailsService {

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		
		UserDetails	userDetails = null;
		User.withUserDetails(userDetails);
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("").password(encoder.encode("")).roles("").build();
		return Mono.just(user);
	}

}
