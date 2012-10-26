/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.domain.mapper.wordsimilarity;

import java.util.ArrayList;

/**
 *
 * @author Djordje
 */
public class MultipleChildrenCharNode extends CharNode{
    
    private ArrayList<MultipleChildrenCharNode> children;

    public MultipleChildrenCharNode(char value) {
        this.value = value;
    }
    
    public MultipleChildrenCharNode(char value, ArrayList<MultipleChildrenCharNode> children) {
        this.value = value;
        this.children = children;
    }
    
    public MultipleChildrenCharNode(char value, ArrayList<MultipleChildrenCharNode> children, int nodeLevel) {
        this.value = value;
        this.children = children;
        this.nodeLevel = nodeLevel;
    }

    /**
     * @return the children
     */
    public ArrayList<MultipleChildrenCharNode> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<MultipleChildrenCharNode> children) {
        this.children = children;
    }
    
    
    
    
}
