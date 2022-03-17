package Projet.Security;

import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Projet.Entity.Recette;
import Projet.Entity.Role;
import Projet.Entity.Utilisateur;
import Projet.Repository.RecetteRepository;
import Projet.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {
	private final UtilisateurRepository UTILISATEURREPOSITORY;
	private final PasswordEncoder PASSWORDENCODER;
	private final RecetteRepository RECETTEREPOSITORY;
	
	@Override
	public void run(String... args) throws Exception {
		//UTILISATEURREPOSITORY.save(new Utilisateur(new Date(System.currentTimeMillis()),new Date(),"DENE","aaa@gmail.com",PASSWORDENCODER.encode("1234"),Role.ADMIN,true));
		//UTILISATEURREPOSITORY.save(new Utilisateur("Cris",PASSWORDENCODER.encode("1234"),Role.USER));
		
	}

}
