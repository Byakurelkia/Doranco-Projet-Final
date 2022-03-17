package Projet.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter  {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(token != null && !token.isEmpty()) {
			try {
				token = token.replace("Bearer ","");
				DecodedJWT decodedToken =    JWT.require(Algorithm.HMAC256(SecurityProperties.SECRET)).build().verify(token);
				String username = decodedToken.getSubject();
				String role = decodedToken.getClaim("role").asString();
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
				Collection<GrantedAuthority> authorities= new ArrayList<>();
				authorities.add(grantedAuthority);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(username,null, Collections.singletonList(grantedAuthority));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}catch(Exception e) {
				System.out.println("Token invalide ! ");
			}
		}
		chain.doFilter(request, response);
	}
	
	

}
