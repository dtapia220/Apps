import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoApp {
    private JFrame frame;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private TaskManager taskManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public TodoApp() {
        taskManager = new TaskManager();
        initialize();
    }

    // Initialize the GUI
    private void initialize() {
        // Set up the main frame
        frame = new JFrame("Todo List Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Set up the task list
        listModel = new DefaultListModel<>();
        taskManager.getTasks().forEach(listModel::addElement);

        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Set up the control panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Add button
        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddTaskDialog();
            }
        });
        panel.add(addButton);

        // Remove button
        JButton removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedTask();
            }
        });
        panel.add(removeButton);

        // Complete button
        JButton completeButton = new JButton("Complete Task");
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeSelectedTask();
            }
        });
        panel.add(completeButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Show dialog to add a new task
    private void showAddTaskDialog() {
        JTextField descriptionField = new JTextField();
        JTextField dueDateField = new JTextField();
        Object[] message = {
                "Description:", descriptionField,
                "Due Date (MM-dd-yyyy):", dueDateField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Task", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String description = descriptionField.getText();
            String dueDateString = dueDateField.getText();

            try {
                // Parse the date and create a new task
                Date dueDate = dateFormat.parse(dueDateString);
                Task task = new Task(description, dueDate);
                taskManager.addTask(task);
                listModel.addElement(task);
            } catch (ParseException e) {
                // Show error message if the date format is invalid
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please use MM-dd-yyyy.");
            }
        }
    }

    // Remove the selected task
    private void removeSelectedTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            taskManager.removeTask(selectedTask);
            listModel.removeElement(selectedTask);
        }
    }

    // Mark the selected task as completed
    private void completeSelectedTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            selectedTask.markAsCompleted();
            taskList.repaint();
            taskManager.saveTasks();
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoApp::new);
    }
}
