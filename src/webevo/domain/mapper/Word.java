/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.domain.mapper;

/**
 *
 * @author Djordje
 */
public class Word {
    private String synset;
    private char wordType;
    private String name;
    private String src;

    public Word() {
    }

    public Word(String synset) {
        this.synset = synset;
       
    }
    
     public Word(String synset, char wordType) {
        this.synset = synset;
        this.wordType = wordType;
    }

    
    public Word(String synset, char wordType, String name, String src) {
        this.synset = synset;
        this.wordType = wordType;
        this.name = name;
        this.src = src;
    }

   
    /**
     * @return the synset
     */
    public String getSynset() {
        return synset;
    }

    /**
     * @param synset the synset to set
     */
    public void setSynset(String synset) {
        this.synset = synset;
    }

    /**
     * @return the wordType
     */
    public char getWordType() {
        return wordType;
    }

    /**
     * @param wordType the wordType to set
     */
    public void setWordType(char wordType) {
        this.wordType = wordType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    
    
}
