/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import org.apache.commons.codec.language.Nysiis;

/**
 *
 * @author Djordje Gligorijevic
 */
public class NYsiisSimilarity {
 
    public static void calculateNysiis(String word1, String word2){
        Nysiis nysiis = new Nysiis();
        nysiis.nysiis(word2);
    }
}
