/**
 * @author Zhi Chen and Larry
 * Consulted code from Patrick Slough, et al.
 */

package assignment7package;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Vector;

import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class UshahidiUserInterface
{

  public static void main(String args[])
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);

    try
      {

        BufferedReader eyes =
            new BufferedReader(new InputStreamReader(System.in));

        pen.println("Welcome to Ushahidi");
        pen.println("Please enter a Ushahidi Client to be filtered: ");

        //Store the user input url as a string
        String url = eyes.readLine();
        //Create a webClient based on user input url
        UshahidiWebClient webClient = new UshahidiWebClient(url);

        //Create a vector to hold ushahidi incidents from the web client 
        Vector<UshahidiIncident> webIncidentVector =
            new Vector<UshahidiIncident>();

        //Move incidents from the WebClient to webIncidentVector
        while (webClient.hasMoreIncidents())
          {
            webIncidentVector.add(webClient.nextIncident());
          }//while()

        //Create a vector to hold filtered incidents
        Vector<UshahidiIncident> filteredIncidentVector =
            new Vector<UshahidiIncident>();

        pen.println("Please enter a type of filter: ");
        pen.println("A for filtering by date");
        pen.println("B for filtering by distance");
        pen.println("C for filtering by description");

        String input = eyes.readLine();
        switch (input)
          {
            case "A":
              {
                //Get user input
                pen.println("Please enter a starting year: ");
                String sy = eyes.readLine();
                int startingYear = Integer.parseInt(sy);
                pen.println("Please enter a starting month. Like 1, 2,...: ");
                String sm = eyes.readLine();
                int startingMonth = Integer.parseInt(sm);
                pen.println("Please enter a starting date. Like 1, 2,... : ");
                String sd = eyes.readLine();
                int startingDate = Integer.parseInt(sd);
                pen.println("Please enter a ending year: ");
                String ey = eyes.readLine();
                int endingYear = Integer.parseInt(ey);
                pen.println("Please enter a ending month. Like 1, 2,...: ");
                String em = eyes.readLine();
                int endingMonth = Integer.parseInt(em);
                pen.println("Please enter a ending date. Like 1, 2,...: ");
                String ed = eyes.readLine();
                int endingDate = Integer.parseInt(ed);

                //Set user input
                LocalDateTime start =
                    LocalDateTime.of(startingYear, startingMonth, startingDate,
                                     12, 00);
                LocalDateTime end =
                    LocalDateTime.of(endingYear, endingMonth, endingDate, 12,
                                     00);

                //Print out incidents within date range
                filteredIncidentVector =
                    UshahidiExtensions.getIncidentsWithinDates(webClient, start,
                                                               end);

                //Print filtered incidents
                UshahidiExtensions.printVectors(pen, filteredIncidentVector);

                pen.println("Filter completed.");

                break;
              } // case A
            case "B":
              {
                //Get user input
                pen.println("Please enter a latitude. Something like 41.747888: ");
                String lat = eyes.readLine();
                double inputLatitude = Double.parseDouble(lat);
                pen.println("Please enter a longitude. Something like -92.720823: ");
                String lon = eyes.readLine();
                double inputLongitude = Double.parseDouble(lon);
                pen.println("Please enter a unit of distance. km for kilometer and mi for mile: ");
                String unit = eyes.readLine();
                pen.println("Please enter a search radius: ");
                String sr = eyes.readLine();
                double searchDistance = Double.parseDouble(sr);

                //Filter incidents
                filteredIncidentVector =
                    UshahidiExtensions.getIncidentsWithinDistance(webIncidentVector,
                                                                  inputLatitude,
                                                                  inputLongitude,
                                                                  searchDistance,
                                                                  unit);

                //Print filtered incidents
                UshahidiExtensions.printVectors(pen, filteredIncidentVector);

                pen.println("Filter completed.");

                break;
              }// case B
            case "C":
              {
                pen.println("Please enter a description to look for: ");
                CharSequence inputString = eyes.readLine();

                filteredIncidentVector =
                    UshahidiExtensions.generalFilter(webIncidentVector,
                                                     (incident) -> {
                                                       return incident.getDescription()
                                                                      .contains(inputString);
                                                     });

                //Print filtered incidents
                UshahidiExtensions.printVectors(pen, filteredIncidentVector);

                pen.println("Filter completed.");

                break;
              } // Case C
            default:
              pen.println("3dein?! Shen me?! What?! ?!?!?! !!! Please try again.");
          }// switch
      }// try
    catch (Exception e)
      {
        pen.println("3dein?! Shen me?! What?! ?!?!?! !!! Please try again.");
      }// catch()
    pen.close();
  }//main()
}//UshahidiUserInterface
