/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.semanticbased;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class WordSimilarityWS4J {

    private static ILexicalDatabase db = new NictWordNet();
    private static RelatednessCalculator[] rcs = {
        new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
        new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
    };

    public static double calculateSimilarities(String word1, String word2) {
        List<Double> results = new ArrayList<>();
        WS4JConfiguration.getInstance().setMFS(true);
        for (RelatednessCalculator rc : rcs) {
            double s = rc.calcRelatednessOfWords(word1, word2);
//            System.out.println(rc.getClass().getName() + "\t" + s);
            System.out.println("Similarity between " + word1 + " and " + word2 + " :" + rc.getClass().getName() + "\t" + s);
            results.add(s);
        }
        double result = 0;
        for (Double sim : results) {
            result = result + sim;
        }
        result = result / results.size();
        return result;
    }

    public static double semanticSimilaritiesWrapper(String firstWord, String secondWord) {
        List<Double> results = new ArrayList<>();
        WS4JConfiguration.getInstance().setMFS(true);
        double result = 0;
        double averageResultSet = 0;

        String[] firstWordParts = firstWord.split(" ");
        if (firstWordParts.length < 2) {
            firstWordParts = firstWord.split("_");
        }
        String[] secondWordParts = secondWord.split(" ");
        if (secondWordParts.length < 2) {
            secondWordParts = secondWord.split("_");
        }

        if (firstWordParts.length < 2) {
            //for ASCII letters
//        String[] r = firstWord.split("(?=\\p{Upper})");
            firstWordParts = firstWord.split("(?=[A-Z])");
            //for non-ASCII letters
            // String[] r = s.split("(?=\\p{Lu})");
        }
        if (secondWordParts.length < 2) {
            secondWordParts = secondWord.split(""
                    + "(?=[A-Z])");
        }


        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            double averageSemanticResult = 0;
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {

                if (-1 == usedIndexesOfSecondWordParts[i]) {
                    for (RelatednessCalculator rc : rcs) {
                        double s = rc.calcRelatednessOfWords(firstWordParts[i], secondWordParts[j]);
                        
                        results.add(s);
                    }
                    for (Double sim : results) {
                        averageResultSet = averageResultSet + sim;
                    }
                    averageResultSet = averageResultSet / results.size();

                    if (averageSemanticResult <= averageResultSet) {
                        averageSemanticResult = averageResultSet;
                        System.out.println("Average semantic similarity between " + firstWordParts[i] + " and " + secondWordParts[j] + " :" + "\t" + averageSemanticResult);
                        usedIndexesOfSecondWordParts[j] = i;
                        jot = j;
                    }

                }

            }
            double d = ((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWord.length() + (double) secondWord.length());

            result += averageSemanticResult * d;
//            System.out.println("U " + i + "toj iteraciji result ima vrednost: " + result);


        }



        return result;
    }

//    public static double calculateAndSaveSimilarities(String word1, String word2, String fileName) throws FileNotFoundException {
//        List<Double> results = new ArrayList<>();
//        File file = new File(fileName);
//        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
//        WS4JConfiguration.getInstance().setMFS(true);
//        for (RelatednessCalculator rc : rcs) {
//            double s = rc.calcRelatednessOfWords(word1, word2);
//            System.out.println(rc.getClass().getName() + "\t" + s);
//            out.write("Similarity between " + word1 + " and " + word2 + " :" + rc.getClass().getName() + "\t" + s);
//            results.add(s);
//        }
//        double result = 0;
//        for (Double sim : results) {
//            result = result + sim;
//        }
//        result = result / results.size();
//        return result;
//    }
    public static double calculateThreshold(ArrayList<String> DBPediaWords, ArrayList<String> SchemaWords) {
        double treshold = 0;
        for (String DBPediaWord : DBPediaWords) {
            for (String SchemaWord : SchemaWords) {
                treshold = treshold + calculateSimilarities(DBPediaWord, SchemaWord);
            }
        }
        treshold = treshold / (DBPediaWords.size() * SchemaWords.size());
        return treshold;
    }

    public static void main(String[] args) {
        long t0 = System.currentTimeMillis();
        calculateSimilarities("value", "price");
//        semanticSimilaritiesWrapper("mbox", "email");
        long t1 = System.currentTimeMillis();
        System.out.println("Done in " + (t1 - t0) + " msec.");
        System.out.println("Max: " + Double.MAX_VALUE);
    }
}