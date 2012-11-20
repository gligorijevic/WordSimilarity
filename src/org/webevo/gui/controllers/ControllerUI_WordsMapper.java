/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.webevo.gui.panels.mapper.PanelMapWordsTable;
import org.webevo.gui.panels.mapper.model.MapWordsTableModel;
import org.webevo.logic.utility.Utility;
import org.webevo.logic.utility.filefilter.SemanticDataFileFilter;
import org.webevo.logic.utility.schema.mapper.MappedWords;
import org.webevo.logic.utility.schema.mapper.N3DataToSchemaMapper;

/**
 *
 * @author Djordje
 */
public class ControllerUI_WordsMapper {

    private PanelMapWordsTable panelMapWordsTable;
    private ArrayList<String> schemaWords = new ArrayList<>();
    private ArrayList<String> DBPediaWords = new ArrayList<>();
    private ArrayList<String> guessedWords = new ArrayList<>();
    private String dbPediaDatasetPath;
    private int columnIndex;
    private double threshold;
    private String fileName;
    private double labelThreshold;
    private double propertiesDataThreshold;
    private double classDataThreshold;

    public ControllerUI_WordsMapper() {
    }

    public void getData() {
        MappedWords mw;
        getTresholdValue();
        getColumnIndexValue();
        try {

            mw = N3DataToSchemaMapper.mapWord(dbPediaDatasetPath, isPredicate(), columnIndex, isUrl(), threshold);
            schemaWords = mw.getSchemaWords();
            DBPediaWords = mw.getDBPediaWords();
            guessedWords = mw.getGuessedWords();

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(panelMapWordsTable, ex);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(panelMapWordsTable, ex);
        }

        clearSelections();
    }

    public void getFileChooser() throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select .csv file to map");
//        chooser.addChoosableFileFilter(new SemanticDataFileFilter());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter csvfilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
        chooser.setFileFilter(csvfilter);


