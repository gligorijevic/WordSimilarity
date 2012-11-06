/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.controllers;

import javax.swing.JPanel;
import org.webevo.gui.forms.MainForm;

/**
 *
 * @author Djordje
 */
public class ControllerUI_Main {

    MainForm mainForm;

    private ControllerUI_Main() {
        mainForm = new MainForm();
//        setActivePanel(new PanelWelcome());

    }

    public static ControllerUI_Main getInstance() {
        return ControllerUI_MainHolder.INSTANCE;
    }

    private static class ControllerUI_MainHolder {

        private static final ControllerUI_Main INSTANCE = new ControllerUI_Main();
    }

    public void setActivePanel(JPanel newPanel) {
        mainForm.setActivePanel(newPanel);
    }

    public void startApplication() {
        mainForm.setVisible(true);
    }

    public MainForm getMainForm() {
        return mainForm;
    }
}
