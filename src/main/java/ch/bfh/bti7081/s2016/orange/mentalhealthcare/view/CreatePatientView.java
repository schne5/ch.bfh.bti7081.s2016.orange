package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class CreatePatientView extends VerticalLayout implements View {
	public static final String NAME = "Create";

	private final PatientController controller;
	private Patient patient;
	private Label hinweis = new Label();

	public CreatePatientView() {
		controller = new PatientController();
		
		// Add layout for Create
		VerticalLayout layoutCreatePatient = getLayoutCreatePatient();
		addComponents(hinweis, layoutCreatePatient);
		setMargin(true);
	}
	
	//layout for Create
	private VerticalLayout getLayoutCreatePatient() {
		VerticalLayout layoutPatientenDaten = new VerticalLayout();
		
		// Add title for Create
		final Label title = new Label("Create");
		
		// Add input fields
		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");
		final TextField firstName = new TextField();
		firstName.setCaption("First name:");
		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");
		final DateField birthDate = new DateField();
		birthDate.setCaption("Birth date:");
		
		//Add create button
		final Button createButton = new Button("Create patient");
		createButton.addClickListener(e -> {
			patient = new Patient();
			patient.setSurename(firstName.getValue());
			patient.setFirstname(firstName.getValue());
			patient.setAssuranceNr(assuranceNr.getValue());
			patient.setBirthdate(birthDate.getValue());
			
			// notification if there are no inputs in fields 
			//if the Patientcreation was correct go to  StartView
			if (controller.create(patient)) {
				getUI().getNavigator().navigateTo(StartView.NAME);
			} else {

				this.hinweis.setCaption("The input was incorrect: the new patient was not created. Please fill all fields");
			}
		});
		
		//Add back button
		final Button startButton = new Button("Return to search view");
		startButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		layoutPatientenDaten.addComponents(title, lastName, firstName, assuranceNr, birthDate, createButton, startButton);
		layoutPatientenDaten.setSpacing(true);
		return layoutPatientenDaten;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}
}