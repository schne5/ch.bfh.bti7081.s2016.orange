package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.DoctorRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.DoctorRepositoryFactory;

public class LoginController {
	Doctor doctor = new Doctor();
	DoctorRepository repository;

	public LoginController() {
		repository = DoctorRepositoryFactory.createJpaRepository();
	}

	//Controll if the username and password are in database
	public Doctor logIn(String username, String password) {
		this.doctor = repository.findDoctorInfo(username, password);
	
		if (doctor != null) {
			if (username.equalsIgnoreCase(doctor.getUsername()) && password.equals(doctor.getPassword())) {
				return doctor;
			}
		}
		return null;
	}
}
