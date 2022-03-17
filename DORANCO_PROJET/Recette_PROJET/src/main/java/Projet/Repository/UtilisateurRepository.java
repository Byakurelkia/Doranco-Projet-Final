package Projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Projet.Entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	Utilisateur findByMail (String mail);

}
