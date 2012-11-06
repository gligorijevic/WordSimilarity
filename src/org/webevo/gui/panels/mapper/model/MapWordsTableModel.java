/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.panels.mapper.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Djordje
 */
public class MapWordsTableModel extends AbstractTableModel {

    private ArrayList<String> DBPediaWords = new ArrayList<>();
    private ArrayList<String> guessedWords = new ArrayList<>();

    public MapWordsTableModel(ArrayList<String> DBPediaWords, ArrayList<String> guessedWords) {
        this.DBPediaWords = DBPediaWords;
        this.guessedWords = guessedWords;
    }

    public MapWordsTableModel() {
    }

    
    
    @Override
    public int getRowCount() {
        return DBPediaWords.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Dataset Word";
            case 1:
                return "Guesses Schema Word";
            default:
                return "ne";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String DBPediaWord = DBPediaWords.get(rowIndex);
        String guessedWord = guessedWords.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return DBPediaWord;
            case 1:
                return guessedWord;
            default:
                return "No Word Found";
        }
    }

}
