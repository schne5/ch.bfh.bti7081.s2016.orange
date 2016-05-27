package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TestView extends VerticalLayout implements View {
	public static final String NAME = "Test";

	private static final long serialVersionUID = -23749064226988867L;

	public TestView() {
		addComponent(new Label("Everything ok so far..."));

		Button button = new Button("Go to Search");
		button.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});

		Button buttonPatientBearbeiten = new Button("Go to edit patient ");
		buttonPatientBearbeiten.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME);
		});

		addComponents(button, buttonPatientBearbeiten);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
