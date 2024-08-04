import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Student implements Serializable {
    private String name;
    private String rollNumber;
    private Date dateOfBirth;
    private List<Double> grades;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    public Student(String name, String rollNumber, Date dateOfBirth, List<Double> grades) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.dateOfBirth = dateOfBirth;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return name + " (Roll: " + rollNumber + ", DOB: " + dateFormat.format(dateOfBirth) + ", Avg Grade: " + getAverageGrade() + ")";
    }
}
