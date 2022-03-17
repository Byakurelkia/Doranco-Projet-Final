package Projet.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recette {

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private Long idRecette;
	@Column(updatable = false)
	private Date dateCreatione;
	private Date dateModification;
	private String libelle;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(columnDefinition = "LONGBLOB")
	private String referenceImage;
	
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Utilisateur utilisateur;
	
	@JsonIgnore
	@OneToMany (mappedBy="recette",orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();
	
}
