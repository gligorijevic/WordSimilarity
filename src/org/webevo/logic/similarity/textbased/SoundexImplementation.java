/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import java.util.ArrayList;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;

/**
 *
 * @author Djordje Gligorijevic
 */
public class SoundexImplementation {

    public static void main(String[] args) throws EncoderException {
//        System.out.println(soundexImplementation("Soundex"));
//        System.out.println(soundexImplementation("Example"));
//        System.out.println(soundexImplementation("Sownteks"));
//        System.out.println(soundexImplementation("Ekzampul"));
        System.out.println(soundexApache("eligibleQuantity", "hasEligibleQuantity"));
        System.out.println(soundexApacheWrapper("eligibleQuantity", "hasEligibleQuantity"));
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
//        System.out.println("soundex apache: " + soundex.soundex(word1));
//        System.out.println("soundex apache: " + soundex.soundex(word2));
        return soundex.difference(word1, word2);
    }

    public static long soundexApacheWrapper(String firstWord, String secondWord) throws EncoderException {

        double resultSimilarity = 0.0;

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
                    return 4;
                }
            }
        } else if (secondWordAbbreviation.length() > 2) {
            for (String part : firstWordParts) {
                if (part.contains(secondWordAbbreviation)) {
                    return 4;
                }
            }
        }


        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            int measuresSoundexSimilarity = 0;
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {
                if (i <= usedIndexesOfSecondWordParts.length - 1) {
                    boolean mapped = false;
                    for (int k : usedIndexesOfSecondWordParts) {
                        if (k == j) {
                            mapped = true;
                        }
                    }
                    if (!mapped) {
                        if (measuresSoundexSimilarity <= soundexApache(firstWordParts[i], secondWordParts[j])) {
                            measuresSoundexSimilarity = soundexApache(firstWordParts[i], secondWordParts[j]);
                            usedIndexesOfSecondWordParts[j] = i;
                            jot = j;
                        }

                    }
                }
            }
            double d = ((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength);
            resultSimilarity += ((double) measuresSoundexSimilarity) * d;
//            System.out.println("U " + i + "toj iteraciji result ima vrednost: " + resultSimilarity);
        }
        long result = Math.round(resultSimilarity);
        return result;

    }

    public static ArrayList<String> capitalWord(String[] stringArray, StringBuilder abbreviationBuilder) {
        ArrayList<String> newArray = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].length() == 1) {
                if (Character.isUpperCase(stringArray[i].charAt(0))) {
                    builder.append(stringArray[i]);
                    abbreviationBuilder.append(stringArray[i].toLowerCase());
                }
            } else if (stringArray[i].length() > 0) {
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
}
