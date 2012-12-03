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
            if (sim == Double.MAX_VALUE || result == Double.MAX_VALUE) {
                result = Double.MAX_VALUE;
            } else {
                result = result + sim;
            }
        }
        result = result / results.size();
        return result;
    }

    public static double calculateAverageSimilarity(String word1, String word2) {
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
            if (sim == Double.MAX_VALUE || result >= 1) {
                result = 1;
            } else {
                result = result + (sim / Double.MAX_VALUE);
            }
        }
        result = result / results.size();
        return result;
    }

    public static double semanticSimilaritiesAverageWrapper(String firstWord, String secondWord) {

        double result = 0;
        int firstWordLength = firstWord.length();
        int secondWordLength = secondWord.length();
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
        } else {
            firstWordLength = firstWord.length() - firstWordParts.length - 1;
        }
        if (secondWordParts.length < 2) {
            secondWordParts = secondWord.split(""
                    + "(?=[A-Z])");
        } else {
            secondWordLength = secondWord.length() - secondWordParts.length - 1;
        }

        String firstWordAbbreviation;
        String secondWordAbbreviation;
        StringBuilder firstWordAbbreviationB = new StringBuilder();
        StringBuilder secondWordAbbreviationB = new StringBuilder();



        ArrayList<String> firstWordPartsArray = capitalWord(firstWordParts, firstWordAbbreviationB);
        ArrayList<String> secondWordPartsArray = capitalWord(secondWordParts, secondWordAbbreviationB);

        firstWordAbbreviation = firstWordAbbreviationB.toString();
        secondWordAbbreviation = secondWordAbbreviationB.toString();

        firstWordParts = firstWordPartsArray.toArray(new String[firstWordPartsArray.size()]);
        secondWordParts = secondWordPartsArray.toArray(new String[secondWordPartsArray.size()]);

        //TODO - but still works. :)
        if (firstWordAbbreviation.length() > 2) {
            for (String part : secondWordParts) {
                if (part.contains(firstWordAbbreviation)) {
                    return Double.MAX_VALUE;
                }
            }
        } else if (secondWordAbbreviation.length() > 2) {
            for (String part : firstWordParts) {
                if (part.contains(secondWordAbbreviation)) {
                    return Double.MAX_VALUE;
                }
            }
        }

        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            double averageSemanticSimilarity = 0;
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {
                if (i <= usedIndexesOfSecondWordParts.length - 1) {

                    if (averageSemanticSimilarity <= getNormalizedSimilarityMatrixAverage(firstWordParts[i], secondWordParts[j])) {
                        averageSemanticSimilarity = getNormalizedSimilarityMatrixAverage(firstWordParts[i], secondWordParts[j]);
                        usedIndexesOfSecondWordParts[j] = i;
                        jot = j;
                    } else if (averageSemanticSimilarity == 0) {
                        averageSemanticSimilarity = 0;
                        usedIndexesOfSecondWordParts[j] = i;
                        jot = j;
                    }
                }
            }
