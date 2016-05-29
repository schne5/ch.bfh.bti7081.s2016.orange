package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.StartController;

public class StartView extends VerticalLayout implements View {
	public static final String NAME = "SearchPatients";

	private static final long serialVersionUID = -4883635345472877648L;

	private final StartController controller;

	public StartView() {
		controller = new StartController();

		setMargin(true);

		// Add title
		final Label title = new Label("Search for Patients");
		addComponent(title);

		// Add content
		final HorizontalLayout content = new HorizontalLayout();
		content.setWidth("100%");
		addComponent(content);

		// Add a horizontal layout for the bottom part
		final HorizontalLayout bottom = new HorizontalLayout();
		addComponent(bottom);

		// Add navigation and display sides
		final VerticalLayout nav = new VerticalLayout();
		nav.setWidth("250px");
		final VerticalLayout display = new VerticalLayout();
		display.setWidth("100%");
		content.addComponents(nav, display);
		content.setExpandRatio(display, 1);

		// Add input fields
		final VerticalLayout input = new VerticalLayout();
		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");
		final TextField firstName = new TextField();
		firstName.setCaption("First name:");
		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");
		final DateField birthDate = new DateField();
		birthDate.setCaption("Birth date:");
		input.addComponents(lastName, firstName, assuranceNr, birthDate);
		input.setHeight("300px");

		// Add output field
		final ListSelect select = new ListSelect("Select patient:");
		select.addItems(controller.getPatients());
		select.setNullSelectionAllowed(false);
		display.addComponents(select);

		// Add search button
		final VerticalLayout button = new VerticalLayout();
		final Button searchButton = new Button("Search patient");
		searchButton.addClickListener(e -> {
			controller.searchPatient(lastName.getValue(), firstName.getValue(), assuranceNr.getValue(),
					birthDate.getValue());
			select.removeAllItems();
			select.addItems(controller.getPatients());
		});
		button.addComponent(searchButton);
		button.setHeight("100px");

		nav.addComponents(input, button);

		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});
		bottom.addComponent(backButton);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
