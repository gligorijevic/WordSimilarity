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
import java.util.ArrayList;
import org.webevo.logic.utility.Utility;

/**
 *
 * @author Djordje
 */
public class N3DataToSchemaMapper {

    private static ArrayList<String> schemaWords = new ArrayList<>();
    private static ArrayList<String> DBPediaWords = new ArrayList<>();
    private static ArrayList<String> GuessedWords = new ArrayList<>();

    public static MappedWords mapWord(String dbPediaDatasetPath, boolean isPredicate, int columnIndex, boolean isURL, double threshold) throws FileNotFoundException, IOException, ClassNotFoundException {
        File DBPediaCSVDataset = new File(dbPediaDatasetPath);
        File SchemaDataset;

        BufferedReader bufRdr = new BufferedReader(new FileReader(DBPediaCSVDataset));


        if (!isPredicate) {
            FileInputStream fileIn = new FileInputStream("schemaorgallclasses.out");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            schemaWords = (ArrayList<String>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("File schemaorgallclasses.out was loaded successfully");
        } else {
            FileInputStream fileIn = new FileInputStream("schemaorgallproperties.out");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            schemaWords = (ArrayList<String>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("File schemaorgallproperties.out was loaded successfully");
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
                    if (WordSimilarity.calculateSimilarities(extractValue(wordToMap, isURL), wordToMapTo) >= threshold) {
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
                            if (WordSimilarity.calculateSimilarities(extractValue(wordToMap, isURL), wordToMapTo) >= threshold) {
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
//    private static boolean isNumeric(String str) {
//        NumberFormat formatter = NumberFormat.getInstance();
//        ParsePosition pos = new ParsePosition(0);
//        formatter.parse(str, pos);
//        return str.length() == pos.getIndex();
//    }
}
