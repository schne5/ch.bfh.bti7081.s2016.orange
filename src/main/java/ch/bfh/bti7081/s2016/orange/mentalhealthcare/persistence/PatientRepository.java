package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class PatientRepository {
	
	public void persist(Patient p){
		
	}
	
	public void update(Patient p){
		
	}
	
	public void delete(Patient p){
		
	}
	
	public Patient get(int patientId){
		return new Patient("","","",1,new Date());
	}
	
	public List<Patient> find(){
		return new ArrayList<Patient>();
	}

}
