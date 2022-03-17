package Projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Projet.Entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
	

}
