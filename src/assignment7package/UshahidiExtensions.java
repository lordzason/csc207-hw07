/**
 * @author Zhi Chen, Larry

 * Consulted Zoe and Ajuna
 */

package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Vector;
import java.util.function.Predicate;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;

public class UshahidiExtensions
{

  /*Consulted code from Zoe and Ajuna
  *Create a UshidiIncidentList with random UshahidiIncidents*/
  public static UshahidiIncidentList UshahidiTestingClient(int amount)
  {
    UshahidiIncidentList incidentTestCases = new UshahidiIncidentList();

    for (int i = 0; i < amount; i++)
      {
        incidentTestCases.addIncident(UshahidiUtils.randomIncident());
      }//for()

    return incidentTestCases;
  }//UshahidiTestingClient(int)

  /*
   * Print UshahidiIncidents in vectors
   */
  public static void printVectors(PrintWriter pen,
                                  Vector<UshahidiIncident> clientVector)
    throws Exception
  {

    for (UshahidiIncident current : clientVector)
      {
        printIncident(pen, current);
      }//while()

  }//printExtremeIncidents()

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
      }//if()
    else
      {
        pen.println("  Location: No location.");
      }//else()
    pen.println("  Status: " + "(Mode:" + incident.getMode() + "," + "Active:"
                + incident.getActive() + "," + "Verified:"
                + incident.getVerified() + ")");
    pen.println();

  }//printIncident(PrintWriter, UshahidiIncident)

  /*
   * Prints out the incidents with the lowest and highest id
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

  /*
   * Print incidents within a date range
   */
  public static void printIncidentsWithinDates(UshahidiClient client,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate,
                                               PrintWriter pen)
  {

    while (client.hasMoreIncidents())
      {
        try
          {
            UshahidiIncident nextClient = client.nextIncident();
            LocalDateTime clientDate = nextClient.getDate();

            if (clientDate.isAfter(startDate) && clientDate.isBefore(endDate))
              {
                UshahidiExtensions.printIncident(pen, nextClient);
              }//if()
          }// try 
        catch (Exception e)
          {
            pen.println("Unable to print incidents within dates");
          }//catch(Exception)
      }//while()

  }//printIncidentsWithinDates(UshahidiClient,LocalDateTime,LocalDateTime,PrintWriter)

  /*
   * Get incidents within a date range
   * and returns them in a vector
   */
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
  }//getIncidentsWithinDates(UshahidiClient,LocalDateTime,LocalDateTime)

  /**
   * Get the incidents within a distance
   * from the given latitude and longitude
   * and returns them in a vector
   * @pre unitOfDistance should be either "km" or "mi"
   */
  public static Vector<UshahidiIncident>
    getIncidentsWithinDistance(Vector<UshahidiIncident> incidentsVector,
                               double givenLatitude, double givenLongitude,
                               double distance, String unitOfDistance)
  {
    /*Constants for the radius of Earth in miles and kilometers*/
    final double RADIUS_IN_MILES = 3961;
    final double RADIUS_IN_KILOMETERS = 6373;

    /*Create a vector to hold filtered Ushahidi incidents*/
    Vector<UshahidiIncident> filteredVector = new Vector<UshahidiIncident>();

    /*
     * Calculations below from: 
     * http://andrew.hedges.name/experiments/haversine/
     * Radius of the Earth in kilometers, 6371 km
    */
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
                * Math.cos(lat2) * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 1;
        if (unitOfDistance.compareTo("km") == 0)
          {
            d = RADIUS_IN_KILOMETERS * c;
          }//if()
        else if (unitOfDistance.compareTo("mi") == 0)
          {
            d = RADIUS_IN_MILES * c;
          }//else if()

        if (d <= distance)
          {
            filteredVector.add(incident);
          }//if()

      }//for()

    return filteredVector;
  }//getIncidentsWithinDistance(Vector, double, double, double)

  /*
   * A general filter to filter UshahidiIncidents
   * Consulted code from Patrick Slough, et al.
   */
  public static Vector<UshahidiIncident>
    generalFilter(Vector<UshahidiIncident> incidentsVector,
                  Predicate<? super UshahidiIncident> predicate)
  {
    Vector<UshahidiIncident> filteredIncidentsVector =
        new Vector<UshahidiIncident>();

    for (int i = 0; i < incidentsVector.size(); i++)
      {
        UshahidiIncident incident = incidentsVector.get(i);

        if (predicate.test(incident))
          {
            filteredIncidentsVector.add(incident);
          }//if()
      }//for()

    return filteredIncidentsVector;
  }//generalFilter(Vector<UshahidiIncident>, Predicate<? super UshahidiIncident>)

}//UshahidiExtensions
