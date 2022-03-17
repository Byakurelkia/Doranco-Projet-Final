package Projet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import Projet.Entity.Recette;

public interface RecetteRepository extends JpaRepository<Recette, Long>{
	List<Recette> findAllByUtilisateur_mail (String mail);//ça va retoruner la liste des recettes ayant le mail en parametre
	Recette findByUtilisateurMail(String mail);
	
	
	
}
