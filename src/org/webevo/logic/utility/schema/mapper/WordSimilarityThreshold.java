/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.webevo.logic.utility.Utility;
import org.webevo.storage.SchemaWordsDatabase;

/**
 *
 * @author Djordje
 */
public class WordSimilarityThreshold {

    private static double labelThreshold;
    private static double propertiesDataThreshold;
    private static double classDataThreshold;
    private static int columnNumber = 0;
    private static int rowsNumber = 0;
    private static ArrayList<String> schemaClassesWords = new ArrayList<>();
    private static ArrayList<String> schemaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaLabelWords = new ArrayList<>();
    private static ArrayList<String> DBPediaClassWords = new ArrayList<>();
    private static ArrayList<String> DBPediaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> GuessedWords = new ArrayList<>();

    public static void calculateThreshold(String dbPediaDatasetPath) throws FileNotFoundException, IOException, ClassNotFoundException {

        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        File SchemaDataset;
        String name = dbPediaDatasetPath.substring(dbPediaDatasetPath.lastIndexOf("\\") + 1, dbPediaDatasetPath.lastIndexOf("."));
        System.out.println(name);
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("threshold for " + name + ".txt")));


        BufferedReader bufRdr = new BufferedReader(new FileReader(DBPediaCSVDataset));

        if (SchemaWordsDatabase.getInstance().getSchemaorgallclasses() != null) {
            schemaClassesWords = SchemaWordsDatabase.getInstance().getSchemaorgallclasses();
        } else {
            FileInputStream fileInClasses = new FileInputStream("schemaorgallclasses.out");
            ObjectInputStream in1 = new ObjectInputStream(fileInClasses);
            schemaClassesWords = (ArrayList<String>) in1.readObject();
            SchemaWordsDatabase.getInstance().setSchemaorgallclasses(schemaClassesWords);
            in1.close();
            fileInClasses.close();
            System.out.println("File schemaorgallclasses.out was loaded successfully, list size: " + schemaClassesWords.size());
        }
        if (SchemaWordsDatabase.getInstance().getSchemaorgallproperties() != null) {
            schemaPropertiesWords = SchemaWordsDatabase.getInstance().getSchemaorgallproperties();
        } else {
            FileInputStream fileInProperties = new FileInputStream("schemaorgallproperties.out");
            ObjectInputStream in2 = new ObjectInputStream(fileInProperties);
            schemaPropertiesWords = (ArrayList<String>) in2.readObject();
            SchemaWordsDatabase.getInstance().setSchemaorgallproperties(schemaPropertiesWords);
            in2.close();
            fileInProperties.close();
            System.out.println("File schemaorgallproperties.out was loaded successfully, list size: " + schemaPropertiesWords.size());
        }


        String line = null;
        String[] lineItems = null;
        while ((line = bufRdr.readLine()) != null) {

            if (line.startsWith("subject")) {
                if (bufRdr.readLine() == null) {
                    String[] DBPediaWordsArray = line.split(",");
                    for (String string : DBPediaWordsArray) {
                        DBPediaLabelWords.add(string);
                    }
                    System.out.println("Calculating label threshold started (with DBpedia classes list size =" + DBPediaLabelWords.size() + " ) at: ");
                    Utility.currentTime();
                    labelThreshold = WordSimilarity.calculateThreshold(DBPediaLabelWords, schemaClassesWords);
                    System.out.println("Threshold for label in " + name + " file is: " + labelThreshold);
                    writer.println("Threshold for label in " + name + " file is: " + labelThreshold);
                    System.out.println("Calculating label threshold finished at: ");
                    Utility.currentTime();
                }
            }

            lineItems = line.split(",");
//            for (String wordToMap : lineItems) {
//                
//                DBPediaPropertiesWords.add(wordToMap);
//            }

            for (int i = 0; i < lineItems.length; i++) {
                if (i == 0) {
                    DBPediaClassWords.add(lineItems[i]);
                } else {
                    DBPediaPropertiesWords.add(lineItems[i]);
                }
            }

            columnNumber = lineItems.length;
        }
        bufRdr.close();
        double calc = 0;

        if (DBPediaClassWords != null) {
//            ArrayList<String> words = new ArrayList<>();
//            for (int i = 0; i < DBPediaClassWords.size(); i++) {
//                for (int j = i + 1; j < DBPediaClassWords.size(); j = j + columnNumber) {
//                    if (DBPediaClassWords.get(i) != null || !DBPediaClassWords.get(i).equals("")) {
//                        words.add(DBPediaClassWords.get(i));
//                    }
//                }
            System.out.println("Calculating classes data threshold started (with DBPedia classes words size = " + DBPediaClassWords.size() + ") at: ");
            Utility.currentTime();
            calc += WordSimilarity.calculateThreshold(DBPediaClassWords, schemaClassesWords);
            System.out.println("Calculating label threshold finished at: ");
            Utility.currentTime();
//            }
        }
        classDataThreshold = calc / columnNumber;
        System.out.println("Threshold for classes data in " + name + " file is: " + classDataThreshold);
        writer.println("Threshold for classes data in " + name + " file is: " + classDataThreshold);

        double calc1 = 0;
        if (DBPediaPropertiesWords != null) {
//            ArrayList<String> words = new ArrayList<>();
//            for (int i = 0; i < DBPediaPropertiesWords.size(); i++) {
//                for (int j = i + 1; j < DBPediaPropertiesWords.size(); j = j + columnNumber) {
//                    if (DBPediaPropertiesWords.get(i) != null || !DBPediaPropertiesWords.get(i).equals("")) {
//                        words.add(DBPediaPropertiesWords.get(i));
//                    }
//                }
            System.out.println("Calculating properties data threshold started (with DBPedia properties words size = " + DBPediaPropertiesWords.size() + ") at: ");
            Utility.currentTime();
            calc1 += WordSimilarity.calculateThreshold(DBPediaPropertiesWords, schemaPropertiesWords);
            System.out.println("Calculating label threshold finished at: ");
            Utility.currentTime();
//            }
        }
        propertiesDataThreshold = calc1 / columnNumber;
        System.out.println("Threshold for properties data in " + name + " file is: " + propertiesDataThreshold);
        writer.println("Threshold for properties data in " + name + " file is: " + propertiesDataThreshold);

        writer.flush();
        writer.close();
    }
}
