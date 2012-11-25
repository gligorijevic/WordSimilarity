/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import java.util.ArrayList;

/**
 *
 * @author Djordje Gligorijevic
 */
public class SequenceSimilarity {

    public static double sequenceSimilarity(String firstWord, String secondWord) {

        double result = 0;

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
        } else if (secondWordParts.length < 2) {
            secondWordParts = secondWord.split(""
                    + "(?=[A-Z])");
        }

        int longestSequence = 0;
        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {

                if (-1 == usedIndexesOfSecondWordParts[i]) {

                    if (longestSequence <= score(firstWordParts[i], secondWordParts[j])) {
                        longestSequence = score(firstWordParts[i], secondWordParts[j]);
                        usedIndexesOfSecondWordParts[j] = i;
                        jot = j;
                    }

                }

            }
            double a = (firstWordParts[i].length() + secondWordParts[usedIndexesOfSecondWordParts[jot]].length());
//            System.out.println(a);
            double b = (firstWord.length() + secondWord.length());
//            System.out.println(b);
            double c = (longestSequence);
//            System.out.println(c);
            double d = (firstWordParts[i].length() + secondWordParts[usedIndexesOfSecondWordParts[jot]].length());
//            System.out.println(d);
//            System.out.println(a / b * c / d * 2);
            result += a / b * c / d * 2;
//            result += ((firstWordParts[i].length() + secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / (firstWord.length() + secondWord.length())) * ((longestSequence / (firstWordParts[i].length() + secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) * 2));
            System.out.println("U "+i+"toj iteraciji result ima vrednost: "+result);


        }






        return result;

    }

    @Deprecated
    private static ArrayList<Character> upperCaseLetters(String word) {
        char[] charArray;
        ArrayList<Character> resultArray = new ArrayList<>();
        charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i])) {
                resultArray.add(charArray[i]);
            }
        }
        return resultArray;
    }

    private static double singleWordSequenceSimilarity(char[] firstWord, char[] secondWord, int i, int j, StringBuilder firstBuilder, StringBuilder secondBuilder) {
        if (firstWord.length > i && secondWord.length > j) {
        }

        return 0;

    }

    /*
     * Implementation:
     * The longest common subsequence (or LCS)
     */
    public static int score(String gene1, String gene2) {
        char[] a = gene1.toCharArray();//assign it directly
        char[] b = gene2.toCharArray();
        return score(a, b, 0, 0);
    }

    private static int score(char[] a, char[] b, int i, int j) {
        //check for end using length, java doesn't use that nullbyte-stuff for it
        //this caused the failure
        if (i == a.length || j == b.length) {
            return 0;
        } else if (a[i] == b[j]) {
            return 1 + score(a, b, i + 1, j + 1);
        } else {
            return max(score(a, b, i + 1, j), score(a, b, i, j + 1));
        }
    }

    private static int max(int a, int b) {
        if (a < b) {
            return b;
        }
        return a;
    }
    /*
     * This is not a particularly fast algorithm, 
     * but it gets the job done eventually. 
     * The speed is a result of many recursive function calls.
     */

    public static String lcsRecursion(String a, String b) {
        int aLen = a.length();
        int bLen = b.length();
        if (aLen == 0 || bLen == 0) {
            return "";
        } else if (a.charAt(aLen - 1) == b.charAt(bLen - 1)) {
            return lcs(a.substring(0, aLen - 1), b.substring(0, bLen - 1))
                    + a.charAt(aLen - 1);
        } else {
            String x = lcs(a, b.substring(0, bLen - 1));
            String y = lcs(a.substring(0, aLen - 1), b);
            return (x.length() > y.length()) ? x : y;
        }
    }

    /*
     * The longest common subsequence (or LCS) of groups A and B is
     * the longest group of elements from A and B that are common 
     * between the two groups and in the same order in each group. 
     * For example, the sequences "1234" and "1224533324" have an LCS of "1234"
     */
    public static String lcs(String a, String b) {
        int[][] lengths = new int[a.length() + 1][b.length() + 1];

        // row 0 and column 0 are initialized to 0 already

        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j < b.length(); j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    lengths[i + 1][j + 1] = lengths[i][j] + 1;
                } else {
                    lengths[i + 1][j + 1] =
                            Math.max(lengths[i + 1][j], lengths[i][j + 1]);
                }
            }
        }

        // read the substring out from the matrix
        StringBuilder sb = new StringBuilder();
        for (int x = a.length(), y = b.length();
                x != 0 && y != 0;) {
            if (lengths[x][y] == lengths[x - 1][y]) {
                x--;
            } else if (lengths[x][y] == lengths[x][y - 1]) {
                y--;
            } else {
                assert a.charAt(x - 1) == b.charAt(y - 1);
                sb.append(a.charAt(x - 1));
                x--;
                y--;
            }
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        String a = "home_page";
        String b = "web_page";

//        System.out.println(score(a, b));
//        System.out.println(lcs(a, b));
        System.out.println(sequenceSimilarity(a, b));

    }
}
