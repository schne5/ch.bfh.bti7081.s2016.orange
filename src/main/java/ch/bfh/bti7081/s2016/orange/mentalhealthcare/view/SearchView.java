package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SearchView extends VerticalLayout implements View {
	public static final String NAME = "Search";

	private static final long serialVersionUID = -4883635345472877648L;

	public SearchView() {
		final Label title = new Label("Search");

		final TextField lastName = new TextField();
		lastName.setCaption("Last name:");

		final TextField firstName = new TextField();
		firstName.setCaption("First name:");

		final TextField assuranceNr = new TextField();
		assuranceNr.setCaption("Social assurance number:");

		final TextField birthDate = new TextField();
		birthDate.setCaption("Birth date:");

		final Button searchButton = new Button("Search patient");
		searchButton.addClickListener(e -> {
			// TODO: add call
		});

		final Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});

		addComponents(title, lastName, firstName, assuranceNr, birthDate, searchButton, backButton);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
	}
}
