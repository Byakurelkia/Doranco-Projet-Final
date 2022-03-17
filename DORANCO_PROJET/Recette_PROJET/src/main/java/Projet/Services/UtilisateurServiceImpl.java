package Projet.Services;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import Projet.Entity.Role;
import Projet.Entity.Utilisateur;
import Projet.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {
	private final UtilisateurRepository utilisateurRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public String desactiverActiverUtilisateur(Long idUtilisateur) {
		String retour;
		if(utilisateurRepository.findById(idUtilisateur).isEmpty())
			retour = "L'utilisateur spécifié n'existe pas dans la base de données!";
		else {
			Utilisateur userASupp = utilisateurRepository.findById(idUtilisateur).get();
			if(userASupp.isActif()==false) {
				userASupp.setActif(true);
				utilisateurRepository.save(userASupp);
				retour = "L'utilisateur a été activé";}
			else {
				userASupp.setActif(false);
				utilisateurRepository.save(userASupp);
			retour = "L'utilisateur a été désactivé avec succès!";}
		}
		return retour;
	}

@Override
public String modifProfil(Utilisateur utilisateur, Long idUtilisateur, Authentication authResult) {
	String retour;
	String userMail = authResult.getPrincipal().toString();
	Utilisateur userAModifier = utilisateurRepository.findById(idUtilisateur).get();
	if(userAModifier.equals(null)) {
	retour = "L'utilisateur avec l'id précisé n'existe pas dans la base de données!";
	}else if (!userAModifier.getMail().equals(userMail)) {
		retour = "L'utilisateur avec l'id saisi ne vous appartient pas, vous ne pouvez la modifier!";
	} else {
		userAModifier.setDateModification(new Date());
		userAModifier.setMail(utilisateur.getMail());
		userAModifier.setNom(utilisateur.getNom());
		userAModifier.setMdp(passwordEncoder.encode(utilisateur.getMdp()));
		userAModifier.setActif(true);
		userAModifier.setRole(Role.USER);
		utilisateurRepository.save(userAModifier);
		retour = "L'utilisateur a été modifié avec succès!";
	}
	return retour;
}

@Override//MARCHE
public String creerUserCompte(Utilisateur utilisateur) {
	String retour;
	Utilisateur resultat = utilisateurRepository.findByMail(utilisateur.getMail());
	if(resultat == null) {
		utilisateur.setDateCreation(new Date());
		utilisateur.setDateModification(new Date());
		utilisateur.setMdp(passwordEncoder.encode(utilisateur.getMdp()));
		utilisateur.setActif(true);
		utilisateur.setRole(Role.USER);
		utilisateurRepository.save(utilisateur);
		retour = "Le compte utilisateur a été crée, vous pouvez vous connecter pour accéder aux services!";
	}else {
		retour = "Un utilisateur existe avec l'adresse mail indiquée! Veuillez vous connecter ou changer de mail..";
	}
	return retour;
}

@Override
public String modifProfilAdmin(Utilisateur utilisateur, Long idUtilisateur) {
	String retour;
	Utilisateur userAModifier = utilisateurRepository.findById(idUtilisateur).get();
	if(userAModifier.equals(null)) {
	retour = "L'utilisateur avec l'id précisé n'existe pas dans la base de données!";
	} else {
		userAModifier.setDateModification(new Date());
		userAModifier.setMail(utilisateur.getMail());
		userAModifier.setNom(utilisateur.getNom());
		userAModifier.setMdp(passwordEncoder.encode(utilisateur.getMdp()));
		userAModifier.setActif(true);
		userAModifier.setRole(utilisateur.getRole());
		utilisateurRepository.save(userAModifier);
		retour = "L'utilisateur a été modifié avec succès!";
	}
	return retour;
}


@Override
public String creerCompteAdmin(Utilisateur utilisateur) {
 
	String retour;
	Utilisateur resultat = utilisateurRepository.findByMail(utilisateur.getMail());
	if(resultat == null) {
		utilisateur.setDateCreation(new Date());
		utilisateur.setDateModification(new Date());
		utilisateur.setMdp(passwordEncoder.encode(utilisateur.getMdp()));
		utilisateur.setActif(true);
		utilisateur.setRole(utilisateur.getRole());
		utilisateurRepository.save(utilisateur);
		retour = "Le compte utilisateur a été crée, vous pouvez vous connecter pour accéder aux services!";
	}else {
		retour = "Un utilisateur existe avec l'adresse mail indiquée! Veuillez vous connecter ou changer de mail..";
	}
	return retour;
}

@Override
public Utilisateur detailsUtilisateurUser(Long idUtilisateur, Authentication authResult) {
	Utilisateur user = null;
	String userMail = authResult.getPrincipal().toString();
	String userRole = authResult.getAuthorities().toString();
	Optional<Utilisateur> utilisateurTrouve = utilisateurRepository.findById(idUtilisateur);
	if(utilisateurTrouve.isPresent())
	{
	  Utilisateur utilisateurTrouve2 = utilisateurTrouve.get();
	  if(utilisateurTrouve.equals(null))
		user = null;
	  else if(userRole.equals("[ROLE_ADMIN]"))
		user = utilisateurTrouve2;
	  else if(userRole.equals("[ROLE_USER]") && utilisateurTrouve2.getMail().equals(userMail))
		user = utilisateurTrouve2;
	  else if(userRole.equals("[ROLE_USER]") && !utilisateurTrouve2.getMail().equals(userMail))
	 	user = null;}
	else
		user = null;
	return user;
}

}


