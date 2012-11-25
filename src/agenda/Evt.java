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
    private HashMap<String, String> match;
    
    public Evt(String id, Date start, Date end){
        this.id = id;
        this.startDate = start;
        this.endDate = end;
        match = new HashMap<>();
    }
    
    
    /**
     * This method allow you to add an entry to the match attribute
     */
    public void addMatch(String key, String value) {
        match.put(key, value);
    }
    
    /**
     * Getter for id
     * @return id
     */
    public String getId() {
        return id;
    }
    
    /**
     * Getter for startDate
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * Getter for endDate
     * @return endDate
     */
    public Date getEndDate() {
        return endDate;
    }
    
    /**
     * Getter for match
     * @return match
     */
    public HashMap<String, String> getMatch() {
        return match;
    }
    
    
    /**
     * Setter for id
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Setter for startDate
     * @param startDate 
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    /**
     * Setter for endDate
     * @param endDate 
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * Setter for match
     * @param match 
     */
    public void setMatch(HashMap<String, String> match) {
        this.match = match;
    }

    /**
     * This method returns a String a textual representation of Evt objects
     * @return 
     */
    @Override
    public String toString() {
        return "Evt{" + "id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", match=" + match + '}';
    }
}
