import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.lang.NumberFormatException;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.*;

public class MilesGUI {
  Scanner input;

  JFrame frame;           // main window
  JPanel cityList;        // left panel of window
  JPanel redeemMiles;     // right panel of window
  JList<String> list;     // list of cities
  JPanel data;            // holds destination mileage information
  JPanel userInput;       // holds 3 sub panels: user miles, month spinner, and redeem button
  JPanel yourMiles;       // holds field for inputting miles
  JPanel departMonth;     // holds month JSpinner
  JPanel redeemButton;    // holds redeem button
  JPanel remainingMiles;  // holds remaining miles information

  MilesRedeemer l = new MilesRedeemer();  // MilesRedeemer object
  MilesRedeemer r = new MilesRedeemer();  // MilesRedeemer object
  ArrayList<Destination> destinations;    // for populating data fields about each city
  File file;                              // file to read from
  String[] cities;                        // names of cities
  String[] months;                        // list of months used for JSpinner

  JTextField reqOutput = new JTextField();
  JTextField upgradeOutput = new JTextField();
  JTextField superSaverOutput = new JTextField();
  JTextField monthsOutput = new JTextField();

  JTextField milesInput;
  JTextArea results;
  JTextField remMiles;

  SpinnerModel model;
  JSpinner monthInput;

  JButton button;

  MilesGUI(String filePath) {
    file = new File(filePath);
  }

  public void go() {
    frame = new JFrame("Mileage Redeemer");
    cityList = new JPanel();
    redeemMiles = new JPanel();

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(950, 350);

    cityList.setBorder(BorderFactory.createLineBorder(Color.black));
    cityList.setBorder(BorderFactory.createTitledBorder("List of Destination Cities"));
    cityList.setBackground(new Color(204, 221, 255));
    cityList.setLayout(new BorderLayout(0, 9));

    // Populate list with city names read from file.

    try {
        input = new Scanner(file);
    } catch(IOException e) {
        System.out.println("Cannot find the file specified. Exiting.");

        System.exit(1);
    }

    l.readDestinations(input);

    cities = l.getCityNames();

    list = new JList<String>(cities);
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list.setVisibleRowCount(-1);
    list.setPreferredSize(new Dimension(250, 150));
    cityList.add(BorderLayout.PAGE_START, list);
    list.addListSelectionListener(new cityListListener());

    data = new JPanel(new GridLayout(4, 2, 7, 19));
    data.setBackground(new Color(204, 221, 255));

    cityList.add(BorderLayout.CENTER, data);

    // Add labels and text fields to data section.
    data.add(new JLabel("Required Miles"));
    data.add(reqOutput);

    data.add(new JLabel("Miles for Upgrading"));
    data.add(upgradeOutput);

    data.add(new JLabel("Miles for SuperSaver"));
    data.add(superSaverOutput);

    data.add(new JLabel("Months for SuperSaver"));
    data.add(monthsOutput);

    redeemMiles.setBorder(BorderFactory.createLineBorder(Color.black));
    redeemMiles.setBorder(BorderFactory.createTitledBorder("Redeem Miles"));
    redeemMiles.setBackground(new Color(217, 217, 217));
    redeemMiles.setLayout(new BorderLayout(0, 9));

    userInput = new JPanel();    // Panel containing fields/sub-panels for usuer input.
    userInput.setLayout(new GridLayout(3, 1));
    userInput.setBackground(new Color(217, 217, 217));
    redeemMiles.add(BorderLayout.PAGE_START, userInput);

    yourMiles = new JPanel();
    yourMiles.setBackground(new Color(217, 217, 217));
    yourMiles.add(new JLabel("Your Accumulated Miles"));
    milesInput = new JTextField(15);
    yourMiles.add(milesInput);
    userInput.add(BorderLayout.PAGE_START, yourMiles);

    departMonth = new JPanel();
    departMonth.setBackground(new Color(217, 217, 217));
    departMonth.add(new JLabel("Month of Departure"));
    months = new String[12];
    months = getMonthStrings();
    model = new SpinnerListModel(months);
    monthInput = new JSpinner(model);
    monthInput.setPreferredSize(new Dimension(120, 20));
    departMonth.add(monthInput);
    userInput.add(departMonth);

    redeemButton = new JPanel();
    redeemButton.setBackground(new Color(217, 217, 217));
    button = new JButton("Redeem tickets > > >");
    button.addActionListener(new redeemButtonListener());
    redeemButton.add(button);
    userInput.add(redeemButton);

    results = new JTextArea();

    redeemMiles.add(BorderLayout.CENTER, results);

    remainingMiles = new JPanel();
    remainingMiles.setBackground(new Color(217, 217, 217));
    remainingMiles.add(new JLabel("Your remaining miles"));
    remMiles = new JTextField(15);
    remainingMiles.add(remMiles);
    redeemMiles.add(BorderLayout.PAGE_END, remainingMiles);

    frame.add(BorderLayout.LINE_START, cityList);
    frame.add(BorderLayout.CENTER, redeemMiles);

    frame.setVisible(true);
  }

