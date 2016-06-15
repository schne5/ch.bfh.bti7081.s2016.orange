package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.DiagnoseController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Diagnose;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Icdcdiagnose;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class CreateDiagnoseView extends VerticalLayout implements View {

	public static final String NAME = "Create Diagnose";

	private final DiagnoseController controller;
	private int patientId;
	private int diagnoseId;
	private Label hinweis = new Label();// Hinweis bei fehlerhafter eingabe

	public CreateDiagnoseView() {
		controller = new DiagnoseController();
	}

	private HorizontalLayout getTop() { // leer mal als Platzhalter drin
		HorizontalLayout layoutTop = new HorizontalLayout();
		layoutTop.addComponents();
		layoutTop.setSpacing(true);
		return layoutTop;
	}

	private void createDiagnoseView() {
		VerticalLayout layoutCreateDiagnose = getLayoutCreateDiagnose();
		addComponents(getTop(), hinweis, layoutCreateDiagnose);
		setMargin(true);
	}

	private VerticalLayout getLayoutCreateDiagnose() {
		VerticalLayout layoutDiagnoseData = new VerticalLayout();
		final Label title = new Label("Create Diagnose");

		final ComboBox icdcDiagnose = new ComboBox();
		icdcDiagnose.setCaption("ICDC diagnose:");
		icdcDiagnose.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		icdcDiagnose.setNullSelectionAllowed(false);
		List<Icdcdiagnose> diagnoses = controller.getIcdcList();

		for (Icdcdiagnose d : diagnoses) {
			icdcDiagnose.addItem(d.getId());
			icdcDiagnose.setItemCaption(d.getId(), d.getName());
		}

		CheckBox active = new CheckBox();
		active.setCaption("Active:");

		if (diagnoseId > 0) {
			Diagnose diagnose = controller.getDiagnoseById(diagnoseId);
			icdcDiagnose.setValue(diagnose.getIcdcdiagnose().getId());
			active.setValue(diagnose.getActive() == 1 ? true : false);
		}

		final Button createButton = new Button("Create Diagnose");
		createButton.addClickListener(e -> {
			int arztId=(int) getSession().getAttribute("user");
			short isActive = (short)(active.getValue()?1:0);
				if(diagnoseId <=0){
					if (controller.saveDiagnose(patientId, (int) icdcDiagnose.getValue(), isActive, arztId)) {
						getUI().getNavigator().navigateTo(PatientView.NAME + "/" + patientId);
					} else {

						this.hinweis.setCaption("Error: New Diagnose couldnt be persisted because of invalid Data");
					}
				}
				
		});
		if (diagnoseId > 0) {
			createButton.setCaption("Save Changes");
			createButton.addClickListener(e -> {
				short isActive = (short) (active.getValue() ? 1 : 0);
				if(diagnoseId >0){
					if (controller.updateDiagnose(patientId, (int) icdcDiagnose.getValue(), isActive,diagnoseId)==true) {
						getUI().getNavigator().navigateTo(PatientView.NAME + "/" + patientId);
					} else {

						this.hinweis.setCaption("Error: New Diagnose couldnt be persisted because of invalid Data");
					}
				}
			});

		}

		final Button startButton = new Button("Return to patient view");
		startButton.setStyleName(BaseTheme.BUTTON_LINK);
		startButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(
					PatientView.NAME + "/" + patientId);
		});

		layoutDiagnoseData.addComponents(title, icdcDiagnose, active,
				createButton, startButton);

		layoutDiagnoseData.setSpacing(true);

		return layoutDiagnoseData;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			try {
				patientId = Integer.parseInt(parameters[0]);
				diagnoseId = Integer.parseInt(parameters[1]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				// TODO: handle error -> create error page
			}
			createDiagnoseView();
		}

	}

}
