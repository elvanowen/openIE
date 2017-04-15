/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui.viewer;

import id.ac.itb.nlp.SentenceTokenizer;
import id.ac.itb.openie.evaluation.ExtractionsEvaluationResult;
import id.ac.itb.openie.relation.Relations;
import id.ac.itb.openie.utils.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author elvanowen
 */
public class EvaluationViewer extends javax.swing.JFrame {

    private ExtractionsEvaluationResult extractionsEvaluationResult;

    /**
     * Creates new form EvaluationViewer
     */
    public EvaluationViewer() {
        initComponents();
    }

    public EvaluationViewer(ExtractionsEvaluationResult extractionsEvaluationResult) {
        this.extractionsEvaluationResult = extractionsEvaluationResult;
        initComponents();
    }

    private void refreshEvaluationSentencesList() {
        File currentlySelectedEvaluationFile = extractionsEvaluationResult.getExtractionsEvaluationModel().getDocuments().get(evaluationResultFilesjList.getSelectedIndex());
        SentenceTokenizer sentenceTokenizer = new SentenceTokenizer();
        ArrayList<String> sentences = sentenceTokenizer.tokenizeSentence(Utilities.getFileContent(currentlySelectedEvaluationFile));

        evaluationResultSentencesjList.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() { return sentences.size(); }
            public String getElementAt(int i) { return (i+1) + ". " + sentences.get(i); }
        });
    }

    private void refreshEvaluationRelationsList() {
        if (evaluationResultFilesjList.getSelectedIndex() >= 0) {
            File selectedEvaluationFile = extractionsEvaluationResult.getExtractionsEvaluationModel().getDocuments().get(evaluationResultFilesjList.getSelectedIndex());

            evaluationResultExtractedRelationsjList.setModel(new javax.swing.AbstractListModel<String>() {
                Relations evaluationRelations = extractionsEvaluationResult.getExtractionsEvaluationModel().getExtractedRelationsByFilename().get(selectedEvaluationFile.getName());

                public int getSize() { return evaluationRelations.getRelations().size(); }
                public String getElementAt(int i) {
                    return String.format("%s. %s(%s, %s)\n", (i+1), evaluationRelations.getRelations().get(i).getRelationTriple().getMiddle(), evaluationRelations.getRelations().get(i).getRelationTriple().getLeft(), evaluationRelations.getRelations().get(i).getRelationTriple().getRight());
                }
            });

            evaluationResultLabelledRelationsjList.setModel(new javax.swing.AbstractListModel<String>() {
                Relations evaluationRelations = extractionsEvaluationResult.getExtractionsEvaluationModel().getLabelledRelationsByFilename().get(selectedEvaluationFile.getName());

                public int getSize() {
                    if (evaluationRelations == null) {
                        return 0;
                    } else {
                        return evaluationRelations.getRelations().size();
                    }
                }
                public String getElementAt(int i) {
                    return String.format("%s. %s(%s, %s)\n", (i+1), evaluationRelations.getRelations().get(i).getRelationTriple().getMiddle(), evaluationRelations.getRelations().get(i).getRelationTriple().getLeft(), evaluationRelations.getRelations().get(i).getRelationTriple().getRight());
                }
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        evaluationResultFilesjList = new javax.swing.JList<>();
        evaluationResultFilesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        evaluationResultSentencesjList = new javax.swing.JList<>();
        evaluationResultSentencesLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        evaluationResultExtractedRelationsjList = new javax.swing.JList<>();
        evaluationResultExtractedRelationsLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        evaluationResultLabelledRelationsjList = new javax.swing.JList<>();
        evaluationResultLabelledRelationsLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        EvaluationResultTotalExtractedSentencesjLabel = new javax.swing.JLabel();
        EvaluationResultTotalExtractedRelationsjLabel = new javax.swing.JLabel();
        EvaluationResultTotalLabelledSentencesjLabel = new javax.swing.JLabel();
        EvaluationResultTotalLabelledRelationsjLabel = new javax.swing.JLabel();
        EvaluationResultTotalExtractedSentencesValuejLabel = new javax.swing.JLabel();
        EvaluationResultTotalExtractedRelationsValuejLabel = new javax.swing.JLabel();
        EvaluationResultTotalLabelledRelationsValuejLabel = new javax.swing.JLabel();
        EvaluationResultTotalLabelledSentencesValuejLabel = new javax.swing.JLabel();
        EvaluationResultTotalCorrectRelationsjLabel = new javax.swing.JLabel();
        EvaluationResultTotalCorrectRelationsValuejLabel = new javax.swing.JLabel();
        EvaluationResultRecalljLabel = new javax.swing.JLabel();
        EvaluationResultPrecisionjLabel = new javax.swing.JLabel();
        EvaluationResultFMeasurejLabel = new javax.swing.JLabel();
        EvaluationResultTotalRecallFormulajLabel = new javax.swing.JLabel();
        EvaluationResultPrecisionFormulajLabel = new javax.swing.JLabel();
        EvaluationResultFMeasureFormulajLabel = new javax.swing.JLabel();
        EvaluationResultRecallFormulaValuejLabel = new javax.swing.JLabel();
        EvaluationResultPrecisionFormulaValuejLabel = new javax.swing.JLabel();
        EvaluationResultFMeasureFormulaValuejLabel = new javax.swing.JLabel();
        EvaluationResultRecallValuejLabel = new javax.swing.JLabel();
        EvaluationResultPrecisionValuejLabel = new javax.swing.JLabel();
        EvaluationResultFMeasureValuejLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        System.out.println(extractionsEvaluationResult.getExtractionsEvaluationModel().getDocuments());

        evaluationResultFilesjList.setModel(new javax.swing.AbstractListModel<String>() {
            ArrayList<File> files = extractionsEvaluationResult.getExtractionsEvaluationModel().getDocuments();
            public int getSize() { return files.size(); }
            public String getElementAt(int i) { return (i+1) + ". " + files.get(i).getName(); }
        });
        jScrollPane1.setViewportView(evaluationResultFilesjList);

        evaluationResultFilesLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        evaluationResultFilesLabel.setText("Files:");

        jScrollPane2.setViewportView(evaluationResultSentencesjList);

        evaluationResultFilesjList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshEvaluationSentencesList();
                refreshEvaluationRelationsList();
            }
        });

        evaluationResultSentencesLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        evaluationResultSentencesLabel.setText("Sentences:");

        jScrollPane3.setViewportView(evaluationResultExtractedRelationsjList);

        evaluationResultExtractedRelationsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        evaluationResultExtractedRelationsLabel.setText("Extracted Relations:");

        jScrollPane4.setViewportView(evaluationResultLabelledRelationsjList);

        evaluationResultLabelledRelationsLabel.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        evaluationResultLabelledRelationsLabel.setText("Labelled Relations:");

        EvaluationResultTotalExtractedSentencesjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultTotalExtractedSentencesjLabel.setText("Total Extracted Sentences");

        EvaluationResultTotalExtractedRelationsjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultTotalExtractedRelationsjLabel.setText("Total Extracted Relations (#Extractions)");

        EvaluationResultTotalLabelledSentencesjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultTotalLabelledSentencesjLabel.setText("Total Labelled Sentences");

        EvaluationResultTotalLabelledRelationsjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultTotalLabelledRelationsjLabel.setText("Total Labelled Relations (#Relations)");

        EvaluationResultTotalExtractedSentencesValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalExtractedSentencesValuejLabel.setText(": 100/200");

        EvaluationResultTotalExtractedRelationsValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalExtractedRelationsValuejLabel.setText(": 90");

        EvaluationResultTotalLabelledRelationsValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalLabelledRelationsValuejLabel.setText(": 50");

        EvaluationResultTotalLabelledSentencesValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalLabelledSentencesValuejLabel.setText(": 100/200");

        EvaluationResultTotalCorrectRelationsjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultTotalCorrectRelationsjLabel.setText("Total Correct Relations (#Correct)");

        EvaluationResultTotalCorrectRelationsValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalCorrectRelationsValuejLabel.setText(": 25");

        EvaluationResultRecalljLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultRecalljLabel.setText("Recall (R)");

        EvaluationResultPrecisionjLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultPrecisionjLabel.setText("Precision (P)");

        EvaluationResultFMeasurejLabel.setFont(new java.awt.Font("Lucida Grande", 3, 11)); // NOI18N
        EvaluationResultFMeasurejLabel.setText("F-measure (F)");

        EvaluationResultTotalRecallFormulajLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultTotalRecallFormulajLabel.setText("= #Correct/#Extractions");

        EvaluationResultPrecisionFormulajLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultPrecisionFormulajLabel.setText("= #Correct/#Relations");

        EvaluationResultFMeasureFormulajLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultFMeasureFormulajLabel.setText("= 2PR/(P + R)");

        EvaluationResultRecallFormulaValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultRecallFormulaValuejLabel.setText("= 25 / 90");

        EvaluationResultPrecisionFormulaValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultPrecisionFormulaValuejLabel.setText("= 25 / 50");

        EvaluationResultFMeasureFormulaValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultFMeasureFormulaValuejLabel.setText("= 0.27/(0.27 + 0.50)");

        EvaluationResultRecallValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultRecallValuejLabel.setText("= 0.27");

        EvaluationResultPrecisionValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultPrecisionValuejLabel.setText("= 0.5");

        EvaluationResultFMeasureValuejLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        EvaluationResultFMeasureValuejLabel.setText("= 0.357");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(evaluationResultExtractedRelationsLabel)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(evaluationResultLabelledRelationsLabel)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane4)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(evaluationResultFilesLabel)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(evaluationResultSentencesLabel)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EvaluationResultTotalCorrectRelationsjLabel)
                        .addGap(37, 37, 37)
                        .addComponent(EvaluationResultTotalCorrectRelationsValuejLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EvaluationResultRecalljLabel)
                                .addGap(143, 143, 143)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(EvaluationResultPrecisionValuejLabel)
                                        .addComponent(EvaluationResultPrecisionFormulaValuejLabel)
                                        .addComponent(EvaluationResultPrecisionFormulajLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(EvaluationResultPrecisionjLabel)
                                        .addGap(59, 59, 59))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EvaluationResultTotalExtractedRelationsjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EvaluationResultTotalExtractedRelationsValuejLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EvaluationResultTotalExtractedSentencesjLabel)
                                .addGap(85, 85, 85)
                                .addComponent(EvaluationResultTotalExtractedSentencesValuejLabel))
                            .addComponent(EvaluationResultTotalRecallFormulajLabel)
                            .addComponent(EvaluationResultRecallFormulaValuejLabel)
                            .addComponent(EvaluationResultRecallValuejLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EvaluationResultFMeasurejLabel)
                                    .addComponent(EvaluationResultFMeasureFormulaValuejLabel)
                                    .addComponent(EvaluationResultFMeasureFormulajLabel)
                                    .addComponent(EvaluationResultFMeasureValuejLabel))
                                .addGap(166, 166, 166))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EvaluationResultTotalLabelledSentencesjLabel)
                                    .addComponent(EvaluationResultTotalLabelledRelationsjLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(EvaluationResultTotalLabelledSentencesValuejLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(EvaluationResultTotalLabelledRelationsValuejLabel)))
                                .addGap(78, 78, 78))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(evaluationResultSentencesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(evaluationResultFilesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(evaluationResultExtractedRelationsLabel)
                    .addComponent(evaluationResultLabelledRelationsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EvaluationResultTotalExtractedSentencesjLabel)
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EvaluationResultTotalExtractedRelationsjLabel)
                            .addComponent(EvaluationResultTotalExtractedRelationsValuejLabel)))
                    .addComponent(EvaluationResultTotalExtractedSentencesValuejLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(EvaluationResultTotalLabelledSentencesjLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(EvaluationResultTotalLabelledRelationsjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(EvaluationResultTotalLabelledRelationsValuejLabel)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(EvaluationResultTotalLabelledSentencesValuejLabel)
                            .addGap(21, 21, 21))))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EvaluationResultTotalCorrectRelationsjLabel)
                    .addComponent(EvaluationResultTotalCorrectRelationsValuejLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(EvaluationResultPrecisionjLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultPrecisionFormulajLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultPrecisionFormulaValuejLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultPrecisionValuejLabel))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(EvaluationResultRecalljLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultTotalRecallFormulajLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultRecallFormulaValuejLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(EvaluationResultRecallValuejLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EvaluationResultFMeasurejLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EvaluationResultFMeasureFormulajLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EvaluationResultFMeasureFormulaValuejLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EvaluationResultFMeasureValuejLabel)))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
        setTitle("Evaluation Viewer");
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EvaluationViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EvaluationViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EvaluationViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EvaluationViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EvaluationViewer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EvaluationResultFMeasureFormulaValuejLabel;
    private javax.swing.JLabel EvaluationResultFMeasureFormulajLabel;
    private javax.swing.JLabel EvaluationResultFMeasureValuejLabel;
    private javax.swing.JLabel EvaluationResultFMeasurejLabel;
    private javax.swing.JLabel EvaluationResultPrecisionFormulaValuejLabel;
    private javax.swing.JLabel EvaluationResultPrecisionFormulajLabel;
    private javax.swing.JLabel EvaluationResultPrecisionValuejLabel;
    private javax.swing.JLabel EvaluationResultPrecisionjLabel;
    private javax.swing.JLabel EvaluationResultRecallFormulaValuejLabel;
    private javax.swing.JLabel EvaluationResultRecallValuejLabel;
    private javax.swing.JLabel EvaluationResultRecalljLabel;
    private javax.swing.JLabel EvaluationResultTotalCorrectRelationsValuejLabel;
    private javax.swing.JLabel EvaluationResultTotalCorrectRelationsjLabel;
    private javax.swing.JLabel EvaluationResultTotalExtractedRelationsValuejLabel;
    private javax.swing.JLabel EvaluationResultTotalExtractedRelationsjLabel;
    private javax.swing.JLabel EvaluationResultTotalExtractedSentencesValuejLabel;
    private javax.swing.JLabel EvaluationResultTotalExtractedSentencesjLabel;
    private javax.swing.JLabel EvaluationResultTotalLabelledRelationsValuejLabel;
    private javax.swing.JLabel EvaluationResultTotalLabelledRelationsjLabel;
    private javax.swing.JLabel EvaluationResultTotalLabelledSentencesValuejLabel;
    private javax.swing.JLabel EvaluationResultTotalLabelledSentencesjLabel;
    private javax.swing.JLabel EvaluationResultTotalRecallFormulajLabel;
    private javax.swing.JLabel evaluationResultExtractedRelationsLabel;
    private javax.swing.JList<String> evaluationResultExtractedRelationsjList;
    private javax.swing.JLabel evaluationResultFilesLabel;
    private javax.swing.JList<String> evaluationResultFilesjList;
    private javax.swing.JLabel evaluationResultLabelledRelationsLabel;
    private javax.swing.JList<String> evaluationResultLabelledRelationsjList;
    private javax.swing.JLabel evaluationResultSentencesLabel;
    private javax.swing.JList<String> evaluationResultSentencesjList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
