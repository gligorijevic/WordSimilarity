/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Djordje
 */
public class N3toCSVParser {

    public static void parse(String n3filepath, boolean hasMultiplePredicts) throws FileNotFoundException, IOException {
        File n3file = new File(n3filepath);

        BufferedReader bufRdr = new BufferedReader(new FileReader(n3file));
        PrintWriter out = null;
        System.out.println(n3file.getPath());
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(n3file.getPath().substring(0, n3file.getPath().length() - 4) + ".csv")));
        String line = null;
        ArrayList<String> predicts = new ArrayList<>();
        HashMap<Integer, String> objects = new HashMap<>();

        String currentSubject = null;
        String lastSubject = null;
        if (!hasMultiplePredicts) {
            while ((line = bufRdr.readLine()) != null) {
                String[] elementsInLine = line.split(">");
                if (elementsInLine[0].trim().startsWith("<")) {
                    String substringedPredict = elementsInLine[1].trim().substring(1);
                    if (!predicts.contains(substringedPredict)) {
                        predicts.add(substringedPredict);
                    }

                    // erase http://dbpedia.org/resource/
                    String substringedSubject = elementsInLine[0].trim().substring(1);
                    lastSubject = substringedSubject;

                    if (!substringedSubject.equals(currentSubject)) {
                        String lastObjectName = currentSubject;
                        String coolSubject = null;
                        if (lastObjectName != null) {
                            coolSubject = lastObjectName.replaceFirst("http://dbpedia.org/resource/", "");
                        }
                        currentSubject = substringedSubject;

                        if (predicts.size() > 0 && lastObjectName != null && coolSubject != null) {
                            out.write(coolSubject);
                            out.write(",");
                            for (int i = 0; i < predicts.size(); i++) {
                                if (objects.containsKey(i)) {
                                    out.write(parseObjectValue(objects.get(i)));
                                } else {
                                    out.write("");
                                }

                                if (i != predicts.size() - 1) {
                                    out.write(",");
                                }
                            }
                        }
                        if (lastObjectName != null) {
                            out.write("\r\n");
                        }
                        out.flush();
                        objects.clear();

                        if (!objects.containsKey(0)) {
                            objects.put(predicts.indexOf(substringedPredict), elementsInLine[2].trim() + ">");
                        }
                    } else {
                        objects.put(predicts.indexOf(substringedPredict), elementsInLine[2].trim()); //parse String
                    }
                }
            }
            String lastObjectName = currentSubject;
            String coolSubject = null;
            if (lastObjectName != null) {
                coolSubject = lastObjectName.replaceFirst("http://dbpedia.org/resource/", "");
            }
            currentSubject = lastSubject;

            if (predicts.size() > 0 && lastObjectName != null && coolSubject != null) {
                out.write(coolSubject);
                out.write(",");
                for (int i = 0; i < predicts.size(); i++) {
                    if (objects.containsKey(i)) {
                        out.write(parseObjectValue(objects.get(i)));
                    } else {
                        out.write("");
                    }

                    if (i != predicts.size() - 1) {
                        out.write(",");
                    }
                }
            }
            if (lastObjectName != null) {
                out.write("\r\n");
            }

            out.flush();
            bufRdr.close();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < predicts.size(); i++) {
                builder.append(predicts.get(i));
                if (i != predicts.size() - 1) {
                    builder.append(",");
                }
            }
            System.out.println(builder.toString());
            out.write("subject," + builder.toString());
        } else {
            int noOfPredictsPerObject = 0;
            int objectIndex = 0;

            while ((line = bufRdr.readLine()) != null) {
                String[] elementsInLine = line.split(">");
                if (elementsInLine[0].trim().startsWith("<")) {
                    String substringedPredict = elementsInLine[1].trim().substring(1);
                    if ((!predicts.contains(substringedPredict) && elementsInLine[0].trim().substring(1).equals(currentSubject)) || (!predicts.contains(substringedPredict) && currentSubject == null) || (!predicts.contains(substringedPredict) && !elementsInLine[0].trim().substring(1).equals(currentSubject) && currentSubject != null)) {
                        predicts.add(substringedPredict + (objectIndex) + (++noOfPredictsPerObject));
                    }

                    // erase http://dbpedia.org/resource/
                    String substringedSubject = elementsInLine[0].trim().substring(1);
                    lastSubject = substringedSubject;
                    if (!substringedSubject.equals(currentSubject)) {
                        String lastObjectName = currentSubject;
                        String coolSubject = null;
                        if (lastObjectName != null) {
                            coolSubject = lastObjectName.replaceFirst("http://dbpedia.org/resource/", "");
                        }
                        currentSubject = substringedSubject;

                        if (predicts.size() > 0 && lastObjectName != null && coolSubject != null) {
                            out.write(coolSubject);
                            out.write(",");
                            for (int i = 0; i < predicts.size(); i++) {
                                if (objects.containsKey(i)) {
                                    out.write(parseObjectValue(objects.get(i)));
                                } else {
                                    out.write("");
                                }

                                if (i != predicts.size() - 1) {
                                    out.write(",");
                                }
                            }

                        }
                        if (lastObjectName != null) {
                            out.write("\r\n");
                        }

                        out.flush();
                        objects.clear();

                        if (!objects.containsKey(0)) {
                            objects.put(predicts.indexOf(substringedPredict + (objectIndex) + (noOfPredictsPerObject)), elementsInLine[2].trim());
                        }
                        noOfPredictsPerObject = objectIndex;
                        objectIndex++;
                    } else {
                        objects.put(predicts.indexOf(substringedPredict + (objectIndex) + (noOfPredictsPerObject)), elementsInLine[2].trim()); //parse String
                    }

                }
            }
            String lastObjectName = currentSubject;
            String coolSubject = null;
            if (lastObjectName != null) {
                coolSubject = lastObjectName.replaceFirst("http://dbpedia.org/resource/", "");
            }
            currentSubject = lastSubject;

            if (predicts.size() > 0 && lastObjectName != null && coolSubject != null) {
                out.write(coolSubject);
                out.write(",");
                for (int i = 0; i < predicts.size(); i++) {
                    if (objects.containsKey(i)) {
                        out.write(parseObjectValue(objects.get(i)));
                    } else {
                        out.write("");
                    }

                    if (i != predicts.size() - 1) {
                        out.write(",");
                    }
                }
                noOfPredictsPerObject = 0;
                objectIndex++;
            }
            if (lastObjectName != null) {
                out.write("\r\n");
            }

            out.flush();
            bufRdr.close();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < predicts.size(); i++) {
                builder.append(predicts.get(i));
                if (i != predicts.size() - 1) {
                    builder.append(",");
                }
            }
            System.out.println(builder.toString());
            out.write("subject," + builder.toString());
        }
        out.flush();
        out.close();
    }

    private static String parseObjectValue(String name) {
        if (name.length() <= 2) {
            return "";
        } else if (name.startsWith("<") || name.startsWith("\"")) {
//            if (name.length() <= 2) {
//                return "";
//            }
            //return name.substring(1, name.length() - 1);
            String a = name.substring(1, name.length());
            if (a.contains("\"")) {
//                System.out.println(a);
                if (a.indexOf("\"") == 0) {
                    return "";
                } else if (a.contains(">")) {
                    a = a.substring(0, a.length() - 1);
                } else {
                    return a.substring(0, a.indexOf("\""));
                }
            } else {
                if (a.contains(">")) {
                    a = a.substring(0, a.length() - 1);
                }
                return a;
            }
        }
        return name;
    }

    public static boolean checkMultiplePredicts(String n3filepath) throws FileNotFoundException, IOException {
        File n3file = new File(n3filepath);
        BufferedReader bufRdr = new BufferedReader(new FileReader(n3file));
        ArrayList<String> predicts = new ArrayList<>();
        String line;
        String currentSubject = null;
        while ((line = bufRdr.readLine()) != null) {
            String[] elementsInLine = line.split(">");
            if (elementsInLine[0].trim().startsWith("<")) {

                String substringedSubject = elementsInLine[0].trim().substring(1);

                if (!substringedSubject.equals(currentSubject)) {
                    String check = currentSubject;
                    String lastObjectName = currentSubject;
                    currentSubject = substringedSubject;
                    if (check != null) {
                        return false;
                    }
                }

                String substringedPredict = elementsInLine[1].trim().substring(1);
                if (!predicts.contains(substringedPredict)) {
                    predicts.add(substringedPredict);
                } else {
                    return true;
                }

                // erase http://dbpedia.org/resource/

            }
        }
        bufRdr.close();
        return false;
    }
}
