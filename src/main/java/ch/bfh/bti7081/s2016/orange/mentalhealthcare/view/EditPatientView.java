package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.Date;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PatientState;

public class EditPatientView extends VerticalLayout implements View {
	public static final String NAME = "EditPatient";

	private static final long serialVersionUID = -326189719935009426L;

	private final PatientController controller;
	private Patient patient = null;

	public EditPatientView() {
		controller = new PatientController();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");

			int patientId = Integer.parseInt(parameters[0]);
			patient = controller.getPatientById(patientId);

			createLayout();
		}
	}

	private void createLayout() {
			
		final GridLayout menu = new GridLayout(2,1);
		menu.setWidth("100%");
				final Button backButton = new Button("Return to search view");
		backButton.setStyleName(BaseTheme.BUTTON_LINK);
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		menu.addComponent(backButton,1,0);
		menu.setComponentAlignment(backButton, Alignment.TOP_RIGHT);
		addComponent(menu);
		
		VerticalLayout patientLayout = new VerticalLayout();

		final Label infoMessage = new Label();
		infoMessage.setHeight("20px");

		final ObjectProperty<Integer> propertyId = new ObjectProperty<Integer>(patient.getId());
		final TextField id = new TextField(propertyId);
		id.setVisible(false);

		final ObjectProperty<String> propertyLastName = new ObjectProperty<String>(patient.getSurename());
		final TextField lastName = new TextField(propertyLastName);
		lastName.setCaption("Last name:");

		final ObjectProperty<String> propertyFirstName = new ObjectProperty<String>(patient.getFirstname());
		final TextField firstName = new TextField(propertyFirstName);
		firstName.setCaption("First name:");

		final ObjectProperty<String> propertySocialAssuranceNumber = new ObjectProperty<String>(
				patient.getAssuranceNr());
		final TextField assuranceNr = new TextField(propertySocialAssuranceNumber);
		assuranceNr.setCaption("Assurance number:");

		final ObjectProperty<Date> propertyBirthDate = new ObjectProperty<Date>(patient.getBirthdate());
		final DateField birthDate = new DateField(propertyBirthDate);
		birthDate.setCaption("Birthdate:");

		final ComboBox patientState = new ComboBox();
		patientState.setCaption("State:");
		patientState.addItems(PatientState.NO_DANGER, PatientState.POTENTIAL_DANGER, PatientState.DANGER);
		patientState.select(PatientState.getByValue(patient.getState()));
		final Button saveButton = new Button("Save patient");
		
		saveButton.addClickListener(e -> {
			this.patient.setSurename(propertyLastName.getValue());
			this.patient.setFirstname(propertyFirstName.getValue());
			this.patient.setAssuranceNr(propertySocialAssuranceNumber.getValue());
			this.patient.setBirthdate(propertyBirthDate.getValue());
			this.patient.setState(((PatientState) patientState.getValue()).getValue());

			Patient updatedPatient = controller.update(patient);
			if (null == updatedPatient) {
				infoMessage.setCaption("Data couldn't be persisted.");
			} else {
				infoMessage.setCaption("Daten persisted.");
				this.patient = updatedPatient;
			}
		});

		final Button deleteButton = new Button("Delete patient");
		deleteButton.addClickListener(e -> {
			controller.delete(patient);
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		

		patientLayout.addComponents(infoMessage, id, lastName, firstName, assuranceNr, birthDate, patientState,
				saveButton, deleteButton);
		patientLayout.setSpacing(true);

		addComponent(patientLayout);
		setMargin(true);
	}
}
