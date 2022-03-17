package Projet.Services;

import org.springframework.security.core.Authentication;

import Projet.Entity.Utilisateur;

public interface UtilisateurService {
	//ALLUSER
	String creerUserCompte(Utilisateur utilisateur);
	//USER
	String modifProfil(Utilisateur utilisateur, Long idUtilisateur, Authentication authResult);
	Utilisateur detailsUtilisateurUser(Long idUtilisateur, Authentication authResult);
	//ADMIN
	String creerCompteAdmin(Utilisateur utilisateur);
	String desactiverActiverUtilisateur(Long idUtilisateur);
	String modifProfilAdmin( Utilisateur utilisateur, Long idUtilisateur);
	
}
