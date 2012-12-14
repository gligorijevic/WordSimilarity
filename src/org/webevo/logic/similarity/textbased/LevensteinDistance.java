/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import java.util.ArrayList;

/**
 *
 * @author Djordje Gligorijevic
 *
 */
public class LevensteinDistance {

    /*
     * This function starts out with several checks in an attempt to save time. 1.
     * The shorter string is always used as the "right-hand" string (as the size of
     * the array is based on its length). 2. If the left string is empty, the length
     * of the right is returned. 3. If the right string is empty, the length of the
     * left is returned. 4. If the strings are equal, a zero-distance is returned.
     * 5. If the left string is contained within the right string, the difference in
     * length is returned. 6. If the right string is contained within the left
     * string, the difference in length is returned. If none of the above conditions
     * were met, the Levenshtein algorithm is used.
     */
    @Deprecated
    public static double levensteinPHPImplementation(String word1, String word2) {
        int nLeftLength = word1.length();
        int nRightLength = word2.length();
        String sLeft;
        String sRight;
        int[] nsDistance;
        int nDiagonal;
        if (nLeftLength >= nRightLength) {
            sLeft = word1;
            sRight = word2;
        } else {
            sLeft = word2;
            sRight = word1;
            nLeftLength += nRightLength;  //  arithmetic swap of two values
            nRightLength = nLeftLength - nRightLength;
            nLeftLength -= nRightLength;
        }

        if (nLeftLength == 0) {
            return nRightLength;
        } else if (nRightLength == 0) {
            return nLeftLength;
        } else if (sLeft.equals(sRight)) {
            return 0;
        } else if ((nLeftLength < nRightLength) && sRight.contains(sLeft) != false) {
            return nRightLength - nLeftLength;
        } else if ((nRightLength < nLeftLength) && (sLeft.contains(sRight) != false)) {
            return nLeftLength - nRightLength;
        } else {
            nsDistance = new int[nRightLength + 1];//$nsDistance = range(0, $nRightLength);
            for (int nLeftPos = 1; nLeftPos < nLeftLength; nLeftPos++) {
                char cLeft = sLeft.toCharArray()[nLeftPos - 1];
                nDiagonal = nLeftPos - 1;
                nsDistance[0] = nLeftPos;
                for (int nRightPos = 1; nRightPos < nRightLength; nRightPos++) {
                    char cRight = sRight.toCharArray()[nRightPos - 1];
                    int nCost = (cRight == cLeft) ? 0 : 1;
                    int nNewDiagonal = nsDistance[nRightPos];
                    nsDistance[nRightPos] = Math.min(Math.min(nsDistance[nRightPos] + 1,
                            nsDistance[nRightPos - 1] + 1), nDiagonal + nCost);
                    nDiagonal = nNewDiagonal;
                }
            }
            return nsDistance[nRightLength];
        }
    }

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= str2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1]
                        + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
            }
        }

        return distance[str1.length()][str2.length()];
    }

    public static int computeDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }
    /*
     * first word must not have prefixes!
     */

    public static double levensteinDistanceWrapper(String firstWord, String secondWord) {

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
                    return 0;
                }
            }
        } else if (secondWordAbbreviation.length() > 2) {
            for (String part : firstWordParts) {
                if (part.contains(secondWordAbbreviation)) {
                    return 0;
                }
            }
        }



        int secondWordIndex = -1;
        int[] usedIndexesOfSecondWordParts = new int[secondWordParts.length];
        for (int i = 0; i < usedIndexesOfSecondWordParts.length; i++) {
            usedIndexesOfSecondWordParts[i] = -1;
        }

        for (int i = 0; i < firstWordParts.length; i++) {
            int calcualtedDistance = 100;
            int jot = 0;
            for (int j = 0; j < secondWordParts.length; j++) {
                boolean mapped = false;
                for (int k : usedIndexesOfSecondWordParts) {
                    if (k == j) {
                        mapped = true;
                    }
                }
                if (!mapped) {
                    if (i <= usedIndexesOfSecondWordParts.length - 1) {
//                    if (-1 == usedIndexesOfSecondWordParts[i] || i == usedIndexesOfSecondWordParts[i]) {

                        if (calcualtedDistance >= computeLevenshteinDistance(firstWordParts[i], secondWordParts[j]) && computeLevenshteinDistance(firstWordParts[i], secondWordParts[j]) < firstWordParts[i].length() + secondWordParts[j].length()) {
                            calcualtedDistance = computeLevenshteinDistance(firstWordParts[i], secondWordParts[j]);
                            usedIndexesOfSecondWordParts[j] = i;
                            jot = j;
                        }
//                    }
                    } else {
                        calcualtedDistance = 0;
                    }
                }
            }
            double d = ((double) firstWordParts[i].length() + (double) secondWordParts[usedIndexesOfSecondWordParts[jot]].length()) / ((double) firstWordLength + (double) secondWordLength);
            result += (double) calcualtedDistance * d;
//            System.out.println("U " + i + "toj iteraciji levenstein distance ima vrednost: " + result);
        }
        //proveri da li ima viska reci!
        long resultLong = Math.round(result);
        return resultLong;
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

    public static void main(String[] args) {
//        System.out.println(LevensteinDistance.levensteinPHPImplementation("rec1", "rec2"));
//        System.out.println(LevensteinDistance.computeLevenshteinDistance("webPage", "homePage"));
        System.out.println(LevensteinDistance.computeDistance("eligibleQuantity", "hasEligibleQuantity"));
        System.out.println(levensteinDistanceWrapper("eligibleQuantity", "hasStupidEligibleQuantity"));
    }
}
