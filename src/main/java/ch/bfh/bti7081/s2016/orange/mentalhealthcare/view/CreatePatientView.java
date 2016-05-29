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
		
		
		//final Label title = new Label("Create"); 
	
		//final TextField lastName = new TextField();//weg
		//lastName.setCaption("Last name:");//weg
		//final TextField firstName = new TextField();//weg
		//firstName.setCaption("First name:");//weg
		//vfinal TextField assuranceNr = new TextField();//weg
		//assuranceNr.setCaption("Social assurance number:");//weg
		//final DateField birthDate = new DateField();//weg
		//birthDate.setCaption("Birth date:");//weg
		//final Button backButton = new Button("Return to main view");//weg
		//backButton.addClickListener(e -> {//weg
		//getUI().getNavigator().navigateTo(TestView.NAME);//weg
		//});
		//final Button createButton = new Button("Create Patient");//weg
		//createButton.addClickListener(e -> {//weg
		//p = new Patient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(), 0, birthDate.getValue());//weg
		//if (controller.create(p)) {//weg
		//	getUI().getNavigator().navigateTo(StartView.NAME);//weg
		//} else {
		//	this.hinweis.setCaption("Fehlerhafte Eingabe: Neuer Patient konnte nicht erstellt werden");//weg
		//	}//weg
		//});//weg

		addComponents(getTop(), hinweis, tabsheet);
		//addComponents(title, lastName, firstName, assuranceNr, birthDate, hinweis,createButton, backButton);
		setMargin(true);
	}
	
	private HorizontalLayout getTop(){ // leer mal als Platzhalter drin
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
		
		layoutPatientenDaten.addComponents(lastName, firstName,
				assuranceNr, birthDate, createButton, startButton, backButton);
		layoutPatientenDaten.setSpacing(true);
		return layoutPatientenDaten;
	}
	
	

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}