/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.storage;

import java.util.ArrayList;

/**
 *
 * @author Djordje Gligorijevic
 */
public class SchemaWordsDatabase {

    private ArrayList<String> schemaorgallproperties;
    private ArrayList<String> schemaorgallclasses;

    private SchemaWordsDatabase() {
    }

    public static SchemaWordsDatabase getInstance() {
        return SchemaWordsDatabaseHolder.INSTANCE;
    }

    /**
     * @return the schemaorgallproperties
     */
    public ArrayList<String> getSchemaorgallproperties() {
        if (schemaorgallproperties != null && schemaorgallproperties.size() > 0) {
            return schemaorgallproperties;
        } else {
            return null;
        }
    }

    /**
     * @param schemaorgallproperties the schemaorgallproperties to set
     */
    public void setSchemaorgallproperties(ArrayList<String> schemaorgallproperties) {
        if (schemaorgallproperties.size() > 0 || schemaorgallproperties == null) {
            this.schemaorgallproperties = schemaorgallproperties;
        } else {
            throw new NullPointerException("Given list is empty or null!");
        }
    }

    /**
     * @return the schemaorgallclasses
     */
    public ArrayList<String> getSchemaorgallclasses() {
        
        if (schemaorgallclasses != null && schemaorgallclasses.size() > 0) {
            return schemaorgallclasses;
        } else {
            return null;
        }
    }

    /**
     * @param schemaorgallclasses the schemaorgallclasses to set
     */
    public void setSchemaorgallclasses(ArrayList<String> schemaorgallclasses) {
        if (schemaorgallclasses.size() > 0 || schemaorgallclasses == null) {
            this.schemaorgallclasses = schemaorgallclasses;
        } else {
            throw new NullPointerException("Given list is empty or null!");
        }
    }

    private static class SchemaWordsDatabaseHolder {

        private static final SchemaWordsDatabase INSTANCE = new SchemaWordsDatabase();
    }
}
