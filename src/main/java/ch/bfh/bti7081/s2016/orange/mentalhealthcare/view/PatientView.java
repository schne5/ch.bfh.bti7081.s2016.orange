package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.Date;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PatientView extends VerticalLayout implements View {
	public static final String NAME = "PatientEdit";
	private final PatientController controller;
	private Patient patient;

	public PatientView() {
		controller = new PatientController();
		// TODO nur zum Testen. Patient nicht mehr setzten, wenn von Suche
		// übergeben
		patient = new Patient("Schenk", "Anna", "123456789", 1, new Date());

		TabSheet tabsheet = new TabSheet();

		VerticalLayout tabPatientenDaten = getTabPatientenDaten();
		VerticalLayout tabPatientenUebersicht = getTabPatientenUebersicht();
		VerticalLayout tabPatientenMedikamente = getTabPatientenMedicaments();
		VerticalLayout tabPatientenDiagnosen = getTabPatientenDiagnoses();

		tabsheet.addTab(tabPatientenDaten).setCaption("Edit");
		tabsheet.addTab(tabPatientenUebersicht).setCaption("Overview");
		tabsheet.addTab(tabPatientenMedikamente).setCaption("Medication");
		tabsheet.addTab(tabPatientenDiagnosen).setCaption("Diagnaoses");

		addComponents(getTop(), tabsheet);
	}

	private HorizontalLayout getTop() {
		HorizontalLayout layoutTop = new HorizontalLayout();

		final Label labelLastname = new Label(this.patient.getName());
		final Label labelFirstName = new Label(this.patient.getVorname());
		final Label labelSocialAssuranceNumber = new Label(
				this.patient.getSvNr());
		final Label labelBirthDate = new Label(this.patient.getGebDatum()
				.toString());

		layoutTop.addComponents(labelLastname, labelFirstName,
				labelSocialAssuranceNumber, labelBirthDate);
		layoutTop.setMargin(true);
		return layoutTop;
	}

	private VerticalLayout getTabPatientenDaten() {
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

		final Button saveButton = new Button("Save patient");
		saveButton.addClickListener(e -> {
			this.patient.setName(propertyLastName.getValue());
			this.patient.setVorname(propertyFirstName.getValue());
			this.patient.setSvNr(propertySocialAssuranceNumber.getValue());
			this.patient.setGebDatum(propertyBirthDate.getValue());
			controller.update(patient);
		});

		final Button backButton = new Button("Return to search view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		layoutPatientenDaten.addComponents(id, lastName, firstName,
				assuranceNr, birthDate, saveButton, backButton);
		layoutPatientenDaten.setMargin(true);
		return layoutPatientenDaten;
	}

	private VerticalLayout getTabPatientenUebersicht() {
		// TODO Auto-generated method stub
		return new VerticalLayout();
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
		// TODO Auto-generated method stub
	}
}
