package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.LoginController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Doctor;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {
	public static final String NAME = "Login";

	private Doctor doctor = null;
	private Label errornote = new Label(); 
	final TextField username = new TextField();
	private final LoginController controller;

	public LoginView() {
		final Label title = new Label("Mentalhealthcare patientdata");
		controller = new LoginController();
		//Add layout for Login
		VerticalLayout layoutLoginDaten = getLayoutLoginDaten();
		addComponents(title, errornote, layoutLoginDaten);
		setMargin(true);
	}
	
	//Layout for Login
	private VerticalLayout getLayoutLoginDaten() {
		VerticalLayout layoutLoginDaten = new VerticalLayout();
		
		//add input fields for username
		username.setCaption("Username:");
		username.setRequired(true);
		username.setNullRepresentation("");
		username.setValue("");
		
		//add input fields for password
		final PasswordField password = new PasswordField();
		password.setCaption("Password");
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");
		
		//add login button
		Button loginButton = new Button("Login");
		loginButton.setWidth("100px");
		loginButton.addClickListener(e -> {
		doctor = controller.logIn(username.getValue(), password.getValue());
			
			// notification if the login informations are wrong or if no input in fields 
			//if the Login was correct go to  StartView and save the Id in Session
			if (null == doctor) {

				username.setRequiredError(" ");
				password.setRequiredError(" ");
				errornote.setCaption("The Input was incorrect, you are not logged in");
				
			} else {
				getSession().setAttribute("user", doctor.getId());
				getUI().getNavigator().navigateTo(StartView.NAME);
			}
		});
		
		layoutLoginDaten.addComponents(username, password, loginButton);
		layoutLoginDaten.setSpacing(true);
		return layoutLoginDaten;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		username.focus();
	}
}
