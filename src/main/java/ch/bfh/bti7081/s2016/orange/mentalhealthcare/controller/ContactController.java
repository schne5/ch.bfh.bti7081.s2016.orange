package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Kontakt;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class ContactController {
	PatientRepository repository;

	public ContactController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public List<Kontakt> getContacts(int id) {
		Patient patient = repository.get(id);

		return patient.getKontakts();
	}
}
