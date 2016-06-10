package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepositoryFactory;

public class ArztController {

	ArztRepository repository;

	public ArztController() {
		repository = ArztRepositoryFactory.createJpaRepository();
	}


	public Arzt getArztById(int id) {
		Arzt patient = repository.get(id);
		return patient;
	}


}
