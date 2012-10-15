/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webevo.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import webevo.logic.utility.parse.N3toCSVParser;

/**
 *
 * @author Djordje
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            N3toCSVParser.parse("E:\\Semantic web baze\\YAGO2\\yago2core_20120109.n3",false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
