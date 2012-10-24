/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.domain.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class WordTypeOntology {

    public static char adjective = 'a';
    public static char adverb = 'r';
    public static char noun = 'n';
    public static char verb = 'v';
    
    private List<Character> wordTypes = new ArrayList({'a','r','n','v'}) ;
    public List<Character> getWordTypes() {
        return wordTypes;
    }
    
    
}