        int returnVal = chooser.showOpenDialog(panelMapWordsTable);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            setDbPediaDatasetPath(chooser.getSelectedFile().getAbsolutePath());
            panelMapWordsTable.getTxtFieldFile().setText(chooser.getSelectedFile().getAbsolutePath());
        }
        fileName = chooser.getSelectedFile().getAbsolutePath();
    }

    public void setValuesToGUI() {
        setTableModel();
    }

    private boolean isPredicate() {
        ButtonGroup gbr = panelMapWordsTable.getBtnGroupMappingType();
        for (Enumeration e = gbr.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel() == gbr.getSelection()) {
                String selectedAvatar = b.getText();
                if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbObject().getText())) {
                    return false;
                } else if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbPredicate().getText())) {
                    return true;
                } else if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbSubject().getText())) {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isSubject() {
        ButtonGroup gbr = panelMapWordsTable.getBtnGroupMappingType();
        for (Enumeration e = gbr.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel() == gbr.getSelection()) {
                String selectedAvatar = b.getText();
                if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbObject().getText())) {
                    return false;
                } else if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbPredicate().getText())) {
                    return false;
                } else if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbSubject().getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isUrl() {
        ButtonGroup gbr = panelMapWordsTable.getBtnGroupIsUrl();
        for (Enumeration e = gbr.getElements(); e.hasMoreElements();) {
            JRadioButton b = (JRadioButton) e.nextElement();
            if (b.getModel() == gbr.getSelection()) {
                String selectedAvatar = b.getText();
                if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbIsUrl().getText())) {
                    return true;
                } else if (selectedAvatar != null && selectedAvatar.equalsIgnoreCase(panelMapWordsTable.getRbIsNotUrl().getText())) {
                    return false;
                }
            }
        }
        return false;
    }

    private void getTresholdValue() {
        if (!Utility.isNumeric(panelMapWordsTable.getTxtFieldTreshold().getText().trim())) {
            JOptionPane.showMessageDialog(panelMapWordsTable, "Threshold value must be a number!");
        } else if (panelMapWordsTable.getTxtFieldTreshold().getText().trim().equals("")) {
            JOptionPane.showMessageDialog(panelMapWordsTable, "Please enter threshold value.");
        } else {
            threshold = Double.parseDouble(panelMapWordsTable.getTxtFieldTreshold().getText().trim());
        }
    }

    private void getColumnIndexValue() {
        if (!isPredicate() && !isSubject()) {
            if (!Utility.isNumeric(panelMapWordsTable.getTxtFiledIndex().getText().trim())) {
                JOptionPane.showMessageDialog(panelMapWordsTable, "Column index value must be a number!");
            } else if (panelMapWordsTable.getTxtFiledIndex().getText().trim().equals("")) {
                JOptionPane.showMessageDialog(panelMapWordsTable, "Please column index value diminished by 1.");
            } else {
                columnIndex = Integer.parseInt(panelMapWordsTable.getTxtFiledIndex().getText().trim());
            }
        } else if (isSubject()) {
            columnIndex = 0;
        } else {
            columnIndex = Integer.parseInt(panelMapWordsTable.getTxtFiledIndex().getText().trim());
        }
    }

    private void clearSelections() {
        panelMapWordsTable.getTxtFieldTreshold().setText("");
        panelMapWordsTable.getTxtFiledIndex().setText("");
        panelMapWordsTable.getBtnGroupMappingType().clearSelection();
        panelMapWordsTable.getBtnGroupMappingType().clearSelection();
    }

    //TODO DEBUG
    public void checkMappingType() {
        if (!panelMapWordsTable.getRbObject().isSelected()) {
            panelMapWordsTable.getTxtFiledIndex().setEditable(false);
            if (panelMapWordsTable.getRbPredicate().isSelected()) {
                setThresholdField(propertiesDataThreshold);
            } else if (panelMapWordsTable.getRbSubject().isSelected()) {
                setThresholdField(classDataThreshold);
            }
        } else {
            if (panelMapWordsTable.getRbObject().isSelected()) {
                setThresholdField(classDataThreshold);
            }
            panelMapWordsTable.getTxtFiledIndex().setEditable(true);
        }


    }

    //TODO DEBUG
    private void setThresholdField(double value) {
        if (labelThreshold != 0 || classDataThreshold != 0 && propertiesDataThreshold != 0) {
            panelMapWordsTable.getTxtFieldTreshold().setText(String.valueOf(value));
        }
    }

    public void getPrecalculatedThresholdValues() throws FileNotFoundException, IOException {
        String file = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length() - 4);
        BufferedReader br = new BufferedReader(new FileReader("threshold for " + file + ".txt"));

        String line = br.readLine();
        while (line != null && !line.equals("")) {
            labelThreshold = Double.parseDouble(line.substring(line.lastIndexOf(":") + 1, line.length() - 1).trim());
            line = br.readLine();
            classDataThreshold = Double.parseDouble(line.substring(line.lastIndexOf(":") + 1, line.length() - 1).trim());
            line = br.readLine();
            propertiesDataThreshold = Double.parseDouble(line.substring(line.lastIndexOf(":") + 1, line.length() - 1).trim());
            line = br.readLine();
        }
    }

    /**
     * @return the viewWOrdsTable
     */
    public PanelMapWordsTable getViewWOrdsTable() {
        return panelMapWordsTable;
    }

    /**
     * @param viewWOrdsTable the viewWOrdsTable to set
     */
    public void setViewWOrdsTable(PanelMapWordsTable viewWOrdsTable) {
        this.panelMapWordsTable = viewWOrdsTable;
        setTableModel();
    }

    /**
     * @return the schemaWords
     */
    public ArrayList<String> getSchemaWords() {
        return schemaWords;
    }

    /**
     * @param schemaWords the schemaWords to set
     */
    public void setSchemaWords(ArrayList<String> schemaWords) {
        this.schemaWords = schemaWords;
    }

    /**
     * @return the DBPediaWords
     */
    public ArrayList<String> getDBPediaWords() {
        return DBPediaWords;
    }

    /**
     * @param DBPediaWords the DBPediaWords to set
     */
    public void setDBPediaWords(ArrayList<String> DBPediaWords) {
        this.DBPediaWords = DBPediaWords;
    }

    /**
     * @return the GuessedWords
     */
    public ArrayList<String> getGuessedWords() {
        return guessedWords;
    }

    /**
     * @param GuessedWords the GuessedWords to set
     */
    public void setGuessedWords(ArrayList<String> GuessedWords) {
        this.guessedWords = GuessedWords;
    }

    private void setTableModel() {
        //TODO implement!
        JTable table = panelMapWordsTable.getTblWordData();
        MapWordsTableModel model = new MapWordsTableModel(DBPediaWords, guessedWords);
        table.setModel(model);
    }

    /**
     * @return the dbPediaDatasetPath
     */
    public String getDbPediaDatasetPath() {
        return dbPediaDatasetPath;
    }

    /**
     * @param dbPediaDatasetPath the dbPediaDatasetPath to set
     */
    public void setDbPediaDatasetPath(String dbPediaDatasetPath) {
        this.dbPediaDatasetPath = dbPediaDatasetPath;
    }
}
