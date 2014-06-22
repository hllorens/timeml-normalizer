package com.cognitionis.timeml_normalizer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hector
 */
public class TimeMLNormalizerTest {

    public TimeMLNormalizerTest() {
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
     * Test of TMQA part ActionHandler
     */
    @Test
    public void testNormalizer() throws Exception {
        System.out.println("tmqa");
        String input_folder1 = this.getClass().getResource("/system_a/").toURI().toString();
        String input_folder2 = this.getClass().getResource("/system_b/").toURI().toString();

        String predicted="";
        String expected="";
        
        // TODO
        

        if(!predicted.equals(expected)){
            System.err.println("ERROR: normalization is not equal to expected");
        }
        assertEquals(expected, predicted);        
    }
    
}
