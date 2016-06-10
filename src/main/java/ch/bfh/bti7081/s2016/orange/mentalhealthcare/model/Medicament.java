package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the medicament database table.
 * 
 */
@Entity
@NamedQuery(name="Medicament.findAll", query="SELECT m FROM Medicament m")
public class Medicament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private short active;

	private BigDecimal dose;

	private int takings;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patientId")
	private Patient patient;

	//bi-directional many-to-one association to Doctor
	@ManyToOne
	@JoinColumn(name="doctorId")
	private Doctor doctor;

	//bi-directional many-to-one association to Compendiummedicament
	@ManyToOne
	@JoinColumn(name="compMedId")
	private Compendiummedicament compendiummedicament;

	public Medicament() {
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

	public BigDecimal getDose() {
		return this.dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public int getTakings() {
		return this.takings;
	}

	public void setTakings(int takings) {
		this.takings = takings;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Compendiummedicament getCompendiummedicament() {
		return this.compendiummedicament;
	}

	public void setCompendiummedicament(Compendiummedicament compendiummedicament) {
		this.compendiummedicament = compendiummedicament;
	}

}