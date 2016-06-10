package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the icdcdiagnose database table.
 * 
 */
@Entity
@NamedQuery(name="Icdcdiagnose.findAll", query="SELECT i FROM Icdcdiagnose i")
public class Icdcdiagnose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	//bi-directional many-to-one association to Diagnose
	@OneToMany(mappedBy="icdcdiagnose")
	private List<Diagnose> diagnoses;

	public Icdcdiagnose() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Diagnose> getDiagnoses() {
		return this.diagnoses;
	}

	public void setDiagnoses(List<Diagnose> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public Diagnose addDiagnos(Diagnose diagnos) {
		getDiagnoses().add(diagnos);
		diagnos.setIcdcdiagnose(this);

		return diagnos;
	}

	public Diagnose removeDiagnos(Diagnose diagnos) {
		getDiagnoses().remove(diagnos);
		diagnos.setIcdcdiagnose(null);

		return diagnos;
	}

}