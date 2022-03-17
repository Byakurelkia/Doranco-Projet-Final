package Projet.RestController;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Projet.Entity.Recette;
import Projet.Entity.Utilisateur;
import Projet.Repository.RecetteRepository;
import Projet.Services.RecetteServiceImpl;
import Projet.Services.UtilisateurServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/secured/admin")
public class AdminRestController {
	private final UtilisateurServiceImpl utilisateurServiceImpl;
	private final RecetteServiceImpl recetteServiceImpl;
	private final RecetteRepository recetteRepository;
	
	@GetMapping("/{id}")//MARCHE
	public Utilisateur detailUtilisateur(@PathVariable(value="id")Long idUser, Authentication authentication) {
		return utilisateurServiceImpl.detailsUtilisateurUser(idUser, authentication);
	}
	
	@PutMapping("/profil/update/{id}")//MARCHE
	public String modifProfilAdmin(@RequestBody Utilisateur utilisateur, @PathVariable(value="id")Long idUtilisateur) {
		return utilisateurServiceImpl.modifProfilAdmin(utilisateur, idUtilisateur);
	}
	
	@GetMapping("/recette/image/{id}")
	@ResponseBody//MARCHE MAIS ON LE VOIT PAS SUR POSTMAN
	public String afficherImgRecette(@PathVariable(value="id")Long idRecette) {
			return recetteServiceImpl.afficherImgAdmin(idRecette);
	}
	
	@PostMapping("/recette/creer")//MARCHE
	public String creerRecette(@RequestBody Recette recette, Authentication authResult) {
		return recetteServiceImpl.creerRecette(recette, authResult);
	}
	
	@PostMapping("/recette/imageModif/{id}")//MARCHE
	public String modifImageAdmin(@PathVariable(value = "id") Long idRecette, @RequestParam("file") MultipartFile file) throws Exception{
		return recetteServiceImpl.modifImageAdmin(file, idRecette);
	
	}
	
	@GetMapping("/recette")//MARCHE
	public Slice<Recette> pagination(Integer page, Integer pageSize){
		Pageable pageable = PageRequest.of(0, RecetteServiceImpl.nmbEntite, Sort.by("libelle"));
		return recetteRepository.findAll(pageable);
	}
	
	
	@DeleteMapping("/profil/desactiver/{id}")//MARCHE
	public String desactiverUtilisateur(@PathVariable(value="id")Long idUtilisateur) {
		return utilisateurServiceImpl.desactiverActiverUtilisateur(idUtilisateur);
	}
	
	@PostMapping("/profil/activer/{id}")//MARCHE
	public String activerUtilisateur(@PathVariable(value="id")Long idUtilisateur) {
		return utilisateurServiceImpl.desactiverActiverUtilisateur(idUtilisateur);
	}
	
	@PutMapping("/recette/update/{id}")//MARCHE
	public String modifierRecetteAdmin(@RequestBody Recette recette, @PathVariable(value="id")Long idRecette) {
		return recetteServiceImpl.modifRecetteAdmin(recette, idRecette);
	}
	
	@DeleteMapping("/recette/supprimer/{id}")//MARCHE
	public String supprimerRecetteAdmin(@PathVariable(value="id")Long idRecette) {
		return recetteServiceImpl.supprimRecetteAdmin(idRecette);
	}
	
	@PostMapping("/profil/creer")//MARCHE
	public String creerCompteAdmin(@RequestBody Utilisateur utilisateur) {
		return utilisateurServiceImpl.creerCompteAdmin(utilisateur);
	}

}
