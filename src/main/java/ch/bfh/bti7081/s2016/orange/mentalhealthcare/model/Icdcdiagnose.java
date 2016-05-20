package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the icdcdiagnose database table.
 * 
 */
@Entity
@NamedQuery(name="Icdcdiagnose.findAll", query="SELECT i FROM Icdcdiagnose i")
public class Icdcdiagnose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

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

}