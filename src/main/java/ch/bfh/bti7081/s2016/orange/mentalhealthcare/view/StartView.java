package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.StartController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class StartView extends VerticalLayout implements View {
	public static final String NAME = "SearchPatients";

	private static final long serialVersionUID = -4883635345472877648L;

	private final StartController controller;

	private static final String LAST_NAME = "Last Name";
	private static final String FIRST_NAME = "First Name";
	private static final String ASSURANCE_NR = "Assurance Number";
	private static final String BIRTHDATE = "Birthdate";

	private final HorizontalLayout displayButtons;

	private ArrayList<Integer> patientIds = null;

	public StartView() {
		controller = new StartController();

		setMargin(true);

		// Add title
		final Label title = new Label("Search for Patients");
		addComponent(title);

		// Add content
		final HorizontalLayout content = new HorizontalLayout();
		content.setWidth("100%");
		addComponent(content);

		// Add navigation and display sides
		final VerticalLayout nav = new VerticalLayout();
		nav.setWidth("250px");
		final VerticalLayout display = new VerticalLayout();
		display.setWidth("100%");
		content.addComponents(nav, display);
		content.setExpandRatio(display, 1);

		// Add input fields
		final VerticalLayout input = new VerticalLayout();
		final TextField lastName = new TextField();
		lastName.setCaption(StartView.LAST_NAME);
		final TextField firstName = new TextField();
		firstName.setCaption(StartView.FIRST_NAME);
		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption(StartView.ASSURANCE_NR);
		final DateField birthDate = new DateField();
		birthDate.setCaption(StartView.BIRTHDATE);
		input.addComponents(lastName, firstName, assuranceNr, birthDate);
		input.setHeight("300px");

		// Add output field
		Table patientTable = createPatientTable();
		display.addComponents(patientTable);

		// Add search button
		final VerticalLayout button = new VerticalLayout();
		final Button searchButton = new Button("Search patient");
		searchButton.addClickListener(e -> {
			controller.searchPatient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(),
					birthDate.getValue());
			updatePatientTable(patientTable);
		});
		button.addComponent(searchButton);
		button.setHeight("100px");

		nav.addComponents(input, button);

		// Add "open patient buttons"
		displayButtons = new HorizontalLayout();
		final Button openButton = new Button("Open patient");
		openButton.addClickListener(e -> {
			int rowNumber = (int) patientTable.getValue();
			int patientId = patientIds.get(rowNumber - 1);
			getUI().getNavigator().navigateTo(PatientView.NAME + "/open/" + patientId);
		});
		final Button editButton = new Button("Edit patient");
		editButton.addClickListener(e -> {
			int rowNumber = (int) patientTable.getValue();
			int patientId = patientIds.get(rowNumber - 1);
			getUI().getNavigator().navigateTo(PatientView.NAME + "/edit/" + patientId);
		});
		displayButtons.addComponents(openButton, editButton);
		displayButtons.setEnabled(false);
		display.addComponent(displayButtons);

		// Add "create patient button"
		final VerticalLayout createPatientButtonLayout = new VerticalLayout();
		final Button createPatientButton = new Button("Create Patient");
		createPatientButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(CreatePatientView.NAME);
		});
		createPatientButtonLayout.addComponent(createPatientButton);
		createPatientButtonLayout.setMargin(new MarginInfo(true, false));
		nav.addComponent(createPatientButtonLayout);

		// Add "back button"
		final VerticalLayout backButtonLayout = new VerticalLayout();
		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});
		backButtonLayout.addComponent(backButton);
		backButtonLayout.setMargin(new MarginInfo(true, false));
		nav.addComponent(backButtonLayout);
	}

	@SuppressWarnings("unchecked")
	private void updatePatientTable(Table patientTable) {
		patientTable.removeAllItems();
		patientIds = new ArrayList<Integer>();

		for (Patient patient : controller.getPatients()) {
			Object newItemId = patientTable.addItem();
			Item row = patientTable.getItem(newItemId);
			row.getItemProperty(StartView.LAST_NAME).setValue(patient.getName());
			row.getItemProperty(StartView.FIRST_NAME).setValue(patient.getVorname());
			row.getItemProperty(StartView.ASSURANCE_NR).setValue(patient.getSvNr());
			row.getItemProperty(StartView.BIRTHDATE).setValue(patient.getGebDatum());

			patientIds.add(patient.getId());
		}
	}

	private Table createPatientTable() {
		Table patientTable = new Table("Select patient:");
		patientTable.setSelectable(true);

		// Define columns
		patientTable.addContainerProperty(StartView.LAST_NAME, String.class, null);
		patientTable.addContainerProperty(StartView.FIRST_NAME, String.class, null);
		patientTable.addContainerProperty(StartView.ASSURANCE_NR, String.class, null);
		patientTable.addContainerProperty(StartView.BIRTHDATE, Date.class, null);

		patientTable.addItemClickListener(e -> {
			displayButtons.setEnabled(true);
		});

		return patientTable;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
