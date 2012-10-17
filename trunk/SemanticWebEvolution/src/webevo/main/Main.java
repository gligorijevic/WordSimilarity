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
            boolean check = N3toCSVParser.checkMultiplePredicts("E:\\Semantic web baze\\DBPedia datasets\\disambiguations_en2.ttl");
            System.out.println(check);
            N3toCSVParser.parse("E:\\Semantic web baze\\DBPedia datasets\\disambiguations_en2.ttl",check);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
