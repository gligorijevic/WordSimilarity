/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.webevo.gui.panels.mapper.PanelCalculateThreshold;
import org.webevo.logic.utility.filefilter.SemanticDataFileFilter;
import org.webevo.logic.utility.schema.mapper.WordSimilarityThreshold;

/**
 *
 * @author Djordje
 */
public class ControllerUI_ThresholdCalculator {

    private PanelCalculateThreshold panelCalculateThreshold;
    private String dbPediaDatasetPath;

    public void getFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select .csv file to map");
        chooser.addChoosableFileFilter(new SemanticDataFileFilter());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter csvfilter = new FileNameExtensionFilter("csv files (*.csv)", "csv");
        chooser.setFileFilter(csvfilter);


        int returnVal = chooser.showOpenDialog(getPanelCalculateThreshold());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            setDbPediaDatasetPath(chooser.getSelectedFile().getAbsolutePath());
            getPanelCalculateThreshold().getTxtFieldDatasetFile().setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void calculateThreshold() throws FileNotFoundException, IOException, ClassNotFoundException {
        WordSimilarityThreshold.calculateThreshold(dbPediaDatasetPath);
    }
    
    /**
     * @return the panelCalculateThreshold
     */
    public PanelCalculateThreshold getPanelCalculateThreshold() {
        return panelCalculateThreshold;
    }

    /**
     * @param panelCalculateThreshold the panelCalculateThreshold to set
     */
    public void setPanelCalculateThreshold(PanelCalculateThreshold panelCalculateThreshold) {
        this.panelCalculateThreshold = panelCalculateThreshold;
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
