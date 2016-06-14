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
@NamedQueries({ @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
	@NamedQuery(name = "Patient.findByNameAndSVNrAndGebD", query = "SELECT p FROM Patient p WHERE p.surename LIKE :surename and p.firstname LIKE :firstname and p.assuranceNr LIKE :assuranceNr and p.birthdate = :birthdate"),
	@NamedQuery(name = "Patient.findByNameAndSVNr", query = "SELECT p FROM Patient p WHERE p.surename LIKE :surename and p.firstname LIKE :firstname and p.assuranceNr LIKE :assuranceNr"), })
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String assuranceNr;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String firstname;

	private int state;

	private String surename;

	//bi-directional many-to-one association to Diagnose
	@OneToMany(mappedBy="patient")
	private List<Diagnose> diagnoses;

	//bi-directional many-to-one association to Contact
	@OneToMany(mappedBy="patient")
	private List<Contact> contacts;

	//bi-directional many-to-one association to Medicament
	@OneToMany(mappedBy="patient")
	private List<Medicament> medicaments;

	public Patient() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssuranceNr() {
		return this.assuranceNr;
	}

	public void setAssuranceNr(String assuranceNr) {
		this.assuranceNr = assuranceNr;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSurename() {
		return this.surename;
	}

	public void setSurename(String surename) {
		this.surename = surename;
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

	public List<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setPatient(this);

		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setPatient(null);

		return contact;
	}

	public List<Medicament> getMedicaments() {
		return this.medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public Medicament addMedicament(Medicament medicament) {
		getMedicaments().add(medicament);
		medicament.setPatient(this);

		return medicament;
	}

	public Medicament removeMedicament(Medicament medicament) {
		getMedicaments().remove(medicament);
		medicament.setPatient(null);

		return medicament;
	}
	
	public Medicament getMedicament(int medicamentId){
		for(Medicament m :getMedicaments()){
			if(m.getId() ==medicamentId){
				return m;
			}
		}
		return null;
	}
}