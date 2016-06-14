package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.MedicationController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.PatientController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Compendiummedicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Medicament;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Patient;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.PatientState;











import com.vaadin.client.ui.VFilterSelect.Select;
import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class CreateMedicationView extends VerticalLayout implements View {

	public static final String NAME = "CreateMedication";

	private final MedicationController controller;
	private Medicament medicament;
	private int patientId;
	private int medicamentId;
	private Label hinweis = new Label();// 

	public CreateMedicationView() {
		controller = new MedicationController();	
	}
	

	private VerticalLayout getLayoutCreateMedication() {
		VerticalLayout layoutMedicationData = new VerticalLayout();
		final Label title = new Label("Create Medication");
		
		final ComboBox compMedication = new ComboBox();
		compMedication.setCaption("Compendium medication:");
		compMedication.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		compMedication.setNullSelectionAllowed(false);
		List<Compendiummedicament> medicaments =controller.getCompendiumList();
		
		for(Compendiummedicament compMed : medicaments){
			compMedication.addItem(compMed.getId());
			compMedication.setItemCaption(compMed.getId(), compMed.getName());
		}
			
		final TextField dose = new TextField();
		dose.setCaption("Dose:");

		CheckBox active = new CheckBox();
		active.setCaption("Active:");
		
		final TextField takings = new TextField();
		takings.setCaption("Takings:");
		
		final ComboBox takingsDuration = new ComboBox();
		takingsDuration.setCaption("When:");
		takingsDuration.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
		takingsDuration.setNullSelectionAllowed(false);
		List<Takings> durations = new ArrayList<Takings>();
		durations.add(Takings.PER_DAY);
		durations.add(Takings.PER_WEEK);
		durations.add(Takings.PER_MONTH);
		
		for(Takings t : durations){
			takingsDuration.addItem(t);
			takingsDuration.setItemCaption(t,t.getText());
		}
		System.out.println("HIER" +medicamentId);
		if(medicamentId>0){
			
			Medicament medicament= controller.getMedicamentById(medicamentId);
			compMedication.setValue(medicament.getCompendiummedicament().getId());
			String doseString= new Double(medicament.getDose().doubleValue()).toString();
			dose.setValue(doseString);
			takings.setValue(new Integer(medicament.getTakings()).toString());
			active.setValue(medicament.getActive() ==1 ? true :false);
		}
		final Button createButton = new Button("Create Medication");
		if(medicamentId<=0){
		
		createButton.addClickListener(e -> {
			int arztId=(int) getSession().getAttribute("user");
			short isActive = (short)(active.getValue()?1:0);
			if(controller.validateDose(dose.getValue(), takings.getValue(),(int) compMedication.getValue())==false){
				Compendiummedicament medicament = controller.getCompendiummedicamentById((int) compMedication.getValue());
				this.hinweis.setCaption("Error: The selected dose is not in the approved Range of the Medicament: "+ medicament.getName() +" Dose: "+medicament.getMaxDose());
			}else{
				if (controller.saveMedication(patientId,(int) compMedication.getValue(), dose.getValue(),takings.getValue(), isActive,arztId)) {
					getUI().getNavigator().navigateTo(PatientView.NAME + "/" + patientId);
				} else {

					this.hinweis.setCaption("Error: New Medication couldnt be persisted because of invalid Data");
				}
			}	
		});
		}
		if(medicamentId>0){
			createButton.setCaption("Save Changes");
			createButton.addClickListener(e -> {
				short isActive = (short)(active.getValue()?1:0);
				if(controller.validateDose(dose.getValue(), takings.getValue(),(int) compMedication.getValue())==false){
					Compendiummedicament medicament = controller.getCompendiummedicamentById((int) compMedication.getValue());
					this.hinweis.setCaption("Error: The selected dose is not in the approved Range of the Medicament: "+ medicament.getName() +" Dose: "+medicament.getMaxDose());
				}else{
					if (controller.updateMedication(patientId,(int) compMedication.getValue(), dose.getValue(),takings.getValue(), isActive,medicamentId)) {
						getUI().getNavigator().navigateTo(PatientView.NAME + "/" + patientId);
					} else {

						this.hinweis.setCaption("Error: New Medication couldnt be persisted because of invalid Data");
					}
				}	
			});
			
		}

		layoutMedicationData.addComponents(title, compMedication, dose, takings, takingsDuration,active,createButton);

		layoutMedicationData.setSpacing(true);

		return layoutMedicationData;
	}
	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			try {
				patientId = Integer.parseInt(parameters[0]);
				medicamentId=Integer.parseInt(parameters[1]);
				System.out.println("ENTER" +medicamentId);
				createMedicationView();
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				// TODO: handle error -> create error page
			}
			
		}
		
	}


	private void createMedicationView() {
		final Button backButton = new Button("Return to patient view");
		backButton.setStyleName(BaseTheme.BUTTON_LINK);
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME+"/"+patientId);
		});
		
		final GridLayout menu = new GridLayout(2,1);
		menu.setWidth("100%");
		menu.addComponent(backButton,1,0);
		menu.setComponentAlignment(backButton, Alignment.TOP_RIGHT);
		addComponent(menu);
		VerticalLayout layoutCreatePatient = getLayoutCreateMedication();
		addComponents(hinweis, layoutCreatePatient);
		setMargin(true);
	
		
	}

}
