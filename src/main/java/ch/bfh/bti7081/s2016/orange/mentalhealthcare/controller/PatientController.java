package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class PatientController {
	PatientRepository repository;
	
	public PatientController() {
		repository=PatientRepositoryFactory.createJpaRepository();
	}
	
	public void create(Patient p){
		repository.persist(p);
	}
	
	public void update(Patient p){
		repository.update(p);
	}
	
	public void delete(Patient p){
		repository.delete(p.getId());
	}

}
