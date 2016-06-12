package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class JpaPatientRepository implements PatientRepository {

	EntityManager entityManager;

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("my-app");
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

	public Patient get(int patientId) {
		EntityManager em = getEntityManager();
		Patient p = em.find(Patient.class, patientId);
		return p;
	}

	public List<Patient> find(String surename, String firstname, Date birthdate, String assuranceNr) {
		List<Patient> patienten;
		EntityManager em = getEntityManager();
		TypedQuery<Patient> query;
		if (birthdate == null) {
			query = em.createNamedQuery("Patient.findByNameAndSVNr", Patient.class);
		} else {
			query = em.createNamedQuery("Patient.findByNameAndSVNrAndGebD", Patient.class);
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
		query=em.createNamedQuery("Compendiummedicament.findAll",Compendiummedicament.class);
		compendiumMedications=query.getResultList();
		return compendiumMedications;
	}
	
	public Compendiummedicament getCompendiumMedicament(int id) {
		EntityManager em = getEntityManager();
		Compendiummedicament medication = em.find(Compendiummedicament.class, id);
		return medication;
	}
}
