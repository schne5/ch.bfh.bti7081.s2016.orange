package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class JpaArztRepository implements ArztRepository {

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
	public Arzt findArztInfo(String username, String password) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT a From Arzt a Where a.username = :username AND a.password = :password ");
			q.setParameter("username", username);
			q.setParameter("password", password);

			Arzt a = (Arzt) q.getSingleResult();

			return a;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Arzt get(int arztId) {
		EntityManager em = getEntityManager();
		Arzt a = em.find(Arzt.class, arztId);
		return a;
		
		// TODO Auto-generated method stub
	}

}