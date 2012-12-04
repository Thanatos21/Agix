/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import agenda.Agenda;
import agenda.Evt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;

/**
 * @author julien
 */
public class Parser {
    
    /**
     * Translates a Lix file into an Ical file
     * @param file
     * @return 
     * @throws IOException
     */
    public static void Lix2Ical(File file) throws IOException, ValidationException {
        Scanner scanner = new Scanner(file);
        
        File outputFile = new File("Lix2IcalResult");
        if ( !outputFile.exists() ) {
            outputFile.createNewFile();
        }
        
        FileWriter writer = new FileWriter(outputFile);
        
        writer.write("BEGIN:VCALENDAR\n" + 
                "VERSION:2.0\n" + 
                "PRODID:-//www.celcat.fr//NONSGML CreateICSFiles//FR\n");
        
        String line;
        
        String title;
        String source;
        String dest;
        String eventId;
        String date_start, date_end;
        String matchKey;
        String matchValue;
        
        boolean firstEvent = true;
        
        while ( scanner.hasNext() ) {
            line = scanner.nextLine();
            
            // found a new title
            if ( line.matches("^\\[.*\\]") ) {
                try {
                    title = (((line.split("^\\["))[1]).split("\\]"))[0];
                    writer.write("X-WR-CALNAME:" + title + "\n");
                } catch ( ArrayIndexOutOfBoundsException e ) {
                    writer.write("X-WR-CALNAME:\n");
                }
            }
            
            // found a new source
            if ( line.matches("^source( )*=( )*.*") ) {
                source = (line.split("^source( )*=( )*"))[1];
            }
            
            // found a new destination
            if ( line.matches("^dest( )*=( )*.*") ) {
                dest = (line.split("^dest( )*=( )*"))[1];
            }
            
            // found a new event
            if ( line.matches("\\*.*") ) {
                eventId = (line.split("\\*( )*"))[1];
                if ( firstEvent ) {
                    writer.write("BEGIN:VEVENT\n");
                    firstEvent = false;
                } else {
                    writer.write("END:VEVENT\n");
                    writer.write("BEGIN:VEVENT\n");
                }
            }
            
            // found a new match
            if ( line.matches( "^match( )*=( )*\\^\\(.*" ) ) {
                try {
                    matchKey = ((line.split("^match( )*=( )*\\^\\(")[1]).split("\\)"))[0];
                    try {
                        matchValue = ((line).split("^match( )*=( )*\\^\\(.*\\)( )*:( )*"))[1];
                        writer.write(matchKey + ":" + matchValue + "\n");
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                        writer.write(matchKey + ":\n");
                    }
                } catch ( ArrayIndexOutOfBoundsException e ) {
                    System.err.println("There is no matchKey for this match");
                }
            }
            
            // found a new date_start
            if ( line.matches("^date_start( )*=( )*.*") ) {
                try {
                    date_start = (line.split("^date_start( )*=( )*"))[1];
                    writer.write("DTSTART:" + date_start + "\n");
                } catch ( ArrayIndexOutOfBoundsException e ) {
                    writer.write("DTSTART:\n");
                }
            }
            
            // found a new date_end
            if ( line.matches("^date_end( )*=( )*.*") ) {
                try {
                    date_end = (line.split("^date_end( )*=( )*"))[1];
                    writer.write("DTEND:" + date_end + "\n");
                } catch ( ArrayIndexOutOfBoundsException e ) {
                    writer.write("DTEND:\n");
                }
            }
        }
        
        writer.write("END:VEVENT\n");
        writer.write("END:VCALENDAR\n");
        
        System.out.println("Lix2Ical Done!");
        
        scanner.close();
        writer.flush();
        writer.close();
    }
    
    
    
