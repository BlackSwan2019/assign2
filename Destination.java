/**
* This class is Destination. It contains all the data for a destination
* as well as operations on it.
*/

import java.util.*;

public class Destination {
    private String cityName = "";     // city name

    private int normMiles = 0;        // normal mileage (also distance from ORD)
    private int superSaveMiles = 0;   // supersaver miles

    private int upgradeMiles = 0;     // miles needed for first class upgrade

    private int startMonth = 1;       // beginning of supersaver month range
    private int endMonth = 12;        // end of supersaver month range

    // Destination object constructor.
    Destination(String newCityName, int newNormMiles, int newSupSaveMiles,
                int newUpgradeMiles, int newStartMonth, int newEndMonth) {
      setCityName(newCityName);
      setNormMiles(newNormMiles);
      setSupSaveMiles(newSupSaveMiles);
      setUpgradeMiles(newUpgradeMiles);
      setStartMonth(newStartMonth);
      setEndMonth(newEndMonth);
    }

    /**
     * Set Destination's city name.
     *
     * @param   newCityName     name of city
     */
    public void setCityName(String newCityName) {
      this.cityName = newCityName;
    }

    /**
     * Set Destination's normal miles.
     *
     * @param   newNormMiles     normal miles
     */
    public void setNormMiles(int newNormMiles) {
      this.normMiles = newNormMiles;
    }

    /**
     * Set Destination's supersaver miles.
     *
     * @param   newSupSaveMiles     supersaver miles
     */
    public void setSupSaveMiles(int newSupSaveMiles) {
      this.superSaveMiles= newSupSaveMiles;
    }

    /**
     * Set Destination's upgrade miles.
     *
     * @param   newUpgradeMiles     miles to upgrade to first class
     */
    public void setUpgradeMiles(int newUpgradeMiles) {
      this.upgradeMiles = newUpgradeMiles;
    }

    /**
     * Set Destination's start month for supersaver miles.
     *
     * @param   newStartMonth     beginning of month range
     */
    public void setStartMonth(int newStartMonth) {
      this.startMonth = newStartMonth;
    }

    /**
     * Set Destination's end month for supersaver miles.
     *
     * @param   newEndMonth     end of month range
     */
    public void setEndMonth(int newEndMonth) {
      this.endMonth = newEndMonth;
    }

    /**
     * Get Destination's city name.
     */
    public String getCityName() {
      return this.cityName;
    }

    /**
     * Get Destination's normal miles.
     */
    public int getNormMiles() {
      return this.normMiles;
    }

    /**
     * Get Destination's supersaver miles.
     */
    public int getSupSaveMiles() {
      return this.superSaveMiles;
    }

    /**
     * Get Destination's upgrade miles.
     */
    public int getUpgradeMiles() {
      return this.upgradeMiles;
    }

    /**
     * Get Destination's supersaver start month.
     */
    public int getStartMonth() {
      return this.startMonth;
    }

    /**
     * Get Destination's supersaver end month.
     */
    public int getEndMonth() {
      return this.endMonth;
    }

    /**
     * Comparator for sorting destination distances in descending order.
     */
    public static Comparator<Destination> mileSort = new Comparator<Destination>() {
      public int compare(Destination d1, Destination d2) {
         /*For descending order*/
         return d2.getNormMiles() - d1.getNormMiles();
     }
    };
}
