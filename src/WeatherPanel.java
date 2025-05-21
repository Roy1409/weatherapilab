import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class WeatherPanel extends JPanel implements ActionListener, ItemListener {
    private JTextField zipcodeEntry;
    private JButton submitButton;
    private JButton clearButton;
    private JButton forecastButton;
    private JCheckBox showCelsiusCheckBox;
    private String tempLabel;
    private String conditionLabel;
    private String locationLabel;
    private BufferedImage conditionImage;
    private WeatherModel weather;
    private boolean y;

    public WeatherPanel() {
        zipcodeEntry = new JTextField(10);
        submitButton = new JButton("Current");
        forecastButton = new JButton("Forecast");

        clearButton = new JButton("Clear");
        showCelsiusCheckBox = new JCheckBox();
        locationLabel = "Location:";
        tempLabel = "Current temp:";
        conditionLabel = "Current condition:";
        try {
            conditionImage = ImageIO.read(new File("src/placeholder.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        zipcodeEntry.addActionListener(this);
        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        showCelsiusCheckBox.addItemListener(this); // JCheckBox uses ItemListener interface

        add(zipcodeEntry);
        add(submitButton);
        add(forecastButton);
        add(clearButton);
        add(showCelsiusCheckBox);

        playMusic();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw title
        Color pink = new Color(184,74,187);
        g.setColor(pink);
        g.setFont(new Font("Arial Black", Font.BOLD, 22));
        g.drawString("Current Weather", 10, 30);


        // draw zip code textbox + label
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Enter Zip Code:", 10, 63);
        zipcodeEntry.setLocation(120, 50);

        // draw buttons
        submitButton.setLocation(250, 45);
        forecastButton.setLocation(330,45);
        clearButton.setLocation(417, 45);


        // draw checkbox + label
        g.drawString("Show Celsius:", 455, 40);
        showCelsiusCheckBox.setLocation(485, 45);


        g.setFont(new Font("Arial", Font.BOLD, 14));
        if (!y){
        g.drawString(locationLabel, 10, 100);
        g.drawString(tempLabel, 10, 140);

        // draw condition label and image
        g.drawString(conditionLabel, 200, 140);
        g.drawImage(conditionImage, 455, 75, null); } else{
            g.drawString();
        }
    }

    private void loadWeather(String zip) {
        if (!y) {
       weather = WeatherNetworking.getWeatherForZip(zip); }else{
            weather=WeatherNetworking.getForeCastZip(zip);
        }
        // make API request which returns a WeatherModel object
        locationLabel = "Location: " + weather.getLocation();
        if (showCelsiusCheckBox.isSelected()) {
            tempLabel = "Current temp: " + weather.getTempC() + "°C";
        } else {
            tempLabel = "Current temp: " + weather.getTempF() + "°F";
        }
        conditionLabel = "Current condition: " + weather.getCondition();

        // convert condition "icon" (image) from a URL to a BufferedImage that
        // can be drawn on the screen
        try {
            URI uri = new URI(weather.getIconURL());
            URL imageURL = uri.toURL();
            conditionImage = ImageIO.read(imageURL);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void reset() {
        zipcodeEntry.setText("");
        locationLabel = "Location: ";
        tempLabel = "Current temp: ";
        conditionLabel = "Current condition: ";
        try {
            conditionImage = ImageIO.read(new File("src/placeholder.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void playMusic() {
        File audioFile = new File("src/music.wav");
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY); // repeats song
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ActionListener interface method (for JButtons)
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == submitButton) {
            y=false;
            String zipCode = zipcodeEntry.getText();
            loadWeather(zipCode);
        } else if (source == clearButton) {
            y=false;
            reset();
        } else if(source == forecastButton) {
            y=true;
            String zipCode = zipcodeEntry.getText();
            loadWeather(zipCode);

        }
        repaint();  // redraw panel
    }

    // ItemListener interface method (for JCheckBox)
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (weather != null && source instanceof JCheckBox) {
            JCheckBox box = (JCheckBox) source;  // cast source from Object to JCheckBox so we
                                                 // can call the isSelected() JCheckBox method
            if (box.isSelected()) {
                tempLabel = "Current temp: " + weather.getTempC() + "°C";
            } else {
                tempLabel = "Current temp: " + weather.getTempF() + "°F";
            }
        }
        repaint();  // redraw panel
    }
}
