package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

import edu.grinnell.glimmer.ushahidi.UshahidiCategory;
import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
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
      UshahidiExtensions.printIncident(pen, webClient.nextIncident());
      
      //Print incidents within dates
      pen.println("\n---Incidents within dates---\n");
      LocalDateTime a = LocalDateTime.of(2000, 1, 1, 12, 00);
      LocalDateTime b = LocalDateTime.of(2020, 12, 31, 12, 00);
      UshahidiExtensions.printIncidentsWithinDates(client, a, b, pen);
      UshahidiExtensions.printIncidentsWithinDates(webClient, a, b, pen);
      
      //Get incidents within dates
      pen.println("\n---Getting incidents within dates---\n");
      Vector<UshahidiIncident> result = UshahidiExtensions.getIncidentsWithinDates(webClient, a, b);
      UshahidiExtensions.printVectors(pen, result);
      
      //Get incidents within a distance
      pen.println("\n---Getting incidents within a distance---\n");
      UshahidiLocation randomLocation = UshahidiUtils.randomLocation();
      Vector<UshahidiIncident> rResult = UshahidiExtensions.getIncidentsWithinDistance(result, randomLocation.getLatitude(),randomLocation.getLongitude(), 5000);
      UshahidiExtensions.printVectors(pen, rResult);
      
      
      //UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());
    } // main(String[])
}
