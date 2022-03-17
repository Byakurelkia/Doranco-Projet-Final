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
@RequestMapping("api/secured")
public class UserRestController {
	private final UtilisateurServiceImpl utilisateurServiceImpl;
	private final RecetteServiceImpl recetteServiceImpl;
	private final RecetteRepository recetteRepository;
	
	
	@GetMapping("/{id}")
	public Utilisateur userEndPoint(@PathVariable(value = "id") Long idUser, Authentication authentication) {
		return utilisateurServiceImpl.detailsUtilisateurUser(idUser, authentication);
	}
	
	@GetMapping("/recette/image/{id}")
	@ResponseBody//MARCHE MAIS ON LE VOITPAS SUR POSTMAN
	public String afficherImgUser(@PathVariable(value="id")Long idRecette, Authentication authentication) {
			return recetteServiceImpl.afficherImgUser(idRecette, authentication);
	}

	@PutMapping("/profil/update/{id}")//MARCHE
	public String modifProfil(@RequestBody Utilisateur utilisateur, @PathVariable(value="id")Long idUtilisateur, Authentication authResult) {
		return utilisateurServiceImpl.modifProfil(utilisateur, idUtilisateur, authResult);
	}
	
	@GetMapping("/recette")//MARCHE
	public Slice<Recette> pagination(Integer page, Integer pageSize){
		Pageable pageable = PageRequest.of(0, RecetteServiceImpl.nmbEntite, Sort.by("DateModification"));
		return recetteRepository.findAll(pageable);
	}
	
	@PostMapping("/recette/creer")//MARCHE
	public String creerRecette(@RequestBody Recette recette, Authentication authResult) {
		return recetteServiceImpl.creerRecette(recette, authResult);
	}
	
	@PutMapping("/recette/update/{id}")//MARCHE 
	public String modifRecetteUser(@RequestBody Recette recette, @PathVariable(value="id") Long idRecette, Authentication authResult) {
		return recetteServiceImpl.modifRecetteUser(recette, idRecette, authResult);
	}
	
	@DeleteMapping("/recette/delete/{id}")//MARCHE
	public String supprimRecetteUser(@PathVariable(value="id") Long idRecetteASupprimer, Authentication authResult) {
		return recetteServiceImpl.supprimRecetteUser(idRecetteASupprimer, authResult);
	}

	@PostMapping("/recette/imageModif/{id}")//MARCHE
	public String modifImageUser(@PathVariable(value = "id") Long idRecette, @RequestParam("file") MultipartFile file, Authentication authentication) throws Exception{
		return recetteServiceImpl.modifImageRecetteUser(file, idRecette, authentication);
	
	}
}
