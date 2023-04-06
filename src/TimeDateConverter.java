import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDateConverter extends JFrame  {
    private JTextField dateTF;
    private JTextField timeTF;
    private JComboBox<String> countryList;
    private JFrame mainFrame;
    private JLabel result;

    public TimeDateConverter(){
        createFrame();
        createInputPanel();
        createButtonFrame();
        mainFrame.setVisible(true);
    }


    /**
        *  createFrame(): This methode will create the main frame of our application
    */
    private void createFrame() {
        mainFrame = new JFrame("Time Zone Converter");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 200);
        mainFrame.setLayout(new FlowLayout());
    }



    /**
        *  createInputPanel(): This method will create the Panels which contain the necessary input
        * info : is a panel which contains a message for the user to show the format of the date and the time in the input
        *  zonePanel: The panel which contains a combo box of the different zone possible to covert
        *  dateTimePanel: The panel which contains the text inputs of the date and the time
     */
    private void createInputPanel() {
        JPanel info = new JPanel();
        info.add(new JLabel("Please insert the date in this format: JJ-MM-YYYY and the time in this format HH:MM"));
        mainFrame.add(info);
        // country selection Panel
        JPanel zonePanel = new JPanel();
        zonePanel.add(new JLabel("Select country: "));
        countryList = new JComboBox<>(ZoneId.getAvailableZoneIds().toArray(new String[0]));
        zonePanel.add(countryList);
        mainFrame.add(zonePanel);

        //Date & Time input Panel
        JPanel dateTimePanel= new JPanel();
        dateTimePanel.add(new JLabel("Date: "));
        dateTF = new JTextField(10);
        dateTimePanel.add(dateTF);

        dateTimePanel.add(new JLabel("Time: "));
        timeTF = new JTextField(10);
        dateTimePanel.add(timeTF);
        mainFrame.add(dateTimePanel);
    }


    /**
        * createButtonFrame(): This method will create the frame which contain the button
        * result: the panel which contain the result of the conversion
    */
    private void createButtonFrame() {
        JButton button = new JButton("Convert");
        button.addActionListener(new ButtonListener());
        mainFrame.add(button);
        result = new JLabel();
        mainFrame.add(result);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the Country Inputs, time and date
            String zone = (String) countryList.getSelectedItem();
            String date = dateTF.getText();
            String time = timeTF.getText();

            // Parse the date and time input into a LocalDateTime object
            LocalDateTime ldt = LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            // Convert the date and the time
            ZoneId actualZone = ZoneId.systemDefault();
            ZoneId destinationZone = ZoneId.of(zone);
            ZonedDateTime convertedTime = ldt.atZone(actualZone).withZoneSameInstant(destinationZone);
            /**
                * ldt: is an object from LocalDateTime which is formatted from th date and the time inputs
                * actualZone: is the user's zone
                * destinationZone: is the zone chosen to convert the time to
                * convertedTime: the converted date and time
            */

            // Format the converted  date and time in the specified time zone
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedDateTime = convertedTime.format(formatter);

            // show the result
            result.setText("Your actual zone is "+actualZone+". The date and the Time in "+ zone + " is "+ formattedDateTime);

        }
    }
    public static void main(String[] args) {
        TimeDateConverter t= new TimeDateConverter();
    }

}
