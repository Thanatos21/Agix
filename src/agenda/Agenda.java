/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.util.ArrayList;

/**
 *
 * @author mael
 */
public class Agenda {
    private String id;
    private String dest;
    private String src;
    private ArrayList<Evt> events;

    /**
     * Construct an Agenda with an id, a destination address and a source address
     * @param id
     * @param dest
     * @param src
     */
    public Agenda(String id, String dest, String src) {
        this.id = id;
        this.dest = dest;
        this.src = src;
    }

    /**
     * Return the if of the agenda
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the destination address of the agenda
     * @return dest
     */
    public String getDest() {
        return dest;
    }

    /**
     * Return the source address of the agenda
     * @return src
     */
    public String getSrc() {
        return src;
    }

    /**
     * Set the agenda list of events.
     * @param events
     */
    public void setEvents(ArrayList<Evt> events) {
        this.events = events;
    }
    
    /**
     * Set the id of the agenda
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the destination address of the agenda
     * @param dest
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     * Set the source address of the agenda
     * @param src
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    /**
     * The list of the events from the agenda.
     * @return events
     */
    public ArrayList<Evt> getEvents() {
        return events;
    }

    /**
     * Add a new Evt into the agenda.
     * @param evt
     */
    public void addEvent(Evt evt){
        this.events.add(evt);
    }
    
    /**
     * Remove the Evt given in parameter from the agenda list of events.
     * @param evt
     */
    public void removeEvt(Evt evt){
        this.events.remove(evt);
    }
    
}
