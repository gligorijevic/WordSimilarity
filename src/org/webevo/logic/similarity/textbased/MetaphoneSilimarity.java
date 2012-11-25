/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import org.apache.commons.codec.language.Metaphone;

/**
 *
 * @author Djordje Gligorijevic
 */
public class MetaphoneSilimarity {
    
    public static boolean calculateMetaphone(String word1, String word2) {
        Metaphone metaphone = new Metaphone();
        return metaphone.isMetaphoneEqual(word1, word2);
    }
    
    public static void main(String[] args) {
        System.out.println(calculateMetaphone("wgs84_pos#lat", "latitude"));
    }
}
