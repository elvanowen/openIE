/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui.viewer;

import id.ac.itb.openie.relations.Relation;
import id.ac.itb.openie.relations.Relations;
import id.ac.itb.openie.utils.Utilities;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author elvanowen
 */
public class ExtractionViewer extends javax.swing.JFrame {

    private File extractDirectory;
    private Relations relations = new Relations();
    private ArrayList<File> files;
    private int currentRelationPointerIndex = 0;
    private ArrayList<Object> currentHighlights = new ArrayList<>();
    private File currentlySelectedFile;
    private HashMap<String, Relations> relationsByFilename = new HashMap<>();
    private Highlighter highlighter;

    /**
     * Creates new form ExtractionViewer
     */
    public ExtractionViewer() {
        initComponents();
    }

    public ExtractionViewer(File extractDirectiory) {
        this.extractDirectory = extractDirectiory;
        loadExtractions();
        initComponents();
    }

    private void loadExtractions() {
        files = Utilities.getDirectoryFiles(this.extractDirectory);

        for (File file: files) {
            relations.addRelations(new Relations(file));
        }
    }

    private void highlightRelations(Highlighter highlighter, String fileContent, Relations relations) {
        Highlighter.HighlightPainter relationPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
        Highlighter.HighlightPainter argumentPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.gray);

