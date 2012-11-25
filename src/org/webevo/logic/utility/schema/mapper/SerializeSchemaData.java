/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.schema.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Djordje
 */
public class SerializeSchemaData {
    
    private static File SchemaDataset;
    private static List<String> schemaData = new ArrayList<>();
    
    public static void serializeSchema(String filePath) throws FileNotFoundException, IOException {
        BufferedReader bufRdrSchema;
        SchemaDataset = new File(filePath);
        bufRdrSchema = new BufferedReader(new FileReader(SchemaDataset));
        String line = null;
        
        while ((line = bufRdrSchema.readLine()) != null) {
            schemaData.add(line.split(",")[0]);
        }
        bufRdrSchema.close();
        
        File serializedFile = new File(filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length() - 4) + ".out");
        ObjectOutput ObjOut = new ObjectOutputStream(new FileOutputStream(serializedFile));
        ObjOut.writeObject(schemaData);
        ObjOut.close();
    }
    
    public static void serializeDBPediaProperties(String filePath) throws FileNotFoundException, IOException {
        BufferedReader bufRdrSchema;
        SchemaDataset = new File(filePath);
        bufRdrSchema = new BufferedReader(new FileReader(SchemaDataset));
        String line = null;
        
        while ((line = bufRdrSchema.readLine()) != null) {
            schemaData.add(line);
        }
        bufRdrSchema.close();
        
        File serializedFile = new File(filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length() - 4) + ".out");
        ObjectOutput ObjOut = new ObjectOutputStream(new FileOutputStream(serializedFile));
        ObjOut.writeObject(schemaData);
        ObjOut.close();
    }
    
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        serializeDBPediaProperties("E:\\Semantic web baze\\DBpedia datasets properties names.txt");
//    }
}
