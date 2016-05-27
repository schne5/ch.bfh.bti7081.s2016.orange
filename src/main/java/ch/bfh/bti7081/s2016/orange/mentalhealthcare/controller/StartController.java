package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class StartController {
	List<Patient> patients = null;

	PatientRepository repository;

	public StartController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public List<String> getPatients() {
		List<String> list = new ArrayList<String>();

		if (patients == null) {
			list.add("empty");
			return list;
		} else {
			list = new ArrayList<String>();
		}

		for (Patient p : patients) {
			String patientName = p.getName() + " " + p.getVorname();
			list.add(patientName);
		}

		return list;
	}

	public void searchPatient(String lastName, String firstName, String svNr, Date birthDate) {
		lastName = "%" + lastName + "%";
		firstName = "%" + firstName + "%";
		svNr = "%" + svNr + "%";

		patients = repository.find(lastName, firstName, birthDate, svNr);
	}
}
