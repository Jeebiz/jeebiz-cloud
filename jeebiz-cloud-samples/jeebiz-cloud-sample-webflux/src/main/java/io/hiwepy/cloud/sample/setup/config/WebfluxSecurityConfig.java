package io.hiwepy.cloud.sample.setup.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebfluxSecurityConfig {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		// logger.info("WebFlux Security begin");
		http.authorizeExchange().pathMatchers("/admin/**").authenticated().pathMatchers("/**").permitAll().and().csrf()
				// .csrfTokenRepository(customCsrfTokenRepository)
				// .requireCsrfProtectionMatcher(customCsrfMatcher)
				.and().formLogin()
				// .loginPage("/login")
				// .authenticationFailureHandler(new
				// RedirectServerAuthenticationFailureHandler("/login?error"))
				// .authenticationSuccessHandler(new
				// RedirectServerAuthenticationSuccessHandler("/admin"))
				.and().logout()
				// .logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandler("/login?logout")).and().build();

		http.authorizeExchange().anyExchange().authenticated().and().httpBasic().and().formLogin();
		return http.build();

	}

	public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
		RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
		successHandler.setLogoutSuccessUrl(URI.create(uri));
		return successHandler;
	}

	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		// 内存中缓存权限数据
		User.UserBuilder userBuilder = User.builder();
		UserDetails admin = userBuilder.username("admin").password(passwordEncoder.encode("123456"))
				.roles("USER", "ADMIN").build();
		// 输出加密密码
		String encodePassword = passwordEncoder.encode("123456");
		//logger.info("encodePassword:" + encodePassword);
		return new MapReactiveUserDetailsService(admin);
	}

}
