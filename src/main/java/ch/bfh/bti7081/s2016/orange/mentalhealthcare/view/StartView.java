package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.ArrayList;
import java.util.Date;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.DoctorController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.StartController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class StartView extends VerticalLayout implements View {
	public static final String NAME = "SearchPatients";

	private static final long serialVersionUID = -4883635345472877648L;

	private final StartController controller;

	private static final String LAST_NAME = "Last Name";
	private static final String FIRST_NAME = "First Name";
	private static final String ASSURANCE_NR = "Assurance Number";
	private static final String BIRTHDATE = "Birthdate";

	private static final String OPEN_BUTTON = "Open";
	private static final String EDIT_BUTTON = "Edit";

	private Label loggingInfo = new Label();
	private ArrayList<Integer> patientIds = null;

	public StartView() {
		controller = new StartController();

		setMargin(true);

		addComponent(loggingInfo);

		// Add layout for menu buttons
		final VerticalLayout menu = new VerticalLayout();
		addComponent(menu);

		// Add "logout button"
		final VerticalLayout logoutButtonLayout = new VerticalLayout();
		final Button logoutButton = new Button("Logout");
		logoutButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(LoginView.NAME);
			getSession().setAttribute("user", null);
		});
		logoutButtonLayout.addComponent(logoutButton);
		logoutButtonLayout.setMargin(new MarginInfo(true, false));
		logoutButtonLayout.setHeight("20px");
		menu.addComponent(logoutButton);

		// Add "create patient button"
		final VerticalLayout createPatientButtonLayout = new VerticalLayout();
		final Button createPatientButton = new Button("Create Patient");
		createPatientButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(CreatePatientView.NAME);
		});
		createPatientButtonLayout.addComponent(createPatientButton);
		createPatientButtonLayout.setMargin(new MarginInfo(true, false));
		menu.addComponent(createPatientButtonLayout);

		// Add layout for search
		final VerticalLayout search = new VerticalLayout();
		addComponent(search);

		// Add title for search
		final Label title = new Label("Search for Patients");
		search.addComponent(title);

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
		search.addComponent(input);

		// Add output field
		Table patientTable = createPatientTable();
		patientTable.setPageLength(0);

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

		search.addComponents(button, patientTable);
	}

	private Table createPatientTable() {
		Table patientTable = new Table("Select patient:");
		patientTable.setSelectable(true);

		// Define columns
		patientTable.addContainerProperty(StartView.LAST_NAME, String.class, null);
		patientTable.addContainerProperty(StartView.FIRST_NAME, String.class, null);
		patientTable.addContainerProperty(StartView.ASSURANCE_NR, String.class, null);
		patientTable.addContainerProperty(StartView.BIRTHDATE, Date.class, null);
		patientTable.addContainerProperty(StartView.OPEN_BUTTON, Button.class, null);
		patientTable.addContainerProperty(StartView.EDIT_BUTTON, Button.class, null);

		return patientTable;
	}

	@SuppressWarnings("unchecked")
	private void updatePatientTable(Table patientTable) {
		patientTable.removeAllItems();
		patientIds = new ArrayList<Integer>();

		for (Patient patient : controller.getPatients()) {
			Object newItemId = patientTable.addItem();
			Item row = patientTable.getItem(newItemId);
			row.getItemProperty(StartView.LAST_NAME).setValue(patient.getSurename());
			row.getItemProperty(StartView.FIRST_NAME).setValue(patient.getFirstname());
			row.getItemProperty(StartView.ASSURANCE_NR).setValue(patient.getAssuranceNr());
			row.getItemProperty(StartView.BIRTHDATE).setValue(patient.getBirthdate());

			Button openButton = new Button();
			openButton.setCaption(StartView.OPEN_BUTTON);
			openButton.addClickListener(e -> {
				getUI().getNavigator().navigateTo(PatientView.NAME + "/open/" + patient.getId());
			});
			row.getItemProperty(StartView.OPEN_BUTTON).setValue(openButton);

			Button editButton = new Button();
			editButton.setCaption(StartView.EDIT_BUTTON);
			editButton.addClickListener(e -> {
				getUI().getNavigator().navigateTo(EditPatientView.NAME + "/" + patient.getId());
			});
			row.getItemProperty(StartView.EDIT_BUTTON).setValue(editButton);

			patientIds.add(patient.getId());
		}

		patientTable.setPageLength(patientTable.getItemIds().size());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// Add welcome text after login
		DoctorController doctorController = new DoctorController();
		if (getSession().getAttribute("user") != null) {
			int i = (int) getSession().getAttribute("user");
			Doctor doctor = doctorController.getDoctorById(i);
			loggingInfo.setCaption("You are logged in as: " + doctor.getName());
		}
	}
}
