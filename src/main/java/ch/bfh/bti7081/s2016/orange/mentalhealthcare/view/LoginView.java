package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.LoginController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Arzt;

import com.vaadin.data.util.ObjectProperty;//TODO no luege ob i ds würk bruche

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;//TODO no luege ob ds würk bruche
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {
	public static final String NAME = "Login";

	// TODO private final LoginController controller;
	// wenn Logincontroller implementiert, wieder rausnehmen

	private Arzt a;

	public LoginView() {
		// controller = new LoginController();
		// a = new Arzt();
		TabSheet tabsheet = new TabSheet();
		// VerticalLayout tabLoginEingabe = getTabLoginEingabe();

		Label title = new Label("Login");
		Button backButton = new Button("Return to search view");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(StartView.NAME);
		});
		addComponents(backButton, title);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
}
