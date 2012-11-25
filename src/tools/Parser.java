/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import agenda.Evt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mael
 */
public class Parser {
    public String Lix2Ical(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        Evt evenement;
        
        while ( scanner.hasNext() ) {
            String line = scanner.nextLine();
            
            // Si la ligne est vide, line.charAt(0) lance une exception...
            // Java ne connait pas '\n'
            switch ( line.charAt(0) ) {
                // Adding the source of the agenda
                case 's' :
                    break;
                    
                // Adding the destination
                case 'd' :
                    break;
                
                // A wild new event appears!
                case '*' :
                    int i = 1;
                    Date start = new Date();
                    Date end = new Date();
                    String nameEvt;
                    
                    while ( line.charAt(i) == ' ' ) {
                        i++;
                    }
                    nameEvt = new String(line.substring(i));
                    
                    
                    // Adding the new event
                    evenement = new Evt(nameEvt, start, end);
                    //System.out.println(evenement.toString());
                    
                    break;
                    
                default :
                    break;
            }
            
            //System.out.println(line);
        }
        
        System.out.println("Lix2Ical Done!");
        
        scanner.close();
        
        return "";
    }
    
    
    
    public String Ical2Lix(String icsFilePath, String lixFilePath) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(new File(icsFilePath));
        FileWriter writer = new FileWriter(new File(lixFilePath), false);
        
        while ( scanner.hasNext() ) {
            String line = scanner.nextLine();
            
            writer.write(line);
        }
        
        System.out.println("Lix2Ical Done!");
        
        scanner.close();
        writer.close();
        
        return "";
    }
    
    
    
    public static void main(String[] args) throws IOException {
        Parser instance = new Parser();
        Scanner cin = new Scanner(System.in);
        
        System.out.println("Entrez l'adresse du fichier ICS: ");
        String filePath = cin.next();
        
        try {
            String result = instance.Ical2Lix(filePath, "C:\\Users\\Julien\\Documents\\NetBeansProjects\\genieLog\\g28556.lix");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
