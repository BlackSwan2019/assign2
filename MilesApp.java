/************************************************************************
CSCI 470 section 1
TA:           Priyanka	Kondapuram
Partner 1     Ben Lane
zID:		      z1806979
Partner 2:    Jinhong Yao
zID:		      z178500
Assignment:   1
Date Due:	    3/21/2018

Purpose:      Shows which destinations you can fly to using your air miles; if possible,
              using supersaver miles. Using the remaining miles, it will upgrade farthest
              flights to first class whenever possible.
*************************************************************************/

public class MilesApp {
  public static void main(String[] args) {
    // Create MilesGUI object.
    MilesGUI app = new MilesGUI(args[0]);

    // Make, populate, and begin app.
    app.go();
  }
}
