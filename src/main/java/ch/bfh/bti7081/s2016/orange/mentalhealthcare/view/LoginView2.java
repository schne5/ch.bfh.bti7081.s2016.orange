package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.LoginController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;

public class LoginView2 extends VerticalLayout implements View {
	public static final String NAME = "Login";
	private Arzt a = null;

	Label hinweis = new Label(); // Hinweis bei fehlerhafter eingabe
	private final LoginController controller;

	public LoginView2() {
		controller = new LoginController();
		TabSheet tabsheet = new TabSheet();
		VerticalLayout tabLoginDaten = getTabLoginDaten();
		tabsheet.addTab(tabLoginDaten);

		addComponents(getTop(), hinweis, tabsheet);
		setMargin(true);
	}

	private HorizontalLayout getTop() {
		HorizontalLayout layoutTop = new HorizontalLayout();
		layoutTop.addComponents();
		layoutTop.setSpacing(true);
		return layoutTop;
	}

	private VerticalLayout getTabLoginDaten() {
		VerticalLayout layoutLoginDaten = new VerticalLayout();
		layoutLoginDaten.setCaption("Login");

		final TextField username = new TextField();
		username.setCaption("Username:");
		username.setRequired(true);
		username.setNullRepresentation("");
		username.setValue("");

		final PasswordField password = new PasswordField();
		password.setCaption("Password");
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		Button loginButton = new Button("Login");
		loginButton.setWidth("100px");
		loginButton.addClickListener(e -> {
			a = controller.logIn(username.getValue(), password.getValue());

			if (null == a) {
				username.setRequiredError(" ");
				password.setRequiredError(" ");
				hinweis.setCaption("Fehlerhafte Eingabe: Login hat nicht funktioniert");
			} else {
				getUI().getNavigator().navigateTo(StartView.NAME);

			}

		});

		Button backButton = new Button("Return to main view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(TestView.NAME);
		});

		layoutLoginDaten.addComponents(username, password, loginButton, backButton);
		layoutLoginDaten.setSpacing(true);

		return layoutLoginDaten;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
