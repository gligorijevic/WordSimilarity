/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.utility.filefilter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Djordje
 */
public class SemanticDataFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (!file.isDirectory()) {
            return file.getAbsolutePath().endsWith(".csv");
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Comma Separated Files selector.";
    }
}
