/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genielog;

import agenda.AgendaManager;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mael
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            AgendaManager manager = AgendaManager.getInstance();
        try {
            //            System.out.println(System.getProperty("http.proxyHost"));
            //            System.out.println(System.getProperty("http.proxyPort"));
            //            System.out.println(System.getProperty("http.proxyUser"));
            //            System.out.println(System.getProperty("http.proxyPassword"));
                        
            //            manager.configProxy();
                        
            //            System.out.println(System.getProperty("http.proxyHost"));
            //            System.out.println(System.getProperty("http.proxyPort"));
            //            System.out.println(System.getProperty("http.proxyUser"));
            //            System.out.println(System.getProperty("http.proxyPassword"));
                        
            //         manager.getAgenda();
                        
         manager.googleAgendasList();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
