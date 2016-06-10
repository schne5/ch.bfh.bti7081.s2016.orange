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

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.ArztController;//Rajina
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;//Rajina
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.StartController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class StartView extends VerticalLayout implements View {
	public static final String NAME = "SearchPatients";

	private static final long serialVersionUID = -4883635345472877648L;

	private final StartController controller;
	private final ArztController acontroller;//Rajina
	private static final String LAST_NAME = "Last Name";
	private static final String FIRST_NAME = "First Name";
	private static final String ASSURANCE_NR = "Assurance Number";
	private static final String BIRTHDATE = "Birthdate";

	private final HorizontalLayout displayButtons;
	Label text = new Label();//Rajina
	private Arzt arzt = null;//Rajina
	private ArrayList<Integer> patientIds = null;

	public StartView() {
		acontroller = new ArztController();//Rajina
		controller = new StartController();

		setMargin(true);

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
		menu.addComponent(logoutButtonLayout);

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
		search.addComponents(title, text);

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
		addComponent(displayButtons);
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
		
		if(getSession().getAttribute("user")!=null){
			int i = (int)getSession().getAttribute("user");
			arzt = acontroller.getArztById(i);
			text.setCaption("eingeloggt als "+arzt.getName());
			
		}
		
	}
}
