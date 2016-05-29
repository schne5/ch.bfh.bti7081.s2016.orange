package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.Date;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

public class CreatePatientView extends VerticalLayout implements View {
	public static final String NAME = "Create";

	private final PatientController controller;
	private Patient p;
	private Label hinweis = new Label();

	public CreatePatientView() {
		controller = new PatientController();

		TabSheet tabsheet = new TabSheet();
		VerticalLayout tabCreatePatient = getTabCreatePatient();
		tabsheet.addTab(tabCreatePatient).setCaption("Create");
		
		addComponents(getTop(), hinweis, tabsheet);

		setMargin(true);
	}

	private HorizontalLayout getTop() { // leer mal als Platzhalter drin
		HorizontalLayout layoutTop = new HorizontalLayout();
		layoutTop.setSpacing(true);
		return layoutTop;
	}

	private VerticalLayout getTabCreatePatient() {
		VerticalLayout layoutPatientenDaten = new VerticalLayout();

		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");

		final TextField firstName = new TextField();
		firstName.setCaption("First name:");

		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");

		final DateField birthDate = new DateField();
		birthDate.setCaption("Birth date:");

		final Button createButton = new Button("Create Patient");
		createButton.addClickListener(e -> {
			p = new Patient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(), 0, birthDate.getValue());
			if (controller.create(p)) {
				getUI().getNavigator().navigateTo(StartView.NAME);
			} else {
				this.hinweis.setCaption("Fehlerhafte Eingabe: Neuer Patient konnte nicht erstellt werden");
			}
		});

		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});
		
		final Button startButton = new Button("Return to search view");
		startButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		layoutPatientenDaten.addComponents(lastName, firstName, assuranceNr, birthDate, createButton, startButton,
				backButton);
		
		layoutPatientenDaten.setSpacing(true);
		
		return layoutPatientenDaten;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}