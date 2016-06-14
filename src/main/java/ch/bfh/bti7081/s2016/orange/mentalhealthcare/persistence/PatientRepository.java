package ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence;
import java.util.Date;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public interface PatientRepository {

	public void persist(Patient patient);

	public Patient update(Patient patient);

	public void delete(int patientId);
	
	public void deleteContact(int contactId);
	
	public void deleteMedicament(int medicamentId);

	public Patient get(int patientId);

	public List<Patient> find(String name, String vorname,Date birth,String svNr);

	public List<Compendiummedicament> getCompdeniumMedicaments();
	
	public Compendiummedicament getCompendiumMedicament(int id);
	
	public Medicament getMedicamentById(int medicamentId);
	
	public Medicament updateMedicament(Medicament medicament);
	
	public Medicament persistMedicament(Medicament medicament);
}
