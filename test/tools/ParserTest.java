/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

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
        String filePath = "C:\\Users\\Julien\\Documents\\NetBeansProjects\\genieLog\\test.lix";
        Parser instance = new Parser();
        String expResult = "";
        String result = instance.Lix2Ical(filePath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Ical2Lix method, of class Parser.
     */
    @Test
    public void testIcal2Lix() throws Exception {
        System.out.println("Ical2Lix");
        String icsFilePath = "";
        String lixFilePath = "";
        Parser instance = new Parser();
        String expResult = "";
        String result = instance.Ical2Lix(icsFilePath, lixFilePath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Parser.
     */
    @Test
    public void testMain() throws Exception {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
