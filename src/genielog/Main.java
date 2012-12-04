/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genielog;

import agenda.Agenda;
import agenda.AgendaManager;
import agenda.Services;
import com.google.gdata.util.ServiceException;
import gui.Mainwindow;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ValidationException;

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
     * Returns the service selected by the user.
     * @return service  
     */
    private static int askAgenda(){
        
        Scanner cin = new Scanner(System.in);
        Console cons = System.console();
        
        System.out.println("################################");
        System.out.println("##      Choose an Agenda      ##");
        System.out.println("################################");
        System.out.print  ("##  Agenda : ");
        int agenda = cin.nextInt();
        System.out.println("##  ------------------------- ##");

       return agenda;
    }
    
    /**
     * Edits the Agenda choosen by the user.
     * @param int askAgenda that represent the Agenda to edit.
     * @return boolean validAgenda
     */
    private static boolean executeEditAgenda(int askAgenda, ArrayList<String> agendas) throws FileNotFoundException, IOException, ValidationException, ParserException, ServiceException {
        boolean validAgenda = true;
        /* the user have choosed the service and the agenda to modify*/
        
        //verification that the choice is valid.
        if(askAgenda < agendas.size() || askAgenda > agendas.size()){
            System.err.println("Please select a valid Agenda...");
            validAgenda = false;
        }else{
            //we have to delete the older agenda from the service to recreate it.
            Agenda toEdit = new Agenda(new File(agendas.get(askAgenda-1)+".lix"));
            System.out.println(toEdit.getTitle());
            AgendaManager.getInstance().removeAgenda(toEdit, service);
            AgendaManager.getInstance().addAgenda(toEdit, service);
        }
        
        
        
        return false;
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
        boolean validAgenda = false;
        Scanner cin = new Scanner(System.in);
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
                do{
                    validService = executeService(askService());
                }while(!validService);
                try {
                    if(service != Services.LOCALHOST) askAuthentication();
                    int i = 1;
                    ArrayList<String> agendas = AgendaManager.getInstance().getAgendasList(service);
                    for(String agenda : agendas){
                        System.out.println("##  " + i + " - " + agenda);
                        i++;
                    }
                    do{
                        try {
                            validAgenda = executeEditAgenda(askAgenda(),agendas);
                        } catch (FileNotFoundException | ValidationException |
                                ParserException | ServiceException ex) {
                            System.err.println("Error when trying to edit the selected Agenda.");
                        } 
                    }while(!validAgenda);
                } catch (IOException | ServiceException ex) {
                    System.err.println("Error in authentication process");
                }
                break;
            case 4:
                // Deleting an agenda
                do{
                    validService = executeService(askService());
                }while(!validService);
                try {
                    if(service != Services.LOCALHOST) askAuthentication();
                    int i = 0;
                    ArrayList<String> agendas = AgendaManager.getInstance().getAgendasList(service);
                    for(String agenda : agendas){
                        i++;
                        System.out.println("##  " + i + " - " + agenda + "##");
                    }
                    System.out.println("##  Which agenda do you want to delete? ##");
                    int choix = cin.nextInt();
                    while ( (choix < 1) || (choix > i) ) {
                        System.out.println("##  Wrong choice, try again : ##");
                        choix = cin.nextInt();
                    }
                    choix--;
                    AgendaManager.getInstance().removeAgenda(agendas.get(choix), service);
                } catch (IOException | ServiceException ex) {
                    System.err.println("Error in authentication process");
                }
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
          
        if(args.length > 0){
            
            if(args[0].compareTo("gui") == 0){
                Mainwindow guiApp = new Mainwindow();
                guiApp.setVisible(true);
            }
            else{
                System.out.println("You have only two options to launch this application : ");
                System.out.println("\"gui\" to launch with a graphical interface");
                System.out.println("Or without argument to launch in a consol mode.");
            }
        }else{
        
            boolean stop = false;  
            do{
                try {
                    stop = executeAction(displayMenu());
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(!stop);
        }
        
    }

}
