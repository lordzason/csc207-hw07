package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Vector;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiIncidentList;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class PrintIncidentExperiment
{

  public static void main(String[] args) 
      throws Exception
    {
      // Create the output device
      PrintWriter pen = new PrintWriter(System.out, true);

      // A few basic incidents
      pen.println("\n---Basic Incidents---\n");
      UshahidiExtensions.printIncident(pen, UshahidiUtils.SAMPLE_INCIDENT);
      UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());
      UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());

      // A newly created incident
      pen.println("\n---Newly Created Incident---\n");
      UshahidiIncident myIncident = new UshahidiIncident(1, "Testing");
      UshahidiExtensions.printIncident(pen, myIncident);

      // One from a list
      pen.println("\n---One incident from a list---\n");
      UshahidiClient client = UshahidiUtils.SAMPLE_CLIENT;
      UshahidiExtensions.printIncident(pen, client.nextIncident());

      // One that requires connecting to the server
      pen.println("\n---One incident from a server---\n");
      UshahidiClient webClient = new UshahidiWebClient("https://farmersmarket.crowdmap.com/");
      UshahidiClient webClient2 = new UshahidiWebClient("http://ushahidi1.grinnell.edu/sandbox/");
      UshahidiExtensions.printIncident(pen, webClient.nextIncident());
      UshahidiExtensions.printIncident(pen, webClient2.nextIncident());
      
      
      //Print incidents within dates
      pen.println("\n---Incidents within dates---\n");
      LocalDateTime a = LocalDateTime.of(2000, 1, 1, 12, 00);
      LocalDateTime b = LocalDateTime.of(2020, 12, 31, 12, 00);
      UshahidiIncidentList randomIncidents = UshahidiExtensions.UshahidiTestingClient(20);
      UshahidiExtensions.printIncidentsWithinDates(randomIncidents, a, b, pen);
      UshahidiExtensions.printIncidentsWithinDates(webClient, a, b, pen);
      UshahidiExtensions.printIncidentsWithinDates(webClient2, a, b, pen);
      
      //Get incidents within dates
      pen.println("\n---Getting incidents within dates---\n");
      Vector<UshahidiIncident> result = UshahidiExtensions.getIncidentsWithinDates(webClient, a, b);
      Vector<UshahidiIncident> result2 = UshahidiExtensions.getIncidentsWithinDates(webClient2, a, b);
      UshahidiExtensions.printVectors(pen, result);
      UshahidiExtensions.printVectors(pen, result2);
      
      //Get incidents within a distance
      pen.println("\n---Getting incidents within a distance---\n");
      UshahidiLocation randomLocation = UshahidiUtils.randomLocation();
      Vector<UshahidiIncident> rResult = UshahidiExtensions.getIncidentsWithinDistance(result, randomLocation.getLatitude(),randomLocation.getLongitude(), 100000, "km");
      UshahidiExtensions.printVectors(pen, rResult);
      UshahidiLocation randomLocation1 = UshahidiUtils.randomLocation();
      Vector<UshahidiIncident> rResult1 = UshahidiExtensions.getIncidentsWithinDistance(result2, randomLocation1.getLatitude(),randomLocation.getLongitude(), 100000, "mi");
      UshahidiExtensions.printVectors(pen, rResult1);

    } // main(String[])
}
