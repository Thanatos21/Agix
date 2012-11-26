/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.googleTools;

import agenda.Agenda;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * This class Provides methode to manage Google calendar.
 * @author mael
 */
public class Tools {
    
    private static String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";
    private static String ALLCALENDARS_FEED_URL_SUFFIX = "/allcalendars/full";
    private static String OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";  
    private static URL metafeedUrl = null;
    private static URL allcalendarsFeedUrl = null;
    private static URL owncalendarsFeedUrl = null;  
    private static CalendarService calServ = null;
    
   /**
    * Prints the titles of calendars in owncalendarsFeedUrl.
    * 
    * @throws IOException If there is a problem communicating with the server.
    * @throws ServiceException If the service is unable to handle the request.
    */
  public static void printUserCalendars()
      throws IOException, ServiceException {
      

        connect();
      
        // Send the request and receive the response:
        CalendarFeed resultFeed = calServ.getFeed(owncalendarsFeedUrl, CalendarFeed.class);

        // Print the title of each calendar
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
          CalendarEntry entry = resultFeed.getEntries().get(i);
          System.out.println("\t" + entry.getTitle().getPlainText());
           
        }
        
        disconnect();
  }
  
 /**
   * Creates a new calendar using the owncalendars feed.
   * @param agenda 
   * @throws IOException If there is a problem communicating with the server.
   * @throws ServiceException If the service is unable to handle the request.
   */
  public static void createCalendar(Agenda agenda) 
        throws IOException, ServiceException {
      
    connect();
      
    System.out.println("Creating " + agenda.getTitle() + " calendar into the current account");

    // Create the calendar
    CalendarEntry calendar = new CalendarEntry();
    calendar.setTitle(new PlainTextConstruct(agenda.getTitle()));
    calendar.setSummary(new PlainTextConstruct(agenda.getSummary()));

    // Insert the calendar
    calServ.insert(owncalendarsFeedUrl, calendar);
    
    disconnect();
  }
   
   /**
    * Deletes the given Agenda.
    * 
    * @param agenda 
    * @throws IOException If there is a problem communicating with the server.
    * @throws ServiceException If the service is unable to handle the request.
    */
    public static void removeCalendar(Agenda agenda) throws IOException, ServiceException {
      
        connect();
        
        System.out.println("Deleting " + agenda.getTitle() + " from the current account");
            
        CalendarFeed resultFeed = calServ.getFeed(owncalendarsFeedUrl, CalendarFeed.class);
        List<CalendarEntry> entries = resultFeed.getEntries();
        
        int i = 0;
        boolean ok = false;
        CalendarEntry entry = null;
        while(!ok && i < entries.size()){
            
            entry = entries.get(i);
            if(entry.getTitle().toString().equals(agenda.toString())){
                ok = true;
            }
            i++;
        }
        
        if(ok)
            entry.delete();
        
        disconnect();    
    }
  
  /**
   * Ask information to authentified the user to his account and initialize 
   * the owncalendarsFeedUrl.
   * @throws IOException
   * @throws ServiceException 
   */
  private static void connect() throws IOException, ServiceException{
        Scanner cin = new Scanner(System.in);

//        System.out.print("login : ");
        String login = "maelbar44@gmail.com";//cin.next();

        System.out.print("password : ");
        String passwd = cin.next();
        
        // Create necessary URL objects
        try {
          owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + login + 
              OWNCALENDARS_FEED_URL_SUFFIX);
          
        } catch (MalformedURLException e) {
            // Bad URL
            System.err.println("Uh oh - you've got an invalid URL.");
            e.printStackTrace();
        }

        // Create CalendarService and authenticate using ClientLogin
        calServ = new CalendarService("kissFairyCorporation-agix-1.0");

        try {
          calServ.setUserCredentials(login, passwd);
        } catch (AuthenticationException e) {
          // Invalid credentials
          e.printStackTrace();
        }
        
      }
  
  /**
   * Disconnect the user from the current google account.
   */
    private static void disconnect(){
        calServ = null;
        metafeedUrl = null;
        allcalendarsFeedUrl = null;
        owncalendarsFeedUrl = null;
    }
  
}
