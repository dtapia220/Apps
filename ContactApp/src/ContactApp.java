import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactApp {
    private JFrame frame;
    private DefaultListModel<Contact> listModel;
    private JList<Contact> contactList;
    private ContactManager contactManager;

    public ContactApp() {
        contactManager = new ContactManager();
        initialize();
    }

    // Initialize the GUI
    private void initialize() {
        // Set up the main frame
        frame = new JFrame("Contact Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Set up the contact list
        listModel = new DefaultListModel<>();
        contactManager.getContacts().forEach(listModel::addElement);

        contactList = new JList<>(listModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(contactList), BorderLayout.CENTER);

        // Set up the control panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Add button
        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddContactDialog();
            }
        });
        panel.add(addButton);

        // Remove button
        JButton removeButton = new JButton("Remove Contact");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedContact();
            }
        });
        panel.add(removeButton);

        frame.setVisible(true);
    }

    // Show dialog to add a new contact
    private void showAddContactDialog() {
        JTextField nameField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();
        Object[] message = {
                "Name:", nameField,
                "Phone Number:", phoneNumberField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Contact", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String email = emailField.getText();

            Contact contact = new Contact(name, phoneNumber, email);
            contactManager.addContact(contact);
            listModel.addElement(contact);
        }
    }

    // Remove the selected contact
    private void removeSelectedContact() {
        Contact selectedContact = contactList.getSelectedValue();
        if (selectedContact != null) {
            contactManager.removeContact(selectedContact);
            listModel.removeElement(selectedContact);
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactApp::new);
    }
}
