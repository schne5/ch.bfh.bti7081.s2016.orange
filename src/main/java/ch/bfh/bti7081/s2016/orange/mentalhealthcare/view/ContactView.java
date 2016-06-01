package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
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
	private static final String deleteButton = "Delete Entry";
	private static final String saveButton = "Save Entry";

	private final ContactController controller;

	private Table contactTable;
	private Table inputTable;

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
				// TODO: handle error -> create error page
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
		contactTable = createContactTable();
		contactTable.setHeight("400px");
		contactTable.setWidth("900px");
		addComponent(contactTable);

		// Add input table
		inputTable = createInputTable();
		inputTable.setHeight("100px");
		inputTable.setWidth("900px");
		addComponent(inputTable);

		// Update tables
		updateContactTable();
		updateInputTable();

		final Button backButton = new Button("Return to Patient View");
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME + "/edit/" + patientId);
		});
		final VerticalLayout nav = new VerticalLayout();
		nav.setMargin(new MarginInfo(true, false));
		nav.addComponent(backButton);
		addComponent(nav);
	}

	private Table createContactTable() {
		Table contactTable = new Table();

		// Define columns
		contactTable.addContainerProperty(ContactView.contactName, String.class, null);
		contactTable.addContainerProperty(ContactView.contactAddress, String.class, null);
		contactTable.addContainerProperty(ContactView.contactTelNr, String.class, null);
		contactTable.addContainerProperty(ContactView.contactType, String.class, null);
		contactTable.addContainerProperty(ContactView.deleteButton, Button.class, null);

		return contactTable;
	}

	private Table createInputTable() {
		Table inputTable = new Table();
		inputTable.setEditable(true);

		// Define columns
		inputTable.addContainerProperty(ContactView.contactName, String.class, null);
		inputTable.addContainerProperty(ContactView.contactAddress, String.class, null);
		inputTable.addContainerProperty(ContactView.contactTelNr, String.class, null);
		inputTable.addContainerProperty(ContactView.contactType, String.class, null);
		inputTable.addContainerProperty(ContactView.saveButton, Button.class, null);

		return inputTable;
	}

	@SuppressWarnings("unchecked")
	private void updateContactTable() {
		contactTable.removeAllItems();

		for (Kontakt k : controller.getContacts(patientId)) {
			Object newItemId = contactTable.addItem();
			Item row = contactTable.getItem(newItemId);
			row.getItemProperty(ContactView.contactName).setValue(k.getName());
			row.getItemProperty(ContactView.contactAddress).setValue(k.getAdresse());
			row.getItemProperty(ContactView.contactTelNr).setValue(k.getTelefonNr());
			row.getItemProperty(ContactView.contactType).setValue(k.getTyp());

			Button deleteButton = new Button();
			deleteButton.setCaption("X");
			row.getItemProperty(ContactView.deleteButton).setValue(deleteButton);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateInputTable() {
		inputTable.removeAllItems();

		Object newItemId = inputTable.addItem();
		Item row = inputTable.getItem(newItemId);
		row.getItemProperty(ContactView.contactName).setValue("");
		row.getItemProperty(ContactView.contactAddress).setValue("");
		row.getItemProperty(ContactView.contactTelNr).setValue("");
		row.getItemProperty(ContactView.contactType).setValue("");

		Button saveButton = new Button();
		saveButton.setCaption("Save");
		saveButton.addClickListener(e -> {
			String name = (String) row.getItemProperty(ContactView.contactName).getValue();
			String address = (String) row.getItemProperty(ContactView.contactAddress).getValue();
			String telNr = (String) row.getItemProperty(ContactView.contactTelNr).getValue();
			String type = (String) row.getItemProperty(ContactView.contactType).getValue();

			controller.saveContact(patientId, name, address, telNr, type);

			updateContactTable();
			updateInputTable();
		});
		row.getItemProperty(ContactView.saveButton).setValue(saveButton);
	}
}
