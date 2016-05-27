package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public interface PatientRepository {

	public void persist(Patient patient);

	public Patient update(Patient patient);

	public void delete(int patientId);

	public Patient get(int patientId);

	public List<Patient> find(String name, String vorname,Date birth,String svNr);

}
