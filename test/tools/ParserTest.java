/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        String filePath = "/home/julien/NetBeansProjects/Agix/test2.lix";
        Parser instance = new Parser();
        String expResult = "";
        
        File file = new File(filePath);
        String result = instance.Lix2Ical(file);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Ical2Lix method, of class Parser.
     */
    @Test
    public void testIcal2Lix() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
