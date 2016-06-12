package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.io.File;
import java.util.Date;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.DangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.NoDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PatientState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PotentialDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.State;

public class PatientView extends VerticalLayout implements View {
	public static final String NAME = "PatientOverview";

	private static final long serialVersionUID = -3394831508440420703L;

	private final PatientController controller;
	private Patient patient = null;
	private Label errorMessage = new Label();
	private State state;

	public PatientView() {
		controller = new PatientController();
		this.state = null;
	}

	// Create the patient overview
	private void createAccordion(boolean editPatient) {
		Accordion accordion = new Accordion();

		accordion.addTab(getPatientOverview()).setCaption("Overview");
		accordion.addTab(getPatientMedication()).setCaption("Medication");
		accordion.addTab(getPatientDiagnose()).setCaption("Diagnose");
		accordion.addTab(getPatientContacts()).setCaption("Contacts");

		addComponents(errorMessage, accordion);
		setMargin(true);
	}

	private VerticalLayout getPatientOverview() {
		VerticalLayout overview = new VerticalLayout();

		Label labelLastName = new Label("Name: " + this.patient.getSurename());
		Label labelFirstName = new Label("First name: " + this.patient.getFirstname());
		Label labelSocialAssuranceNumber = new Label("Assurance Nr.: " + this.patient.getAssuranceNr());
		Label labelBirthDate = new Label("Birthdate: " + this.patient.getBirthdate().toString());

		overview.addComponents(labelLastName, labelFirstName, labelSocialAssuranceNumber, labelBirthDate);
		overview.setSpacing(true);

		return overview;
	}

	private VerticalLayout getPatientMedication() {
		VerticalLayout medication = new VerticalLayout();

		Label labelMedicaments = new Label("Current Medicaments:");
		BeanItemContainer<Medicament> medis = new BeanItemContainer<Medicament>(Medicament.class,
				patient.getMedicaments());
		Grid gridMedicaments = getMedicamentsGrid(medis);
		Button createMedication = new Button("Add Medication");
		createMedication.addClickListener(c -> {
			getUI().getNavigator().navigateTo(CreateMedicationView.NAME + "/" + patient.getId());
		});

		medication.addComponents(labelMedicaments, gridMedicaments, createMedication);

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
		return contacts;
	}
	/*
	 * private HorizontalLayout getTabPatientenUebersicht() { HorizontalLayout
	 * layoutPatientenDaten = new HorizontalLayout();
	 * 
	 * layoutPatientenDaten.setMargin(true);
	 * 
	 * VerticalLayout listen = new VerticalLayout(); listen.setSpacing(true);
	 * 
	 * Label labelDiagnosen = new Label("Current Diagnoses:");
	 * BeanItemContainer<Diagnose> diagnoses = new
	 * BeanItemContainer<Diagnose>(Diagnose.class, patient.getDiagnoses()); Grid
	 * gridDiagnosen = getDiagnoseGrid(diagnoses);
	 * 
	 * listen.addComponents(labelMedikamente, gridMedikamente, createMedication,
	 * labelDiagnosen, gridDiagnosen);
	 * 
	 * VerticalLayout kontakteLayout = new VerticalLayout();
	 * kontakteLayout.setSpacing(true); kontakteLayout.setMargin(new
	 * MarginInfo(false, true, false, true)); Label labelKontakte = new
	 * Label("Contacts:"); BeanItemContainer<Contact> kontakte = new
	 * BeanItemContainer<Contact>(Contact.class, patient.getContacts()); Grid
	 * gridKontakte = getKontakteGrid(kontakte); Button newKontakt = new Button(
	 * "New Contact"); kontakteLayout.addComponents(labelKontakte, gridKontakte,
	 * newKontakt); String basepath =
	 * VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	 * 
	 * VerticalLayout statusLayout = new VerticalLayout();
	 * statusLayout.setMargin(new MarginInfo(true, false, false, false)); //
	 * Image as a file resource FileResource resource = new FileResource(new
	 * File(basepath + "/images/" + state.changeAmpel(this)));
	 * 
	 * // Show the image in the application Image image = new Image(
	 * "Patient gefährlich für andere", resource); image.setHeight("200");
	 * image.setWidth("100"); statusLayout.addComponent(image);
	 * 
	 * layoutPatientenDaten.addComponents(listen, kontakteLayout, statusLayout);
	 * 
	 * return layoutPatientenDaten; }
	 */

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
		boolean editPatient = false;

		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			int patientId;
			try {
				patientId = Integer.parseInt(parameters[1]);
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

			// Check if patient should be opened for editing
			if (parameters[0].equals("edit")) {
				editPatient = true;
			}
		}
		createAccordion(editPatient);
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

	public void setPatientState(State state) {
		this.state = state;
	}

	public State getPatientState() {
		return state;
	}
}
