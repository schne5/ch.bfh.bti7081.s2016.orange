package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the kontakt database table.
 * 
 */
@Entity
@NamedQuery(name="Kontakt.findAll", query="SELECT k FROM Kontakt k")
public class Kontakt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	private String name;

	private String telefonNr;

	private String typ;

	//bi-directional many-to-one association to Patient
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="patientId")
	private Patient patient;

	public Kontakt() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefonNr() {
		return this.telefonNr;
	}

	public void setTelefonNr(String telefonNr) {
		this.telefonNr = telefonNr;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}