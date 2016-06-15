package ch.bfh.bti7081.s2016.orange.mentalhealthcare;

import javax.servlet.annotation.WebServlet;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.ContactView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.CreatePatientView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.EditPatientView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.CreateMedicationView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.PatientView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.StartView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.TestView;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.view.LoginView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("ch.bfh.bti7081.s2016.orange.mentalhealthcare.MyAppWidgetset")
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		Navigator navigator = new Navigator(this, this);
		navigator.addView(StartView.NAME, StartView.class);
		navigator.addView(PatientView.NAME, PatientView.class);
		navigator.addView(ContactView.NAME, ContactView.class);
		navigator.addView(TestView.NAME, TestView.class);
		navigator.addView(CreatePatientView.NAME, CreatePatientView.class);
		navigator.addView(LoginView.NAME, LoginView.class);
		navigator.addView(CreateMedicationView.NAME, CreateMedicationView.class);
		navigator.addView(EditPatientView.NAME, EditPatientView.class);
		navigator.navigateTo(LoginView.NAME);

		getNavigator().addViewChangeListener(new ViewChangeListener() {
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				// Check if a user has logged in
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof LoginView;

				if (!isLoggedIn && !isLoginView) {
					// go to login view if not logged in
					getNavigator().navigateTo(LoginView.NAME);
					return false;

				} else if (isLoggedIn && isLoginView) {
					// cancel If someone tries to access to login view while
					// logged in
					// it will navigate to StartView
					getNavigator().navigateTo(StartView.NAME);
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub

			}

		});
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
