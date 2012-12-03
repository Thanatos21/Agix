/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genielog;

import agenda.AgendaManager;
import agenda.Services;
import com.google.gdata.util.ServiceException;
import java.io.Console;
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
    
    private static Services service = null;

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
        System.out.println("##  5 - Display agendas list  ##");
        System.out.println("##  0 -                 Quit  ##");
        System.out.println("##  ------------------------- ##");
        System.out.print("##  Action :  ");
        try{
            int action = cin.nextInt();
            System.out.println("################################");
            return action;
        }catch(InputMismatchException e){}
        
        return -1;  
    }
    
    /**
     * Returns the service selected by the user.
     * @return service  
     */
    private static int askService(){
        
        Scanner cin = new Scanner(System.in);
        
        System.out.println("################################");
        System.out.println("##      Service selection     ##");
        System.out.println("################################");
        System.out.println("##                            ##");
        System.out.println("##  1 - Localhost             ##");
        System.out.println("##  2 - Google Agenda         ##");
        System.out.println("##  ------------------------- ##");
        System.out.print("##  service :  ");
        try{
            int serv = cin.nextInt();
            System.out.println("################################");
            return serv;

        }catch(InputMismatchException e){}
        
        return -1;
    }
    
    /**
     * Returns the service selected by the user.
     * @return service  
     */
    private static void askAuthentication(){
        
        Scanner cin = new Scanner(System.in);
        Console cons = System.console();
        
        System.out.println("################################");
        System.out.println("##  Authentication to " + service.toString() + " ##");
        System.out.println("################################");
        System.out.print  ("##  Login : ");
        String login = cin.next();
        System.out.print  ("##  Password : ");
        String passwd = cin.next();
        System.out.println("##  ------------------------- ##");

       AgendaManager.getInstance().setLogin(login);
       AgendaManager.getInstance().setPassword(passwd);
    }
    
    /**
     * Selects the service choosen by the user.
     * @param service
     * @return 
     */
    private static boolean executeService(int serv){
        boolean validService = true;
        
        switch(serv){
            case 1:
                service = Services.LOCALHOST;
                break;
            case 2:
                service = Services.GOOGLE;
                break;
            default:
                System.err.println("\t Please choose a valid service");
                validService = false;
                break;
        }
        return validService;
    }
    
    /**
     * Executes the action choosen by the user.
     * @param action
     * @return 
     */
    private static boolean executeAction(int action) throws IOException{
        
        boolean stop = false;
        boolean validService;
        switch(action){
            case 0:
                System.out.println("\tThis program has been generated by Kiss Fairy Corporation.");
                stop = true;
                break;
            case 1:
                tools.NetworkTools.setUpProxy(null, null, null, null);
                break;
            case 2:
                System.err.println("\tNot yet implemented...");
                break;
            case 3 :
                System.err.println("\tNot yet implemented...");
                break;
            case 4:
                System.err.println("\tNot yet implemented...");
                break;
            case 5:
                do{
                    validService = executeService(askService());
                }while(!validService);
                try {
                    if(service != Services.LOCALHOST) askAuthentication();
                    for(String agenda : AgendaManager.getInstance().getAgendasList(service)){
                        System.out.println("##  * " + agenda);
                    }
                } catch (IOException | ServiceException ex) {
                    System.err.println("Error in authentication process");
                }
                break;
            default:
                System.err.println("\tPlease choose a valid action");
                break;
        }
        
        return stop;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        System.out.println("Try scanner password");
        Scanner sc = new Scanner(System.in);
        
        
        
        
//        boolean stop = false;  
//        do{
//            try {
//                stop = executeAction(displayMenu());
//            } catch (IOException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }while(!stop);
        
    }
    
}
