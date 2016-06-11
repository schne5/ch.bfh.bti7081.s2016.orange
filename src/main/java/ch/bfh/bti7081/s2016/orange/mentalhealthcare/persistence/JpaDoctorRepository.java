package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;

public class JpaDoctorRepository implements DoctorRepository {

	EntityManager entityManager;

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("my-app");
			entityManager = emfactory.createEntityManager();
			return entityManager;
		}
		return entityManager;

	}

	@Override
	public Doctor findDoctorInfo(String username, String password) {
		EntityManager em = getEntityManager();
		try {
			Query query = em.createQuery("SELECT a From Doctor a Where a.username = :username AND a.password = :password ");
			query.setParameter("username", username);
			query.setParameter("password", password);

			Doctor doctor = (Doctor) query.getSingleResult();

			return doctor;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Doctor get(int doctorId) {
		EntityManager em = getEntityManager();
		Doctor doctor = em.find(Doctor.class, doctorId);
		return doctor;
		
		// TODO Auto-generated method stub
	}

}