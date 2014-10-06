package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDateTime;

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
      UshahidiExtensions.printIncident(pen, UshahidiUtils.SAMPLE_INCIDENT);
      UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());
      UshahidiExtensions.printIncident(pen, UshahidiUtils.randomIncident());

      // A newly created incident
      /*int testId = 999;
      String incidentName = "IncidentName";
      LocalDateTime testDateTime = LocalDateTime.now();
      UshahidiLocation testLocation = new UshahidiLocation(100, "Test", 999, 999);
      String testDescription = "Test Description";
      UshahidiCategory testCategory = new UshahidiCategory(101, "Test Category Name");
      UshahidiIncident myIncident = new UshahidiIncident(testId, incidentName, testDateTime, testLocation, testDescription, testCategory);
      */
      //UshahidiIncident myIncident = new UshahidiIncident();
      //UshahidiExtensions.printIncident(pen, myIncident);

      // One from a list
      UshahidiClient client = UshahidiUtils.SAMPLE_CLIENT;
      UshahidiExtensions.printIncident(pen, client.nextIncident());

      // One that requires connecting to the server
      UshahidiClient webclient = new UshahidiWebClient("https://farmersmarket.crowdmap.com/");
      UshahidiExtensions.printIncident(pen, webclient.nextIncident());
    } // main(String[])
}
