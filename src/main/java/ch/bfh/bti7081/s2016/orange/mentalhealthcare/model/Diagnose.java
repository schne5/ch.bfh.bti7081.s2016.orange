package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnose database table.
 * 
 */
@Entity
@NamedQuery(name="Diagnose.findAll", query="SELECT d FROM Diagnose d")
public class Diagnose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private short active;

	//bi-directional many-to-one association to Arzt
	@ManyToOne
	@JoinColumn(name="arztId")
	private Arzt arzt;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patientId")
	private Patient patient;

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

	public Arzt getArzt() {
		return this.arzt;
	}

	public void setArzt(Arzt arzt) {
		this.arzt = arzt;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}