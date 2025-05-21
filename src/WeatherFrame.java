import javax.swing.*;

public class WeatherFrame {
    public WeatherFrame() {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 200);
        frame.setLocationRelativeTo(null);
        WeatherPanel panel = new WeatherPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
