/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.domain.mapper.wordsimilarity;

/**
 *
 * @author Djordje
 */
public abstract class CharNode {
    protected char value;
    protected int nodeLevel;

    /**
     * @return the value
     */
    public char getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(char value) {
        this.value = value;
    }

    /**
     * @return the nodeLevel
     */
    public int getNodeLevel() {
        return nodeLevel;
    }

    /**
     * @param nodeLevel the nodeLevel to set
     */
    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
    
    
    
}
