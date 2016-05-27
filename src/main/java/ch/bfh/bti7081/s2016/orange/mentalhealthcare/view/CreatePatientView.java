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

	private final PatientController pcontroller;
	private Patient p;

	public CreatePatientView() {
		pcontroller = new PatientController();

		final Label title = new Label("Create");

		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");

		final TextField firstName = new TextField();
		firstName.setCaption("First name:");

		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");

		final DateField birthDate = new DateField();
		birthDate.setCaption("Birth date:");

	

		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});

		final Button createButton = new Button("Create Patient");
		createButton.addClickListener(e -> {
			 p = new Patient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(), 0,
					birthDate.getValue());
			pcontroller.create(p);
		});

		
		addComponents(title, lastName, firstName, assuranceNr, birthDate, createButton, backButton);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}