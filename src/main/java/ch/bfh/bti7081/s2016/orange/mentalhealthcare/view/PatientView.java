package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.io.File;
import java.math.BigDecimal;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.MedicationController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.DangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.NoDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PotentialDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.State;

public class PatientView extends VerticalLayout implements View {
	public static final String NAME = "PatientOverview";
	private static final String Delete_BUTTON = "Delete";
	private static final String EDIT_BUTTON = "Edit";
	
	private static final String MEDICAMENT_NAME = "Medicament Name";
	private static final String DOSE = "Dose";
	private static final String TAKINGS = "Takings";
	private static final String ACTIVE = "Active";
	private static final String DOCTOR_NAME = "Doctor";

	private static final long serialVersionUID = -3394831508440420703L;

	private final PatientController controller;
	private final MedicationController medicationController;
	private Patient patient = null;
	private Label errorMessage = new Label();
	private State state;
	private Table medicamentGrid;

	public PatientView() {
		controller = new PatientController();
		medicationController = new MedicationController();
		this.state = null;
	}

	// Create the patient overview
	private void createView() {
		final GridLayout menu = new GridLayout(2, 1);
		menu.setWidth("100%");
		final Button backButton = new Button("Return to search view");
		backButton.setStyleName(BaseTheme.BUTTON_LINK);
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		menu.addComponent(backButton, 1, 0);
		menu.setComponentAlignment(backButton, Alignment.TOP_RIGHT);

		menu.addComponent(errorMessage, 0, 0);
		menu.setComponentAlignment(errorMessage, Alignment.TOP_LEFT);
		menu.setHeight("50px");
		addComponent(menu);

		Accordion accordion = new Accordion();

		accordion.addTab(getPatientOverview()).setCaption("Overview");
		accordion.addTab(getPatientMedication()).setCaption("Medication");
		accordion.addTab(getPatientDiagnose()).setCaption("Diagnose");
		accordion.addTab(getPatientContacts()).setCaption("Contacts");

		addComponent(accordion);
		setMargin(true);
	}

	private GridLayout getPatientOverview() {
		GridLayout overview = new GridLayout(2, 1);
		overview.setWidth("100%");

		// Add patient information
		VerticalLayout patientInfo = new VerticalLayout();

		Label labelLastName = new Label("Name: " + this.patient.getSurename());
		Label labelFirstName = new Label("First name: " + this.patient.getFirstname());
		Label labelSocialAssuranceNumber = new Label("Assurance Nr.: " + this.patient.getAssuranceNr());
		Label labelBirthDate = new Label("Birthdate: " + this.patient.getBirthdate().toString());

		patientInfo.addComponents(labelLastName, labelFirstName, labelSocialAssuranceNumber, labelBirthDate);
		patientInfo.setSpacing(true);

		overview.addComponent(patientInfo, 0, 0);

		// Add patient status
		VerticalLayout statusLayout = new VerticalLayout();
		statusLayout.setMargin(new MarginInfo(true, false, false, false));

		String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		// Image as a file resource
		FileResource resource = new FileResource(new File(basePath + "/images/" + state.changeAmpel(this)));
		// Show the image in the application
		Image image = new Image("Patient danger level", resource);
		image.setHeight("150");
		image.setWidth("75");
		statusLayout.addComponent(image);
		statusLayout.setComponentAlignment(image, Alignment.TOP_CENTER);

		overview.addComponent(statusLayout, 1, 0);
		overview.setMargin(true);

		return overview;
	}

	private VerticalLayout getPatientMedication() {
		VerticalLayout medication = new VerticalLayout();
		medication.setMargin(true);
		medication.setSpacing(true);

		Label labelMedicaments = new Label("Current Medicaments:");
		medicamentGrid = getMedicamentTable();
		medicamentGrid.setHeight("200px");
	
		
		updateMedicamentTable();
		
		Button createMedication = new Button("Add Medication");
		createMedication.addClickListener(c -> {
			getUI().getNavigator().navigateTo(CreateMedicationView.NAME + "/" + patient.getId());
		});

		medication.addComponents(labelMedicaments, medicamentGrid,createMedication);

		return medication;
	}

	private VerticalLayout getPatientDiagnose() {
		VerticalLayout diagnose = new VerticalLayout();

		Label labelDiagnoses = new Label("Current Diagnoses:");
		BeanItemContainer<Diagnose> diagnoses = new BeanItemContainer<Diagnose>(Diagnose.class, patient.getDiagnoses());
		Grid gridDiagnoses = getDiagnosesGrid(diagnoses);

		diagnose.addComponents(labelDiagnoses, gridDiagnoses);

		return diagnose;
	}