        for (Relation relation: relations.getRelations()) {

            String sentence = relation.getOriginSentence();
            sentence = StringUtils.strip(sentence, ".").trim();

            String rel = relation.getRelationTriple().getMiddle();
            String arg1 = relation.getRelationTriple().getLeft();
            String arg2 = relation.getRelationTriple().getRight();

//            System.out.println("Check");
//            System.out.println(fileContent);
//            System.out.println(rel);
//            System.out.println(fileContent.indexOf(rel));

            int pointerRelStart = fileContent.indexOf(sentence) + sentence.indexOf(rel);
            int pointerRelEnd = pointerRelStart + rel.length();

            try {
                if (pointerRelStart > 0) {
                    highlighter.addHighlight(pointerRelStart, pointerRelEnd, relationPainter);
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

            int pointerArg1Start = fileContent.indexOf(sentence) + sentence.indexOf(arg1);
            int pointerArg1End = pointerArg1Start + arg1.length();

            try {
                if (pointerArg1Start > 0) {
                    highlighter.addHighlight(pointerArg1Start, pointerArg1End, argumentPainter);
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

            int pointerArg2Start = fileContent.indexOf(sentence) + sentence.indexOf(arg2);
            int pointerArg2End = pointerArg2Start + arg2.length();

            try {
                if (pointerArg2Start > 0) {
                    highlighter.addHighlight(pointerArg2Start, pointerArg2End, argumentPainter);
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void highlightCurrentRelation() {
        Highlighter.HighlightPainter relationPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.orange);
        Highlighter.HighlightPainter argumentPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);

        String fileContent = StringUtils.join(Utilities.getFileContent(currentlySelectedFile), "");
        Relation relation = relationsByFilename.get(currentlySelectedFile.getName()).getRelations().get(currentRelationPointerIndex);

        String sentence = relation.getOriginSentence();
        sentence = StringUtils.strip(sentence, ".").trim();

        String rel = relation.getRelationTriple().getMiddle();
        String arg1 = relation.getRelationTriple().getLeft();
        String arg2 = relation.getRelationTriple().getRight();

        System.out.println("Check");
        System.out.println(fileContent);
        System.out.println(sentence);
        System.out.println(arg1);
        System.out.println(rel);
        System.out.println(arg2);
        System.out.println(fileContent.indexOf(rel));

        // Remove old highlights
        for (Object highlight: currentHighlights) {
            highlighter.removeHighlight(highlight);
        }

        // Highlight each word in relation separately
        String relSentence = sentence;
        int offsetRelSentence = 0;
        for (String word: rel.split("\\s")) {
            int pointerRelStart = relSentence.indexOf(word);
            int pointerRelEnd = pointerRelStart + word.length();

            try {
                if (pointerRelStart >= 0) {
                    relSentence = relSentence.substring(pointerRelEnd);

                    // Add offset
                    currentHighlights.add(highlighter.addHighlight(fileContent.indexOf(sentence) + offsetRelSentence + pointerRelStart, fileContent.indexOf(sentence) + offsetRelSentence + pointerRelEnd, relationPainter));

                    offsetRelSentence += pointerRelEnd;
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }

        // Highlight each word in 1st argument separately
        String arg1Sentence = sentence;
        int offsetArg1Sentence = 0;
        for (String word: arg1.split("\\s")) {
            int pointerArg1Start = arg1Sentence.indexOf(word);
            int pointerArg1End = pointerArg1Start + word.length();

            try {
                if (pointerArg1Start >= 0) {
                    arg1Sentence = arg1Sentence.substring(pointerArg1End);

                    // Add offset
                    currentHighlights.add(highlighter.addHighlight(fileContent.indexOf(sentence) + offsetArg1Sentence + pointerArg1Start, fileContent.indexOf(sentence) + offsetArg1Sentence + pointerArg1End, argumentPainter));

                    offsetArg1Sentence += pointerArg1End;
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }

        // Highlight each word in 1st argument separately
        String arg2Sentence = sentence;
        int offsetArg2Sentence = 0;
        for (String word: arg2.split("\\s")) {
            int pointerArg2Start = arg2Sentence.indexOf(word);
            int pointerArg2End = pointerArg2Start + word.length();

            try {
                if (pointerArg2Start >= 0) {
                    arg2Sentence = arg2Sentence.substring(pointerArg2End);

                    // Add offset
                    currentHighlights.add(highlighter.addHighlight(fileContent.indexOf(sentence) + offsetArg2Sentence + pointerArg2Start, fileContent.indexOf(sentence) + offsetArg2Sentence + pointerArg2End, argumentPainter));

                    offsetArg2Sentence += pointerArg2End;
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void showCurrentPointerProgress() {
        int totalRelations = relationsByFilename.get(currentlySelectedFile.getName()).getRelations().size();

        if (totalRelations > 0) {
            additionalInformationLabel.setText(totalRelations + " relations extracted." + " Pointer : " + (currentRelationPointerIndex+1) + "/" + totalRelations);
        } else {
            additionalInformationLabel.setText("No relations extracted.");
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

        closeButton = new javax.swing.JButton();
        additionalInformationLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        nextButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        additionalInformationLabel.setText(files.size() + " extraction files existed");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setMargin(new Insets(8,8,8,8));
        jScrollPane2.setViewportView(jTextArea1);

        HashSet<File> _files = new HashSet<>();

        for (Relation relation: relations.getRelations()) {
            File relationSource = new File(relation.getOriginFile());
            _files.add(relationSource);

            relationsByFilename.putIfAbsent(relationSource.getName(), new Relations());
            relationsByFilename.put(relationSource.getName(), relationsByFilename.get(relationSource.getName()).addRelation(relation));
        }

        ArrayList<File> files = new ArrayList<>(_files);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() { return files.size(); }
            public String getElementAt(int i) { return (i+1) + ". " + files.get(i).getName() ; }
        });
        jScrollPane3.setViewportView(jList1);

        highlighter = jTextArea1.getHighlighter();

        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                // Reset pointer
                currentRelationPointerIndex = 0;

                // Set selected file
                currentlySelectedFile = files.get(jList1.getSelectedIndex());

                String fileContent = StringUtils.join(Utilities.getFileContent(currentlySelectedFile), "");
                jTextArea1.setText(fileContent);
                setTitle(currentlySelectedFile.getName());

                showCurrentPointerProgress();

//                System.out.println(relationsByFilename.get(currentlySelectedFile.getName()));

//                highlightRelations(highlighter, fileContent, relationsByFilename.get(selectedFile.getName()));
                highlightCurrentRelation();
            }
        });

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.setText("Previous");
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(additionalInformationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(previousButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton)
                        .addGap(4, 4, 4)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton)
                        .addComponent(additionalInformationLabel)
                        .addComponent(nextButton)
                        .addComponent(previousButton))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
        setTitle("Extraction Viewer");
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:

        this.dispose();

    }//GEN-LAST:event_closeButtonActionPerformed

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        // TODO add your handling code here:

        if (currentRelationPointerIndex - 1 >=0) {
            currentRelationPointerIndex--;

            highlightCurrentRelation();
            showCurrentPointerProgress();
        }
    }//GEN-LAST:event_previousButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // TODO add your handling code here:

        int totalRelations = relationsByFilename.get(currentlySelectedFile.getName()).getRelations().size();

        if (currentRelationPointerIndex + 1 < totalRelations) {
            currentRelationPointerIndex++;

            highlightCurrentRelation();
            showCurrentPointerProgress();
        }
    }//GEN-LAST:event_nextButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ExtractionViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExtractionViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExtractionViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExtractionViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExtractionViewer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel additionalInformationLabel;
    private javax.swing.JButton closeButton;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton previousButton;
    // End of variables declaration//GEN-END:variables
}
