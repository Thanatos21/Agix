/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;
import tools.Parser;

/**
 *
 * @author mael
 */
public class Agenda {
    
    private String id;
    private String dest;
    private String src;
    private String title;
    private String summary;
    private ArrayList<Evt> events;

    /**
     * Constructs an Agenda with an id, a destination address and a source address
     * @param id
     * @param dest
     * @param src
     */
    public Agenda(String id, String dest, String src) {
        this.id = id;
        this.dest = dest;
        this.src = src;
        events = new ArrayList<>();
    }
    
    /**
     * Constructs an Agenda according to the file given.
     * Make sure that you are using this method with .lix or .ics
     * @param file
     */
    public Agenda(File file) throws FileNotFoundException, IOException, ValidationException, ParserException {
        if(!file.getName().endsWith(".lix") && !file.getName().endsWith(".ics"))
        {
            System.err.println("Unsupported file.");
        }else{
            if(file.getName().endsWith(".lix")){
                //convert to ical 
                Parser.Lix2Ical(file);
            }
        }

        Agenda tmp = Parser.ical2Agenda("Lix2IcalResult");
        this.id = tmp.id;
        this.dest = tmp.dest;
        this.src = tmp.src;
        this.title = tmp.title;
        this.summary = tmp.summary;
        this.events = tmp.events;
        
    }

    /**
     * Returns the if of the agenda
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the destination address of the agenda
     * @return dest
     */
    public String getDest() {
        return dest;
    }

    /**
     * Returns the source address of the agenda
     * @return src
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the agenda list of events.
     * @param events
     */
    public void setEvents(ArrayList<Evt> events) {
        this.events = events;
    }
    
    /**
     * Sets the id of the agenda
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the destination address of the agenda
     * @param dest
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     * Sets the source address of the agenda
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    /**
     * Returns the list of the events from the agenda.
     * @return events
     */
    public ArrayList<Evt> getEvents() {
        return events;
    }

    /**
     * Sets the title of the agenda.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the summary of the agenda.
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Returns the title of the agenda.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the summary of the agenda.
     * @return summary
     */
    public String getSummary() {
        return summary;
    }
    
    
    /**
     * Adds a new Evt into the agenda.
     * @param evt
     */
    public void addEvent(Evt evt){
        this.events.add(evt);
    }
    
    /**
     * Removes the Evt given in parameter from the agenda list of events.
     * @param evt
     */
    public void removeEvt(Evt evt){
        this.events.remove(evt);
    }
    
}
