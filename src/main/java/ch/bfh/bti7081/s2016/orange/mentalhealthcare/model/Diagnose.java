package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the diagnose database table.
 * 
 */
@Entity
@NamedQuery(name = "Diagnose.findAll", query = "SELECT d FROM Diagnose d")
public class Diagnose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private short active;

	// bi-directional many-to-one association to Icdcdiagnose
	@ManyToOne
	@JoinColumn(name = "icdcId")
	private Icdcdiagnose icdcdiagnose;

	// bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name = "patientId")
	private Patient patient;

	// bi-directional many-to-one association to Doctor
	@ManyToOne
	@JoinColumn(name = "doctorId")
	private Doctor doctor;

	public Diagnose() {
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

	public Icdcdiagnose getIcdcdiagnose() {
		return this.icdcdiagnose;
	}

	public void setIcdcdiagnose(Icdcdiagnose icdcdiagnose) {
		this.icdcdiagnose = icdcdiagnose;
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

	public String getDiagnoseName() {
		return this.icdcdiagnose.getName();
	}

}