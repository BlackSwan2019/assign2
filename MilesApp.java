/************************************************************************
CSCI 470 section 1
TA:           Priyanka	Kondapuram
Partner 1     Ben Lane
zID:		      z1806979
Partner 2:    Jinhong Yao
zID:		      z178500
Assignment:   2
Date Due:	    3/21/2018

Purpose:      Shows which destinations you can fly to using your air miles; if possible,
              using supersaver miles. Using the remaining miles, it will upgrade farthest
              flights to first class whenever possible.
*************************************************************************/

public class MilesApp {
  public static void main(String[] args) {
    MilesGUI app;   // application window and GUI

    // Make sure a file is supplied at the command line.
    if (args.length != 1) {
      System.out.println("Incorrect number of arguments.");
      System.out.println("Please input only a path to a text file.");

      System.exit(1);
    }

    // Create MilesGUI object.
    app = new MilesGUI(args[0]);

    // Make, populate, and begin app.
    app.go();
  }
}
