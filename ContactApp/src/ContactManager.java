import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private List<Contact> contacts;
    private static final String FILE_PATH = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        saveContacts();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            contacts = new ArrayList<>();
        }
    }
}
