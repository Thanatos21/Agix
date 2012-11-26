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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author julien
 */
public class Parser {
    
    public String Lix2Ical(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String line1;
        
        while ( scanner.hasNext() ) {
            line1 = scanner.nextLine();
            
            if ( line1.matches("^source( )*=( )*.*") ) {
                String source = (line1.split("^source( )*=( )*"))[1];
                System.out.println(source);
            }
            
            if ( line1.matches("^dest( )*=( )*.*") ) {
                String dest = (line1.split("^dest( )*=( )*"))[1];
                System.out.println(dest);
            }
            
            if ( line1.matches( "^match.*" ) ) {
                String matchKey = ((line1.split("^match( )*=( )*\\^\\(")[1]).split("\\)"))[0];
                System.out.println(matchKey);
                
                String matchValue = (line1.split("( )*:( )*"))[1];
                System.out.println(matchValue);
            }
            
            if ( line1.matches("\\*") ) {
                System.out.println("lolilol");
            }
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
    
}
