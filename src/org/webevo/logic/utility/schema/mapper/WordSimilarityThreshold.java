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

/**
 *
 * @author Djordje
 */
public class WordSimilarityThreshold {

    private static double labelThreshold;
    private static double dataThreshold;
    private static int columnNumber = 0;
    private static int rowsNumber = 0;
    private static ArrayList<String> schemaClassesWords = new ArrayList<>();
    private static ArrayList<String> schemaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaClassesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> GuessedWords = new ArrayList<>();

    public static void calculateThreshold(String dbPediaDatasetPath) throws FileNotFoundException, IOException, ClassNotFoundException {

        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        File SchemaDataset;
        String name = dbPediaDatasetPath.substring(dbPediaDatasetPath.lastIndexOf("\\") + 1, dbPediaDatasetPath.lastIndexOf("."));
        System.out.println(name);
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("threshold for " + name + ".txt")));


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
                    System.out.println("Threshold for label in " + name + " file is: " + labelThreshold);
                    writer.println("Threshold for label in " + name + " file is: " + labelThreshold);
                }
            }

            lineItems = line.split(",");
            for (String wordToMap : lineItems) {
                DBPediaPropertiesWords.add(wordToMap);
            }
            columnNumber = lineItems.length;
        }
        bufRdr.close();
        double calc = 0;
        if (DBPediaPropertiesWords != null) {
            ArrayList<String> words = new ArrayList<>();
            for (int i = 0; i < DBPediaPropertiesWords.size(); i++) {
                for (int j = i + 1; j < DBPediaPropertiesWords.size(); j = j + columnNumber) {
                    if (DBPediaPropertiesWords.get(i) != null || !DBPediaPropertiesWords.get(i).equals("")) {
                        words.add(DBPediaPropertiesWords.get(i));
                    }
                }
                calc += WordSimilarity.calculateThreshold(DBPediaPropertiesWords, schemaPropertiesWords);
            }
        }
        dataThreshold = calc / columnNumber;
        System.out.println("Threshold for data in " + name + " file is: " + dataThreshold);
        writer.println("Threshold for data in " + name + " file is: " + dataThreshold);
        writer.flush();
        writer.close();

    }
}
