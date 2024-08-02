import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private static final String FILE_PATH = "expenses.txt";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public void removeExpense(Expense expense) {
        expenses.remove(expense);
        saveExpenses();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            expenses = (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            expenses = new ArrayList<>();
        }
    }
}
