package Projet.Services;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import Projet.Entity.Recette;

public interface RecetteService {
	
	//USER
	//List<Recette> listerAllRecette();
	String supprimRecetteAdmin(Long idRecette);
	String supprimRecetteUser(Long idRecette, Authentication authResult);
	String modifRecetteAdmin(Recette recette, Long idRecette);
	String modifRecetteUser(Recette recette, Long idRecette, Authentication authResult);
	String creerRecette(Recette recette, Authentication authResult);
	String modifImageRecetteUser(MultipartFile referenceImage, Long idRecette, Authentication authentication);
	String modifImageAdmin (MultipartFile referenceImage, Long idRecette);
	String afficherImgUser(Long idRecette, Authentication authentication);
	String afficherImgAdmin(Long idRecette);
}
