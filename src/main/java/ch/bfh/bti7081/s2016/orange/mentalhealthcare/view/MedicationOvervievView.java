package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.math.BigDecimal;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.ContactController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.MedicationController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class MedicationOvervievView extends VerticalLayout implements View {

	public static final String NAME = "Mediactionoverview";
	private static final String Delete_BUTTON = "Delete";
	private static final String EDIT_BUTTON = "Edit";
	
	private static final String MEDICAMENT_NAME = "Medicament Name";
	private static final String DOSE = "Dose";
	private static final String TAKINGS = "Takings";
	private static final String ACTIVE = "Active";
	private static final String DOCTOR_NAME = "Doctor";

	
	private final MedicationController controller;
	private final PatientController patientController;

	private Table medicamentGrid;
	private Table inputTable;

	private int patientId;
	private Patient patient;
	
	public MedicationOvervievView() {
		controller = new MedicationController();
		patientController = new PatientController();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			try {
				patientId = Integer.parseInt(parameters[0]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				// TODO: handle error -> create error page
			}
			createMedicationView();
		}	
	}private void createMedicationView() {
		setMargin(true);

		// Add title
		final Label title = new Label("Patient Medicaments");
		addComponent(title);

		// Add content
		final HorizontalLayout content = new HorizontalLayout();
		content.setWidth("100%");
		addComponent(content);
		patient =patientController.getPatientById(patientId);
		BeanItemContainer<Medicament> medis = new BeanItemContainer<Medicament>(Medicament.class,
				patient.getMedicaments());
		
		// Add medicament table
		medicamentGrid = getMedicamentTable(medis);
		medicamentGrid.setHeight("400px");
		medicamentGrid.setWidth("900px");
		addComponent(medicamentGrid);
		
		updateMedicamentTable();
		
		final Button backButton = new Button("Return to Patient View");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME + "/edit/" + patientId);
		});
		
		Button createMedication = new Button("Add Medication");
		createMedication.addClickListener(c -> {
			getUI().getNavigator().navigateTo(CreateMedicationView.NAME + "/" + patient.getId());
		});
		
		final VerticalLayout nav = new VerticalLayout();
		nav.setMargin(new MarginInfo(true, false));
		nav.addComponent(backButton);
		addComponents(createMedication,nav);	
	}
	
	public Table getMedicamentTable(BeanItemContainer<Medicament> medis) {
		
		Table medicamentTable = new Table();
		
		// Define columns
		medicamentTable.addContainerProperty(MedicationOvervievView.MEDICAMENT_NAME, String.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.DOSE, BigDecimal.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.TAKINGS, Integer.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.DOCTOR_NAME, String.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.ACTIVE, Short.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.Delete_BUTTON, Button.class, null);
		medicamentTable.addContainerProperty(MedicationOvervievView.EDIT_BUTTON, Button.class, null);
		
		return medicamentTable;
	}
	
	@SuppressWarnings("unchecked")
	private void updateMedicamentTable() {
		medicamentGrid.removeAllItems();

		for (Medicament medicament : controller.getMedications(patientId)) {
			Object newItemId = medicamentGrid.addItem();
			Item row = medicamentGrid.getItem(newItemId);
			row.getItemProperty(MedicationOvervievView.MEDICAMENT_NAME).setValue(medicament.getMedicamentName());
			row.getItemProperty(MedicationOvervievView.DOSE).setValue(medicament.getDose());
			row.getItemProperty(MedicationOvervievView.TAKINGS).setValue(medicament.getTakings());
			row.getItemProperty(MedicationOvervievView.DOCTOR_NAME).setValue(medicament.getDoctor().getName());

			Button deleteButton = new Button();
			deleteButton.setCaption("Delete");
			deleteButton.addClickListener(e -> {
				controller.deleteMedication(patientId, medicament);
				updateMedicamentTable();
			});
			row.getItemProperty(MedicationOvervievView.Delete_BUTTON).setValue(deleteButton);
		}
	}
}
