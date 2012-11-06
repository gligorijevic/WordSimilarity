/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Djordje
 */
public class WordSimilarityThreshold {

    private static double labelThreshold;
    private static double dataThreshold;
    private static ArrayList<String> schemaClassesWords = new ArrayList<>();
    private static ArrayList<String> schemaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaClassesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> GuessedWords = new ArrayList<>();

    public static void mapWord(String dbPediaDatasetPath) throws FileNotFoundException, IOException, ClassNotFoundException {

        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        File SchemaDataset;
        PrintWriter writer = new PrintWriter(new File("Threshold for "+dbPediaDatasetPath));

        BufferedReader bufRdr = new BufferedReader(new FileReader(DBPediaCSVDataset));


        FileInputStream fileInClasses = new FileInputStream("schemaorgallclasses.out");
        ObjectInputStream in1 = new ObjectInputStream(fileInClasses);
        schemaClassesWords = (ArrayList<String>) in1.readObject();
        in1.close();
        fileInClasses.close();
        System.out.println("File schemaorgallclasses.out was loaded successfully");
        FileInputStream fileInProperties = new FileInputStream("schemaorgallproperties.out");
        ObjectInputStream in2 = new ObjectInputStream(fileInProperties);
        schemaPropertiesWords = (ArrayList<String>) in2.readObject();
        in2.close();
        fileInProperties.close();
        System.out.println("File schemaorgallproperties.out was loaded successfully");
        //Get DBPedia words and guesses words
        String line = null;
        String[] lineItems = null;
        while ((line = bufRdr.readLine()) != null) {

            if (line.startsWith("subject")) {
                if (bufRdr.readLine() == null) {
                    String[] DBPediaWordsArray = line.split(",");
                    for (String string : DBPediaWordsArray) {
                        DBPediaClassesWords.add(string);
                    }
                    labelThreshold = WordSimilarity.calculateThreshold(DBPediaClassesWords, schemaClassesWords);
                    System.out.println("Threshold for label in " + dbPediaDatasetPath + " file is: " + labelThreshold);
                    writer.write("Threshold for label in " + dbPediaDatasetPath + " file is: " + labelThreshold);
                }
            }

            lineItems = line.split(",");
            for (String wordToMap : lineItems) {
                DBPediaPropertiesWords.add(wordToMap);
            }

        }
        bufRdr.close();
        double calc = 0;
        if (DBPediaPropertiesWords != null) {
            ArrayList<String> words = new ArrayList<>();
            for (int i = 0; i < DBPediaPropertiesWords.size(); i++) {
                for (int j = i + 1; j < DBPediaPropertiesWords.size(); j = j + lineItems.length) {
                    if (DBPediaPropertiesWords.get(i) != null || !DBPediaPropertiesWords.get(i).equals("")) {
                        words.add(DBPediaPropertiesWords.get(i));
                    }
                }
                calc += WordSimilarity.calculateThreshold(DBPediaPropertiesWords, schemaPropertiesWords);
            }
        }
        dataThreshold = calc / lineItems.length;
        System.out.println("Threshold for data in " + dbPediaDatasetPath + " file is: " + dataThreshold);
        writer.write("Threshold for data in " + dbPediaDatasetPath + " file is: " + dataThreshold);
        
        
    }
}
