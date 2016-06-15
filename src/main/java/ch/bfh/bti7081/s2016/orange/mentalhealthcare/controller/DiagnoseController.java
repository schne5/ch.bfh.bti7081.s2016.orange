package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import java.math.BigDecimal;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Icdcdiagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepository;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.persistence.PatientRepositoryFactory;

public class DiagnoseController {
	PatientRepository repository;

	public DiagnoseController() {
		repository = PatientRepositoryFactory.createJpaRepository();
	}

	public List<Diagnose> getDiagnoses(int id) {
		Patient patient = repository.get(id);
		return patient.getDiagnoses();
	}

	public boolean saveDiagnose(int patientId, int icdcDiagnoseId, String dose,
			String takings, short active, int arztId) {
		Patient patient = repository.get(patientId);
		Icdcdiagnose icdcDiagnose = getIcdcdiagnoseById(icdcDiagnoseId);
		Diagnose diagnose = new Diagnose();

		diagnose.setIcdcdiagnose(icdcDiagnose);
		try {
			double doseDouble = Double.parseDouble(dose);
			int takingsInt = Integer.parseInt(takings);
			BigDecimal doseDecimal = new BigDecimal(doseDouble);
			diagnose.setActive(active);
		} catch (Exception e) {
			System.out.println("Exception could not parse String to int");
			return false;
		}

		boolean isValid = validateDiagnose(diagnose);
		if (isValid) {
			diagnose.setPatient(patient);
			Diagnose persisted = repository.persistDiagnose(diagnose);
			System.out.println(persisted.getId());
			patient.addDiagnose(persisted);
		}
		return isValid;
	}

	public boolean updateDiagnose(int patientId, int icdcDiagnoseId,
			String doctorId, short active, int diagnoseId) {
		Patient patient = repository.get(patientId);
		Icdcdiagnose icdcDiagnose = getIcdcdiagnoseById(icdcDiagnoseId);
		Diagnose diagnose = patient.getDiagnose(diagnoseId);
		diagnose.setIcdcdiagnose(icdcDiagnose);
		try {
			int doctorIdInt = Integer.parseInt(doctorId);
			diagnose.setActive(active);
		} catch (Exception e) {
			System.out.println("Exception could not parse String to int");
			return false;
		}

		boolean isValid = validateDiagnose(diagnose);
		if (isValid) {
			System.out.println(diagnose.getId());

			diagnose = repository.updateDiagnose(diagnose);
		}
		return isValid;
	}

	public void deleteDiagnose(int id, Diagnose diagnose) {
		Patient patient = repository.get(id);
		List<Diagnose> diagnoses = patient.getDiagnoses();
		diagnoses.remove(diagnose);
		repository.deleteDiagnose(diagnose.getId());
	}

	public boolean validateDiagnose(Diagnose diagnose) {
		boolean isValid = diagnose.getIcdcdiagnose() != null;
		isValid &= (diagnose.getActive() == 0 || diagnose.getActive() == 1);
		return isValid;
	}

	public boolean validateDose(String dose, String takings, int icdcDiagnoseId) {
		try {
			Icdcdiagnose icdcDiagnose = getIcdcdiagnoseById(icdcDiagnoseId);
			// double doseDouble = Double.parseDouble(dose);
			// int takingsInt = Integer.parseInt(takings);
			// double calculatedDose = doseDouble * takingsInt;
			// if (calculatedDose > icdcDiagnose.getMaxDose().doubleValue()) {
			// System.out.println(calculatedDose);
			// System.out.println(icdcDiagnose.getMaxDose().doubleValue());
			// return false;
			// }

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<Icdcdiagnose> getIcdcList() {
		return repository.getIcdcDiagnoses();
	}

	public Icdcdiagnose getIcdcdiagnoseById(int id) {
		Icdcdiagnose icdcDiagnose = repository.getIcdcDiagnose(id);
		return icdcDiagnose;
	}

	public Diagnose getDiagnoseById(int diagnoseId) {
		Diagnose diagnose = repository.getDiagnoseById(diagnoseId);
		return diagnose;
	}

}
