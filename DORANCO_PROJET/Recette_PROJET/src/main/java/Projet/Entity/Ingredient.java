package Projet.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idIngredient;
	@Column(updatable=false)
    private Date dateCreation;
    private Date dateModification;
	@Column (nullable = false )
	private String libelle;
	private double quantite;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Recette recette;
	
	
}
