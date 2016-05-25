package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.Date;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PatientView extends VerticalLayout implements View {
	public static final String NAME = "PatientEdit";
	private final PatientController controller;
	private Patient patient;

	public PatientView() {
		controller = new PatientController();
		patient = new Patient("", "", "", 1, new Date());
		final Label title = new Label("Edit");

		TabSheet tabsheet = new TabSheet();

		VerticalLayout tabPatientenDaten = getTabPatientenDaten();

		addComponents(tabsheet, title, tabPatientenDaten);
	}

	private VerticalLayout getTabPatientenDaten() {
		VerticalLayout layout = new VerticalLayout();

		final ObjectProperty<String> propertyLastName = new ObjectProperty<String>(
				patient.getName());
		final TextField lastName = new TextField(propertyLastName);
		lastName.setCaption("Last name:");

		final ObjectProperty<String> propertyFirstName = new ObjectProperty<String>(
				patient.getVorname());
		final TextField firstName = new TextField(propertyFirstName);
		firstName.setCaption("First name:");

		final ObjectProperty<String> propertySocialAssuranceNumber = new ObjectProperty<String>(
				patient.getSvNr());
		final TextField assuranceNr = new TextField(
				propertySocialAssuranceNumber);
		assuranceNr.setCaption("Social assurance number:");

		final ObjectProperty<Date> propertyBirthDate = new ObjectProperty<Date>(
				patient.getGebDatum());
		final DateField birthDate = new DateField(propertyBirthDate);
		birthDate.setCaption("Birth date:");

		final Button saveButton = new Button("Save patient");
		saveButton.addClickListener(e -> {
			controller.update(patient);
		});

		final Button backButton = new Button("Return to search view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		layout.addComponents(lastName, firstName, assuranceNr, birthDate,
				saveButton, backButton);
		layout.setMargin(true);
		return layout;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
