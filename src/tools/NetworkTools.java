/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import agenda.Services;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.googleTools.Tools;

/**
 *
 * @author mael
 */
public class NetworkTools {
    
    /**
     * Configure the Proxy
     * @param host
     * @param port
     * @param username
     * @param passwd
     */
    public static void setUpProxy(String host, String port, String username, String passwd){
        System.getProperties().put("http.proxyHost", host);
        System.getProperties().put("http.proxyPort", port);
//        System.getProperties().put("http.proxyUser", username);
//        System.getProperties().put("http.proxyPassword", passwd);
    }
    
   
    /**
     * Download and save an agenda into the HDD.
     * @param host
     */
    public static void downloadFrom(String host){
        InputStream input = null;
        FileOutputStream writeFile = null;

        try
        {
            URL url = new URL(host);
            URLConnection connection = url.openConnection();
            int fileLength = connection.getContentLength();

            if (fileLength == -1)
            {
                System.out.println("Invalide URL or file.");
            }

            input = connection.getInputStream();
            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            writeFile = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int read;

            while ((read = input.read(buffer)) > 0)
                writeFile.write(buffer, 0, read);
            writeFile.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error while trying to download the file.");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writeFile.close();
                input.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /** Authentication to the service Using the login/passwd given in parameter.
     * Return the URL to get the list of all calendar of the service for the given acount.
     *  @return URL 
     */
    public static URL authTo(Services service, String login, String passwd) throws IOException, ServiceException{
        switch(service){
            case GOOGLE: 
                return tools.googleTools.Tools.authTo(login, passwd);
        }
        
        return null;    
    }
    
    public static void main(String args[]){
       
    }
    
}
