package Projet.Services;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Projet.Entity.Recette;
import Projet.Entity.Utilisateur;
import Projet.Repository.RecetteRepository;
import Projet.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecetteServiceImpl implements RecetteService{
	
	public static int nmbEntite = 2;
	private final RecetteRepository recetteRepository;
	private final UtilisateurRepository utilisateurRepository;



@Override
public String supprimRecetteAdmin(Long idRecette) {
	String retour;
	if (recetteRepository.findById(idRecette).isEmpty())
		retour = "La recette précisé n'existe pas!";
	else {
		recetteRepository.deleteById(idRecette);
		retour = "La recette a été supprimé avec succès de la base de données!";
	}
	return retour;
}

@Override
public String modifRecetteAdmin(Recette recette, Long idRecette) {
	String retour = null;
	if (recetteRepository.findById(idRecette) == null) {
		retour = "Recette non existant";
	}else {
		recetteRepository.findById(idRecette).ifPresent(recetteAModifier ->{
		recetteAModifier.setDateModification(new Date());
		recetteAModifier.setDescription(recette.getDescription());
		recetteAModifier.setLibelle(recette.getLibelle());
		recetteRepository.save(recetteAModifier);
	});
		retour = "Recette modifié avec succès!";
	}
	
	return retour;
}

@Override//MARCHE
public String creerRecette(Recette recette, Authentication authResult) {
	String retour;
	String userMail = authResult.getPrincipal().toString();
	Utilisateur resultat = utilisateurRepository.findByMail(userMail);
	if  (resultat == null)
		retour = "L'utilisateur n'existe pas, vous ne pouvez pas sauvegarder cette recette";
	else {
		recette.setDateModification(new Date());
		recette.setDateCreatione(new Date());
		recette.setUtilisateur(resultat);
		recette.setReferenceImage(recette.getReferenceImage());
		recetteRepository.save(recette);
	    retour= " La recette a bien été crée!";
	}
	return retour;
}

@Override//MARCHE 
public String modifRecetteUser(Recette recette, Long idRecette, Authentication authResult) {
	String retour = null;
	String userMail = authResult.getPrincipal().toString();
	Optional<Recette> resultat = recetteRepository.findById(idRecette);
	if(resultat.isEmpty())
		retour = "La recette d'existe pas!";
	else {
		Recette recetteATrouver = resultat.get();
		if (recetteATrouver.getUtilisateur().getMail().equals(userMail)) {
			recetteATrouver.setDateModification(new Date());
			recetteATrouver.setLibelle(recette.getLibelle());
			recetteATrouver.setDescription(recette.getDescription());
			recetteATrouver.setReferenceImage(recette.getReferenceImage());
			recetteRepository.save(recetteATrouver);
			retour="La recette a été modifié";
		}else {
			retour = "La recette que vous avez indiqué ne vous appartient pas, vous ne pouvez la modifier.";
		}
	}
	return retour;
}


@Override
public String supprimRecetteUser(Long idRecette, Authentication authResult) {
	String retour;
	String userMail = authResult.getPrincipal().toString();
	Optional<Recette> resultat = recetteRepository.findById(idRecette);
	if(resultat.isEmpty())
		retour = "La recette précisé n'existe pas!";
	else {
		Recette recetteASupprimer = resultat.get();
		if(recetteASupprimer.getUtilisateur().getMail().equals(userMail)) {
			recetteRepository.deleteById(idRecette);
			retour = "Votre recette a bien été supprimé!";
		}else {
			retour = "La recette ne vous appartient pas! vous ne pouvez la modifier.";
		}
	}
	return retour;
}

@Override
public String modifImageRecetteUser(MultipartFile file, Long idRecette, Authentication authentication) {
	String retour;
	String userMail = authentication.getPrincipal().toString();
	Optional<Recette> recette = recetteRepository.findById(idRecette);
	if(recette.isEmpty())
		retour = "La recette avec l'id indiqué n'existe pas!";
	else {
		Recette recetteATrouver = recette.get();
		if(recetteATrouver.getUtilisateur().getMail().equals(userMail)) {
			try {
				recetteATrouver.setReferenceImage(Base64.getEncoder().encodeToString(file.getBytes()));
				recetteRepository.save(recetteATrouver);
			} catch (IOException e) {
				e.printStackTrace();
			}
			retour = "L'image a été sauvegardé!";
		}else {
			retour = "La recette avec l'id indiqué ne vous appartient pas, vous ne pouvez modifier son image!";
		}
	}
	return retour;
}

@Override
public String modifImageAdmin(MultipartFile file, Long idRecette) {
	String retour;
	Optional<Recette> recette = recetteRepository.findById(idRecette);
	if(recette.isEmpty())
		retour= "La recette avec l'id saisi n'existe pas !";
	else {
		recetteRepository.findById(idRecette).ifPresent(p -> {
		try {
			p.setReferenceImage(Base64.getEncoder().encodeToString(file.getBytes()));
			recetteRepository.save(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	});
	retour = "valid file saved";
	}
	return retour;
}

@Override
public String afficherImgUser(Long idRecette, Authentication authentication) {
	String retour;
	String userMail = authentication.getPrincipal().toString();
	Optional<Recette> recette = recetteRepository.findById(idRecette);
	if (recette.isEmpty())
		retour = "La recette avec l'id saisi n'existe pas!";
	else {
		Recette recetteATrouver = recette.get();
		if(recetteATrouver.getUtilisateur().getMail().equals(userMail)) {
			String imgRecette = recetteATrouver.getReferenceImage();
			retour = "<img src=\"data:image/jpeg;base64,"+imgRecette+"\" width=\"500\" height=\"500\">";
		}else {
			retour = "La recette avec l'id saisi ne vous appartient pas, vous ne pouvez afficher son image!";
		}
	}
	return retour;
}

@Override
public String afficherImgAdmin(Long idRecette) {
	String retour;
		Optional<Recette> recette = recetteRepository.findById(idRecette);
		if(recette.isEmpty()) {
			retour = "La recette avec l'id indiqué n'existe pas!";
		}else {
			Recette imgRecette = recette.get();
			String img = imgRecette.getReferenceImage();
			retour = "<img src=\"data:image/jpeg;base64,"+img+"\" width=\"500\" height=\"500\">";
		}
		return retour ;

}}