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

/**
* This class produces & populates the GUI and handles actions.
*/
public class MilesGUI {
  Scanner input;

  JFrame frame;           // main window
  JPanel cityList;        // left panel of window
  JPanel redeemMiles;     // right panel of window
  JList<String> list;     // list of cities
  JScrollPane scrollPane;
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

  JTextField reqOutput = new JTextField();        // required miles output
  JTextField upgradeOutput = new JTextField();    // upgrade miles output
  JTextField superSaverOutput = new JTextField(); // SuperSaver miles output
  JTextField monthsOutput = new JTextField();     // SuperSaver months output

  JTextField milesInput;    // user types accumulated miles here
  JTextArea results;        // text area where list of results will be displayed
  JTextField remMiles;      // display of remaining miles after redeeming

  SpinnerModel model;       // spinner model used within the JSpinner
  JSpinner monthInput;      // selection of months

  JButton button;           // redeem button

  /**
   * Constructor for MilesGUI class.
   * @param   filePath    path to file of destinations
   * @return  void
   */
  MilesGUI(String filePath) {
    file = new File(filePath);
  }

  /**
   * Makes the GUI and handles actions.
   * @param   none
   * @return  void
   */
  public void go() {
    frame = new JFrame("Mileage Redeemer");   // Make a Windows frame.
    cityList = new JPanel();                  // Make left panel.
    redeemMiles = new JPanel();               // Make right panel.

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    // Make execution stop upon clicking X.
    frame.setSize(950, 360);                                          // Make window 950x350.
    frame.setMinimumSize(new Dimension(950, 360));                    // Minimum size of window

    // Left panel layout.
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

    l.readDestinations(input);    // Fill MilesRedeemer l with destination objects.

    cities = l.getCityNames();    // Populate cities list with city names.

    // Create city list scroll panel and add to app's uppepr left panel.
    list = new JList<String>(cities);
    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    list.setVisibleRowCount(-1);
    list.addListSelectionListener(new cityListListener());
    scrollPane = new JScrollPane(list);
    scrollPane.setPreferredSize(new Dimension(250, 150));
    cityList.add(BorderLayout.PAGE_START, scrollPane);

    // Create city data fields and add to app's bottom left panel.
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

    // Format the right side of the app.
    redeemMiles.setBorder(BorderFactory.createLineBorder(Color.black));
    redeemMiles.setBorder(BorderFactory.createTitledBorder("Redeem Miles"));
    redeemMiles.setBackground(new Color(217, 217, 217));
    redeemMiles.setLayout(new BorderLayout(0, 9));

    // Panel containing fields/sub-panels for user input.
    userInput = new JPanel();
    userInput.setLayout(new GridLayout(3, 1));
    userInput.setBackground(new Color(217, 217, 217));
    redeemMiles.add(BorderLayout.PAGE_START, userInput);

    // Area for user's accumulated miles input.
    yourMiles = new JPanel();
    yourMiles.setBackground(new Color(217, 217, 217));
    yourMiles.add(new JLabel("Your Accumulated Miles"));
    milesInput = new JTextField(15);
    yourMiles.add(milesInput);
    userInput.add(BorderLayout.PAGE_START, yourMiles);

    // JSpinner month selector area.
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

    // Redeem button area.
    redeemButton = new JPanel();
    redeemButton.setBackground(new Color(217, 217, 217));
    button = new JButton("Redeem tickets > > >");
    button.addActionListener(new redeemButtonListener());
    redeemButton.add(button);
    userInput.add(redeemButton);

    // Create results area and place it on app's bottom right.
    results = new JTextArea();
    scrollPane = new JScrollPane(results);
    scrollPane.setPreferredSize(new Dimension(250, 150));
    redeemMiles.add(BorderLayout.CENTER, scrollPane);

    // Create remaining miles data section.
    remainingMiles = new JPanel();
    remainingMiles.setBackground(new Color(217, 217, 217));
    remainingMiles.add(new JLabel("Your remaining miles"));
    remMiles = new JTextField(15);
    remainingMiles.add(remMiles);
    redeemMiles.add(BorderLayout.PAGE_END, remainingMiles);

    // Now place the 2 main panels onto the app's frame/window.
    frame.add(BorderLayout.LINE_START, cityList);
    frame.add(BorderLayout.CENTER, redeemMiles);

    frame.setVisible(true);
  }

  /**
  * This class listens for selections on the list of cities.
  */
  class cityListListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent event) {
      int index = list.getSelectedIndex();    // Get selected city's index number.
      int normMiles = 0;                      // normal miles
      int upgradeMiles = 0;                   // upgrade miles
      int superSaveMiles = 0;                 // SuperSaver miles
      String months = "";                     // month names used for SuperSaver range

      // Fill 'destinations' with city objects.
      destinations = new ArrayList<Destination>(l.getDestList());

      // Loop through destinations and obtain field data from each one.
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

      // Display city data in the four fields.
      reqOutput.setText(Integer.toString(normMiles));
      upgradeOutput.setText(Integer.toString(upgradeMiles));
      superSaverOutput.setText(Integer.toString(superSaveMiles));
      monthsOutput.setText(months);
    }
  }

  /**
  * This class listens to the 'redeem' button.
  */
  class redeemButtonListener implements ActionListener {
    /**
    * Implementation of actionPerformed for ActionListener.
    * @param   event      redeem button click event
    * @return  void
    */
    public void actionPerformed(ActionEvent event) {
      r = new MilesRedeemer();  // MilesRedeemer object

      ArrayList<String> resultsList = new ArrayList<String>(); // list of results

      // Populate list with city names read from file.
      try {
        Scanner input = new Scanner(file);
        r.readDestinations(input);
      } catch(IOException e) {
          System.out.println("Cannot find the file specified. Exiting.");

          System.exit(1);
        }

      // Get selected month's string
      String monthNameSpinner = (String)monthInput.getValue();

      // Get index number of selected month.
      int monthNumber = getSelectedIndex(months, monthNameSpinner);

      // As long as there is a number in the miles input field, process input and get results.
      try {
        if (!milesInput.getText().equals(""))
          resultsList = r.redeemMiles(Integer.parseInt(milesInput.getText()), monthNumber);
      } catch(NumberFormatException e) {
        System.out.println("Enter a proper number for accumulated miles.");
      }

      // Reset results area for every click.
      results.setText("");

      // Put a header for the results in the results  area.
      results.append("Your accumulated miles can be used to redeem the following air tickets:\n\n");

      // Populate results area with strings of answers.
      for (String s : resultsList)
        results.append(s + "\n");

      // Display remaining miles in proper field.
      remMiles.setText(Integer.toString(r.getRemainingMiles()));
    }
  }

  /**
  * Gets a list of months.
  * @param   none
  * @return  String[]     a string array of month names
  */
  private String[] getMonthStrings() {
     // Get list of months.
     String[] months = new java.text.DateFormatSymbols().getMonths();

     // Get last index value.
     int lastIndex = months.length - 1;

     // If months is empty or null, copy 'months' into 'monthStrings'.
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

 /**
 * Turns index number into month name.
 * @param   num          month number
 * @return  String       month name
 */
 private String getMonthName(int num) {
   String month = "";   // month name

   // Assign month a name according to month number.
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

 /**
 * Get index number of month name.
 * @param   list         list of months
 * @param   month        selected month
 * @return  int          month number
 */
 public int getSelectedIndex(String[] list, String month) {
    int index = 1;    // month number; default January

    // Loop through month list, looking for match between 'list' element and 'month'.
    for(Object l :list) {
        if(l.equals(month))
            return index;

        index++;
    }

    return -1;
 }
}
