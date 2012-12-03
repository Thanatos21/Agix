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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.Validator;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

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
    public String Lix2Ical(File file) throws IOException, ValidationException {
        Scanner scanner = new Scanner(file);
        
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("Agix"));
        calendar.getProperties().add(Version.VERSION_2_0);
        
        VEvent comp = new VEvent(new DateTime(), "");
//        UidGenerator ui = new UidGenerator("1");
//        comp.getProperties().add(ui.generateUid());
        calendar.getComponents().add(comp);
        
        String line;
        
        String title;
        String source;
        String dest;
        String eventId;
        String matchKey;
        String matchValue;
        
        while ( scanner.hasNext() ) {
            line = scanner.nextLine();
            
            // found a new title
            if ( line.matches("^\\[.*\\]") ) {
                title = (((line.split("^\\["))[1]).split("\\]"))[0];
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
            }
            
            // found a new match
            if ( line.matches( "^match( )*=( )*\\^\\(.*" ) ) {
                matchKey = ((line.split("^match( )*=( )*\\^\\(")[1]).split("\\)"))[0];
                matchValue = (line.split("( )*:( )*"))[1];
                
            }
            
            // found a new date_start
            if ( line.matches("^date_start( )*=( )*.*") ) {
            }
            
            // found a new date_end
            if ( line.matches("^date_end( )*=( )*.*") ) {
            }
        }
        
        System.out.println("Lix2Ical Done!");
        
        scanner.close();
        
//        FileOutputStream outputFile = new FileOutputStream("Lix2IcalResult");
//        CalendarOutputter outputter = new CalendarOutputter();
//        outputter.output(calendar, outputFile);
        
        return "";
    }
    
    
    
    /**
     * Translates an Ical file into a Lix file
     * @param icsFilePath The relative or absolute adress of the Ical file to read
     * @param lixFilePath The relative or absolute adress of the Lix file to write
     * @return
     * @throws IOException
     */
    public String Ical2Lix(String icsFilePath, String lixFilePath) throws FileNotFoundException, IOException, ParserException {
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
        
        return "";
    }
    
}
