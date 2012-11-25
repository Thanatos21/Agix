/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author mael
 */
public class NetworkTools {
    
    public static void setUpProxy(String host, String port, String username, String passwd){
        System.getProperties().put("http.proxyHost", host);
        System.getProperties().put("http.proxyPort", port);
        System.getProperties().put("http.proxyUser", username);
        System.getProperties().put("http.proxyPassword", passwd);
    }
    
    public static void main(String args[]){
        System.out.println(System.getProperty("http.proxyPort"));
    }
    
}
