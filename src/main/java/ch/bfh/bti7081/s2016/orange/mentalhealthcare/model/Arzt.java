package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the arzt database table.
 * 
 */
@Entity
@NamedQuery(name="Arzt.findAll", query="SELECT a FROM Arzt a")
public class Arzt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	private String name;

	private String spezialgebiet;
	
	//private String username;
	
	//private String password;

	//bi-directional many-to-one association to Diagnose
	@OneToMany(mappedBy="arzt")
	private List<Diagnose> diagnoses;

	//bi-directional many-to-one association to Medikament
	@OneToMany(mappedBy="arzt")
	private List<Medikament> medikaments;

	public Arzt() {
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

	public String getSpezialgebiet() {
		return this.spezialgebiet;
	}

	public void setSpezialgebiet(String spezialgebiet) {
		this.spezialgebiet = spezialgebiet;
	}

	public List<Diagnose> getDiagnoses() {
		return this.diagnoses;
	}

	public void setDiagnoses(List<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public Diagnose addDiagnos(Diagnose diagnos) {
		getDiagnoses().add(diagnos);
		diagnos.setArzt(this);

		return diagnos;
	}

	public Diagnose removeDiagnos(Diagnose diagnos) {
		getDiagnoses().remove(diagnos);
		diagnos.setArzt(null);

		return diagnos;
	}

	public List<Medikament> getMedikaments() {
		return this.medikaments;
	}

	public void setMedikaments(List<Medikament> medikaments) {
		this.medikaments = medikaments;
	}

	public Medikament addMedikament(Medikament medikament) {
		getMedikaments().add(medikament);
		medikament.setArzt(this);

		return medikament;
	}

	public Medikament removeMedikament(Medikament medikament) {
		getMedikaments().remove(medikament);
		medikament.setArzt(null);

		return medikament;
	}

}