	private VerticalLayout getPatientContacts() {
		VerticalLayout contacts = new VerticalLayout();

		contacts.setSpacing(true);
		contacts.setMargin(true);
		Label labelContacts = new Label("Contacts:");
		BeanItemContainer<Contact> contact = new BeanItemContainer<Contact>(Contact.class, patient.getContacts());
		Grid gridKontakte = getContactsGrid(contact);
		Button newContact = new Button("New Contact");
		newContact.addClickListener(e -> {
			int patientId = this.patient.getId();
			getUI().getNavigator().navigateTo(ContactView.NAME + "/" + patientId);
		});
		contacts.addComponents(labelContacts, gridKontakte, newContact);

		return contacts;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setPatient(int patientId) {
		System.out.println(patientId);
		setPatient(controller.getPatientById(patientId));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			int patientId;
			try {
				patientId = Integer.parseInt(parameters[0]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				patientId = 0;
			}
			if (patientId > 0) {
				setPatient(patientId);
				switch (this.patient.getState()) {
				case 0:
					NoDangerState noDanger = new NoDangerState();
					noDanger.changeAmpel(this);
					break;
				case 1:
					PotentialDangerState potDanger = new PotentialDangerState();
					potDanger.changeAmpel(this);
					break;
				case 2:
					DangerState danger = new DangerState();
					danger.changeAmpel(this);
					break;
				}
			} else {
				this.patient = null;
			}
		}
		createView();
	}

	public Grid getMedicamentsGrid(BeanItemContainer<Medicament> medis) {
		Grid gridMedicaments = new Grid(medis);
		gridMedicaments.removeColumn("id");
		gridMedicaments.removeColumn("patient");
		gridMedicaments.removeColumn("active");
		gridMedicaments.removeColumn("compendiummedicament");
		gridMedicaments.setColumnOrder("medicamentName", "dose", "doctor");
		gridMedicaments.setHeight("200");
		return gridMedicaments;
	}

	public Grid getDiagnosesGrid(BeanItemContainer<Diagnose> diagnoses) {
		Grid gridDiagnoses = new Grid(diagnoses);
		gridDiagnoses.removeColumn("patient");
		gridDiagnoses.setHeight("200");
		return gridDiagnoses;
	}

	public Grid getContactsGrid(BeanItemContainer<Contact> contacts) {
		Grid gridContacts = new Grid(contacts);
		gridContacts.removeColumn("patient");
		gridContacts.setHeight("200");
		gridContacts.removeColumn("id");
		gridContacts.setColumnOrder("name", "adress", "phoneNr");
		return gridContacts;
	}
	
	public Table getMedicamentTable() {
		
		Table medicamentTable = new Table();
		
		// Define columns
		medicamentTable.addContainerProperty(PatientView.MEDICAMENT_NAME, String.class, null);
		medicamentTable.addContainerProperty(PatientView.DOSE, BigDecimal.class, null);
		medicamentTable.addContainerProperty(PatientView.TAKINGS, Integer.class, null);
		medicamentTable.addContainerProperty(PatientView.DOCTOR_NAME, String.class, null);
		medicamentTable.addContainerProperty(PatientView.ACTIVE, Short.class, null);
		medicamentTable.addContainerProperty(PatientView.Delete_BUTTON, Button.class, null);
		medicamentTable.addContainerProperty(PatientView.EDIT_BUTTON, Button.class, null);
		
		return medicamentTable;
	}
	
	@SuppressWarnings("unchecked")
	private void updateMedicamentTable() {
		medicamentGrid.removeAllItems();

		for (Medicament medicament : medicationController.getMedications(patient.getId())) {
			Object newItemId = medicamentGrid.addItem();
			Item row = medicamentGrid.getItem(newItemId);
			row.getItemProperty(PatientView.MEDICAMENT_NAME).setValue(medicament.getMedicamentName());
			row.getItemProperty(PatientView.DOSE).setValue(medicament.getDose());
			row.getItemProperty(PatientView.TAKINGS).setValue(medicament.getTakings());
			//row.getItemProperty(PatientView.DOCTOR_NAME).setValue(medicament.getDoctor().getName());
			row.getItemProperty(PatientView.ACTIVE).setValue(medicament.getActive());

			Button deleteButton = new Button();
			deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
			deleteButton.setCaption("Delete");
			deleteButton.addClickListener(e -> {
				medicationController.deleteMedication(patient.getId(), medicament);
				System.out.println(medicament.getId());
				updateMedicamentTable();
			});
			row.getItemProperty(PatientView.Delete_BUTTON).setValue(deleteButton);
			Button editButton = new Button();
			editButton.setStyleName(BaseTheme.BUTTON_LINK);
			editButton.setCaption("Edit");
			editButton.addClickListener(e -> {
				getUI().getNavigator().navigateTo(CreateMedicationView.NAME + "/" + patient.getId() +"/" +medicament.getId());
			});
			row.getItemProperty(PatientView.EDIT_BUTTON).setValue(editButton);
		}
	}

	public void setPatientState(State state) {
		this.state = state;
	}

	public State getPatientState() {
		return state;
	}
}
