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
public interface WordAsTreeSimilarity {
    
    public BinaryCharNode createWordTree();
    
    public double calcSimilarSubtrees(BinaryCharNode cn);
    
    public double calcFirstLevelSimilarity(BinaryCharNode cn);
    
    public double calcTreeStructureSimilarity(BinaryCharNode cn);
    
    public int countNodesInTree(BinaryCharNode cn);
    
    public int countTreeDepth(BinaryCharNode cn);
    
    public void killStructure();
}
