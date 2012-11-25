package agenda;

import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import tools.NetworkTools;

/**
 * This class provides some methods to manage agendas.
 * @author mael
 */
public class AgendaManager {
    
    private static AgendaManager instance; 
    private ArrayList<Agenda> agendas;

    private AgendaManager(){
         
    }
    
    /**
     * Creates an instance of agendaManager if does not exist and return it.
     * @return instance
     */
    public static synchronized AgendaManager getInstance(){
        if(instance == null){
            instance = new AgendaManager();
        }
        return instance;   
    }
    
    /**
     * Sets the agendas list for the agendaManager
     * @param agendas
     */
    public void setAgendas(ArrayList<Agenda> agendas) {
        this.agendas = agendas;
    }

    /**
     * Returns the agendas list of the AgendaManager
     * @return agendas
     */
    public ArrayList<Agenda> getAgendas() {
        return agendas;
    }
    
    /**
     * Configuration of the proxy if need.
     */
    public void configProxy(){
        
        Scanner cin = new Scanner(System.in);
        
        // Ask new Proxy Information
        System.out.print("Proxy host ? ");
        String host = cin.next();
        
        System.out.print("Proxy port ?");
        String port = cin.next();
        
//        System.out.print("User name ?");
//        String user = cin.next();
//        String user = "";
        
//        System.out.print("Password ?");
//        String psswd = cin.next();
//        String psswd = "";
        
        NetworkTools.setUpProxy(host, port, null, null);
    }
    
    /**
     * Downloads and saves an agenda from the URL asked by the method.
     */
    public void getAgenda(){
        System.out.print("Download agenda from : ");
        Scanner cin = new Scanner(System.in);
        String url = cin.next();
        NetworkTools.downloadFrom(url);
    }
    
    /**
     * Adds the agenda given in parameter into the agendas list of the 
     * AgendaManager.
     * @param agenda
     * @param service
     * @throws IOException 
     * @throws ServiceException  
     */
    public void addAgenda(Agenda agenda, Services service) throws IOException, ServiceException{
        
        this.agendas.add(agenda);
        
        switch(service){
            case GOOGLE:
                tools.googleTools.Tools.createCalendar(agenda);
                break;
            case LOCALHOST:
                tools.localhostTools.Tools.createCalendar(agenda);
                break;
        }
        
    }
    
    /**
     * Removes the agenda given in parameter from the agendas list of the
     * AgendaManager.
     * @param agenda 
     */
    public void removeAgenda(Agenda agenda, Services service) throws IOException, ServiceException{
        this.agendas.remove(agenda);
        
        switch(service){
            case GOOGLE:
                tools.googleTools.Tools.removeCalendar(agenda);
                break;
            case LOCALHOST:
                tools.localhostTools.Tools.removeCalendar(agenda);
                break;
        }
        
    }
    
    /**
     * Edits an agenda. Replace an agenda into the agendas list of the 
     * AgendaManager by another agenda. For this method, the id of the agenda to
     * edit have to be the same that the agenda given in parameter.
     * @param agenda 
     */
    public void editAgenda(Agenda agenda){
        //first, we have to find the index of the agenda in the ArrayList.
        int i = 0;
        while(this.agendas.get(i).getId().equals(agenda.getId())){   
            i++;
        }
        
        // In the second step we replace the agenda.
        this.agendas.set(i, agenda);
    }
    
    /**
     * Asks information to connect to a google account and dysplay the list of all 
     * calendars for this account.
     * @throws IOException
     * @throws ServiceException 
     */
    public void googleAgendasList() throws IOException, ServiceException{
        Scanner cin = new Scanner(System.in);
        
        // Ask new Proxy Information
//        System.out.print("login : ");
//        String login = cin.next();
//        
        System.out.print("password : ");
        String passwd = cin.next();
        
        URL url = NetworkTools.authTo(Services.GOOGLE, "maelbar44@gmail.com", passwd);
        
        tools.googleTools.Tools.printUserCalendars(url);
        
    }
    
}
