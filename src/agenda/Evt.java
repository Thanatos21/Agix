/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agenda;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author mael
 */
public class Evt {
    private String id;
    private Date startDate;
    private Date endDate;
    private HashMap<String, String> matches;
    
    public Evt(String id, Date start, Date end){
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        matches = new HashMap<>();
    }
    
    
 /**
     * Return the id of the event
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Return the starting date of the event
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Return the ending date of the event
     * @return endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Return the HashMap wich contains all the matches of the event
     * @return matches
     */
    public HashMap<String, String> getMatch() {
        return matches;
    }

    /**
     * Set the id of the Event
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *Set the starting date of the event
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Set the ending date of the event
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Set the HashMap matches
     * @param matches
     */
    public void setMatches(HashMap<String, String> matches) {
        this.matches = matches;
    }
    
    /**
     * Add a new entry in the event matches
     * @param key
     * @param value
     */
    public void addMatch(String key, String value){
        this.matches.put(key, value);
    }
    
    
    /**
     * Remove a match from the event matches
     * @param key
     */
    public void removeMatch(String key){
        this.matches.remove(key);
    }

    /**
     * This method returns a String a textual representation of Evt objects
     * @return 
     */
    @Override
    public String toString() {
        return "Evt{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", match=" + matches + '}';
    }
}
