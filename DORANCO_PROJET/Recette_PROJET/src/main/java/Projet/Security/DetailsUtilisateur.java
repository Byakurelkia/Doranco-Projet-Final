package Projet.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import Projet.Entity.Utilisateur;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DetailsUtilisateur implements UserDetails {
	
	private Utilisateur utilisateur;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().toString());
		return Collections.singletonList(grantedAuthority);
	}
	
	public Long getId() {
		return utilisateur.getId();
	}
	
	@Override
	public String getPassword() {
		
		return utilisateur.getMdp();
	}

	@Override
	public String getUsername() {
	
		return utilisateur.getMail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return utilisateur.isActif();
	}

}
