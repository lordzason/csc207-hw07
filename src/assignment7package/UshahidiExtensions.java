/**
 * @author Zhi Chen, Larry
 * Consulted Zoey and Ajuna
 */

package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Vector;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;

public class UshahidiExtensions
{

  /*Consulted code from Zoey and Ajuna*/
  public UshahidiIncidentList UshahidiTestingClient(int amount)
  {
    UshahidiIncidentList incidentTestCases = new UshahidiIncidentList();

    for (int i = 0; i < amount; i++)
      {
        incidentTestCases.addIncident(UshahidiUtils.randomIncident());
      }

    return incidentTestCases;
  }

  /*
   * Prints an incident out using a format
   */
  public static void printIncident(PrintWriter pen, UshahidiIncident incident)
    throws Exception
  {
    pen.println("Incident #: " + incident.getTitle());
    pen.println("  " + incident.getDescription());
    if (incident.getLocation() != null)
      {
        pen.println("  Location: " + incident.getLocation().toString());
      }
    else 
      {
        pen.println("  Location: No location.");
      }
    pen.println("  Status: " + "(Mode:" + incident.getMode() + "," + "Active:"
                + incident.getActive() + "," + "Verified:"
                + incident.getVerified() + ")");
    pen.println();

  }//printIncident(PrintWriter, UshahidiIncident)

  /*
   * Prints out the incidents with the lowest and highest id
   * 
   * Help from Mira Hall
   */
  public static void printExtremeIncidents(PrintWriter pen,
                                           UshahidiClient client)
    throws Exception
  {

    UshahidiIncident lowestIDIncident = client.nextIncident();
    UshahidiIncident highestIDIncident = client.nextIncident();

    while (client.hasMoreIncidents())
      {
        int clientID = client.nextIncident().getId();

        if (clientID <= lowestIDIncident.getId())
          {
            lowestIDIncident = client.nextIncident();
          }//if()
        else if (clientID >= highestIDIncident.getId())
          {
            highestIDIncident = client.nextIncident();

          }//else if()

        pen.println("The lowest ID incident is:" + lowestIDIncident);
        pen.println("The highest ID incident is:" + highestIDIncident);

      }//while()

  }//printExtremeIncidents()

  public static void printVectors(PrintWriter pen, Vector<UshahidiIncident> clientVector)
    throws Exception
  {

    for(UshahidiIncident current : clientVector)
      {
        UshahidiIncident clientP = current;
        printIncident(pen, clientP);
      }//while()

  }//printExtremeIncidents()

  public static void printIncidentsWithinDates(UshahidiClient client,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate,
                                               PrintWriter pen)
    throws Exception
  {

    while (client.hasMoreIncidents())
      {

        UshahidiIncident nextClient = client.nextIncident();
        LocalDateTime clientDate = nextClient.getDate();

        if (clientDate.isAfter(startDate) && clientDate.isBefore(endDate))
          {
            pen.println("Client Date:" + clientDate);
          }//if()
      }//while()
  }//printIncidentsWithinDates(UshahidiClient, LocalDateTime, LocalDateTime, PrintWriter)

  public static Vector<UshahidiIncident>
    getIncidentsWithinDates(UshahidiClient client, LocalDateTime startDate,
                            LocalDateTime endDate)
  {
    Vector<UshahidiIncident> filteredIncidentsVector =
        new Vector<UshahidiIncident>();

    UshahidiIncident[] incidentArray = client.getIncidents();

    for (UshahidiIncident incident : incidentArray)
      {
        LocalDateTime incidentDate = incident.getDate();

        if (incidentDate.isAfter(startDate) && incidentDate.isBefore(endDate))
          {
            filteredIncidentsVector.add(incident);
          }//if()
      }//for()

    return filteredIncidentsVector;
  }//getIncidentsWithinDates(UshahidiClient, LocalDateTime, LocalDateTime)

  public static Vector<UshahidiIncident>
    getIncidentsWithinDistance(Vector<UshahidiIncident> incidentsVector,
                               double givenLatitude, double givenLongitude,
                               double distance)
  {
    Vector<UshahidiIncident> filteredVector = new Vector<UshahidiIncident>();

    /*Calculations below from: http://andrew.hedges.name/experiments/haversine/*/
    /*Calculations in kilometers*/
    double lat1 = givenLatitude;
    double lon1 = givenLongitude;

    for (UshahidiIncident incident : incidentsVector)
      {
        double lat2 = incident.getLocation().getLatitude();
        double lon2 = incident.getLocation().getLongitude();

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a =
            (Math.sin(dlat / 2) * Math.sin(dlat / 2)) + Math.cos(lat1)
                * Math.cos(lat2) * (Math.sin(dlon / 2));
        double a1 = a * a;
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
        double d = 6371 * c;//(where R is the radius of the Earth)

        if (d <= distance)
          {
            filteredVector.add(incident);
          }
      }

    return filteredVector;
  }
}//UshahidiExtensions
