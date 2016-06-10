package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the doctor database table.
 * 
 */
@Entity
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d")
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String adress;

	private String name;

	private String password;

	private String specialisation;

	private String username;

	//bi-directional many-to-one association to Diagnose
	@OneToMany(mappedBy="doctor")
	private List<Diagnose> diagnoses;

	//bi-directional many-to-one association to Medicament
	@OneToMany(mappedBy="doctor")
	private List<Medicament> medicaments;

	public Doctor() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSpecialisation() {
		return this.specialisation;
	}

	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Diagnose> getDiagnoses() {
		return this.diagnoses;
	}

	public void setDiagnoses(List<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public Diagnose addDiagnos(Diagnose diagnos) {
		getDiagnoses().add(diagnos);
		diagnos.setDoctor(this);

		return diagnos;
	}

	public Diagnose removeDiagnos(Diagnose diagnos) {
		getDiagnoses().remove(diagnos);
		diagnos.setDoctor(null);

		return diagnos;
	}

	public List<Medicament> getMedicaments() {
		return this.medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public Medicament addMedicament(Medicament medicament) {
		getMedicaments().add(medicament);
		medicament.setDoctor(this);

		return medicament;
	}

	public Medicament removeMedicament(Medicament medicament) {
		getMedicaments().remove(medicament);
		medicament.setDoctor(null);

		return medicament;
	}

}