/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper.similarity;

import org.webevo.domain.mapper.wordsimilarity.BinaryCharNode;

/**
 *
 * @author Djordje
 */
public class WordAsBinaryTreeSimilarity implements WordAsTreeSimilarity {

    char[] samoglasnici = new char[]{'a', 'e', 'i', 'o', 'u'};
    double similarity = 0;
    String word1 = null;
    String word2 = null;
    BinaryCharNode tree1 = new BinaryCharNode(' ');
    BinaryCharNode dummy1 = tree1;
    BinaryCharNode tree2 = new BinaryCharNode(' ');
    BinaryCharNode dummy2 = tree2;
    char[] word1InChars = word1.toCharArray();
    char[] word2InChars = word2.toCharArray();

    public WordAsBinaryTreeSimilarity(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    /*
     * i = 0 & j = word1InChars.length-1
     */
    public void createWordTree(int i, int j, BinaryCharNode dummy, char[] charArray) {
        int boundary = 0;
        if (charArray.length % 2 == 0) {
            boundary = charArray.length / 2;
        } else{
            boundary = (charArray.length + 1) / 2;
        }

        dummy.setLeftChild(new BinaryCharNode(charArray[i]));
        dummy.setRightChild(new BinaryCharNode(charArray[j]));

        createLeftSubTree(i, boundary, dummy, charArray);
        createRightSubTree(j, boundary, dummy, charArray);
    }

    public void createLeftSubTree(int i, int boundary, BinaryCharNode dummy, char[] charArray) {
        if (i > 0 && boundary < charArray.length - 1 && i <= boundary) {
            if (charArray[i] == samoglasnici[0] || charArray[i] == samoglasnici[1] || charArray[i] == samoglasnici[2] || charArray[i] == samoglasnici[3] || charArray[i] == samoglasnici[4]) {
                dummy.setLeftChild(new BinaryCharNode(charArray[i]));
                createLeftSubTree(i++, boundary, dummy.getLeftChild(), charArray);
            } else {
                dummy.setRightChild(new BinaryCharNode(charArray[i]));
                createLeftSubTree(i++, boundary, dummy.getRightChild(), charArray);
            }
        }
    }

    public void createRightSubTree(int j, int boundary, BinaryCharNode dummy, char[] charArray) {
        if (j < charArray.length && boundary > j) {
            if (charArray[j] == samoglasnici[0] || charArray[j] == samoglasnici[1] || charArray[j] == samoglasnici[2] || charArray[j] == samoglasnici[3] || charArray[j] == samoglasnici[4]) {
                dummy.setLeftChild(new BinaryCharNode(charArray[j]));
                createRightSubTree(j--, boundary, dummy.getLeftChild(), charArray);
            } else {
                dummy.setRightChild(new BinaryCharNode(charArray[j]));
                createRightSubTree(j--, boundary, dummy.getRightChild(), charArray);
            }
        }
    }

    @Override
    public double calcSimilarSubtrees(BinaryCharNode cn) {
        return 0;
    }

    @Override
    public double calcFirstLevelSimilarity(BinaryCharNode cn) {
        return 0;
    }

    @Override
    public double calcTreeStructureSimilarity(BinaryCharNode cn) {
        return 0;
    }

    @Override
    public int countNodesInTree(BinaryCharNode cn) {
        return 0;
    }

    @Override
    public int countTreeDepth(BinaryCharNode cn) {
        return 0;
    }

    @Override
    public void killStructure() {
        tree1 = null;
        tree2 = null;
        dummy1 = null;
        dummy2 = null;
    }

    @Override
    public BinaryCharNode createWordTree() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
