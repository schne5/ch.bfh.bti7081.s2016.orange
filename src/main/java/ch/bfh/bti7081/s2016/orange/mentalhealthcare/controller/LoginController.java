package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.ArztRepositoryFactory;

public class LoginController {
	Arzt a = new Arzt();
	ArztRepository repository;

	public LoginController() {
		repository = ArztRepositoryFactory.createJpaRepository();
	}

	public Arzt logIn(String username, String password) {
		this.a = repository.findArztInfo(username, password);// (username,
																// passwort)
		if (a != null) {
			if (username.equalsIgnoreCase(a.getUsername()) && password.equals(a.getPassword())) {
				return a;
			}
		}
		return null;
	}
}
