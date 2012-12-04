/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import agenda.Agenda;
import agenda.Evt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import net.fortuna.ical4j.data.ParserException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Julien
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Lix2Ical method, of class Parser.
     */
    @Test
    public void testLix2Ical() throws Exception {
        String filePath = "ical2LixResult";
        Parser instance = new Parser();
        String result = "";
        
        File file = new File(filePath);
        assertEquals(result, instance.Lix2Ical(file));
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of Ical2Lix method, of class Parser.
     */
    @Test
    public void testIcal2Lix() throws Exception {
        String filePath = "g28556.ics";
        String result = "";
        Parser instance = new Parser();
        
        //assertEquals(result, instance.Ical2Lix(filePath, "ical2LixResult"));
    }
    
    /**
     * Test of ical2Agenda method, of class Parser
     */
    @Test
    public void testIcal2Agenda() throws FileNotFoundException, IOException, ParserException {
        String filePath = "Lix2IcalResult";
        Parser instance = new Parser();
        Agenda agenda = instance.ical2Agenda(filePath);
        
        int i = 0;
        
        Iterator evtit = agenda.getEvents().iterator();
        while ( evtit.hasNext() ) {
            Evt e = (Evt) evtit.next();
            System.out.println(e.toString());
            i++;
        }
        System.out.println(i + "Events succesfully added");
    }
}
