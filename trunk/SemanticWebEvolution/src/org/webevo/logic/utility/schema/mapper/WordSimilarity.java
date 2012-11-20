/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

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
public class WordSimilarity {

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
            results.add(s);
        }
        double result = 0;
        for (Double sim : results) {
            result = result + sim;
        }
        result = result / results.size();
        return result;
    }

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
        calculateSimilarities("A", "A");
        long t1 = System.currentTimeMillis();
        System.out.println("Done in " + (t1 - t0) + " msec.");
        System.out.println("Max: " + Double.MAX_VALUE);
    }
}
