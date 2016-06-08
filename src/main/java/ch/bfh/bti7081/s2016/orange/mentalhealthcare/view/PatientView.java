package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.io.File;
import java.util.Date;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.DangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Kontakt;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medikament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.NoDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PatientState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PotentialDangerState;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.State;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PatientView extends VerticalLayout implements View {

	public static final String NAME = "PatientEdit";
	private final PatientController controller;
	private Patient patient = null;
	Label labelFehler = new Label();
	private State state;

	public PatientView() {
		controller = new PatientController();
		this.state = null;

	}

	private void createTabsheet(boolean editPatient) {
		TabSheet tabsheet = new TabSheet();

		VerticalLayout tabPatientenDaten = getTabPatientBearbeiten();
		HorizontalLayout tabPatientenUebersicht = getTabPatientenUebersicht();
		VerticalLayout tabPatientenMedikamente = getTabPatientenMedicaments();
		VerticalLayout tabPatientenDiagnosen = getTabPatientenDiagnoses();

		tabsheet.addTab(tabPatientenDaten).setCaption("Edit");
		tabsheet.addTab(tabPatientenUebersicht).setCaption("Overview");
		tabsheet.addTab(tabPatientenMedikamente).setCaption("Medication");
		tabsheet.addTab(tabPatientenDiagnosen).setCaption("Diagnaoses");

		if (editPatient) {
			tabsheet.setSelectedTab(0);
		} else {
			tabsheet.setSelectedTab(1);
		}

		addComponents(getTop(), labelFehler, tabsheet);
		setMargin(true);
	}

	private HorizontalLayout getTop() {
		HorizontalLayout layoutTop = new HorizontalLayout();

		Label labelLastname = new Label(this.patient.getName());
		Label labelFirstName = new Label(this.patient.getVorname());
		Label labelSocialAssuranceNumber = new Label(this.patient.getSvNr());
		Label labelBirthDate = new Label(this.patient.getGebDatum().toString());

		layoutTop.addComponents(labelLastname, labelFirstName,
				labelSocialAssuranceNumber, labelBirthDate);
		layoutTop.setSpacing(true);
		return layoutTop;
	}

	private VerticalLayout getTabPatientBearbeiten() {
		VerticalLayout layoutPatientenDaten = new VerticalLayout();

		final ObjectProperty<Integer> propertyId = new ObjectProperty<Integer>(
				patient.getId());
		final TextField id = new TextField(propertyId);
		id.setVisible(false);

		final ObjectProperty<String> propertyLastName = new ObjectProperty<String>(
				patient.getName());
		final TextField lastName = new TextField(propertyLastName);
		lastName.setCaption("Last name:");

		final ObjectProperty<String> propertyFirstName = new ObjectProperty<String>(
				patient.getVorname());
		final TextField firstName = new TextField(propertyFirstName);
		firstName.setCaption("First name:");

		final ObjectProperty<String> propertySocialAssuranceNumber = new ObjectProperty<String>(
				patient.getSvNr());
		final TextField assuranceNr = new TextField(
				propertySocialAssuranceNumber);
		assuranceNr.setCaption("Social assurance number:");

		final ObjectProperty<Date> propertyBirthDate = new ObjectProperty<Date>(
				patient.getGebDatum());
		final DateField birthDate = new DateField(propertyBirthDate);
		birthDate.setCaption("Birth date:");

		final ComboBox patientState = new ComboBox();
		patientState.setCaption("State:");
		patientState.addItems(PatientState.NO_DANGER,
				PatientState.POTENTIAL_DANGER, PatientState.DANGER);
		patientState.select(PatientState.getByValue(patient.getStatus()));
		final Button saveButton = new Button("Save patient");
		saveButton.addClickListener(e -> {
			this.patient.setName(propertyLastName.getValue());
			this.patient.setVorname(propertyFirstName.getValue());
			this.patient.setSvNr(propertySocialAssuranceNumber.getValue());
			this.patient.setGebDatum(propertyBirthDate.getValue());
			this.patient.setStatus(((PatientState) patientState.getValue())
					.getValue());

			Patient updatedPatient = controller.update(patient);
			if (null == updatedPatient) {
				this.labelFehler
						.setCaption("Daten konnten nicht gespeichert werden.");
			} else {
				this.labelFehler.setCaption("Daten gespeichert.");
				this.patient = updatedPatient;
			}
		});

		final Button deleteButton = new Button("delete patient");
		deleteButton.addClickListener(e -> {
			controller.delete(patient);
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		final Button backButton = new Button("Return to search view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		layoutPatientenDaten.addComponents(id, lastName, firstName,
				assuranceNr, birthDate, patientState, saveButton, deleteButton,
				backButton);
		layoutPatientenDaten.setSpacing(true);
		return layoutPatientenDaten;
	}

	private HorizontalLayout getTabPatientenUebersicht() {
		HorizontalLayout layoutPatientenDaten = new HorizontalLayout();

		layoutPatientenDaten.setMargin(true);

		VerticalLayout listen = new VerticalLayout();
		listen.setSpacing(true);

		Label labelMedikamente = new Label("Aktuelle Medikamente:");
		BeanItemContainer<Medikament> medis = new BeanItemContainer<Medikament>(
				Medikament.class, patient.getMedikaments());
		Grid gridMedikamente = getMedikamenteGrid(medis);

		Label labelDiagnosen = new Label("Aktuelle Diagnosen:");
		BeanItemContainer<Diagnose> diagnoses = new BeanItemContainer<Diagnose>(
				Diagnose.class, patient.getDiagnoses());
		Grid gridDiagnosen = getDiagnoseGrid(diagnoses);
		listen.addComponents(labelMedikamente, gridMedikamente, labelDiagnosen,
				gridDiagnosen);

		VerticalLayout kontakteLayout = new VerticalLayout();
		kontakteLayout.setSpacing(true);
		kontakteLayout.setMargin(new MarginInfo(false, true, false, true));
		Label labelKontakte = new Label("Kontakte:");
		BeanItemContainer<Kontakt> kontakte = new BeanItemContainer<Kontakt>(
				Kontakt.class, patient.getKontakts());
		Grid gridKontakte = getKontakteGrid(kontakte);
		kontakteLayout.addComponents(labelKontakte, gridKontakte);
		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();

		VerticalLayout statusLayout = new VerticalLayout();
		statusLayout.setMargin(new MarginInfo(true, false, false, false));
		// Image as a file resource
		FileResource resource = new FileResource(new File(basepath + "/images/"
				+ state.changeAmpel(this)));

		// Show the image in the application
		Image image = new Image("Patient gefährlich für andere", resource);
		image.setHeight("200");
		image.setWidth("100");
		statusLayout.addComponent(image);

		layoutPatientenDaten
				.addComponents(listen, kontakteLayout, statusLayout);

		return layoutPatientenDaten;
	}

	private VerticalLayout getTabPatientenMedicaments() {
		VerticalLayout layout = new VerticalLayout();
		Label labelMedikamente = new Label("Medikamente:");
		BeanItemContainer<Medikament> medis = new BeanItemContainer<Medikament>(
				Medikament.class, patient.getMedikamentHistory());
		Grid gridMedikamente = getMedikamenteGrid(medis);
		layout.addComponents(labelMedikamente, gridMedikamente);
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(true, false, false, false));
		return layout;
	}

	private VerticalLayout getTabPatientenDiagnoses() {
		VerticalLayout layout = new VerticalLayout();
		Label labelDiagnosen = new Label("Diagnosen:");
		BeanItemContainer<Diagnose> diagnoses = new BeanItemContainer<Diagnose>(
				Diagnose.class, patient.getDiagnoseHistory());
		Grid gridDiagnosen = getDiagnoseGrid(diagnoses);
		layout.addComponents(labelDiagnosen, gridDiagnosen);
		layout.setSpacing(true);
		layout.setMargin(new MarginInfo(true, false, false, false));
		return layout;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setPatient(int patientId) {
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
				switch (this.patient.getStatus()) {
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
		createTabsheet(editPatient);
	}

	public Grid getMedikamenteGrid(BeanItemContainer<Medikament> medis) {
		Grid gridMedikamente = new Grid(medis);
		gridMedikamente.removeColumn("id");
		gridMedikamente.removeColumn("patient");
		gridMedikamente.removeColumn("active");
		gridMedikamente.removeColumn("compendiummedikament");
		gridMedikamente
				.setColumnOrder("medikamentBezeichnung", "dosis", "arzt");
		gridMedikamente.setHeight("200");
		return gridMedikamente;
	}

	public Grid getDiagnoseGrid(BeanItemContainer<Diagnose> diagnoses) {
		Grid gridDiagnosen = new Grid(diagnoses);
		gridDiagnosen.removeColumn("patient");
		gridDiagnosen.setHeight("200");
		return gridDiagnosen;
	}

	public Grid getKontakteGrid(BeanItemContainer<Kontakt> kontakte) {
		Grid gridKontakte = new Grid(kontakte);
		gridKontakte.removeColumn("patient");
		gridKontakte.setHeight("200");
		gridKontakte.removeColumn("id");
		gridKontakte.setColumnOrder("name", "adresse", "telefonNr");
		return gridKontakte;
	}

	public void setPatientState(State state) {
		this.state = state;
	}

	public State getPatientState() {
		return state;
	}

}
