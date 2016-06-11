package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepositoryFactory;

public class LoginController {
	Doctor doctor = new Doctor();
	ArztRepository repository;

	public LoginController() {
		repository = ArztRepositoryFactory.createJpaRepository();
	}

	//Controll if the username and password are in database
	public Doctor logIn(String username, String password) {
		this.doctor = repository.findDoctortInfo(username, password);/
		
		if (doctor != null) {
			if (username.equalsIgnoreCase(Doctor.getUsername()) && password.equals(doctor.getPassword())) {
				return doctor;
			}
		}
		return null;
	}
}