//            result += (((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength)) * (((double) averageSemanticSimilarity / ((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) * 2.0));
            System.out.println(result);
            System.out.println((((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength)) * averageSemanticSimilarity);
            result += ((((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength)) * averageSemanticSimilarity);
            System.out.println("U " + i + "toj iteraciji result ima vrednost: " + result);

        }
        return result;
    }

    private static ArrayList<String> capitalWord(String[] stringArray, StringBuilder abbreviationBuilder) {
        ArrayList<String> newArray = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].length() == 1) {
                if (Character.isUpperCase(stringArray[i].charAt(0))) {
                    builder.append(stringArray[i]);
                    abbreviationBuilder.append(stringArray[i].toLowerCase());
                }
            } else {
                if (Character.isUpperCase(stringArray[i].charAt(0))) {
//                    if (i > 0) {
//                        if (Character.isUpperCase(stringArray[i - 1].charAt(0))) {
//                            builder.append(stringArray[i]);
//                        }
//                    }
                    abbreviationBuilder.append(stringArray[i].toLowerCase().toCharArray()[0]);
                    newArray.add(stringArray[i].toLowerCase());
                } else {
                    newArray.add(stringArray[i].toLowerCase());
                }
            }
        }
        if (!builder.toString().equals("") && builder.toString() != null) {
            newArray.add(builder.toString());
        }
        return newArray;
    }

    private static double getNormalizedSimilarityMatrixAverage(String word1, String word2) {
        double score = 0.0;
        double sum = 0.0;
        String[] a = new String[]{word1};
        String[] b = new String[]{word2};


        WS4JConfiguration.getInstance().setMFS(true);
        for (RelatednessCalculator rc : rcs) {
            double[][] s = rc.getNormalizedSimilarityMatrix(a, b);
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (i == j) {
                        sum += s[i][j];
                    }
                }
            }
        }
        score = sum / rcs.length;
        return score;
    }

    public static double semanticSimilaritiesWrapper(String firstWord, String secondWord) {

        double result = 0;
        int firstWordLength = firstWord.length();
        int secondWordLength = secondWord.length();
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
        } else {
            firstWordLength = firstWord.length() - firstWordParts.length - 1;
        }
        if (secondWordParts.length < 2) {
            secondWordParts = secondWord.split(""
                    + "(?=[A-Z])");
        } else {
            secondWordLength = secondWord.length() - secondWordParts.length - 1;
        }

        String firstWordAbbreviation;
        String secondWordAbbreviation;
        StringBuilder firstWordAbbreviationB = new StringBuilder();
        StringBuilder secondWordAbbreviationB = new StringBuilder();



        ArrayList<String> firstWordPartsArray = capitalWord(firstWordParts, firstWordAbbreviationB);
        ArrayList<String> secondWordPartsArray = capitalWord(secondWordParts, secondWordAbbreviationB);

        firstWordAbbreviation = firstWordAbbreviationB.toString();
        secondWordAbbreviation = secondWordAbbreviationB.toString();

        firstWordParts = firstWordPartsArray.toArray(new String[firstWordPartsArray.size()]);
        secondWordParts = secondWordPartsArray.toArray(new String[secondWordPartsArray.size()]);

        //TODO - but still works. :)
        if (firstWordAbbreviation.length() > 2) {
            for (String part : secondWordParts) {
                if (part.contains(firstWordAbbreviation)) {
                    return Double.MAX_VALUE;
                }
            }
        } else if (secondWordAbbreviation.length() > 2) {
            for (String part : firstWordParts) {
                if (part.contains(secondWordAbbreviation)) {
                    return Double.MAX_VALUE;
                }
            }
        }

        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            double averageSemanticSimilarity = 0;
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {
                if (i <= usedIndexesOfSecondWordParts.length - 1 && usedIndexesOfSecondWordParts[i] == -1) {
                    boolean mapped = false;
                    for (int k : usedIndexesOfSecondWordParts) {
                        if (k == j) {
                            mapped = true;
                        }
                    }
                    if (!mapped) {
                        if (averageSemanticSimilarity <= getNormalizedSimilarityMatrix(firstWordParts[i], secondWordParts[j], false)) {
                            averageSemanticSimilarity = getNormalizedSimilarityMatrix(firstWordParts[i], secondWordParts[j], true);
                            usedIndexesOfSecondWordParts[i] = j;
                            jot = j;
                        } else if (averageSemanticSimilarity == 0) {
                            averageSemanticSimilarity = 0;
                            usedIndexesOfSecondWordParts[i] = j;
                            jot = j;
                        }
                    }
                }
            }
            result += ((((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength)) * averageSemanticSimilarity);
            System.out.println("U " + i + "toj iteraciji result ima vrednost: " + result);

        }
        return result;
    }

    /*
     * Returns an array of similarities on all measurments
     */
    private static double getNormalizedSimilarityMatrix(String word1, String word2, boolean print) {
        double score = 0.0;
        double sum = 0.0;
        String[] a = new String[]{word1};
        String[] b = new String[]{word2};


        WS4JConfiguration.getInstance().setMFS(true);
        for (RelatednessCalculator rc : rcs) {
            double[][] s = rc.getNormalizedSimilarityMatrix(a, b);
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (i == j) {
                        if (print == true) {
                            System.out.println("Normalized similarity between " + word1 + " and " + word2 + " :" + rc.getClass().getName() + "\t" + s[i][j]);
                        }
                        sum += s[i][j];
                    }
                }
            }
        }
        score = sum / rcs.length;
        return score;
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
//        long t0 = System.currentTimeMillis();
//        calculateSimilarities("swim", "drown");
        semanticSimilaritiesWrapper("priceType", "priceSpecification");
//        long t1 = System.currentTimeMillis();
//        System.out.println("Done in " + (t1 - t0) + " msec.");
//        System.out.println("Max: " + Double.MAX_VALUE);

//        Resnik r = new Resnik(db);
//        System.out.println("Relatedness of words: " + r.calcRelatednessOfWords("price", "value"));
//        String[] a = new String[]{"price", "water", "buy", "big", "quickly", "on"};
//        String[] b = new String[]{"value", "wet", "purchase", "large", "speedly", "upon"};
//        r.getSimilarityMatrix(a, b);
//        double[][] result = r.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        HirstStOnge hso = new HirstStOnge(db);
//        result = hso.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        LeacockChodorow lc = new LeacockChodorow(db);
//        result = lc.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        Lesk l = new Lesk(db);
//        result = l.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        WuPalmer wp = new WuPalmer(db);
//        result = wp.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        JiangConrath jc = new JiangConrath(db);
//        result = jc.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        Lin lin = new Lin(db);
//        result = lin.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//        Path p = new Path(db);
//        result = p.getNormalizedSimilarityMatrix(a, b);
//
//        for (int i = 0; i < result.length; i++) {
//            for (int j = 0; j < result.length; j++) {
//                if (i == j) {
//                    System.out.println(result[i][j]);
//                }
//            }
//        }
//
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(getNormalizedSimilarityMatrixAverage("price","value"));
    }
}