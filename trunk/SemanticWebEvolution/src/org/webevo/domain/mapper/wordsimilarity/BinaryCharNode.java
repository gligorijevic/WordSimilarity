/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.domain.mapper.wordsimilarity;

/**
 *
 * @author Djordje
 */
public class BinaryCharNode extends CharNode{
    private BinaryCharNode leftChild;
    private BinaryCharNode rightChild;

    public BinaryCharNode(char value) {
        this.value = value;
    }
    
    public BinaryCharNode(char value, BinaryCharNode leftChild, BinaryCharNode rightChar) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChar;
    }

    /**
     * @return the leftChild
     */
    public BinaryCharNode getLeftChild() {
        return leftChild;
    }

    /**
     * @param leftChild the leftChild to set
     */
    public void setLeftChild(BinaryCharNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * @return the rightChar
     */
    public BinaryCharNode getRightChild() {
        return rightChild;
    }

    /**
     * @param rightChar the rightChar to set
     */
    public void setRightChild(BinaryCharNode rightChar) {
        this.rightChild = rightChar;
    }
    
    
    
}
