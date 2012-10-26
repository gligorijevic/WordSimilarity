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
public interface WordAsTreeSimilarity {
    
    public void createWordTree(int i, int j, CharNode dummy, char[] charArray);
    
    public double calcSimilarSubtrees(CharNode cn1, CharNode cn2);
    
    public double calcFirstLevelSimilarity(CharNode cn1, CharNode cn2);
    
    public double calcTreeStructureSimilarity(CharNode cn1, CharNode cn2);
    
    public int countNodesInTree(CharNode cn);
    
    public int countTreeDepth(CharNode cn);
    
    public void killStructure();
}
