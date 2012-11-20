/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.gui.panels.mapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.webevo.gui.controllers.ControllerUI_WordsMapper;
import org.webevo.logic.utility.filefilter.SemanticDataFileFilter;

/**
 *
 * @author Djordje
 */
public class PanelMapWordsTable extends javax.swing.JPanel {

    private ControllerUI_WordsMapper controller;

    /**
     * Creates new form PanelMapWordsTable
     */
    public PanelMapWordsTable() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupMappingType = new javax.swing.ButtonGroup();
        btnGroupIsUrl = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtFieldFile = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rbSubject = new javax.swing.JRadioButton();
        rbPredicate = new javax.swing.JRadioButton();
        rbObject = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        rbIsUrl = new javax.swing.JRadioButton();
        rbIsNotUrl = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFiledIndex = new javax.swing.JTextField();
        txtFieldTreshold = new javax.swing.JTextField();
        btnLoadPrecalculatedThresholdValues = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblWordData = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Choose Dataset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtFieldFile.setText("No File Chosen");

        jLabel1.setText("Choose mapping Type: ");

        btnGroupMappingType.add(rbSubject);
        rbSubject.setText("Subject");
        rbSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSubjectActionPerformed(evt);
            }
        });

        btnGroupMappingType.add(rbPredicate);
        rbPredicate.setText("Predicate");
        rbPredicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPredicateActionPerformed(evt);
            }
        });

        btnGroupMappingType.add(rbObject);
        rbObject.setText("Object");
        rbObject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbObjectActionPerformed(evt);
            }
        });

        jButton2.setText("Load Data To Table");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Is Object URL?");

        btnGroupIsUrl.add(rbIsUrl);
        rbIsUrl.setText("Yes");

        btnGroupIsUrl.add(rbIsNotUrl);
        rbIsNotUrl.setText("No");

        jLabel3.setText("Column index in CSV file(if it's not predicate): ");

        jLabel4.setText("Word Similarity Threshold: ");

        btnLoadPrecalculatedThresholdValues.setText("Load precalculated threshold values");
        btnLoadPrecalculatedThresholdValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadPrecalculatedThresholdValuesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbSubject)
                                    .addComponent(rbIsUrl))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbPredicate)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbObject))
                                    .addComponent(rbIsNotUrl)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFieldTreshold)
                                    .addComponent(txtFiledIndex))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnLoadPrecalculatedThresholdValues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFieldFile, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txtFieldFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(btnLoadPrecalculatedThresholdValues)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rbSubject)
                    .addComponent(rbPredicate)
                    .addComponent(rbObject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFiledIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFieldTreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(rbIsUrl)
                    .addComponent(rbIsNotUrl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        tblWordData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblWordData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            controller.getFileChooser();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        controller.getData();
        controller.setValuesToGUI();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void rbSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSubjectActionPerformed
        controller.checkMappingType();
    }//GEN-LAST:event_rbSubjectActionPerformed

    private void rbPredicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPredicateActionPerformed
        controller.checkMappingType();
    }//GEN-LAST:event_rbPredicateActionPerformed

    private void rbObjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbObjectActionPerformed
        controller.checkMappingType();
    }//GEN-LAST:event_rbObjectActionPerformed

    private void btnLoadPrecalculatedThresholdValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadPrecalculatedThresholdValuesActionPerformed
        try {
            controller.getPrecalculatedThresholdValues();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "File with precalculated threshold values has not been found. Please Use calculate threshold function to create needed file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_btnLoadPrecalculatedThresholdValuesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupIsUrl;
    private javax.swing.ButtonGroup btnGroupMappingType;
    private javax.swing.JButton btnLoadPrecalculatedThresholdValues;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbIsNotUrl;
    private javax.swing.JRadioButton rbIsUrl;
    private javax.swing.JRadioButton rbObject;
    private javax.swing.JRadioButton rbPredicate;
    private javax.swing.JRadioButton rbSubject;
    private javax.swing.JTable tblWordData;
    private javax.swing.JTextField txtFieldFile;
    private javax.swing.JTextField txtFieldTreshold;
    private javax.swing.JTextField txtFiledIndex;
    // End of variables declaration//GEN-END:variables

    /**
     * @param coordinator the coordinator to set
     */
    public void setCoordinator(ControllerUI_WordsMapper coordinator) {
        this.controller = coordinator;
    }

    /**
     * @return the btnGroupIsUrl
     */
    public javax.swing.ButtonGroup getBtnGroupIsUrl() {
        return btnGroupIsUrl;
    }

    /**
     * @param btnGroupIsUrl the btnGroupIsUrl to set
     */
    public void setBtnGroupIsUrl(javax.swing.ButtonGroup btnGroupIsUrl) {
        this.btnGroupIsUrl = btnGroupIsUrl;
    }

    /**
     * @return the btnGroupMappingType
     */
    public javax.swing.ButtonGroup getBtnGroupMappingType() {
        return btnGroupMappingType;
    }

    /**
     * @param btnGroupMappingType the btnGroupMappingType to set
     */
    public void setBtnGroupMappingType(javax.swing.ButtonGroup btnGroupMappingType) {
        this.btnGroupMappingType = btnGroupMappingType;
    }

    /**
     * @return the jButton1
     */
    public javax.swing.JButton getjButton1() {
        return jButton1;
    }

    /**
     * @param jButton1 the jButton1 to set
     */
    public void setjButton1(javax.swing.JButton jButton1) {
        this.jButton1 = jButton1;
    }

    /**
     * @return the jButton2
     */
    public javax.swing.JButton getjButton2() {
        return jButton2;
    }

    /**
     * @param jButton2 the jButton2 to set
     */
    public void setjButton2(javax.swing.JButton jButton2) {
        this.jButton2 = jButton2;
    }

    /**
     * @return the jLabel1
     */
    public javax.swing.JLabel getjLabel1() {
        return jLabel1;
    }

    /**
     * @param jLabel1 the jLabel1 to set
     */
    public void setjLabel1(javax.swing.JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    /**
     * @return the jLabel2
     */
    public javax.swing.JLabel getjLabel2() {
        return jLabel2;
    }

    /**
     * @param jLabel2 the jLabel2 to set
     */
    public void setjLabel2(javax.swing.JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    /**
     * @return the jPanel1
     */
    public javax.swing.JPanel getjPanel1() {
        return jPanel1;
    }

    /**
     * @param jPanel1 the jPanel1 to set
     */
    public void setjPanel1(javax.swing.JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    /**
     * @return the jScrollPane1
     */
    public javax.swing.JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    /**
     * @param jScrollPane1 the jScrollPane1 to set
     */
    public void setjScrollPane1(javax.swing.JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    /**
     * @return the rbIsNotUrl
     */
    public javax.swing.JRadioButton getRbIsNotUrl() {
        return rbIsNotUrl;
    }

    /**
     * @param rbIsNotUrl the rbIsNotUrl to set
     */
    public void setRbIsNotUrl(javax.swing.JRadioButton rbIsNotUrl) {
        this.rbIsNotUrl = rbIsNotUrl;
    }

    /**
     * @return the rbIsUrl
     */
    public javax.swing.JRadioButton getRbIsUrl() {
        return rbIsUrl;
    }

    /**
     * @param rbIsUrl the rbIsUrl to set
     */
    public void setRbIsUrl(javax.swing.JRadioButton rbIsUrl) {
        this.rbIsUrl = rbIsUrl;
    }

    /**
     * @return the rbObject
     */
    public javax.swing.JRadioButton getRbObject() {
        return rbObject;
    }

    /**
     * @param rbObject the rbObject to set
     */
    public void setRbObject(javax.swing.JRadioButton rbObject) {
        this.rbObject = rbObject;
    }

    /**
     * @return the rbPredicate
     */
    public javax.swing.JRadioButton getRbPredicate() {
        return rbPredicate;
    }

    /**
     * @param rbPredicate the rbPredicate to set
     */
    public void setRbPredicate(javax.swing.JRadioButton rbPredicate) {
        this.rbPredicate = rbPredicate;
    }

    /**
     * @return the rbSubject
     */
    public javax.swing.JRadioButton getRbSubject() {
        return rbSubject;
    }

    /**
     * @param rbSubject the rbSubject to set
     */
    public void setRbSubject(javax.swing.JRadioButton rbSubject) {
        this.rbSubject = rbSubject;
    }

    /**
     * @return the tblWordData
     */
    public javax.swing.JTable getTblWordData() {
        return tblWordData;
    }

    /**
     * @param tblWordData the tblWordData to set
     */
    public void setTblWordData(javax.swing.JTable tblWordData) {
        this.tblWordData = tblWordData;
    }

    /**
     * @return the txtFieldTreshold
     */
    public javax.swing.JTextField getTxtFieldTreshold() {
        return txtFieldTreshold;
    }

    /**
     * @return the txtFiledIndex
     */
    public javax.swing.JTextField getTxtFiledIndex() {
        return txtFiledIndex;
    }

    /**
     * @return the txtFieldFile
     */
    public javax.swing.JTextField getTxtFieldFile() {
        return txtFieldFile;
    }

    /**
     * @param txtFieldFile the txtFieldFile to set
     */
    public void setTxtFieldFile(javax.swing.JTextField txtFieldFile) {
        this.txtFieldFile = txtFieldFile;
    }
}
