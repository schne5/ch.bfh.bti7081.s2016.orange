package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class PatientController {
	PatientRepository repository;

	public PatientController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public void create(Patient p) {
		repository.persist(p);
	}

	public Patient update(Patient p) {
		if (isValid(p)) {
			return repository.update(p);
		}
		// TODO Etwas sinnvolles
		return null;
	}

	public void delete(Patient p) {
		repository.delete(p.getId());
	}

	public boolean isValid(Patient p) {
		boolean isValid = !"".equals(p.getName()) && null != p.getName()
				&& p.getName().length() <= 50;
		isValid &= !"".equals(p.getVorname()) && null != p.getVorname()
				&& p.getName().length() <= 50;
		isValid &= !"".equals(p.getSvNr()) && null != p.getSvNr()
				&& p.getName().length() <= 25;
		isValid &= null != p.getGebDatum();
		return isValid;
	}
}
