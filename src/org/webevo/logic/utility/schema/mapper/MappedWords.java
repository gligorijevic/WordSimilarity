/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

import java.util.ArrayList;

/**
 *
 * @author Djordje
 */
public class MappedWords {
    private ArrayList<String> schemaWords;
    private ArrayList<String> DBPediaWords;
    private ArrayList<String> GuessedWords;

    public MappedWords(ArrayList<String> schemaWords, ArrayList<String> DBPediaWords, ArrayList<String> GuessedWords) {
        this.schemaWords = schemaWords;
        this.DBPediaWords = DBPediaWords;
        this.GuessedWords = GuessedWords;
    }

    public MappedWords() {
    }

    /**
     * @return the schemaWords
     */
    public ArrayList<String> getSchemaWords() {
        return schemaWords;
    }

    /**
     * @param schemaWords the schemaWords to set
     */
    public void setSchemaWords(ArrayList<String> schemaWords) {
        this.schemaWords = schemaWords;
    }

    /**
     * @return the DBPediaWords
     */
    public ArrayList<String> getDBPediaWords() {
        return DBPediaWords;
    }

    /**
     * @param DBPediaWords the DBPediaWords to set
     */
    public void setDBPediaWords(ArrayList<String> DBPediaWords) {
        this.DBPediaWords = DBPediaWords;
    }

    /**
     * @return the GuessedWords
     */
    public ArrayList<String> getGuessedWords() {
        return GuessedWords;
    }

    /**
     * @param GuessedWords the GuessedWords to set
     */
    public void setGuessedWords(ArrayList<String> GuessedWords) {
        this.GuessedWords = GuessedWords;
    }
    
    
}
