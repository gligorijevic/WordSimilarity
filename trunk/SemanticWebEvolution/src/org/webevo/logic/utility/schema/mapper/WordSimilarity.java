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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class WordSimilarity {
    
        private static ILexicalDatabase db = new NictWordNet();
        private static RelatednessCalculator[] rcs = {
                        new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db), 
                        new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
                        };
        
        
        private static List<Double> calculateSimilarities( String word1, String word2 ) {
                List<Double> results = new ArrayList<>();
                WS4JConfiguration.getInstance().setMFS(true);
                for ( RelatednessCalculator rc : rcs ) {
                        double s = rc.calcRelatednessOfWords(word1, word2);
                        System.out.println( rc.getClass().getName()+"\t"+s );
                        results.add(s);
                }
                return results;
        }
        
        
        public static void main(String[] args) {
                long t0 = System.currentTimeMillis();
                calculateSimilarities( "Metric","Asymetric" );
                long t1 = System.currentTimeMillis();
                System.out.println( "Done in "+(t1-t0)+" msec." );
                System.out.println("Max: " + Double.MAX_VALUE);
        }
}
