package Projet.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Projet.Entity.Utilisateur;
import Projet.Services.RecetteServiceImpl;
import Projet.Services.UtilisateurServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/create")
@RequiredArgsConstructor
public class AllRestController {
	private final UtilisateurServiceImpl utilisateurServiceImpl;
	private final RecetteServiceImpl recetteServiceImpl;
	
	
	@PostMapping//MARCHE 
	public String creerCompte(@RequestBody Utilisateur utilisateur) {
		return utilisateurServiceImpl.creerUserCompte(utilisateur);
	}
	
	@GetMapping("/recette/image/{id}")
	@ResponseBody//MARCHE, ON LE VOIT SUR LA PAGE HTML DU NAVIGATEUR(C'était seulement pour montrer que la méthode marche, sinon il a pas sa place ici)
	public String afficherImgRecette(@PathVariable(value="id")Long idRecette) {
			return recetteServiceImpl.afficherImgAdmin(idRecette);
	}

}
