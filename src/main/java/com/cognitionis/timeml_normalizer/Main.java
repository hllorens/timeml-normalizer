package com.cognitionis.timeml_normalizer;

import java.io.*;
import java.util.*;
import org.apache.commons.cli.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import com.cognitionis.nlp_files.*;
import com.cognitionis.utils_basickit.*;


/**
 *
 * @author Hector Llorens
 * @since 2011
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String annotations = null;
        ArrayList<File[]> annotationList = new ArrayList<File[]>();
        Boolean respect=false;
        
        try {
            long startTime = System.currentTimeMillis();


            Options opt = new Options();
            //addOption(String opt, boolean hasArg, String description)
            opt.addOption("h", "help", false, "Print this help");
            opt.addOption("respect", "respect-first-element-ids", false, "Use the first annotation as guide and respect its ids");
            opt.addOption("a", "annotations", true, "List of folders containing annotations of the same docs (between \"\" and separated by ;)");
            opt.addOption("d", "debug", false, "Debug mode: Output errors stack trace (default: disabled)");

            PosixParser parser = new PosixParser();
            CommandLine cl_options = parser.parse(opt, args);
            HelpFormatter hf = new HelpFormatter();
            if (cl_options.hasOption('h')) {
                hf.printHelp("TimeML-Normalizer", opt);
                System.exit(0);
            } else {
                if (cl_options.hasOption('d')) {
                    System.setProperty("DEBUG", "true");
                }
                if (cl_options.hasOption("respect")) {
                    respect=true;
                }
                String[] annotationsarr = null;

                if (cl_options.hasOption('a')) {
                    annotations = cl_options.getOptionValue("a");
                    annotationsarr = annotations.split(";");
                    if (annotationsarr.length < 2) {
                        hf.printHelp("TimeML-Normalizer", opt);
                        throw new Exception("At least TWO annnotations are required.");
                    }
                    for (int i = 0; i < annotationsarr.length; i++) {
                        File f = new File(annotationsarr[i]);
                        if (!f.exists()) {
                            hf.printHelp("TimeML-Normalizer", opt);
                            throw new Exception("Annotation does not exist: " + annotationsarr[i]);
                        }
                        if (f.isFile()) {
                            File[] files = {f};
                            XMLFile xmlfile = new XMLFile(f.getAbsolutePath(),FileUtils.getApplicationPath()+"program-data"+File.separator+"default-NLPFiles-descriptions"+File.separator+"tml-min-consistency-ids-only.xsd");
                            xmlfile.overrideExtension("tml-min-consistency");
                            if (!xmlfile.isWellFormatted()|| !validateTEXTDCT(f)) {
                                throw new Exception("File: " + xmlfile.getFile().getCanonicalPath() + " is not a valid TimeML XML file.");
                            }
                            annotationList.add(files);
                        } else {
                            File[] files = f.listFiles(FileUtils.onlyFilesFilter);
                            if (files.length == 0) {
                                throw new Exception("Empty folder: " + f.getName());
                            }
                            for (int fn = 0; fn < files.length; fn++) {
                                XMLFile xmlfile = new XMLFile(files[fn].getAbsolutePath(),FileUtils.getApplicationPath()+"program-data"+File.separator+"default-NLPFiles-descriptions"+File.separator+"tml-min-consistency-ids-only.xsd");
                                xmlfile.overrideExtension("tml-min-consistency");
                                if (!xmlfile.isWellFormatted()|| !validateTEXTDCT(xmlfile.getFile())) {
                                    throw new Exception("File: " + xmlfile.getFile().getCanonicalPath() + " is not a valid TimeML XML file.");
                                }
                            }
                            annotationList.add(files);
                        }
                        if (i > 0) {
                            // check equal to previous
                            File[] files1 = annotationList.get(i - 1);
                            File[] files2 = annotationList.get(i);

                            if (files1.length != files2.length) {
                                throw new Exception("Annotation folders must contain exactly the same number of files: " + files1.length);
                            } else {
                                for (int fn = 0; fn < files1.length; fn++) {
                                    if (!files1[fn].getName().equals(files2[fn].getName())) {
                                        throw new Exception("Annotation folders must contain exactly the same files: " + files1[fn]);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    hf.printHelp("TimeML-Normalizer", opt);
                    throw new Exception("Annotations parameter is required.");
                }
            }

            TimeML_Normalizer.normalize(annotationList,respect);

            long endTime = System.currentTimeMillis();
            long sec = (endTime - startTime) / 1000;
            if (sec < 60) {
                System.err.println("Done in " + StringUtils.twoDecPosS(sec) + " sec!\n");
            } else {
                System.err.println("Done in " + StringUtils.twoDecPosS(sec / 60) + " min!\n");
            }
        } catch (Exception e) {
            System.err.println("Errors found:\n\t" + e.getMessage() + "\n");
            if (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
                e.printStackTrace(System.err);
            }
            System.exit(1);
        }

    }

    public static boolean validateTEXTDCT(File f){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            doc.getDocumentElement().normalize();
            NodeList dctnodes = doc.getElementsByTagName("DCT");
            if (dctnodes.getLength() == 0) {
                throw new Exception("ERROR: <DCT> tag not found.");
            }
            if (dctnodes.getLength() > 1) {
                throw new Exception("ERROR: More than one <DCT> tag found.");
            }
            if(((Element) dctnodes.item(0)).getElementsByTagName("TIMEX3").getLength()!=1){
                throw new Exception("ERROR: <DCT> must contain one and only one <TIMEX3> tag. Expected: <DCT><TIMEX3 tid=\"t0\" type=... value=... temporalFunction=\"false\" functionInDocument=\"CREATION_TIME\">...some timex...</TIMEX3></DCT>");
            }
            NodeList text = doc.getElementsByTagName("TEXT");
            if (text.getLength() == 0) {
                throw new Exception("ERROR: <TEXT> tag not found.");
            }
            if (text.getLength() > 1) {
                throw new Exception("ERROR: More than one <TEXT> tag found.");
            }
            return true;
        }catch(Exception e){
            System.err.println("Errors found:\n\t" + e.toString() + "\n");
            if (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
            return false;
        }
    }

}
