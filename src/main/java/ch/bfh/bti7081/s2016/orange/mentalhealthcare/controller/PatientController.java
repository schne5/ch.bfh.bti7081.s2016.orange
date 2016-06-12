package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class PatientController {
	PatientRepository repository;

	public PatientController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public boolean create(Patient patient) {
		if (isValid(patient)) {
			repository.persist(patient);
			return true;
		}
		return false;
	}

	public Patient update(Patient patient) {
		if (isValid(patient)) {
			return repository.update(patient);
		}
		return null;
	}

	public void delete(Patient patient) {
		repository.delete(patient.getId());
	}

	public boolean isValid(Patient patient) {
		boolean isValid = !"".equals(patient.getSurename()) && null != patient.getSurename() && patient.getSurename().length() <= 50;
		isValid &= !"".equals(patient.getFirstname()) && null != patient.getFirstname() && patient.getFirstname().length() <= 50;
		isValid &= !"".equals(patient.getAssuranceNr()) && null != patient.getAssuranceNr() && patient.getAssuranceNr().length() <= 25;
		isValid &= null != patient.getBirthdate();
		return isValid;
	}

	public Patient getPatientById(int id) {
		Patient patient = repository.get(id);
		return patient;
	}
}
