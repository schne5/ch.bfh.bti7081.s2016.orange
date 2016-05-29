package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.io.File;
import java.util.Date;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PatientView extends VerticalLayout implements View {

	public static final String NAME = "PatientEdit";
	private final PatientController controller;
	private Patient patient;
	Label labelFehler = new Label();

	public PatientView() {
		controller = new PatientController();
	}

	private void setPatient() {
		TabSheet tabsheet = new TabSheet();

		VerticalLayout tabPatientenDaten = getTabPatientenDaten();
		HorizontalLayout tabPatientenUebersicht = getTabPatientenUebersicht();
		VerticalLayout tabPatientenMedikamente = getTabPatientenMedicaments();
		VerticalLayout tabPatientenDiagnosen = getTabPatientenDiagnoses();

		tabsheet.addTab(tabPatientenDaten).setCaption("Edit");
		tabsheet.addTab(tabPatientenUebersicht).setCaption("Overview");
		tabsheet.addTab(tabPatientenMedikamente).setCaption("Medication");
		tabsheet.addTab(tabPatientenDiagnosen).setCaption("Diagnaoses");

		addComponents(getTop(), labelFehler, tabsheet);
		setMargin(true);
	}

	private HorizontalLayout getTop() {
		HorizontalLayout layoutTop = new HorizontalLayout();

		Label labelLastname = new Label(this.patient.getName());
		Label labelFirstName = new Label(this.patient.getVorname());
		Label labelSocialAssuranceNumber = new Label(this.patient.getSvNr());
		Label labelBirthDate = new Label(this.patient.getGebDatum().toString());

		layoutTop.addComponents(labelLastname, labelFirstName, labelSocialAssuranceNumber, labelBirthDate);
		layoutTop.setSpacing(true);
		return layoutTop;
	}

	private VerticalLayout getTabPatientenDaten() {
		VerticalLayout layoutPatientenDaten = new VerticalLayout();

		final ObjectProperty<Integer> propertyId = new ObjectProperty<Integer>(patient.getId());
		final TextField id = new TextField(propertyId);
		id.setVisible(false);

		final ObjectProperty<String> propertyLastName = new ObjectProperty<String>(patient.getName());
		final TextField lastName = new TextField(propertyLastName);
		lastName.setCaption("Last name:");

		final ObjectProperty<String> propertyFirstName = new ObjectProperty<String>(patient.getVorname());
		final TextField firstName = new TextField(propertyFirstName);
		firstName.setCaption("First name:");

		final ObjectProperty<String> propertySocialAssuranceNumber = new ObjectProperty<String>(patient.getSvNr());
		final TextField assuranceNr = new TextField(propertySocialAssuranceNumber);
		assuranceNr.setCaption("Social assurance number:");

		final ObjectProperty<Date> propertyBirthDate = new ObjectProperty<Date>(patient.getGebDatum());
		final DateField birthDate = new DateField(propertyBirthDate);
		birthDate.setCaption("Birth date:");

		final Button saveButton = new Button("Save patient");
		saveButton.addClickListener(e -> {
			this.patient.setName(propertyLastName.getValue());
			this.patient.setVorname(propertyFirstName.getValue());
			this.patient.setSvNr(propertySocialAssuranceNumber.getValue());
			this.patient.setGebDatum(propertyBirthDate.getValue());
			if (null == controller.update(patient)) {
				this.labelFehler.setCaption("Daten konnten nicht gespeichert werden.");
			} else {
				this.labelFehler.setCaption("Daten gespeichert.");
			}
		});

		final Button backButton = new Button("Return to search view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		layoutPatientenDaten.addComponents(id, lastName, firstName, assuranceNr, birthDate, saveButton, backButton);
		layoutPatientenDaten.setSpacing(true);
		return layoutPatientenDaten;
	}

	private HorizontalLayout getTabPatientenUebersicht() {
		HorizontalLayout layoutPatientenDaten = new HorizontalLayout();
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

		// Image as a file resource
		FileResource resource = new FileResource(new File(basepath + "/images/ampel_rot.png"));

		// Show the image in the application
		Image image = new Image("Patient gefährlich für andere", resource);
		layoutPatientenDaten.addComponent(image);

		return layoutPatientenDaten;
	}

	private VerticalLayout getTabPatientenMedicaments() {
		// TODO Auto-generated method stub
		return new VerticalLayout();
	}

	private VerticalLayout getTabPatientenDiagnoses() {
		// TODO Auto-generated method stub
		return new VerticalLayout();
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			int patientId;
			try {
				patientId = Integer.parseInt(parameters[0]);
				this.labelFehler.setCaption(Integer.toString(patientId));
			} catch (NumberFormatException e) {
				patientId = 0;
			}
			if (patientId > 0) {
				patient = controller.getPatientById(patientId);
			} else {
				// TODO: remove debug data
				patient = new Patient("Schenk", "Anna", "123456789", 1, new Date());
				patient.setId(1);
			}
			setPatient();
		}
	}
}
