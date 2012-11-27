/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

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
//        System.getProperties().put("http.proxyHost", host);
//        System.getProperties().put("http.proxyPort", port);
//        System.getProperties().put("http.proxyUser", username);
//        System.getProperties().put("http.proxyPassword", passwd);
        try {
            
            System.setProperty("java.net.useSystemProxies","true");
            List l = ProxySelector.getDefault().select(
                        new URI("http://www.google.com/"));
            
            for (Iterator iter = l.iterator(); iter.hasNext(); ) {
                
                Proxy proxy = (Proxy) iter.next();
                
                System.out.println("proxy hostname : " + proxy.type());
                
                InetSocketAddress addr = (InetSocketAddress)
                    proxy.address();
                
                if(addr == null) {
                    
                    System.out.println("No Proxy");
                    
                } else {
                    
                    System.out.println("proxy hostname : " + 
                            addr.getHostName());
                    
                    System.out.println("proxy port : " + 
                            addr.getPort());
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
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
       downloadFrom("https://www.edt-sciences.univ-nantes.fr/g6935.ics");
    }
    
}
