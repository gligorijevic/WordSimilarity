/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper.similarity;

import org.webevo.domain.mapper.wordsimilarity.BinaryCharNode;
import org.webevo.domain.mapper.wordsimilarity.CharNode;
import org.webevo.domain.mapper.wordsimilarity.MultipleChildrenCharNode;

/**
 *
 * @author Djordje
 */
public class WordAsMultipleChildrenTreeSimilarity implements WordAsTreeSimilarity{

    char[] samoglasnici = new char[]{'a', 'e', 'i', 'o', 'u'};
    double similarity = 0;
    String word1 = null;
    String word2 = null;
    private MultipleChildrenCharNode tree1 = new MultipleChildrenCharNode(' ');
    private MultipleChildrenCharNode dummy1 = tree1;
    private MultipleChildrenCharNode tree2 = new MultipleChildrenCharNode(' ');
    private MultipleChildrenCharNode dummy2 = tree2;
    char[] word1InChars = word1.toCharArray();
    char[] word2InChars = word2.toCharArray();

    public WordAsMultipleChildrenTreeSimilarity(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    
    
    @Override
    public void killStructure() {
        tree1 = null;
        tree2 = null;
        dummy1 = null;
        dummy2 = null;
    }

    @Override
    public void createWordTree(int i, int j, CharNode dummy, char[] charArray) {
        word1InChars = word1.toCharArray();
//        int
//        for (char c : charArray) {
//            if
//        }
//        Character.isUpperCase( chr)
    }

    @Override
    public double calcSimilarSubtrees(CharNode cn1, CharNode cn2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double calcFirstLevelSimilarity(CharNode cn1, CharNode cn2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double calcTreeStructureSimilarity(CharNode cn1, CharNode cn2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countNodesInTree(CharNode cn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int countTreeDepth(CharNode cn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
