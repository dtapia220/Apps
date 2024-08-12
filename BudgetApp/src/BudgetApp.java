import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class BudgetApp extends JFrame {
    private BudgetManager budgetManager;
    private DefaultListModel<String> transactionListModel;

    public BudgetApp() {
        budgetManager = new BudgetManager();
        transactionListModel = new DefaultListModel<>();

        setTitle("Budget Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Type (Income/Expense):"));
        JTextField typeField = new JTextField();
        inputPanel.add(typeField);

        inputPanel.add(new JLabel("Category:"));
        JTextField categoryField = new JTextField();
        inputPanel.add(categoryField);

        inputPanel.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        JTextField dateField = new JTextField();
        inputPanel.add(dateField);

        JButton addButton = new JButton("Add Transaction");
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        transactionListModel = new DefaultListModel<>();
        JList<String> transactionList = new JList<>(transactionListModel);
        add(new JScrollPane(transactionList), BorderLayout.CENTER);

        JPanel summaryPanel = new JPanel(new GridLayout(3, 1));
        JLabel incomeLabel = new JLabel("Total Income: $0.00");
        JLabel expenseLabel = new JLabel("Total Expenses: $0.00");
        JLabel balanceLabel = new JLabel("Balance: $0.00");

        summaryPanel.add(incomeLabel);
        summaryPanel.add(expenseLabel);
        summaryPanel.add(balanceLabel);

        add(summaryPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = typeField.getText();
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());
                LocalDate date = LocalDate.parse(dateField.getText());

                Transaction transaction = new Transaction(type, category, amount, date);
                budgetManager.addTransaction(transaction);

                transactionListModel.addElement(
                        String.format("%s - %s: $%.2f on %s", type, category, amount, date));

                incomeLabel.setText(String.format("Total Income: $%.2f", budgetManager.getTotalIncome()));
                expenseLabel.setText(String.format("Total Expenses: $%.2f", budgetManager.getTotalExpenses()));
                balanceLabel.setText(String.format("Balance: $%.2f", budgetManager.getBalance()));

                typeField.setText("");
                categoryField.setText("");
                amountField.setText("");
                dateField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BudgetApp app = new BudgetApp();
            app.setVisible(true);
        });
    }
}
