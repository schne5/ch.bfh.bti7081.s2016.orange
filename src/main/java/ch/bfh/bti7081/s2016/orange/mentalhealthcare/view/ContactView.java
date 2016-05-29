package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.ContactController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Kontakt;

public class ContactView extends VerticalLayout implements View {
	public static final String NAME = "Contacts";

	private static final long serialVersionUID = -2127813907372672333L;

	private static final String contactName = "Name";
	private static final String contactAddress = "Address";
	private static final String contactTelNr = "Telefon Number";
	private static final String contactType = "Type of Contact";

	private final ContactController controller;

	private int patientId;

	public ContactView() {
		controller = new ContactController();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (event.getParameters() != null) {
			String[] parameters = event.getParameters().split("/");
			try {
				patientId = Integer.parseInt(parameters[0]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				// TODO: handle error
			}
			createContactView();
		}
	}

	private void createContactView() {
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

		// Update table
		updateContactTable(contactTable);

		final Button backButton = new Button("Return to Patient View");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME + "/edit/" + patientId);
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

	@SuppressWarnings("unchecked")
	private void updateContactTable(Table contactTable) {
		contactTable.removeAllItems();

		for (Kontakt k : controller.getContacts(patientId)) {
			Object newItemId = contactTable.addItem();
			Item row = contactTable.getItem(newItemId);
			row.getItemProperty(ContactView.contactName).setValue(k.getName());
			row.getItemProperty(ContactView.contactAddress).setValue(k.getAdresse());
			row.getItemProperty(ContactView.contactTelNr).setValue(k.getTelefonNr());
			row.getItemProperty(ContactView.contactType).setValue(k.getTyp());
		}
	}
}
