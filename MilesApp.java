/************************************************************************
CSCI 470 section 1
TA:
Partner 1     Ben Lane
zID:		      z1806979
Partner 2:    Jinhong Yao
zID:		      z178500
Assignment:   1
Date Due:	    2/14/2018

Purpose:      Shows which destinations you can fly to using your air miles; if possible,
              using supersaver miles. Using the remaining miles, it will upgrade farthest
              flights to first class whenever possible.
*************************************************************************/

public class MilesApp {
  public static void main(String[] args) {
    MilesGUI app = new MilesGUI(args[0]);

    app.go();
  }
}