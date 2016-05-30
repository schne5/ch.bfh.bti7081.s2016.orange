package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medikament database table.
 * 
 */
@Entity
@NamedQuery(name="Medikament.findAll", query="SELECT m FROM Medikament m")
public class Medikament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private short active;

	private String dosis;

	//bi-directional many-to-one association to Arzt
	@ManyToOne
	@JoinColumn(name="arztId")
	private Arzt arzt;

	//bi-directional many-to-one association to Compendiummedikament
	@ManyToOne
	@JoinColumn(name="compMedId")
	private Compendiummedikament compendiummedikament;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patientId")
	private Patient patient;

	public Medikament() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getActive() {
		return this.active;
	}

	public void setActive(short active) {
		this.active = active;
	}

	public String getDosis() {
		return this.dosis;
	}

	public void setDosis(String dosis) {
		this.dosis = dosis;
	}

	public Arzt getArzt() {
		return this.arzt;
	}

	public void setArzt(Arzt arzt) {
		this.arzt = arzt;
	}

	public Compendiummedikament getCompendiummedikament() {
		return this.compendiummedikament;
	}

	public void setCompendiummedikament(Compendiummedikament compendiummedikament) {
		this.compendiummedikament = compendiummedikament;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public String getMedikamentBezeichnung(){
		return compendiummedikament.getName();
	}

}