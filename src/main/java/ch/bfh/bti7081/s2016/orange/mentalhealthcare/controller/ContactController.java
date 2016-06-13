package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class ContactController {
	PatientRepository repository;

	public ContactController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public List<Contact> getContacts(int id) {
		Patient patient = repository.get(id);

		return patient.getContacts();
	}

	public void saveContact(int id, String name, String address, String telNr, String type) {
		Patient patient = repository.get(id);

		Contact contact = new Contact();
		contact.setName(name);
		contact.setAdress(address);
		contact.setPhoneNr(telNr);
		contact.setContactType(type);

		patient.addContact(contact);

		repository.persist(patient);
	}

	public void deleteContact(int id, Contact contact) {
		Patient patient = repository.get(id);
		List<Contact> contacts = patient.getContacts();
		contacts.remove(contact);

		repository.deleteContact(contact.getId());
	}
}
