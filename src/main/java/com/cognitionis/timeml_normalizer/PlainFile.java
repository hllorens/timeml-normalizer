package com.cognitionis.timeml_normalizer;


/**
 *
 * @author Héctor Llorens
 * @since 2011
 */
public class PlainFile extends NLPFile {

    private final static String format="PLAIN";

    public String toPlain(){
        return this.getFile().toString();
    }

    public Boolean isWellFormed() {
        try {
            if (!super.isLoaded()) {
                throw new Exception("No file loaded in NLPFile object");
            }

        } catch (Exception e) {
            System.err.println("Errors found ("+this.getClass().getSimpleName()+"):\n\t" + e.toString() + "\n");
            if(System.getProperty("DEBUG")!=null && System.getProperty("DEBUG").equalsIgnoreCase("true")){e.printStackTrace(System.err);}
            return false;
        }
        return true;
    }
    /*
     * UTF-8 patterns test over UTF-8 files
    public void print_e(){
    try{
    BufferedReader reader = new BufferedReader(new FileReader(this.f));
    try {
    String line = null;
    int linen = 0;

    while ((line = reader.readLine()) != null) {
    linen++; //System.getProperty("line.separator")

    if (line.matches(".*é.*")) {
    System.out.println("é in: "+line);
    }else{
    System.out.println("asdfasdfasd");
    }

    }
    } finally {
    if (reader != null) {
    reader.close();
    }
    }


    } catch (Exception e) {
    System.err.println("Errors found:\n" + e.toString() + ":" + e.getMessage() + "\n");
    e.printStackTrace(System.err);
    System.exit(1);
    }
    }*/


    public String getStats(String parameters){
        String statistics="";

        

        return statistics;
    }


    public String pair2plain(String plainmodel){
        return null;
    }

    public String merge_tok_n_xml(String xmlfile, String root_tag, String tags_re, String attrs_re) {
        return null;
    }


}
