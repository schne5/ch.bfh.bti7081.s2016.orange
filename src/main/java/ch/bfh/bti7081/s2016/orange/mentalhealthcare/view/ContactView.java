package ch.bfh.bti7081.s2016.orange.mentalhealthcare.view;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import ch.bfh.bti7081.s2016.orange.mentalhealthcare.controller.ContactController;
import ch.bfh.bti7081.s2016.orange.mentalhealthcare.model.Contact;

public class ContactView extends VerticalLayout implements View {
	public static final String NAME = "Contacts";

	private static final long serialVersionUID = -2127813907372672333L;

	private static final String CONTACT_NAME = "Name";
	private static final String CONTACT_ADDRESS = "Address";
	private static final String CONTACT_TEL_NR = "Telefon Number";
	private static final String CONTACT_TYPE = "Type of Contact";
	private static final String DELETE_BUTTON = "Delete Entry";
	private static final String SAVE_BUTTON = "Save Entry";

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

			patientId = Integer.parseInt(parameters[0]);

			createContactView();
		}
	}

	private void createContactView() {
		setMargin(true);
		
		final Button backButton = new Button("Return to Patient View");
		backButton.setStyleName(BaseTheme.BUTTON_LINK);
		backButton.addClickListener(e -> {
			getUI().getNavigator().navigateTo(PatientView.NAME + "/" + patientId);
		});
		
		final GridLayout menu = new GridLayout(2,1);
		menu.setWidth("100%");
		menu.addComponent(backButton,1,0);
		menu.setComponentAlignment(backButton, Alignment.TOP_RIGHT);
		addComponent(menu);
		
		
		
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
		contactTable.setWidth("950px");
		addComponent(contactTable);

		// Add input table
		inputTable = createInputTable();
		inputTable.setHeight("100px");
		inputTable.setWidth("950px");
		addComponent(inputTable);

		// Update tables
		updateContactTable();
		updateInputTable();

		final VerticalLayout nav = new VerticalLayout();
		nav.setMargin(new MarginInfo(true, false));
		addComponent(nav);
	}

	private Table createContactTable() {
		Table contactTable = new Table();

		// Define columns
		contactTable.addContainerProperty(ContactView.CONTACT_NAME, String.class, null);
		contactTable.addContainerProperty(ContactView.CONTACT_ADDRESS, String.class, null);
		contactTable.addContainerProperty(ContactView.CONTACT_TEL_NR, String.class, null);
		contactTable.addContainerProperty(ContactView.CONTACT_TYPE, String.class, null);
		contactTable.addContainerProperty(ContactView.DELETE_BUTTON, Button.class, null);

		return contactTable;
	}

	private Table createInputTable() {
		Table inputTable = new Table();
		inputTable.setEditable(true);

		// Define columns
		inputTable.addContainerProperty(ContactView.CONTACT_NAME, String.class, null);
		inputTable.addContainerProperty(ContactView.CONTACT_ADDRESS, String.class, null);
		inputTable.addContainerProperty(ContactView.CONTACT_TEL_NR, String.class, null);
		inputTable.addContainerProperty(ContactView.CONTACT_TYPE, String.class, null);
		inputTable.addContainerProperty(ContactView.SAVE_BUTTON, Button.class, null);

		return inputTable;
	}

	@SuppressWarnings("unchecked")
	private void updateContactTable() {
		contactTable.removeAllItems();

		for (Contact contact : controller.getContacts(patientId)) {
			Object newItemId = contactTable.addItem();
			Item row = contactTable.getItem(newItemId);
			row.getItemProperty(ContactView.CONTACT_NAME).setValue(contact.getName());
			row.getItemProperty(ContactView.CONTACT_ADDRESS).setValue(contact.getAdress());
			row.getItemProperty(ContactView.CONTACT_TEL_NR).setValue(contact.getPhoneNr());
			row.getItemProperty(ContactView.CONTACT_TYPE).setValue(contact.getContactType());

			Button deleteButton = new Button();
			deleteButton.setStyleName(BaseTheme.BUTTON_LINK);
			deleteButton.setCaption("Delete");
			deleteButton.addClickListener(e -> {
				controller.deleteContact(patientId, contact);
				updateContactTable();
			});
			row.getItemProperty(ContactView.DELETE_BUTTON).setValue(deleteButton);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateInputTable() {
		inputTable.removeAllItems();

		Object newItemId = inputTable.addItem();
		Item row = inputTable.getItem(newItemId);
		row.getItemProperty(ContactView.CONTACT_NAME).setValue("");
		row.getItemProperty(ContactView.CONTACT_ADDRESS).setValue("");
		row.getItemProperty(ContactView.CONTACT_TEL_NR).setValue("");
		row.getItemProperty(ContactView.CONTACT_TYPE).setValue("");

		Button saveButton = new Button();
		saveButton.setStyleName(BaseTheme.BUTTON_LINK);
		saveButton.setCaption("Save");
		saveButton.addClickListener(e -> {
			String name = (String) row.getItemProperty(ContactView.CONTACT_NAME).getValue();
			String address = (String) row.getItemProperty(ContactView.CONTACT_ADDRESS).getValue();
			String telNr = (String) row.getItemProperty(ContactView.CONTACT_TEL_NR).getValue();
			String type = (String) row.getItemProperty(ContactView.CONTACT_TYPE).getValue();

			controller.saveContact(patientId, name, address, telNr, type);
			updateContactTable();
			updateInputTable();
		});
		row.getItemProperty(ContactView.SAVE_BUTTON).setValue(saveButton);
	}
}
