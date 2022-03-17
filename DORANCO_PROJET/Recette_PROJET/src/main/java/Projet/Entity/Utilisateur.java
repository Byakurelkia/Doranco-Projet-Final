package Projet.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Utilisateur implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	@Column(updatable=false)
    private Date dateCreation;
    private Date dateModification;
    @Column(nullable=false)
    private String nom;
    @Column(nullable=false, unique= true)
    private String mail;
	private String mdp;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean isActif;
	
	@JsonIgnore
	@OneToMany (mappedBy = "utilisateur", orphanRemoval = true)
	private List<Recette> recettes = new ArrayList<>();

	public Utilisateur(Date dateCreation, Date dateModification, String nom, String mail, String mdp, Role role,
			boolean isActif) {
		super();
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.nom = nom;
		this.mail = mail;
		this.mdp = mdp;
		this.role = role;
		this.isActif = true;
	
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", dateCreation=" + dateCreation + ", nom=" + nom + ", mail=" + mail
				+ ", role=" + role + "]";
	}

	

}