    /**
     * Translates an Ical file into a Lix file
     * @param icsFilePath The relative or absolute adress of the Ical file to read
     * @param lixFilePath The relative or absolute adress of the Lix file to write
     * @return
     * @throws IOException
     */
    public static void Ical2Lix(String icsFilePath, String lixFilePath) throws FileNotFoundException, IOException, ParserException {
        FileInputStream inputFile = new FileInputStream(icsFilePath);
        
        CalendarBuilder builder = new CalendarBuilder();
        
        File outputFile = new File(lixFilePath);
        if ( !outputFile.exists() ) {
            outputFile.createNewFile();
        }
        
        FileWriter writer = new FileWriter(outputFile);
        // We have to add the source and the dest BEFORE the first while
        
        ComponentList eventsList;
        PropertyList propertiesList;
        Iterator itEvents, itProperties;
        
        Calendar cal = builder.build(inputFile);
        eventsList = cal.getComponents();
        itEvents = eventsList.iterator();
        
        writer.write("[" + (cal.getProperty("X-WR-CALNAME")).getValue() + "]\n");
        
        while (itEvents.hasNext()) {
            Component event = (Component) itEvents.next();
            propertiesList = event.getProperties();
            itProperties = propertiesList.iterator();
            writer.write("\n* " + event.getProperty("UID").toString());
            
            while ( itProperties.hasNext() ) {
                Property p = (Property) itProperties.next();
                switch (p.getName()) {
                    case "DTSTART":
                        writer.write("date_start = " + p.getValue() + "\n");
                        break;
                        
                    case "DTEND":
                        writer.write("date_end = " + p.getValue() + "\n");
                        break;
                    
                    default:
                        writer.write("match = ^(" + p.getName() + "): " + (p.getValue().replaceAll("\n", "\\\\" + "n")).replaceAll("\\,", "\\\\,") + "\n");
                        break;
                }
            }
        }
        
        writer.flush();
        writer.close();
        
        System.out.println("Ical2Lix Done!");
    }
    
    
    /**
     * This method translate an ical file into an Agenda
     * @param icsFilePath The relative or absolute adress of the Ical file to read
     * @return The Agenda corresponding to the ical file
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParserException 
     */
    public static Agenda ical2Agenda(String icsFilePath) throws FileNotFoundException, IOException, ParserException {
        Agenda agenda = new Agenda(null, null, null);
        ArrayList<Evt> eventList = new ArrayList<>();
        
        FileInputStream inputFile = new FileInputStream(icsFilePath);
        
        CalendarBuilder builder = new CalendarBuilder();
        
        ComponentList eventsList;
        PropertyList propertiesList;
        Iterator itEvents, itProperties;
        
        Calendar cal = builder.build(inputFile);
        eventsList = cal.getComponents();
        itEvents = eventsList.iterator();
        
        agenda.setTitle(cal.getProperty("X-WR-CALNAME").getValue());
        
        while ( itEvents.hasNext() ) {
            Component event = (Component) itEvents.next();
            
            if ( event.getName().equals("VEVENT") ) {
                propertiesList = event.getProperties();
                itProperties = propertiesList.iterator();
//                Evt currentEvt = new Evt(event.getProperty("UID").getValue(),
//                                 new Date(event.getProperty("DTSTART").getValue()),
//                                 new Date(event.getProperty("DTEND").getValue())
//                        );
                Evt currentEvt = new Evt(event.getProperty("UID").getValue(), null, null);

                while ( itProperties.hasNext() ) {
                    Property p = (Property) itProperties.next();
                    switch (p.getName()) {
                        case "DTSTART" :
                            currentEvt.setStartDate(p.getValue());
                            break;
                            
                        case "DTEND" :
                            currentEvt.setEndDate(p.getValue());
                            break;
                            
                        default :
                            currentEvt.addMatch(p.getName(), ((p.getValue()).replaceAll("\n", "\\\\" + "n")).replaceAll("\\,", "\\\\" + ","));
                            break;
                    }
                }
                
                agenda.addEvent(currentEvt);
            }
        }
        
        System.out.println("ical2Agenda Done!");
        
        return agenda;
    }
    
}
