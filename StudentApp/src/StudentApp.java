import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentApp {
    private JFrame frame;
    private DefaultListModel<Student> listModel;
    private JList<Student> studentList;
    private StudentManager studentManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public StudentApp() {
        studentManager = new StudentManager();
        initialize();
    }

    // Initialize the GUI
    private void initialize() {
        // Set up the main frame
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Set up the student list
        listModel = new DefaultListModel<>();
        studentManager.getStudents().forEach(listModel::addElement);

        studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(new JScrollPane(studentList), BorderLayout.CENTER);

        // Set up the control panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Create and add buttons to the panel
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");

        // Add modern look and feel to the buttons
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        removeButton.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(addButton);
        panel.add(removeButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddStudentDialog();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedStudent();
            }
        });

        frame.setVisible(true);
    }

    // Show dialog to add a new student
    private void showAddStudentDialog() {
        JTextField nameField = new JTextField();
        JTextField rollNumberField = new JTextField();
        JTextField dateOfBirthField = new JTextField();
        JTextField gradesField = new JTextField();

        // Set modern look and feel to the text fields
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        rollNumberField.setFont(new Font("Arial", Font.PLAIN, 14));
        dateOfBirthField.setFont(new Font("Arial", Font.PLAIN, 14));
        gradesField.setFont(new Font("Arial", Font.PLAIN, 14));

        Object[] message = {
                "Name:", nameField,
                "Roll Number:", rollNumberField,
                "Date of Birth (MM-dd-yyyy):", dateOfBirthField,
                "Grades (comma separated):", gradesField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String rollNumber = rollNumberField.getText();
            String dateOfBirthString = dateOfBirthField.getText();
            String gradesString = gradesField.getText();

            try {
                Date dateOfBirth = dateFormat.parse(dateOfBirthString);
                List<Double> grades = new ArrayList<>();
                for (String grade : gradesString.split(",")) {
                    grades.add(Double.parseDouble(grade.trim()));
                }

                Student student = new Student(name, rollNumber, dateOfBirth, grades);
                studentManager.addStudent(student);
                listModel.addElement(student);
            } catch (ParseException | NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please check the date format and grades.");
            }
        }
    }

    // Remove the selected student
    private void removeSelectedStudent() {
        Student selectedStudent = studentList.getSelectedValue();
        if (selectedStudent != null) {
            studentManager.removeStudent(selectedStudent);
            listModel.removeElement(selectedStudent);
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentApp::new);
    }
}
