/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

import org.webevo.logic.similarity.semanticbased.WordSimilarityWS4J;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.webevo.logic.utility.Utility;
import org.webevo.storage.SchemaWordsDatabase;

/**
 *
 * @author Djordje
 */
public class N3DataToSchemaMapper {

    private static ArrayList<String> schemaWords = new ArrayList<>();
    private static ArrayList<String> schemaClassesWords = new ArrayList<>();
    private static ArrayList<String> schemaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> DBPediaWords = new ArrayList<>();
    private static ArrayList<String> DBPediaPropertiesWords = new ArrayList<>();
    private static ArrayList<String> GuessedWords = new ArrayList<>();

    public static MappedWords mapWord(String dbPediaDatasetPath, boolean isPredicate, int columnIndex, boolean isURL, double threshold) throws FileNotFoundException, IOException, ClassNotFoundException {
        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        BufferedReader bufRdr = new BufferedReader(new FileReader(DBPediaCSVDataset));


        if (!isPredicate) {
            if (SchemaWordsDatabase.getInstance().getSchemaorgallclasses() != null) {
                schemaWords = SchemaWordsDatabase.getInstance().getSchemaorgallclasses();
            } else {
                FileInputStream fileIn = new FileInputStream("schemaorgallclasses.out");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                schemaWords = (ArrayList<String>) in.readObject();
                SchemaWordsDatabase.getInstance().setSchemaorgallclasses(schemaWords);
                in.close();
                fileIn.close();
                System.out.println("File schemaorgallclasses.out was loaded successfully");
            }
        } else {
            if (SchemaWordsDatabase.getInstance().getSchemaorgallproperties() != null) {
                schemaWords = SchemaWordsDatabase.getInstance().getSchemaorgallproperties();
            } else {
                FileInputStream fileIn = new FileInputStream("schemaorgallproperties.out");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                schemaWords = (ArrayList<String>) in.readObject();
                SchemaWordsDatabase.getInstance().setSchemaorgallproperties(schemaWords);
                in.close();
                fileIn.close();
                System.out.println("File schemaorgallproperties.out was loaded successfully");
            }
        }

        //Get Schema words
        String guessWord;
        String[] temp;

        //Get DBPedia words and guesses words
        String line = null;
        String[] lineItems;
        while ((line = bufRdr.readLine()) != null) {

            if (!isPredicate) {
                if (line.startsWith("subject")) {
                    break;
                }
                lineItems = line.split(",");
                String wordToMap = lineItems[columnIndex];
                DBPediaWords.add(wordToMap);

                for (String wordToMapTo : schemaWords) {
                    if (WordSimilarityWS4J.calculateSimilarities(extractValue(wordToMap, isURL), wordToMapTo) >= threshold) {
                        GuessedWords.add(wordToMapTo);
                    } else {
                        GuessedWords.add(guessDataType(wordToMap));
                    }
                }
            } else {
                if (bufRdr.readLine() == null) {
                    String[] DBPediaWordsArray = line.split(",");
                    for (String string : DBPediaWordsArray) {
                        DBPediaWords.add(string);
                    }
                    for (String wordToMap : DBPediaWords) {
                        for (String wordToMapTo : schemaWords) {
                            if (WordSimilarityWS4J.calculateSimilarities(extractValue(wordToMap, isURL), wordToMapTo) >= threshold) {
                                GuessedWords.add(wordToMapTo);
                            } else {
                                GuessedWords.add(guessDataType(wordToMap));
                            }
                        }
                    }
                }
            }
        }
        bufRdr.close();

        MappedWords mw = new MappedWords(schemaWords, DBPediaWords, GuessedWords);
        System.out.println("schemaWords, DBPediaWords and GuessedWords have been loaded and sent.");
        return mw;
    }

    private static String extractValue(String DBPediaResource, boolean isURL) {
        if (DBPediaResource.startsWith("http") && isURL == false) {
            return DBPediaResource.substring(DBPediaResource.lastIndexOf("/"));
        } else if (DBPediaResource.contains(":")) {
            return DBPediaResource.substring(DBPediaResource.lastIndexOf(":"));
        }
        return DBPediaResource;
    }

    private static String guessDataType(String wordToMap) {
        if (Utility.isNumeric(wordToMap)) {
            return "Number";
        } else if (wordToMap.startsWith("http")) {
            return "URL";
        } else if (wordToMap.equals("true") || wordToMap.equals("false")) {
            return "Boolean";
        } else if (wordToMap.length() >= 10 && wordToMap.contains("-") && wordToMap.indexOf("-") == 5) {
            return "Date";
        } else {
            return "Text";
        }
    }

