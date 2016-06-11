package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.DoctorRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.DoctorRepositoryFactory;

public class DoctorController {

	DoctorRepository repository;

	public DoctorController() {
		repository = DoctorRepositoryFactory.createJpaRepository();
	}


	public Doctor getArztById(int id) {
		Doctor doctor = repository.get(id);
		return doctor;
	}


}
