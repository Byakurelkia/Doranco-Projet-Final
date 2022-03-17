package Projet.Security;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginModel loginModel= null;
		try {
			loginModel =  new ObjectMapper().readValue(request.getInputStream(), LoginModel.class);
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());
		return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		DetailsUtilisateur detailsUtilisateur = (DetailsUtilisateur)authResult.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = detailsUtilisateur.getAuthorities();
		String role =  authorities.toArray()[0].toString();
		Date dateExpirationToken = new Date(System.currentTimeMillis()+ 60*60*1000);
		String token = JWT.create().withSubject(detailsUtilisateur.getUsername())
				.withExpiresAt(dateExpirationToken)
				.withIssuedAt(new Date())
				.withClaim("role", role)
				.withClaim("id", detailsUtilisateur.getId())
				.sign(Algorithm.HMAC256(SecurityProperties.SECRET));
		response.addHeader("Authorization", token);
	}
	
}
