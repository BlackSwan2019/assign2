/**
* This class reads a file and processes user info to create
* a list of destinations a flyer can go to and what class status
* they can fly with per flight, all within an accrued miles amount.
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.StringTokenizer;
import java.util.*;
import java.util.Iterator;

public class MilesRedeemer {
  ArrayList<Destination> dests = new ArrayList<Destination>();
  ArrayList<Destination> finalList = new ArrayList<Destination>();  // helper list for finalOutput
  ArrayList<String> finalOutput = new ArrayList<String>();      // list of sentences showing destinations and class-type

  private String city;
  private int normMiles;      // normal mileage (also distance from ORD)
  private int supMiles;       // supersaver miles
  private int upMiles;        // miles needed for first class upgrade
  private int startMonth;     // beginning of supersaver month range
  private int endMonth;       // end of supersaver month range

  private int remainingMiles = 0;

  private int numLines = 0;   // number of lines in text file

  private int i = 0;          // array index

  /**
   * Fills an array list with destinations read from a text file.
   *
   * @param   fileScanner   file to be read and parsed
   *
   * @return  void
   */
  public void readDestinations(Scanner fileScanner) {
    StringTokenizer line;   // holds a string of single destination data.

    // While there are lines to be read in the file.
    while (fileScanner.hasNextLine()) {
      // Put a single line from file into line.
      line = new StringTokenizer(fileScanner.nextLine(), ";-");

      // While there are still lines in the file.
      try{
        while (line.hasMoreTokens()) {
          // Fill each data point with a token from the line.
          city = line.nextToken();
          normMiles = Integer.parseInt(line.nextToken());
          supMiles = Integer.parseInt(line.nextToken());
          upMiles = Integer.parseInt(line.nextToken());
          startMonth = Integer.parseInt(line.nextToken());
          endMonth = Integer.parseInt(line.nextToken());
        }
      } catch (NumberFormatException e) {
        System.out.println("Error reading the file. Exiting.");

        System.exit(1);
        }

      // Add city and data to array list.
      dests.add(new Destination(city, normMiles, supMiles, upMiles, startMonth, endMonth));

      // Increment the number of lines/destinations found so far.
      numLines++;
    }

    // Sort destinations in ascending order via normal miles.
    Collections.sort(dests, Destination.mileSort);
  }

  /**
   * Obtains the city names from each destination.
   *
   * @param   none
   *
   * @return  String[]     array of city names
   */
  public String[] getCityNames() {
    String[] cityNames = new String[numLines];  // holds city names

    // Fill city name array with cities.
    for (Destination d : dests) {
      cityNames[i++] = d.getCityName();
    }

    // Sort city names by alphabetical order.
    Arrays.sort(cityNames);

    return cityNames;
  }

  /**
   * Using a certain amount of miles and month of departure,
   * builds a list of destinations user can go to along with
   * upgrading those flights from economy to first class with remaining
   * miles if possible.
   *
   * @param   miles           destination distance from ORD
   *
   * @param   month           month of departure
   *
   * @return  ArrayList[]     array of city names
   */
  public ArrayList<String> redeemMiles(int miles, int month) {
    int outputIndex = 0;

    remainingMiles = miles;

    // Add destinations to final output until not enough miles. Use supersaver miles when possible.
    for (Destination d : dests) {
      if (month >= d.getStartMonth() && month <= d.getEndMonth()) {
        if (remainingMiles - d.getSupSaveMiles() >= 0) {
          remainingMiles -= d.getSupSaveMiles();

          finalList.add(d);

          finalOutput.add("* A trip to " + d.getCityName() + ", economy class");
        }
      }
      else if (remainingMiles - d.getNormMiles() >= 0) {
        remainingMiles -= d.getNormMiles();

        finalList.add(d);

        finalOutput.add("* A trip to " + d.getCityName() + ", economy class");
      }
    }

    // Try to upgrade to first class using remaining, starting from farthest destination.
    for (Destination d : finalList) {
      if (d.getUpgradeMiles() <= remainingMiles) {
        finalOutput.set(outputIndex, "* A trip to " + d.getCityName() + ", first class");

        remainingMiles -= d.getUpgradeMiles();
      }

      outputIndex++;
    }

    outputIndex = 0;

    return finalOutput;
  }
  /**
   * Returns the amount of miles remaining in account.
   *
   * @return  int             remaining miles
   */
  public int getRemainingMiles() {
    return remainingMiles;
  }

  /**
   * Returns a list of destination objects.
   *
   * @return  ArrayList<Destination>    destinations
   */
  public ArrayList<Destination> getDestList() {
    return dests;
  }
}
