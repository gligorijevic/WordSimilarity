/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper.similarity;

import org.webevo.domain.mapper.wordsimilarity.BinaryCharNode;
import org.webevo.domain.mapper.wordsimilarity.CharNode;

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
    private BinaryCharNode dummy1 = tree1;
    BinaryCharNode tree2 = new BinaryCharNode(' ');
    private BinaryCharNode dummy2 = tree2;
    char[] word1InChars = word1.toCharArray();
    char[] word2InChars = word2.toCharArray();

    public WordAsBinaryTreeSimilarity(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    @Override
    public void createWordTree(int i, int j, CharNode dummy, char[] charArray) {
        int nodeLevel = 1;
        int boundary = 0;
        if (charArray.length % 2 == 0) {
            boundary = charArray.length / 2;
        } else {
            boundary = (charArray.length - 1) / 2;
        }

        ((BinaryCharNode)dummy).setLeftChild(new BinaryCharNode(charArray[i], nodeLevel));
        ((BinaryCharNode)dummy).setRightChild(new BinaryCharNode(charArray[j], nodeLevel));

        createLeftSubTree(i, boundary, ((BinaryCharNode)dummy), charArray, nodeLevel++);
        createRightSubTree(j, boundary, ((BinaryCharNode)dummy), charArray, nodeLevel++);
    }

    public void createLeftSubTree(int i, int boundary, BinaryCharNode dummy, char[] charArray, int nodeLevel) {
        if (i > 0 && boundary < charArray.length - 1 && i <= boundary) {
            if (charArray[i] == samoglasnici[0] || charArray[i] == samoglasnici[1] || charArray[i] == samoglasnici[2] || charArray[i] == samoglasnici[3] || charArray[i] == samoglasnici[4]) {
                dummy.setLeftChild(new BinaryCharNode(charArray[i], nodeLevel));
                createLeftSubTree(i++, boundary, dummy.getLeftChild(), charArray, nodeLevel++);
            } else {
                dummy.setRightChild(new BinaryCharNode(charArray[i], nodeLevel));
                createRightSubTree(i++, boundary, dummy.getRightChild(), charArray, nodeLevel++);
            }
        }
    }

    public void createRightSubTree(int j, int boundary, BinaryCharNode dummy, char[] charArray, int nodeLevel) {
        if (j < charArray.length && boundary > j) {
            if (charArray[j] == samoglasnici[0] || charArray[j] == samoglasnici[1] || charArray[j] == samoglasnici[2] || charArray[j] == samoglasnici[3] || charArray[j] == samoglasnici[4]) {
                dummy.setLeftChild(new BinaryCharNode(charArray[j], nodeLevel));
                createLeftSubTree(j--, boundary, dummy.getLeftChild(), charArray, nodeLevel++);
            } else {
                dummy.setRightChild(new BinaryCharNode(charArray[j], nodeLevel));
                createRightSubTree(j--, boundary, dummy.getRightChild(), charArray, nodeLevel++);
            }
        }
    }

    @Override
    public double calcSimilarSubtrees(CharNode bcn1, CharNode bcn2) {
        return 0;
    }

    @Override
    public double calcFirstLevelSimilarity(CharNode bcn1, CharNode bcn2) {
        return 0;
    }

    @Override
    public double calcTreeStructureSimilarity(CharNode bcn1, CharNode bcn2) {
        
        return 0;
    }
    
    public boolean isIdentical(BinaryCharNode bcn1, BinaryCharNode bcn2) {
        if(bcn1 == null && bcn2 == null){
            return true;
        }
        if(bcn1!=null && bcn2!=null){
            if(bcn1.getValue()==bcn2.getValue()){
                return isIdentical(bcn1.getLeftChild(), bcn2.getLeftChild()) && isIdentical(bcn1.getRightChild(), bcn2.getRightChild());
            }
        }
        return false;
    }

    @Override
    public int countNodesInTree(CharNode cn) {
        if (cn == null) {
            return 0;
        }
        return 1 + countNodesInTree(((BinaryCharNode)cn).getLeftChild()) + countNodesInTree(((BinaryCharNode)cn).getRightChild());
    }

    @Override
    public int countTreeDepth(CharNode cn) {
        if (cn == null) {
            return 0;
        }
        return 1 + Math.max(countTreeDepth(((BinaryCharNode)cn).getRightChild()), countTreeDepth(((BinaryCharNode)cn).getLeftChild()));
    }

    @Override
    public void killStructure() {
        tree1 = null;
        tree2 = null;
        dummy1 = null;
        dummy2 = null;
    }

    /**
     * @return the dummy1
     */
    public BinaryCharNode getDummy1() {
        return dummy1;
    }

    /**
     * @return the dummy2
     */
    public BinaryCharNode getDummy2() {
        return dummy2;
    }
}
