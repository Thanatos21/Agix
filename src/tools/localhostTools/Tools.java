/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.localhostTools;

import agenda.Agenda;
import agenda.Evt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provids methods to convert an Agenda into a Lix format.
 * @author mael
 */
public class Tools {
    
    public static void createCalendar(Agenda agenda) throws IOException{
        File lix = new File(agenda.getTitle() + ".lix");
        FileWriter writer = new FileWriter(lix,true);
        
        writer.write("dest = " + agenda.getDest() + "\n");
        writer.write("source = " + agenda.getSrc() + "\n");
        
        try{
            for(Evt event : agenda.getEvents()){
                writer.write("* " + agenda.getTitle() + "\n");
                writer.write("match = ");
                for(Entry<String,String> entry : event.getMatch().entrySet()){
                    writer.write("^("+entry.getKey()+"):"+entry.getValue() + "\n");
                }
                
                writer.write("date_start = " + event.getStartDate().toString() + "\n");
                writer.write("date_end = " + event.getEndDate().toString() + "\n");
            }
        }catch(NullPointerException e){
            System.out.println("No Event into the Agenda...");
        }
        
        writer.close();
        
        
        
    }

    public static void removeCalendar(Agenda agenda) {
        File lix = new File(agenda.getTitle() + ".lix");
        lix.delete();
    }
    
    
    
}
