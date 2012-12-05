/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.googleTools;

import agenda.Agenda;
import agenda.AgendaManager;
import agenda.Evt;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.data.ParseSource;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.TimeZoneProperty;
import com.google.gdata.data.extensions.EventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;

/**
 * This class Provides methode to manage Google calendar.
 * @author mael
 */
public class Tools {
    
    private static String login = null;
    private static String passwd = null;
    
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
  public static ArrayList<String> printUserCalendars()
      throws IOException, ServiceException {
      
        connect();
      
        // Send the request and receive the response:
        CalendarFeed resultFeed = calServ.getFeed(owncalendarsFeedUrl, CalendarFeed.class);

        ArrayList<String> agendas = new ArrayList<>();
        
        // Print the title of each calendar
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
          CalendarEntry entry = resultFeed.getEntries().get(i);
          agendas.add(entry.getTitle().getPlainText());  
          
          System.out.println(entry.getId());
          
          
        }
               
        disconnect();
        
        return agendas;

  }
  
 /**
   * Creates a new calendar using the owncalendars feed.
   * @param agenda 
   * @throws IOException If there is a problem communicating with the server.
   * @throws ServiceException If the service is unable to handle the request.
   */
  public static void createCalendar(Agenda agenda) 
        throws IOException, ServiceException, ParseException {
      
    connect();
      
    System.out.println("Creating " + agenda.getTitle() + " calendar into the current account");

    // Create the calendar
    CalendarEntry calendar = new CalendarEntry();
    calendar.setTitle(new PlainTextConstruct(agenda.getTitle()));
    calendar.setSummary(new PlainTextConstruct(agenda.getSummary()));
    calendar.setTimeZone(new TimeZoneProperty("Europe/Paris"));
    
    // Insert the calendar
    calServ.insert(owncalendarsFeedUrl, calendar);
    
//    retrieve the PostUrl for this agenda.
        CalendarFeed resultFeed = calServ.getFeed(owncalendarsFeedUrl, CalendarFeed.class);
        String postUrlString = null;

        for(CalendarEntry cal : resultFeed.getEntries()){
            if(cal.getTitle().getPlainText().equals(agenda.getTitle())){
                postUrlString = cal.getLink("alternate", "application/atom+xml").getHref();
            }
        }
        
    //Add all events to the calendar.
    try{
        for(Evt evt : agenda.getEvents()){

            EventEntry event = new EventEntry();

            /* Time */
            When time = new When();

            SimpleDateFormat simpledate = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            Date s = simpledate.parse(evt.getStartDate());     
            Date e = simpledate.parse(evt.getEndDate());

            time.setStartTime(new DateTime(s));
            time.setEndTime(new DateTime(e));
            event.addTime(time);

            /* Title */
            event.setTitle(TextConstruct.plainText(evt.getMatch().get("SUMMARY")));

            /* Description */
            event.setSummary(TextConstruct.plainText(evt.getMatch().get("DESCRIPTION")));

            /* Place */
            Where where = new Where();
            where.setValueString(evt.getMatch().get("LOCATION"));
            event.addLocation(where);

            System.out.println(evt.getId());

            calServ.insert(new URL(postUrlString), event);

        }
    }catch(NullPointerException e){
        System.err.println("No Event into the Agenda...");
    }
  
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
            if(entry.getTitle().getPlainText().equals(agenda.getTitle())){
                ok = true;
            }
            i++;
        }
        
        if(ok)
            entry.delete();
        
        disconnect();    
    }
    
       /**
    * Deletes the given Agenda.
    * 
    * @param agenda 
    * @throws IOException If there is a problem communicating with the server.
    * @throws ServiceException If the service is unable to handle the request.
    */
    public static void removeCalendar(String agenda) throws IOException, ServiceException {
      
        connect();
        
        System.out.println("Deleting " + agenda + " from the current account");
            
        CalendarFeed resultFeed = calServ.getFeed(owncalendarsFeedUrl, CalendarFeed.class);
        List<CalendarEntry> entries = resultFeed.getEntries();
        
        int i = 0;
        boolean ok = false;
        CalendarEntry entry = null;
        while(!ok && i < entries.size()){
            
            entry = entries.get(i);
            System.out.println("Entry : " + entry.getTitle().getPlainText());
            if(entry.getTitle().getPlainText().equals(agenda)){
                System.out.println("--->"+entry.getTitle().toString());
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
        
      login = AgendaManager.getInstance().getLogin();
      passwd = AgendaManager.getInstance().getPassword();

      
        // Create necessary URL objects
        try {
          owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + login + 
              OWNCALENDARS_FEED_URL_SUFFIX);
          
        } catch (MalformedURLException e) {
            System.err.println("Uh oh - you've got an invalid URL.");
        }

        // Create CalendarService and authenticate using ClientLogin
        calServ = new CalendarService("kissFairyCorporation-agix-1.0");

        try {

          calServ.setUserCredentials(login, passwd);
        } catch (AuthenticationException e) {}
        
      }
  
  /**
   * Disconnect the user from the current google account.
   */
    private static void disconnect(){
        login = null;
        passwd = null;
        
        calServ = null;
        metafeedUrl = null;
        allcalendarsFeedUrl = null;
        owncalendarsFeedUrl = null;
    }
  
    public static void main(String[] args) throws IOException, ServiceException, FileNotFoundException, ValidationException, ParserException, ParseException{
        Tools.connect();
        
        Agenda Agix = new Agenda(new File("Agix.lix"));
        
        Tools.createCalendar(Agix);
        
        Tools.disconnect();
    }
    
}
