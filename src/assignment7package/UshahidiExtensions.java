/**
 * Help from Mira Hall
 */

package assignment7package;

import java.io.PrintWriter;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;

public class UshahidiExtensions
{
  /*
   * Prints an incident out using a format
   */
  public static void printIncident(PrintWriter pen, UshahidiIncident incident)
  throws Exception
  {
    pen.println("Incident #: " + incident.getTitle());
    pen.println("  " + incident.getDescription());
    pen.println("  Location: " + incident.getLocation().toString());
    pen.println("  Status: " + "(Mode:" + incident.getMode() + "," + "Active:"
                + incident.getActive() + "," + "Verified:"
                + incident.getVerified() + ")");
    pen.println();

  }//printIncident(PrintWriter, UshahidiIncident)

  /*
   * Prints out the incidents with the lowest and highest id
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
            pen.println(lowestIDIncident);
            lowestIDIncident = client.nextIncident();
          }//if()
        else if (clientID >= highestIDIncident.getId())
          {
            pen.println(highestIDIncident);
            highestIDIncident = client.nextIncident();
          }//else if()

      }//while()

  }//printExtremeIncidents()

}//UshahidiExtensions
