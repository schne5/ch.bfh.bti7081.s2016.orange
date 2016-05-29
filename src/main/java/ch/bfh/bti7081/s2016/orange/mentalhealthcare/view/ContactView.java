package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class ContactView extends VerticalLayout implements View {
	public static final String NAME = "Contacts";

	private static final long serialVersionUID = -2127813907372672333L;

	private static final String contactName = "Name";
	private static final String contactAddress = "Address";
	private static final String contactTelNr = "Telefon Number";
	private static final String contactType = "Type of Contact";

	// private final ContactController controller;

	public ContactView() {
		// controller = new ContactController();

		setMargin(true);

		// Add title
		final Label title = new Label("Patient Contacts");
		addComponent(title);

		// Add content
		final HorizontalLayout content = new HorizontalLayout();
		content.setWidth("100%");
		addComponent(content);

		// Add contact table
		final Table contactTable = createContactTable();
		addComponents(contactTable);

		final Button backButton = new Button("Return to Patient View");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME);
		});
		addComponents(backButton);
	}

	private Table createContactTable() {
		Table contactTable = new Table();
		contactTable.setSelectable(true);

		// Define columns
		contactTable.addContainerProperty(ContactView.contactName, String.class, null);
		contactTable.addContainerProperty(ContactView.contactAddress, String.class, null);
		contactTable.addContainerProperty(ContactView.contactTelNr, String.class, null);
		contactTable.addContainerProperty(ContactView.contactType, String.class, null);

		return contactTable;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
