/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.webevo.exception.NoFileSelectedException;
import org.webevo.gui.panels.sampler.PanelSampler;
import org.webevo.logic.utility.sample.SampleN3Data;

/**
 *
 * @author Djordje Gligorijevic
 */
public class ControllerUI_Sampler {

    private PanelSampler panelSampler;
    private String datasetFilepath;
    private int sampleSize;

    public ControllerUI_Sampler() {
    }

    public ControllerUI_Sampler(PanelSampler panelSampler) {
        this.panelSampler = panelSampler;
    }

    public void getFileChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select .csv file to map");
//        chooser.addChoosableFileFilter(new SemanticDataFileFilter());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter csvfilter = new FileNameExtensionFilter("n3 files (*.n3)", "ttl", "n3", "nt");
        chooser.setFileFilter(csvfilter);


        int returnVal = chooser.showOpenDialog(getPanelSampler());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            setDatasetFilepath(chooser.getSelectedFile().getAbsolutePath());
            getPanelSampler().getTxtFieldDatasetFilePath().setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public void createSample() throws NoFileSelectedException, FileNotFoundException, IOException {
        getSampleSizeFromGUI();
        if (datasetFilepath.equals("") || datasetFilepath == null) {
            throw new NoFileSelectedException("No file has been selected");
        }

        SampleN3Data.createSample(datasetFilepath, sampleSize);

    }

    private void getSampleSizeFromGUI() {
        sampleSize = Integer.parseInt(getPanelSampler().getTxtFieldSampleSize().getText().trim());
    }

    /**
     * @return the panelSampler
     */
    public PanelSampler getPanelSampler() {
        return panelSampler;
    }

    /**
     * @param panelSampler the panelSampler to set
     */
    public void setPanelSampler(PanelSampler panelSampler) {
        this.panelSampler = panelSampler;
    }

    /**
     * @return the datasetFilepath
     */
    public String getDatasetFilepath() {
        return datasetFilepath;
    }

    /**
     * @param datasetFilepath the datasetFilepath to set
     */
    public void setDatasetFilepath(String datasetFilepath) {
        this.datasetFilepath = datasetFilepath;
    }

    /**
     * @return the sampleSize
     */
    public int getSampleSize() {
        return sampleSize;
    }

    /**
     * @param sampleSize the sampleSize to set
     */
    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }
}
