import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense implements Serializable {
    private String description;
    private double amount;
    private Date date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public Expense(String description, double amount, Date date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return description + " - $" + amount + " (Date: " + dateFormat.format(date) + ")";
    }
}
