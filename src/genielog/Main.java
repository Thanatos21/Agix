/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genielog;

import agenda.AgendaManager;
import agenda.Services;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mael
 */
public class Main {

    /**
     * Display the main menu of the application.
     */
    private static void displayMenu(){
        
        Scanner cin = new Scanner(System.in);
        
        System.out.println("################################");
        System.out.println("##            AGIX            ##");
        System.out.println("################################");
        System.out.println("##                            ##");
        System.out.println("##  1 - Create a new Agenda   ##");
        System.out.println("##  2 - Edit an Agenda        ##");
        System.out.println("##  3 - Delete an Agenda      ##");
        System.out.println("##  ------------------------- ##");
        System.out.print("##  Action :  ");
        int action = cin.nextInt();
        System.out.println("################################");


    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
            AgendaManager manager = AgendaManager.getInstance();
            displayMenu();
    }
}