    /*
     * Automatic maps words that are similar and saves them as their schema.org 
     * words. Other words will remain the same. 
     * User cannot influence on this mapping function.
     */
    public static void mapAndSaveCSVFile(String dbPediaDatasetPath, double threshold) throws FileNotFoundException, IOException, ClassNotFoundException {
        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        File schemaDataset = new File(dbPediaDatasetPath.substring(0, dbPediaDatasetPath.lastIndexOf(".")) + "_schema.csv");
        PrintWriter out;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(schemaDataset)));
        BufferedReader bufRdr = new BufferedReader(new FileReader(DBPediaCSVDataset));

        if (SchemaWordsDatabase.getInstance().getSchemaorgallclasses() != null) {
            schemaClassesWords = SchemaWordsDatabase.getInstance().getSchemaorgallclasses();
        } else {
            try (FileInputStream fileIn = new FileInputStream("schemaorgallclasses.out"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                schemaClassesWords = (ArrayList<String>) in.readObject();
                SchemaWordsDatabase.getInstance().setSchemaorgallclasses(schemaClassesWords);
            }
            System.out.println("File schemaorgallclasses.out was loaded successfully");
        }
        if (SchemaWordsDatabase.getInstance().getSchemaorgallproperties() != null) {
            schemaPropertiesWords = SchemaWordsDatabase.getInstance().getSchemaorgallproperties();
        } else {
            try (FileInputStream fileIn = new FileInputStream("schemaorgallproperties.out"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                schemaPropertiesWords = (ArrayList<String>) in.readObject();
                SchemaWordsDatabase.getInstance().setSchemaorgallproperties(schemaPropertiesWords);
            }
            System.out.println("File schemaorgallproperties.out was loaded successfully");
        }

        String line;
        while ((line = bufRdr.readLine()) != null) {
            String[] wordsInLine = line.split(",");

            if (line.startsWith("subject")) {
                if (bufRdr.readLine() == null) {

                    for (String dbpediaWord : wordsInLine) {
                        for (int i = 0; i < schemaPropertiesWords.size(); i++) {
                            if (WordSimilarityWS4J.calculateSimilarities(extractValue(dbpediaWord, false), schemaPropertiesWords.get(i)) >= threshold) {
                                GuessedWords.add(schemaPropertiesWords.get(i));
                                break;
                            } else {
                                if (i == schemaPropertiesWords.size() - 1) {
                                    GuessedWords.add(dbpediaWord);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            for (String dbpediaWord : wordsInLine) {
                for (int i = 0; i < schemaClassesWords.size(); i++) {
                    if (WordSimilarityWS4J.calculateSimilarities(extractValue(dbpediaWord, false), schemaClassesWords.get(i)) >= threshold) {
                        GuessedWords.add(schemaClassesWords.get(i));
                        break;
                    } else {
                        if (i == schemaClassesWords.size() - 1) {
                            GuessedWords.add(dbpediaWord);
                            break;
                        }
                    }
                }
            }

            String lineToWrite = "";
            for (String guessedWords : GuessedWords) {
                lineToWrite = lineToWrite + guessedWords + ",";
            }
            lineToWrite = lineToWrite.substring(0, lineToWrite.length() - 1) + "\r\n";
            out.write(lineToWrite);
            out.flush();


            GuessedWords.clear();
            DBPediaWords.clear();
        }
        bufRdr.close();
        out.close();
    }

    public static void loadWordsToMeasureVariousSimilarities() throws FileNotFoundException, IOException, ClassNotFoundException {
        if (SchemaWordsDatabase.getInstance().getdBPediaproperties() != null) {
            DBPediaPropertiesWords = SchemaWordsDatabase.getInstance().getdBPediaproperties();
        } else {
            FileInputStream fileIn = new FileInputStream("DBpedia datasets properties names.out");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            DBPediaPropertiesWords = (ArrayList<String>) in.readObject();
            SchemaWordsDatabase.getInstance().setdBPediaproperties(DBPediaPropertiesWords);
            in.close();
            fileIn.close();
            System.out.println("File DBpedia datasets properties names.out was loaded successfully");
        }

        if (SchemaWordsDatabase.getInstance().getSchemaorgallproperties() != null) {
            schemaWords = SchemaWordsDatabase.getInstance().getSchemaorgallproperties();
        } else {
            FileInputStream fileIn = new FileInputStream("schemaorgallproperties.out");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            schemaWords = (ArrayList<String>) in.readObject();
            SchemaWordsDatabase.getInstance().setSchemaorgallproperties(schemaWords);
            in.close();
            fileIn.close();
            System.out.println("File schemaorgallproperties.out was loaded successfully");
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        loadWordsToMeasureVariousSimilarities();
        double similarity = 0;
        for (String dbPediapropWord : SchemaWordsDatabase.getInstance().getdBPediaproperties()) {
            for (String schemaPropWord : SchemaWordsDatabase.getInstance().getSchemaorgallproperties()) {
                WordSimilarityWS4J.calculateSimilarities(dbPediapropWord, schemaPropWord);
            }
        }

    }
}
