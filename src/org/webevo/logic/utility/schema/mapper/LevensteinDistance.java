/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;
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

    public static void main(String[] args) {
        System.out.println(LevensteinDistance.levensteinPHPImplementation("rec1", "rec2"));
        System.out.println(LevensteinDistance.computeLevenshteinDistance("rec1", "rec2"));
        System.out.println(LevensteinDistance.computeDistance("rec1", "rec2"));
    }
}
