/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;

/**
 *
 * @author Djordje Gligorijevic
 */
public class SoundexImplementation {

    public static void main(String[] args) throws EncoderException {
        System.out.println(soundexImplementation("Soundex"));
        System.out.println(soundexImplementation("Example"));
        System.out.println(soundexImplementation("Sownteks"));
        System.out.println(soundexImplementation("Ekzampul"));
        System.out.println(soundexApache("lat", "latitude"));
    }

    private static String getCode(char c) {
        switch (c) {
            case 'B':
            case 'F':
            case 'P':
            case 'V':
                return "1";
            case 'C':
            case 'G':
            case 'J':
            case 'K':
            case 'Q':
            case 'S':
            case 'X':
            case 'Z':
                return "2";
            case 'D':
            case 'T':
                return "3";
            case 'L':
                return "4";
            case 'M':
            case 'N':
                return "5";
            case 'R':
                return "6";
            default:
                return "";
        }
    }

    public static String soundexImplementation(String s) {
        String code, previous, soundex;
        code = s.toUpperCase().charAt(0) + "";
        previous = "7";
        for (int i = 1; i < s.length(); i++) {
            String current = getCode(s.toUpperCase().charAt(i));
            if (current.length() > 0 && !current.equals(previous)) {
                code = code + current;
            }
            previous = current;
        }
        soundex = (code + "0000").substring(0, 4);
        return soundex;
    }

    public static int soundexApache(String word1, String word2) throws EncoderException {
        Soundex soundex = new Soundex();
        System.out.println("soundex apache: " + soundex.soundex(word1));
        System.out.println("soundex apache: " + soundex.soundex(word2));

        return soundex.difference(word1, word2);
    }
}
