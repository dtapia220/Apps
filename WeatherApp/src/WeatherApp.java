import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherApp {
    private JFrame frame;
    private JTextField cityField;
    private JTextArea resultArea;

    public WeatherApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Weather Monitoring System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField(20);
        JButton fetchButton = new JButton("Fetch Weather");

        panel.add(cityLabel);
        panel.add(cityField);
        panel.add(fetchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                WeatherData weatherData = WeatherFetcher.fetchWeather(city);
                if (weatherData != null) {
                    resultArea.setText(weatherData.toString());
                } else {
                    resultArea.setText("Error fetching weather data.");
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}
