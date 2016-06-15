package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Icdcdiagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class JpaPatientRepository implements PatientRepository {

	EntityManager entityManager;

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			EntityManagerFactory emfactory = Persistence
					.createEntityManagerFactory("my-app");
			entityManager = emfactory.createEntityManager();
			return entityManager;
		}
		return entityManager;
	}

	public void persist(Patient patient) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(patient);
		em.getTransaction().commit();
	}

	public Patient update(Patient patient) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Patient p = em.merge(patient);
		em.getTransaction().commit();
		return p;
	}

	public void delete(int patientId) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Patient p = em.find(Patient.class, patientId);
		em.remove(p);
		em.getTransaction().commit();
	}

	public void deleteContact(int contactId) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Contact c = em.find(Contact.class, contactId);
		em.remove(c);
		em.getTransaction().commit();
	}

	public Patient get(int patientId) {
		EntityManager em = getEntityManager();
		Patient p = em.find(Patient.class, patientId);
		return p;
	}

	public List<Patient> find(String surename, String firstname,
			Date birthdate, String assuranceNr) {
		List<Patient> patienten;
		EntityManager em = getEntityManager();
		TypedQuery<Patient> query;
		if (birthdate == null) {
			query = em.createNamedQuery("Patient.findByNameAndSVNr",
					Patient.class);
		} else {
			query = em.createNamedQuery("Patient.findByNameAndSVNrAndGebD",
					Patient.class);
			query.setParameter("birthdate", birthdate);
		}
		query.setParameter("surename", surename);
		query.setParameter("firstname", firstname);
		query.setParameter("assuranceNr", assuranceNr);

		patienten = query.getResultList();

		return patienten;
	}

	@Override
	public List<Compendiummedicament> getCompdeniumMedicaments() {
		List<Compendiummedicament> compendiumMedications;
		EntityManager em = getEntityManager();
		TypedQuery<Compendiummedicament> query;
		query = em.createNamedQuery("Compendiummedicament.findAll",
				Compendiummedicament.class);
		compendiumMedications = query.getResultList();
		return compendiumMedications;
	}

	public Compendiummedicament getCompendiumMedicament(int id) {
		EntityManager em = getEntityManager();
		Compendiummedicament medication = em.find(Compendiummedicament.class,
				id);
		return medication;
	}

	@Override
	public void deleteMedicament(int medicamentId) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Medicament medicament = em.find(Medicament.class, medicamentId);
		em.remove(medicament);
		em.getTransaction().commit();
	}

	@Override
	public Medicament getMedicamentById(int medicamentId) {
		EntityManager em = getEntityManager();
		Medicament medicament = em.find(Medicament.class, medicamentId);
		return medicament;
	}

	@Override
	public Medicament updateMedicament(Medicament medicament) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Medicament medi = em.merge(medicament);
		em.getTransaction().commit();
		return medi;
	}

	@Override
	public Medicament persistMedicament(Medicament medicament) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Medicament m = em.merge(medicament);
		em.getTransaction().commit();
		return m;
	}

	@Override
	public Diagnose persistDiagnose(Diagnose diagnose) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Diagnose d = em.merge(diagnose);
		em.getTransaction().commit();
		return d;
	}

	@Override
	public Diagnose updateDiagnose(Diagnose diagnose) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Diagnose d = em.merge(diagnose);
		em.getTransaction().commit();
		return d;
	}

	@Override
	public void deleteDiagnose(int id) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Diagnose diagnose = em.find(Diagnose.class, id);
		em.remove(diagnose);
		em.getTransaction().commit();
	}

	@Override
	public List<Icdcdiagnose> getIcdcDiagnoses() {
		List<Icdcdiagnose> icdcDiagnoses;
		EntityManager em = getEntityManager();
		TypedQuery<Icdcdiagnose> query;
		query = em.createNamedQuery("Icdcdiagnose.findAll", Icdcdiagnose.class);
		icdcDiagnoses = query.getResultList();
		return icdcDiagnoses;
	}

	@Override
	public Icdcdiagnose getIcdcDiagnose(int id) {
		EntityManager em = getEntityManager();
		Icdcdiagnose diagnose = em.find(Icdcdiagnose.class, id);
		return diagnose;
	}

	@Override
	public Diagnose getDiagnoseById(int diagnoseId) {
		EntityManager em = getEntityManager();
		Diagnose diagnose = em.find(Diagnose.class, diagnoseId);
		return diagnose;
	}
}
