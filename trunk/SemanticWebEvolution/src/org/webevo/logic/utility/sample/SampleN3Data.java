/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author Djordje
 */
public class SampleN3Data {
    
    public static void createSample(String n3filepath, int instances) throws FileNotFoundException, IOException {
        File n3file = new File(n3filepath);
        
        BufferedReader bufRdr = new BufferedReader(new FileReader(n3file));
        PrintWriter out = null;
        System.out.println(n3file.getPath());
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(n3file.getPath().substring(0, n3file.getPath().length() - 4) + "_sample.ttl")));
        String line = null;
        int numberOfLines = 0;
        while ((line = bufRdr.readLine()) != null && numberOfLines <= instances) {
            out.write(line);
            out.write("\r\n");
            numberOfLines++;
        }
        out.flush();
        bufRdr.close();
        out.close();
        
    }
}
