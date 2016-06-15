package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.DiagnoseController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class DiagnoseOverviewView extends VerticalLayout implements View {
	public static final String NAME = "Diagnoseoverview";
	private static final String Delete_BUTTON = "Delete";
	private static final String EDIT_BUTTON = "Edit";

	private static final String DIAGNOSE_NAME = "Diagnosen Name";
	private static final String ACTIVE = "Active";
	private static final String DOCTOR_NAME = "Doctor";

	private final DiagnoseController controller;
	private final PatientController patientController;

	private Table diagnoseGrid;
	private Table inputTable;

	private int patientId;
	private Patient patient;

	public DiagnoseOverviewView() {
		controller = new DiagnoseController();
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
			createDiagnoseView();
		}
	}

	private void createDiagnoseView() {
		setMargin(true);

		// Add title
		final Label title = new Label("Patient Diagnoses");
		addComponent(title);

		// Add content
		final HorizontalLayout content = new HorizontalLayout();
		content.setWidth("100%");
		addComponent(content);
		patient = patientController.getPatientById(patientId);
		BeanItemContainer<Diagnose> d = new BeanItemContainer<Diagnose>(
				Diagnose.class, patient.getDiagnoses());

		// Add diagnose table
		diagnoseGrid = getDiagnoseTable(d);
		diagnoseGrid.setHeight("400px");
		diagnoseGrid.setWidth("900px");
		addComponent(diagnoseGrid);

		updateDiagnoseTable();

		final Button backButton = new Button("Return to Patient View");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(
					PatientView.NAME + "/edit/" + patientId);
		});

		Button createDiagnose = new Button("Add Diagnose");
		createDiagnose.addClickListener(c -> {
			getUI().getNavigator().navigateTo(
					CreateDiagnoseView.NAME + "/" + patient.getId());
		});

		final VerticalLayout nav = new VerticalLayout();
		nav.setMargin(new MarginInfo(true, false));
		nav.addComponent(backButton);
		addComponents(createDiagnose, nav);
	}

	public Table getDiagnoseTable(BeanItemContainer<Diagnose> diagnoses) {

		Table diagnoseTable = new Table();

		// Define columns
		diagnoseTable.addContainerProperty(DiagnoseOverviewView.DIAGNOSE_NAME,
				String.class, null);
		diagnoseTable.addContainerProperty(DiagnoseOverviewView.DOCTOR_NAME,
				String.class, null);
		diagnoseTable.addContainerProperty(DiagnoseOverviewView.ACTIVE,
				Short.class, null);
		diagnoseTable.addContainerProperty(DiagnoseOverviewView.Delete_BUTTON,
				Button.class, null);
		diagnoseTable.addContainerProperty(DiagnoseOverviewView.EDIT_BUTTON,
				Button.class, null);

		return diagnoseTable;
	}

	@SuppressWarnings("unchecked")
	private void updateDiagnoseTable() {
		diagnoseGrid.removeAllItems();

		for (Diagnose diagnose : controller.getDiagnoses(patientId)) {
			Object newItemId = diagnoseGrid.addItem();
			Item row = diagnoseGrid.getItem(newItemId);
			row.getItemProperty(DiagnoseOverviewView.DIAGNOSE_NAME).setValue(
					diagnose.getDiagnoseName());
			row.getItemProperty(DiagnoseOverviewView.DOCTOR_NAME).setValue(
					diagnose.getDoctor().getName());

			Button deleteButton = new Button();
			deleteButton.setCaption("Delete");
			deleteButton.addClickListener(e -> {
				controller.deleteDiagnose(patientId, diagnose);
				updateDiagnoseTable();
			});
			row.getItemProperty(DiagnoseOverviewView.Delete_BUTTON).setValue(
					deleteButton);
		}
	}
}