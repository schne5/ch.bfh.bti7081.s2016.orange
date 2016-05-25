package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class StartController {
	List<Patient> patients;

	public List<String> getPatients() {
		// return patients;
		List<String> list = new ArrayList<String>();
		list.add("Test patient");

		return list;
	}

	public void searchPatient(String lastName, String firstName, String svNr, Date birthDate) {
	}
}
