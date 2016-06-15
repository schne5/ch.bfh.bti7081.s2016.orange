package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;

public class MedicamentValidator {
	
	public static boolean validateDose(String dose, String takings, Compendiummedicament compMed){
		try{
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

}
