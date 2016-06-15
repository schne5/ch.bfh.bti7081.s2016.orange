package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class CreatePatientView extends VerticalLayout implements View {
	public static final String NAME = "Create";

	private final PatientController controller;
	private Patient patient;
	private Label hinweis = new Label();

	public CreatePatientView() {
		controller = new PatientController();
		
		//Add back button
		final Button backButton = new Button("Return to search view");
		backButton.setStyleName(BaseTheme.BUTTON_LINK);
		backButton.addClickListener(e -> {
		getUI().getNavigator().navigateTo(StartView.NAME);
		});
		final GridLayout menu = new GridLayout(2,1);
		menu.setWidth("100%");
		menu.addComponent(backButton,1,0);
		menu.setComponentAlignment(backButton, Alignment.TOP_RIGHT);
		addComponent(menu);
		
		// Add layout for Create
		VerticalLayout layoutCreatePatient = getLayoutCreatePatient();
		addComponents(hinweis, layoutCreatePatient);
		setMargin(true);
	}
	
	//layout for Create
	private VerticalLayout getLayoutCreatePatient() {
		VerticalLayout layoutPatientenDaten = new VerticalLayout();
		
		// Add title for Create
		final Label title = new Label("Create Patient");
		
		// Add input fields
		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");
		final TextField firstName = new TextField();
		firstName.setCaption("First name:");
		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Assurance number:");
		final DateField birthDate = new DateField();
		birthDate.setCaption("Birthdate:");
		
		//Add create button
		final Button createButton = new Button("Create patient");
		createButton.addClickListener(e -> {
			patient = new Patient();
			patient.setSurename(lastName.getValue());
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
		startButton.setStyleName(BaseTheme.BUTTON_LINK);
		startButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		layoutPatientenDaten.addComponents(title, lastName, firstName, assuranceNr, birthDate, createButton);

		layoutPatientenDaten.setSpacing(true);
		return layoutPatientenDaten;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
		
	}
}