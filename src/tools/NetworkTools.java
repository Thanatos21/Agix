/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLConnection;

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
    public static void setUpProxy(String host, String port, String username, String passwd) throws IOException{        
        UrlPacScriptSource pacScript = new UrlPacScriptSource("http://www.cri.univ-nantes.fr/cache.pac");
        System.out.println(pacScript.getScriptContent());
        PacProxySelector pacProxselect = new PacProxySelector(pacScript);
        ProxySelector.setDefault(pacProxselect);
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
    
    
    
    public static void main(String args[]){
       downloadFrom("http://www.edt-sciences.univ-nantes.fr/g6935.ics");
       downloadFrom("https://www.google.com/calendar/ical/ngtambgsg91ntk75s2mc9ifte4%40group.calendar.google.com/private-f2577d7def9bee876bfaedde5c249ca0/basic.ics");
    }
    
}
