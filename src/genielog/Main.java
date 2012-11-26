/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genielog;

import agenda.AgendaManager;
import agenda.Services;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.util.InputMismatchException;
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
    private static int displayMenu(){
        
        Scanner cin = new Scanner(System.in);
        
        System.out.println("################################");
        System.out.println("##            AGIX            ##");
        System.out.println("################################");
        System.out.println("##                            ##");
        System.out.println("##  1 - Configure the Proxy   ##");
        System.out.println("##  2 - Create a new Agenda   ##");
        System.out.println("##  3 - Edit an Agenda        ##");
        System.out.println("##  4 - Delete an Agenda      ##");
        System.out.println("##  ------------------------- ##");
        System.out.print("##  Action :  ");
        try{
            int action = cin.nextInt();
            System.out.println("################################");
            return action;

        }catch(InputMismatchException e){
            System.out.println("\t Error in your choice.");
        }
        
        return 0;
        
    }
    
    private static boolean executeAction(int action){
        
        boolean validAction = true;
        
        switch(action){
            case 1:
                break;
            case 2:
                break;
            case 3 :
                break;
            case 4:
                break;
            default:
                System.out.println("Please choose a valid action");
                validAction = false;
                break;
        }
        
        return validAction;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        boolean again;  
        do{
            again = !executeAction(displayMenu());
        }while(again);
        
    }
    
}
