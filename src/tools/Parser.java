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
 * @author julien
 */
public class Parser {
    public String Lix2Ical(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        Evt evenement;
        String line;
        
        while ( scanner.hasNext() ) {
            line = scanner.nextLine();
            
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
                    
                    evenement = new Evt(line, start, end);
                    
                    while ( ( scanner.hasNext() ) && ( line.charAt(1) != '*' ) ) {
                        line = scanner.nextLine();
                        
                        switch ( line.charAt(0) ) {
                            // Adding a "match"
                            case 'm' :
                                int matchKeyStart = 1;
                                int matchKeyEnd;
                                String matchKey;
                                
                                int matchValueStart;
                                String matchValue;
                                
                                while ( line.charAt(matchKeyStart) != '^' ) {
                                    matchKeyStart++;
                                }
                                matchKeyStart+=2;
                                
                                matchKeyEnd = matchKeyStart;
                                while ( line.charAt(matchKeyEnd) != ')' ) {
                                    matchKeyEnd++;
                                }
                                
                                matchKey = line.substring(matchKeyStart,matchKeyEnd);
                                
                                matchValueStart = matchKeyEnd + 1;
                                while ( line.charAt(matchValueStart) == ':' || line.charAt(matchValueStart) == ' ' ) {
                                    matchValueStart++;
                                }
                                
                                matchValue = line.substring(matchValueStart);
                                
                                evenement.addMatch(matchKey, matchValue);
                                System.out.println(evenement.toString());
                                
                                break;
                            
                            // Adding a date (start or end)
                            case 'd' :
                                break;
                            
                            // Default case, we do nothing
                            default :
                                break;
                        }
                    }
                    
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
        
//        System.out.println("Entrez l'adresse du fichier ICS: ");
//        String filePath = cin.next();
        String filePath = "C:\\Users\\Julien\\Documents\\NetBeansProjects\\genieLog\\test.lix";
        
        try {
            String result = instance.Lix2Ical(filePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
