package com.AuthBack.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.AuthBack.springboot.utils.RsaKeyProprities;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	private final RsaKeyProprities keys;
	
	@Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	
	public WebSecurity(RsaKeyProprities keys) {
		this.keys= keys;
	}

	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( (authorize) ->{
                	
                        authorize.requestMatchers("/auth/**").permitAll();
                        authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                        authorize.requestMatchers("/user/**").hasAnyRole("USER","ADMIN");
                        authorize.anyRequest().authenticated();
                }
                );
             
                http 
                .oauth2ResourceServer()
                // kotlou rao maash https basics , behs noualy nekhedmou bel Jwt donc expecti Jwt w 7adher rouhek
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
                
                http 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                /*
                 * ici maash besh nekhdmou b normal session w stocki session ID fil coockies
                 * => statless policy yaani will not create or use http session to store user authentication state
                 * =>kol request behs tetkhdem wahad'hom carrément ama a travers Jwt token kol mara 
                 * => yaani mode connécté aamel rouhou deconnécté  
                 */
               return  http.build();
    } 
	
	
	@Bean
	public AuthenticationManager authManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();  
		// UserDetailsService is used by DaoAuthenticationProvider for retrieving a username, a password, 
		// and other attributes for authenticating with a username and password
		daoProvider.setUserDetailsService(userDetailsService);
		/*
		 * The userDetailsService is an object responsible for loading user-specific data 
		 * (usually related to authentication and authorization) from a data source
		 */
		daoProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(daoProvider);
	}
		
		
		@Bean
		public JwtDecoder jwtDecoder() {
			return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
		}
		
		@Bean 
		public JwtEncoder jwtEncoder() {
			JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
			JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
					return new NimbusJwtEncoder(jwks);
					
		}
		 @Bean
		    public JwtAuthenticationConverter jwtAuthenticationConverter(){
		        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
		        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		        return jwtConverter;
		    }
		
	
}