  class cityListListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent event) {
      int index = list.getSelectedIndex();
      int normMiles = 0;
      int upgradeMiles = 0;
      int superSaveMiles = 0;
      String months = "";

      destinations = new ArrayList<Destination>(l.getDestList());

      for (int i = 0; i < destinations.size(); i++) {
        if (list.getModel().getElementAt(index).equals(destinations.get(i).getCityName())) {
          normMiles = destinations.get(i).getNormMiles();
          upgradeMiles = destinations.get(i).getUpgradeMiles();
          superSaveMiles = destinations.get(i).getSupSaveMiles();
          months = getMonthName(destinations.get(i).getStartMonth());
          months += " to ";
          months += getMonthName(destinations.get(i).getEndMonth());
        }
      }

      reqOutput.setText(Integer.toString(normMiles));
      upgradeOutput.setText(Integer.toString(upgradeMiles));
      superSaverOutput.setText(Integer.toString(superSaveMiles));
      monthsOutput.setText(months);
    }
  }

  class redeemButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {

      r = new MilesRedeemer();  // MilesRedeemer object

      // Populate list with city names read from file.
      try {
        Scanner input = new Scanner(file);
        r.readDestinations(input);
      } catch(IOException e) {
          System.out.println("Cannot find the file specified. Exiting.");

          System.exit(1);
        }

      ArrayList<String> resultsList = new ArrayList<String>();

      String monthNameSpinner = (String)monthInput.getValue();

      int monthNumber = getSelectedIndex(months, monthNameSpinner);

      try {
        if (!milesInput.getText().equals(""))
          resultsList = r.redeemMiles(Integer.parseInt(milesInput.getText()), monthNumber);
      } catch(NumberFormatException e) {
        System.out.println("Enter a proper number.");
      }

      results.setText("");

      results.append("Your accumulated miles can be used to redeem the following air tickets:\n\n");

      for (String s : resultsList)
        results.append(s + "\n");

      remMiles.setText(Integer.toString(r.getRemainingMiles()));
    }
  }

  private String[] getMonthStrings() {
     String[] months = new java.text.DateFormatSymbols().getMonths();

     int lastIndex = months.length - 1;

     if (months[lastIndex] == null || months[lastIndex].length() <= 0) {

     //last item empty
     String[] monthStrings = new String[lastIndex];

     System.arraycopy(months, 0, monthStrings, 0, lastIndex);

     return monthStrings;
     }
     else {

     //last item not empty
     return months;
   }
 }

 private String getMonthName(int num) {
   String month = "";

   switch(num) {
     case 1: month = "January";
             break;
     case 2: month = "February";
             break;
     case 3: month = "March";
             break;
     case 4: month = "April";
             break;
     case 5: month = "May";
             break;
     case 6: month = "June";
             break;
     case 7: month = "July";
             break;
     case 8: month = "August";
             break;
     case 9: month = "September";
             break;
     case 10: month = "October";
             break;
     case 11: month = "November";
             break;
     case 12: month = "December";
             break;
   }

   return month;
 }

 public int getSelectedIndex(String[] list, String month) {
    int index = 1;

    for(Object l :list) {
        if(l.equals(month))
            return index;

        index++;
    }

    return -1;
 }
}
