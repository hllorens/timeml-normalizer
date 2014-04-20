package com.cognitionis.timeml_normalizer;


import org.xml.sax.*;

/**
 * @author Hector Llorens
 * @since 2011
 */

public class SimpleErrorHandler implements ErrorHandler {

    public void warning(SAXParseException e) throws SAXException {
        System.err.println("Warning:" +e.getMessage());
        if (System.getProperty("DEBUG")!=null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
            e.printStackTrace(System.err);
        }
    }

    public void error(SAXParseException e) throws SAXException {
        //System.err.println(e.getMessage());
        if (System.getProperty("DEBUG")!=null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
            e.printStackTrace(System.err);
        }
        throw e;
    }

    public void fatalError(SAXParseException e) throws SAXException {
        //System.err.println(e.getMessage());
        if (System.getProperty("DEBUG")!=null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
            e.printStackTrace(System.err);
        }
        throw e;
    }
}
