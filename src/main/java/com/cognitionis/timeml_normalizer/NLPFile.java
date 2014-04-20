package com.cognitionis.timeml_normalizer;

import java.io.*;

/**
 *
 * @author Héctor Llorens
 * @since 2011
 */
public abstract class NLPFile {

    public static enum Subclasses {

        PipesFile, PlainFile, TreebankFile, XMLFile;
    }
    public static int MAX_CHARS_4_CATEGORIZE = 200;
    protected File f;
    protected String language;
    protected String encoding;
    protected String extension;

    public abstract Boolean isWellFormed();

    public abstract String getStats(String parameters);


    /**
     * Creates a plain file from any type of NLPFile.
     *
     * @return  String: the canonical path to the created file
     */
    public abstract String toPlain();

    /**
     * Creates a paired file from any type of NLPFile.
     * Paired means equivalent or containing all the information to get one from another.
     * Example: In a pipes file this will correct the tokens if needed and include the leading blanks or the offset of each token.
     *
     * @param plainmodel the plain file (model) to pair with
     * @return  String: the canonical path to the created file
     */
    public abstract String pair2plain(String plainmodel);

    /**
     * Creates a merged tok and tml file from a tok Pipes file.
     *
     * @param tml file to merge
     * @return  String: the canonical path to the created file
     */
    //public abstract String merge_tok_n_xml(String xmlfile, String root_tag, String tags_re, String attrs_re);
    public void loadFile(File f) {
        try {
            if (!f.exists()) {
                throw new FileNotFoundException("File does not exist: " + f);
            }
            if (!f.isFile()) {
                throw new IllegalArgumentException("Should be a file (not directory, etc): " + f);
            }
            this.f = f;
            this.extension=CognitionisFileUtils.getExtension(this.f.getName());

        } catch (Exception e) {
            System.err.println("Errors found (" + this.getClass().getSimpleName() + "):\n\t" + e.toString() + "\n");
            if (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equalsIgnoreCase("true")) {
                e.printStackTrace(System.err);
            }
            this.f = null;
        }

    }

    public File getFile() {
        return this.f;
    }

    /**
     * Returns the directory of the File including the separator (for Linux only)
     * @return
     * @throws IOException
     */
    public String getFileDirectory() throws IOException {
        return this.f.getCanonicalPath().substring(0,this.f.getCanonicalPath().lastIndexOf('/')+1);
    }

    public Boolean saveAs(String dest_file) {
        if (this.f != null) {
            System.out.println("hola");
            return true;
        }

        return false;
    }

    public Boolean isLoaded() {
        if (this.f == null) {
            return false;
        }
        return true;
    }

    public void setEncoding(String e) {
        this.encoding = e;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setLanguage(String lang) {
        this.language = lang;
    }

    public String getLanguage() {
        if(this.language==null){
            this.language="en";
        }
        return this.language;
    }

    public String getExtension() {
        if(this.extension==null){
            this.extension="unknown";
        }
        return this.extension;
    }

    public void overrideExtension(String newext){
        this.extension=newext;
    }

}
