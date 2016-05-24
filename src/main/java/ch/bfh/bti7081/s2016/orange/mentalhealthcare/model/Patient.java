package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the patient database table.
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
		@NamedQuery(name = "Patient.findByNameAndSVNr", query = "SELECT p FROM Patient p WHERE p.name = :name and p.gebDatum = :gebDatum and p.svNr= :svNr"), })
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date gebDatum;

	private String name;

	private int status;

	private String svNr;

	private String vorname;

	// bi-directional many-to-one association to Diagnose
	@OneToMany(mappedBy = "patient")
	private List<Diagnose> diagnoses;

	// bi-directional many-to-one association to Kontakt
	@OneToMany(mappedBy = "patient")
	private List<Kontakt> kontakts;

	// bi-directional many-to-one association to Medikament
	@OneToMany(mappedBy = "patient")
	private List<Medikament> medikaments;

	public Patient(String name, String vorname, String svNr, int status,
			Date gebDatum) {
		this.name = name;
		this.vorname = vorname;
		this.svNr = svNr;
		this.gebDatum = gebDatum;
	}

	Patient() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGebDatum() {
		return this.gebDatum;
	}

	public void setGebDatum(Date gebDatum) {
		this.gebDatum = gebDatum;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSvNr() {
		return this.svNr;
	}

	public void setSvNr(String svNr) {
		this.svNr = svNr;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public List<Diagnose> getDiagnoses() {
		return this.diagnoses;
	}

	public void setDiagnoses(List<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public Diagnose addDiagnos(Diagnose diagnos) {
		getDiagnoses().add(diagnos);
		diagnos.setPatient(this);

		return diagnos;
	}

	public Diagnose removeDiagnos(Diagnose diagnos) {
		getDiagnoses().remove(diagnos);
		diagnos.setPatient(null);

		return diagnos;
	}

	public List<Kontakt> getKontakts() {
		return this.kontakts;
	}

	public void setKontakts(List<Kontakt> kontakts) {
		this.kontakts = kontakts;
	}

	public Kontakt addKontakt(Kontakt kontakt) {
		getKontakts().add(kontakt);
		kontakt.setPatient(this);

		return kontakt;
	}

	public Kontakt removeKontakt(Kontakt kontakt) {
		getKontakts().remove(kontakt);
		kontakt.setPatient(null);

		return kontakt;
	}

	public List<Medikament> getMedikaments() {
		return this.medikaments;
	}

	public void setMedikaments(List<Medikament> medikaments) {
		this.medikaments = medikaments;
	}

	public Medikament addMedikament(Medikament medikament) {
		getMedikaments().add(medikament);
		medikament.setPatient(this);

		return medikament;
	}

	public Medikament removeMedikament(Medikament medikament) {
		getMedikaments().remove(medikament);
		medikament.setPatient(null);

		return medikament;
	}

}