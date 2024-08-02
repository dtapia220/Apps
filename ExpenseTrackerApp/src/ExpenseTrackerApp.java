import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseTrackerApp {
    private JFrame frame;
    private DefaultListModel<Expense> listModel;
    private JList<Expense> expenseList;
    private ExpenseManager expenseManager;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public ExpenseTrackerApp() {
        expenseManager = new ExpenseManager();
        initialize();
    }

    // Initialize the GUI
    private void initialize() {
        // Set up the main frame
        frame = new JFrame("Expense Tracker Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Set up the expense list
        listModel = new DefaultListModel<>();
        expenseManager.getExpenses().forEach(listModel::addElement);

        expenseList = new JList<>(listModel);
        expenseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(expenseList), BorderLayout.CENTER);

        // Set up the control panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        // Add button
        JButton addButton = new JButton("Add Expense");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddExpenseDialog();
            }
        });
        panel.add(addButton);

        // Remove button
        JButton removeButton = new JButton("Remove Expense");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedExpense();
            }
        });
        panel.add(removeButton);

        frame.setVisible(true);
    }

    // Show dialog to add a new expense
    private void showAddExpenseDialog() {
        JTextField descriptionField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        Object[] message = {
                "Description:", descriptionField,
                "Amount:", amountField,
                "Date (MM-dd-yyyy):", dateField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Expense", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String description = descriptionField.getText();
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid amount. Please enter a valid number.");
                return;
            }
            String dateString = dateField.getText();

            try {
                // Parse the date and create a new expense
                Date date = dateFormat.parse(dateString);
                Expense expense = new Expense(description, amount, date);
                expenseManager.addExpense(expense);
                listModel.addElement(expense);
            } catch (ParseException e) {
                // Show error message if the date format is invalid
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please use MM-dd-yyyy.");
            }
        }
    }

    // Remove the selected expense
    private void removeSelectedExpense() {
        Expense selectedExpense = expenseList.getSelectedValue();
        if (selectedExpense != null) {
            expenseManager.removeExpense(selectedExpense);
            listModel.removeElement(selectedExpense);
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseTrackerApp::new);
    }
}
