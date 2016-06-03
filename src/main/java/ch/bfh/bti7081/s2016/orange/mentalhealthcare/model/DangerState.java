package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.PatientView;

public class DangerState implements State {

	@Override
	public String changeAmpel(PatientView view) {
		view.setPatientState(this);
		return "ampel_rot.png";
	}

	public String toString() {
		return "Danger";
	}

}
