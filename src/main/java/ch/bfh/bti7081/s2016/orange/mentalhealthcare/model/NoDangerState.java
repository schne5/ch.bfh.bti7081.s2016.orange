package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.PatientView;

public class NoDangerState implements State {

	@Override
	public String changeAmpel(PatientView view) {
		view.setPatientState(this);
		return "ampel_gruen.png";
	}

	public String toString() {
		return "No Danger";
	}

}
