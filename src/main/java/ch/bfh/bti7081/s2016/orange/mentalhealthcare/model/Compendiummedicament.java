package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the compendiummedicament database table.
 * 
 */
@Entity
@NamedQuery(name="Compendiummedicament.findAll", query="SELECT c FROM Compendiummedicament c")
public class Compendiummedicament implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal maxDose;

	private int maxDosePerDay;

	private String name;

	//bi-directional many-to-one association to Medicament
	@OneToMany(mappedBy="compendiummedicament")
	private List<Medicament> medicaments;

	public Compendiummedicament() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getMaxDose() {
		return this.maxDose;
	}

	public void setMaxDose(BigDecimal maxDose) {
		this.maxDose = maxDose;
	}

	public int getMaxDosePerDay() {
		return this.maxDosePerDay;
	}

	public void setMaxDosePerDay(int maxDosePerDay) {
		this.maxDosePerDay = maxDosePerDay;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Medicament> getMedicaments() {
		return this.medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public Medicament addMedicament(Medicament medicament) {
		getMedicaments().add(medicament);
		medicament.setCompendiummedicament(this);

		return medicament;
	}

	public Medicament removeMedicament(Medicament medicament) {
		getMedicaments().remove(medicament);
		medicament.setCompendiummedicament(null);

		return medicament;
	}

}