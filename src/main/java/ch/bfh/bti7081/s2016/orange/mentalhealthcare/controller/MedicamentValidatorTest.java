package ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;


public class MedicamentValidatorTest {

	@Test
	public void testValidateDoseFail() {
		Compendiummedicament compMed = new Compendiummedicament();
		compMed.setMaxDose(new BigDecimal(3));
		String dose="1";
		String takings="4";
		boolean result=MedicamentValidator.validateDose(dose, takings, compMed);
		assertFalse(result);
	}
	
	@Test
	public void testValidateDoseSuccess() {
		Compendiummedicament compMed = new Compendiummedicament();
		compMed.setMaxDose(new BigDecimal(3));
		String dose="1";
		String takings="3";
		boolean result=MedicamentValidator.validateDose(dose, takings, compMed);
		assertTrue(result);
	}

}
