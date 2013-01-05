/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.EncoderException;
import org.webevo.logic.similarity.semanticbased.WordSimilarityWS4J;
import org.webevo.storage.SchemaWordsDatabase;

/**
 *
 * @author Djordje Gligorijevic
 */
public class OntologyWordTester {

    static ArrayList<String> firstOntologyProperties = new ArrayList<>();
    static ArrayList<String> secondOntologyProperties = new ArrayList<>();

    /*
     * Reads lines from any txt file. First Ontology is alwas Schema.org
     * and its ontologies are stored in a file schemaorgallproperties.out in
     * the project (thus it is not neccesary to read additional file)
     * Code needed for storing those files can be found in:
     */
    public static void getWordsData(String sourceOne) throws FileNotFoundException, IOException, ClassNotFoundException {
        File sourceOneFile = new File(sourceOne);

        BufferedReader bufRdr = null;
        bufRdr = new BufferedReader(new FileReader(sourceOneFile));
        String line = null;
        while ((line = bufRdr.readLine()) != null) {
            secondOntologyProperties.add(line);
        }
        bufRdr.close();

        FileInputStream fileIn = new FileInputStream("schemaorgallproperties.out");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        firstOntologyProperties = (ArrayList<String>) in.readObject();
        in.close();
        fileIn.close();
        System.out.println("File schemaorgallproperties.out was loaded successfully");
//        int ii = 0;od 
//        for (int i = 0; i < secondOntologyProperties.size(); i++) {
//            if (secondOntologyProperties.get(i).contains("/")) {
//                String[] array = secondOntologyProperties.get(i).split("/");
//                for (int j = 0; j < array.length; j++) {
//                    secondOntologyProperties.add(array[j]);
//                }
//                secondOntologyProperties.remove(i);
//                ii++;
//            }
//        }
//        System.out.println(ii);
    }

    public static String measureSintaxSimilarities() throws EncoderException {
        String finalResult = "";
        for (int i = 0; i < firstOntologyProperties.size(); i++) {
            for (int j = 0; j < secondOntologyProperties.size(); j++) {

                String secondword = secondOntologyProperties.get(j);
                String firstword = firstOntologyProperties.get(i);
                if (secondword.contains("/")) {
                    secondword = secondword.substring(secondword.lastIndexOf("/") + 1);
                }
                System.out.println(firstword + "-" + secondword);
                String result = new String();
                if (firstword.startsWith("has") || firstword.startsWith("is") || firstword.startsWith("set") || firstword.startsWith("get") || firstword.startsWith("in") || (firstword.length() >= secondword.length() && (!secondword.startsWith("has") || !secondword.startsWith("is") || !secondword.startsWith("set") || !secondword.startsWith("get") || !secondword.startsWith("in")))) {
//                    System.out.println("SECOND");
                    result += firstword + "-" + secondword + ";";
                    result += LevensteinDistance.computeDistance(secondword, firstword) + ";";
                    result += (LevensteinDistance.levensteinDistanceWrapper(secondword, firstword) + ";");
                    result += (SoundexImplementation.soundexApache(secondword, firstword) + ";");
                    result += (SoundexImplementation.soundexApacheWrapper(secondword, firstword) + ";");
                    result += (SequenceSimilarity.sequenceSimilarity(secondword, firstword) + "\n");
                    System.out.println(result);

                } else {
//                    System.out.println("FIRST");
                    result += firstword + "-" + secondword + ";";
                    result += LevensteinDistance.computeDistance(firstword, secondword) + ";";
                    result += (LevensteinDistance.levensteinDistanceWrapper(firstword, secondword) + ";");
                    result += (SoundexImplementation.soundexApache(firstword, secondword) + ";");
                    result += (SoundexImplementation.soundexApacheWrapper(firstword, secondword) + ";");
                    result += (SequenceSimilarity.sequenceSimilarity(firstword, secondword) + "\n");
                    System.out.println(result);
                }
                finalResult += result;
            }
            finalResult += "\n";

        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new File("C:/Users/gligo_000/desktop/sintaxsimilaritylog_popravka.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OntologyWordTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        writer.println(finalResult);
        writer.close();
        return finalResult;
    }

    public static String measureSemanticSimilarity() {
        String finalRes = new String();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(new File("C:/Users/gligo_000/desktop/semanticsimilaritylog_popravka.txt"), true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OntologyWordTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < firstOntologyProperties.size(); i++) {
            System.out.println("Index of last word mapped is: " + i);
            for (int j = 0; j < secondOntologyProperties.size(); j++) {

                String secondword = secondOntologyProperties.get(j);
                String firstword = firstOntologyProperties.get(i);
                if (secondword.contains("/")) {
                    secondword = secondword.substring(secondword.lastIndexOf("/") + 1);
                }

                System.out.println(firstword + "-" + secondword);
                if (firstword.startsWith("has") || firstword.startsWith("is") || firstword.startsWith("set") || firstword.startsWith("get") || firstword.startsWith("in") || (firstword.length() >= secondword.length() && (!secondword.startsWith("has") || !secondword.startsWith("is") || !secondword.startsWith("set") || !secondword.startsWith("get")))) {
//                    System.out.println("SECOND");
                    List<Double> results = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

                    WordSimilarityWS4J.semanticSimilaritiesWrapper2(secondword, firstword, results);
                    //System.out.println("RESULTS");
                    finalRes = firstword + "-" + secondword + ";";
                    writer.print(firstword + "-" + secondword + ";");
                    for (int l = 0; l < results.size(); l++) {
                        Double double1 = results.get(l);
                        if (l == results.size() - 1) {
                            finalRes += (double1 + "\n");
                            writer.print(double1 + "\n");
                        } else {
                            finalRes += (double1 + ";");
                            writer.print(double1 + ";");
                        }
                    }
                    writer.flush();
                    //System.out.println(result);

                } else {
//                    System.out.println("FIRST");
                    List<Double> results = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                    WordSimilarityWS4J.semanticSimilaritiesWrapper2(firstword, secondword, results);
                    //System.out.println("RESULTS");
                    finalRes = finalRes + "" + firstword + "-" + secondword + ";";
                    writer.print(firstword + "-" + secondword + ";");
                    for (int k = 0; k < results.size(); k++) {
                        Double double1 = results.get(k);
                        if (k == results.size() - 1) {
                            finalRes += (double1 + "\n");
                            writer.print(double1 + "\n");
                        } else {
                            finalRes += (double1 + ";");
                            writer.print(double1 + ";");
                        }
                    }
                    writer.flush();
                    //System.out.println(result);
                }
            }
            writer.print("\n");
            writer.flush();
        }
        System.out.println(finalRes);


//        writer.println(finalRes);
//        writer.close();
        return finalRes;
    }

    public static void main(String[] args) throws EncoderException {
        try {
            getWordsData("C:\\Users\\gligo_000\\Desktop\\FON\\Diplomski\\Word similarity related work\\popravka.txt");
//            measureSintaxSimilarities();
            measureSemanticSimilarity();


        } catch (FileNotFoundException ex) {
            Logger.getLogger(OntologyWordTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(OntologyWordTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
