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

	public void saveContact(int id, String name, String address, String telNr, String type) {
		Patient patient = repository.get(id);

		Kontakt contact = new Kontakt();
		contact.setName(name);
		contact.setAdresse(address);
		contact.setTelefonNr(telNr);
		contact.setTyp(type);

		patient.addKontakt(contact);

		repository.persist(patient);
	}

	public void deleteContact(int id, Kontakt contact) {
		Patient patient = repository.get(id);
		List<Kontakt> contacts = patient.getKontakts();
		contacts.remove(contact);

		repository.update(patient);
	}
}
