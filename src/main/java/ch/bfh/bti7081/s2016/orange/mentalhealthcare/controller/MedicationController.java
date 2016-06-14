package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class MedicationController {

	PatientRepository repository;

	public MedicationController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public List<Medicament> getMedications(int id) {
		Patient patient = repository.get(id);
		return patient.getMedicaments();
	}

	public boolean saveMedication(int patientId, int compMedicationId, String dose,String takings,short active,int arztId) {
		Patient patient = repository.get(patientId);
		Compendiummedicament compMed = getCompendiummedicamentById(compMedicationId);
		Medicament medication = new Medicament();
	
	//	medication.setDoctor(doctor);
		medication.setCompendiummedicament(compMed);
		try{
			double doseDouble = Double.parseDouble(dose);
			int takingsInt =Integer.parseInt(takings);
			BigDecimal doseDecimal = new BigDecimal(doseDouble);
			medication.setTakings(takingsInt);
			medication.setDose(doseDecimal);
			medication.setActive(active);
		}catch(Exception e){
			System.out.println("Exception could not parse String to int");
			return false;	
		}
					
		boolean isValid=validateMedication(medication);
		if(isValid){
			medication.setPatient(patient);
			Medicament persisted=repository.persistMedicament(medication);
			System.out.println(persisted.getId());
			patient.addMedicament(persisted);
		}
		return isValid;
	}
	
	public boolean updateMedication(int patientId, int compMedicationId, String dose,String takings,short active, int medicamentId) {
		Patient patient = repository.get(patientId);
		Compendiummedicament compMed = getCompendiummedicamentById(compMedicationId);
		Medicament medication = patient.getMedicament(medicamentId);
		medication.setCompendiummedicament(compMed);
		try{
			double doseDouble = Double.parseDouble(dose);
			int takingsInt =Integer.parseInt(takings);
			BigDecimal doseDecimal = new BigDecimal(doseDouble);
			medication.setTakings(takingsInt);
			medication.setDose(doseDecimal);
			medication.setActive(active);
		}catch(Exception e){
			System.out.println("Exception could not parse String to int");
			return false;	
		}
					
		boolean isValid=validateMedication(medication);
		if(isValid){
			System.out.println(medication.getId());
			
			medication =repository.updateMedicament(medication);		
		}
		return isValid;
	}

	public void deleteMedication(int id, Medicament medication) {
		Patient patient = repository.get(id);
		List<Medicament> medications = patient.getMedicaments();
		medications.remove(medication);
		repository.deleteMedicament(medication.getId());
	}
	
	public boolean validateMedication(Medicament medication){
		boolean isValid =medication.getCompendiummedicament()!=null;
		isValid&=(medication.getActive() ==0 || medication.getActive()==1);
		return isValid;
	}
	
	public boolean validateDose(String dose, String takings, int compMedId){
		try{
			Compendiummedicament compMed = getCompendiummedicamentById(compMedId);
			double doseDouble = Double.parseDouble(dose);
			int takingsInt =Integer.parseInt(takings);	
			double calculatedDose = doseDouble *takingsInt;
			if(calculatedDose > compMed.getMaxDose().doubleValue()){
				System.out.println(calculatedDose);
				System.out.println(compMed.getMaxDose().doubleValue());
				return false;
			}
			
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	public List<Compendiummedicament> getCompendiumList(){	
		return repository.getCompdeniumMedicaments();
	}
	
	
	public Compendiummedicament getCompendiummedicamentById(int id) {
		Compendiummedicament medication = repository.getCompendiumMedicament(id);
		return medication;
	}
	
	public Medicament getMedicamentById(int medicamentId){
		Medicament medicament = repository.getMedicamentById(medicamentId);
		return medicament;
	}
	
	
	
	
	
}
