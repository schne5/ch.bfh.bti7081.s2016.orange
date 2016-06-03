package ch.bfh.bti7081.s2016.orange.mentalhealthcare.model;

public enum PatientState {
	NO_DANGER(0), POTENTIAL_DANGER(1), DANGER(2);

	private int value;

	PatientState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PatientState getByValue(int value) {
		switch (value) {
		case 0:
			return NO_DANGER;
		case 1:
			return POTENTIAL_DANGER;

		case 2:
			return DANGER;
		}
		return null;
	}

	public String toString() {
		switch (value) {
		case 0:
			return "no danger";
		case 1:
			return "potential danger";

		case 2:
			return "danger";
		}
		return null;
	}
}
