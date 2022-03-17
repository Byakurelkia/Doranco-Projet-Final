package Projet.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Projet.Entity.Utilisateur;
import Projet.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailsUtilisateursServices implements UserDetailsService {
	
	private final UtilisateurRepository UTILISATEURREPOSITORY;
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Utilisateur utilisateur = UTILISATEURREPOSITORY.findByMail(mail);
		if(utilisateur != null) {
			return new DetailsUtilisateur(utilisateur);
		}
		throw new UsernameNotFoundException(mail + " n'est pas dans la base de données");
	}
}