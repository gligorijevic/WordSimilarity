/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.webevo.logic.utility.Utility;
import org.webevo.logic.utility.parse.N3toCSVParser;
import org.webevo.logic.utility.sample.SampleN3Data;
import org.webevo.logic.utility.schema.mapper.N3DataToSchemaMapper;
import org.webevo.logic.utility.schema.mapper.SerializeSchemaData;
import org.webevo.logic.similarity.semanticbased.WordSimilarityWS4J;
import org.webevo.logic.utility.schema.mapper.WordSimilarityThreshold;

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
            try {
                ////            // TODO code application logic here
                //            boolean check = N3toCSVParser.checkMultiplePredicts("E:\\Semantic web baze\\DBPedia datasets\\homepages_en_sample.ttl");
                //            System.out.println(check);
                //            N3toCSVParser.parse("E:\\Semantic web baze\\DBPedia datasets\\homepages_en_sample.ttl",check);
                ////            SampleN3Data.createSample("E:\\Semantic web baze\\DBPedia datasets\\homepages_en.ttl",2000);
//                WordSimilarityThreshold.calculateThreshold("E:\\Semantic web baze\\DBPedia datasets\\homepages_en_sample.csv");
                N3DataToSchemaMapper.mapAndSaveCSVFile("E:\\Semantic web baze\\DBPedia datasets\\homepages_en_sample.csv", 0.023867366324182057);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if (Utility.isNumeric("4.4")) {
//            System.out.println("true");
//        } else {
//            System.out.println("false");
//        }


    }
}
