package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the compendiummedikament database table.
 * 
 */
@Entity
@NamedQuery(name="Compendiummedikament.findAll", query="SELECT c FROM Compendiummedikament c")
public class Compendiummedikament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-one association to Medikament
	@OneToMany(mappedBy="compendiummedikament")
	private List<Medikament> medikaments;

	public Compendiummedikament() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Medikament> getMedikaments() {
		return this.medikaments;
	}

	public void setMedikaments(List<Medikament> medikaments) {
		this.medikaments = medikaments;
	}

	public Medikament addMedikament(Medikament medikament) {
		getMedikaments().add(medikament);
		medikament.setCompendiummedikament(this);

		return medikament;
	}

	public Medikament removeMedikament(Medikament medikament) {
		getMedikaments().remove(medikament);
		medikament.setCompendiummedikament(null);

		return medikament;
	}

}