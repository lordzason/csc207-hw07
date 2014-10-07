package assignment7package;

import java.io.PrintWriter;
import java.time.LocalDateTime;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;

public class Extra1
{
  
  public static void printIncidentsWithinDates(UshahidiClient client,
                                        LocalDateTime startDate,
                                        LocalDateTime endDate, PrintWriter pen)
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
  
}//Extra1
