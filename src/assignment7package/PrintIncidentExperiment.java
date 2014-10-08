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


/******* Design reflections *****/
Design Reflections
Boateng Asante, Larry & Chen, Zhi

/** Section 1 **/
Both BulkItem and BulkContainer are classes. They are designed differently from each other in that BulkItem implements the Item interface whereas BulkContainer is a subclass of BulkItem. This means that BulkContainer inherits all of the properties and methods (i.e excluding all properties and methods marked as "private") while BulkItem implements all the methods in the Item interface. Package and ManyPackage are both classes which implement the Item interface. In that, the two classes both have all the method bodies in the Item interface. However, both classes have a different implementation of equals(Object) differently.

The advantage in inheriting from a class is that the client doesn't have to rewrite code for similar methods which are implemented in the parent class. This contributes to the brevity of code. But this comes at a cost. Inheriting from a class tightly couples both the parent class and the subclass thus making it very difficult to use the classes individually. Also, this makes maintenance of code somewhat difficult since the classes are tightly coupled. In that, if the user wants to update existing code in one class, it automatically affects the method's utilization in the subclass. Assuming the client wants to use this update specifically on the parent class, it makes it impossible to do so. In using an interface, both classes could work independently as well as have the polymorphic method bodies from the interface they implement. This also has it's downside since the client will have to add all the unimplemented methods from the interface. This can make the code somewhat long and confusing.

We taught it wise to use inheritance when we needed the fields and properties of the a similarly implemented class whose function is similar to that of our immediate class while we will want an interface when we want to have independent classes which have share similar methods but have custom implementations tailored to meet each class's demand.

/** Section 2 **/

Using getName will only return the name assigned to the item and if there are additional names or aliases for the item, it probably will not return the desired value. equals(String) only checks if the given string occupies the same space in memory as the compared. Using hasName(String) allows the client to write more checks for the names.

/** Section 3 **/
It does make sense to have them in separate classes since each of the class's functionality is independent of the other. We could have grouped all the similar methods of both classes in the Item interface and use a custom implementation per each class's demands. We could have also made one a subclass of the other however this contradicts the use of each class as well as make the two tightly coupled hence loosing it's individual signature. In terms of writing more maintainable code, using individual classes which implement an interface that contains all the necessary method bodies makes more sense.

/** Section 4 **/
(1)There is no interface in the other design. It strictly uses inheritance hence making all subclasses a type of Item. Thus all subclasses possess the fields and methods of Item.
(2) Our design does not include access modifiers for our fields thus protection level of these fields is pretty low since they can be access by all methods in the class as well as other classes in the package. The other design, however, instills some protection thus restricting access to these fields.
(3) The other design uses "double" to represent prices while our design uses an "int". Using ints prevent the rounding off error since doubles round of their values after some level of precision. However, using BigDecimal seems to be the best choice since BigDecimal allows an arbitrary level of precision and also allows the client to control the level of precision.
(4) All the classes in the other design are not packaged and this doesn't separate a group of classes from what they do. In our design, we do well to group our classes based on what they represent or which categories they fall under.
(5) The equal method in the other design just check for matching names while our design checks for matching type and all fields. Our design goes extensively to ensure true equality of items other than the mere equality of names. 

