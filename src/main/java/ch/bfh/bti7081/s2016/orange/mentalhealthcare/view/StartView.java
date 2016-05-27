package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.StartController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class StartView extends VerticalLayout implements View {
	public static final String NAME = "Create & Search";

	private static final long serialVersionUID = -4883635345472877648L;

	private final StartController controller;
	private final PatientController pcontroller;

	public StartView() {
		controller = new StartController();
		pcontroller = new PatientController();

		final Label title = new Label("Create & Search");

		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");

		final TextField firstName = new TextField();
		firstName.setCaption("First name:");

		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");

		final DateField birthDate = new DateField();
		birthDate.setCaption("Birth date:");

		final ListSelect select = new ListSelect("Matching patients");
		select.addItems(controller.getPatients());
		select.setNullSelectionAllowed(false);

		final Button searchButton = new Button("Search patient");
		searchButton.addClickListener(e -> {
			controller.searchPatient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(),
					birthDate.getValue());
			select.removeAllItems();
			select.addItems(controller.getPatients());
		});

		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});

		final Button createButton = new Button("Create Patient");
		createButton.addClickListener(e -> {
			final Patient p = new Patient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(), 0,
					birthDate.getValue());
			pcontroller.create(p);
		});

		addComponents(title, lastName, firstName, assuranceNr, birthDate, createButton, searchButton, select,
				backButton);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
