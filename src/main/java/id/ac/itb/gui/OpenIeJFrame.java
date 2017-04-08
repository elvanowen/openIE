/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui;

import id.ac.itb.gui.alert.Alert;
import id.ac.itb.gui.config.ConfigDialog;
import id.ac.itb.gui.progressbar.CrawlerProgress;
import id.ac.itb.gui.progressbar.PreprocessorProgress;
import id.ac.itb.openie.crawler.Crawler;
import id.ac.itb.openie.crawler.CrawlerPipeline;
import id.ac.itb.openie.crawler.ICrawlerHandler;
import id.ac.itb.openie.crawler.ICrawlerPipelineElement;
import id.ac.itb.openie.extractor.Extractor;
import id.ac.itb.openie.extractor.ExtractorPipeline;
import id.ac.itb.openie.extractor.IExtractorHandler;
import id.ac.itb.openie.extractor.IExtractorPipelineElement;
import id.ac.itb.openie.pipeline.OpenIePipeline;
import id.ac.itb.openie.plugins.PluginLoader;
import id.ac.itb.openie.postprocess.IPostprocessorHandler;
import id.ac.itb.openie.postprocess.Postprocessor;
import id.ac.itb.openie.preprocess.IPreprocessorHandler;
import id.ac.itb.openie.preprocess.IPreprocessorPipelineElement;
import id.ac.itb.openie.preprocess.Preprocessor;
import id.ac.itb.openie.preprocess.PreprocessorPipeline;
import id.ac.itb.util.UnzipUtility;
import org.apache.commons.lang3.SerializationUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author elvanowen
 */
public class OpenIeJFrame extends javax.swing.JFrame {

    private DefaultListModel crawlPipelineListModel = new DefaultListModel();
    private DefaultListModel preprocessPipelineListModel = new DefaultListModel();
    private DefaultListModel extractPipelineListModel = new DefaultListModel();
    private DefaultListModel postprocessPipelineListModel = new DefaultListModel();
    private DefaultListModel openIePipelineListModel = new DefaultListModel();
    private PluginLoader pluginLoader = new PluginLoader();

    /**
     * Creates new form CustomizeCrawlerJFrame
     */
    public OpenIeJFrame() {
        initPlugins();
        initComponents();
    }

    private void initPlugins() {
        pluginLoader
                .registerAvailableExtensions(ICrawlerHandler.class)
                .registerAvailableExtensions(IPreprocessorHandler.class)
                .registerAvailableExtensions(IExtractorHandler.class)
                .registerAvailableExtensions(IPostprocessorHandler.class);
    }

    private void loadPlugin() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                String target = System.getProperty("pf4j.pluginsDir", "plugins") + File.separator + selectedFile.getName();
                File targetZip = new File(target);
                String UnzipTarget = target.replaceFirst("[.][^.]+$", "");
                Files.copy(selectedFile.toPath(), targetZip.toPath(), StandardCopyOption.REPLACE_EXISTING);

                UnzipUtility unzipUtility = new UnzipUtility();
                unzipUtility.unzip(target, UnzipTarget);
                targetZip.delete();

                new Alert("Required restarting application to load new plugins.").setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void browseStartingDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            System.out.println(selectedFile);

            // By default add File Reader to execution pipeline
            for (int i=0;i<openIESectionPreprocessComboBox.getItemCount(); i++) {
                IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) pluginLoader.getExtensions(IPreprocessorHandler.class).get(i);
                String pluginName = preprocessorHandler.getPluginName();

                if (pluginName.contains("File Reader")) {
                    Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                    preprocessor.getPreprocessorHandler().setAvailableConfigurations("Input Directory", selectedFile.getAbsolutePath());

                    openIePipelineListModel.addElement(preprocessor);
                    openIePipelineDragDropList.printItems();
                    break;
                }
            }
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        startingDirectoryLabel = new javax.swing.JLabel();
        browseStartingDirectoryButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        openIESectionPreprocessLabel = new javax.swing.JLabel();
        openIESectionPreprocessComboBox = new javax.swing.JComboBox<>();
        openIESectionAddPreprocessesButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        openIESectionExtractionLabel = new javax.swing.JLabel();
        openIESectionExtractionComboBox = new javax.swing.JComboBox<>();
        openIESectionAddExtractionButton = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        openIESectionPostprocessLabel = new javax.swing.JLabel();
        openIESectionPostprocessComboBox = new javax.swing.JComboBox<>();
        openIESectionAddPostprocessesButton = new javax.swing.JButton();
        openIESectionExecutionPipelineLabel = new javax.swing.JLabel();
        openIESectionRemovePipelineElementButton = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jScrollPane6 = new javax.swing.JScrollPane();
        openIESectionExecutePipelineElementButton = new javax.swing.JButton();
        openIESectionConfigurePipelineElementButton1 = new javax.swing.JButton();
        openIESectionCrawlerLabel = new javax.swing.JLabel();
        openIESectionCrawlerComboBox = new javax.swing.JComboBox<>();
        openIESectionAddCrawlersButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        loadCrawlerButton = new javax.swing.JButton();
        configureCrawlerButton = new javax.swing.JButton();
        crawlerComboBox = new javax.swing.JComboBox<Object>();
        runCrawlerButton = new javax.swing.JButton();
        crawlerPipelineLabel = new javax.swing.JLabel();
        addCrawlerButton = new javax.swing.JButton();
        crawlerListLabel = new javax.swing.JLabel();
        removeCrawlerButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        crawlerPipelineDragDropList = new id.ac.itb.gui.dragdroplist.DragDropList(crawlPipelineListModel);
        preprocessorPipelineDragDropList = new id.ac.itb.gui.dragdroplist.DragDropList(preprocessPipelineListModel);
        extractorPipelineDragDropList = new id.ac.itb.gui.dragdroplist.DragDropList(extractPipelineListModel);
        openIePipelineDragDropList = new id.ac.itb.gui.dragdroplist.DragDropList(openIePipelineListModel);
        postprocessorPipelineDragDropList = new id.ac.itb.gui.dragdroplist.DragDropList(postprocessPipelineListModel);
        preprocessorListLabel = new javax.swing.JLabel();
        preprocessorComboBox = new javax.swing.JComboBox<>();
        preprocessorPipelineLabel = new javax.swing.JLabel();
        addPreprocessorButton = new javax.swing.JButton();
        removePreprocessorButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        loadPreprocessorButton = new javax.swing.JButton();
        configurePreprocessorButton = new javax.swing.JButton();
        runPreprocessorButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        extractorListLabel = new javax.swing.JLabel();
        extractorComboBox = new javax.swing.JComboBox<>();
        extractorPipelineLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        addExtractorButton = new javax.swing.JButton();
        removeExtractorButton = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        loadExtractorButton = new javax.swing.JButton();
        configureExtractorButton = new javax.swing.JButton();
        runExtractorButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        postprocessorListLabel = new javax.swing.JLabel();
        postprocessorComboBox = new javax.swing.JComboBox<>();
        postprocessorPipelineLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        addPostprocessorButton = new javax.swing.JButton();
        removePostprocessorButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        loadPostprocessorButton = new javax.swing.JButton();
        configurePostprocessorButton = new javax.swing.JButton();
        runPostprocessorButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startingDirectoryLabel.setText("Starting Directory");

        browseStartingDirectoryButton.setText("Browse");
        browseStartingDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseStartingDirectoryButtonActionPerformed(evt);
            }
        });

        openIESectionPreprocessLabel.setText("Preprocesses");

        openIESectionPreprocessComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IPreprocessorHandler.class).toArray()));

        openIESectionAddPreprocessesButton.setText("+");
        openIESectionAddPreprocessesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionAddPreprocessesButtonActionPerformed(evt);
            }
        });

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);

        openIESectionExtractionLabel.setText("Extraction");

        openIESectionExtractionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IExtractorHandler.class).toArray()));

        openIESectionAddExtractionButton.setText("+");
        openIESectionAddExtractionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionAddExtractionButtonActionPerformed(evt);
            }
        });

        openIESectionPostprocessLabel.setText("Postprocesses");

        openIESectionPostprocessComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IPostprocessorHandler.class).toArray()));

        openIESectionAddPostprocessesButton.setText("+");
        openIESectionAddPostprocessesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionAddPostprocessesButtonActionPerformed(evt);
            }
        });

        openIESectionExecutionPipelineLabel.setText("Execution Pipeline");

        openIePipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {

                Object selectedPipelineElement = openIePipelineDragDropList.getSelectedValue();

                if (selectedPipelineElement != null) {

                    HashMap<String, String> availableConfigurations = null;

                    if (availableConfigurations != null) {
                        openIESectionConfigurePipelineElementButton1.setEnabled(true);
                    } else {
                        openIESectionConfigurePipelineElementButton1.setEnabled(false);
                    }
                }
            }
        });

        openIESectionRemovePipelineElementButton.setText("Remove");
        openIESectionRemovePipelineElementButton.setEnabled(false);
        openIESectionRemovePipelineElementButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        openIESectionRemovePipelineElementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionRemovePipelineElementButtonActionPerformed(evt);
            }
        });

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        openIESectionExecutePipelineElementButton.setText("Execute");
        openIESectionExecutePipelineElementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionExecutePipelineElementButtonActionPerformed(evt);
            }
        });

        openIESectionConfigurePipelineElementButton1.setText("Configure");
        openIESectionConfigurePipelineElementButton1.setEnabled(false);
        openIESectionConfigurePipelineElementButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionConfigurePipelineElementButton1ActionPerformed(evt);
            }
        });

        openIESectionCrawlerLabel.setText("Crawlers");
        openIESectionCrawlerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(ICrawlerHandler.class).toArray()));

        openIESectionAddCrawlersButton.setText("+");
        openIESectionAddCrawlersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openIESectionAddCrawlersButtonActionPerformed(evt);
            }
        });

        jScrollPane6.setViewportView(openIePipelineDragDropList);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openIESectionPreprocessLabel)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(openIESectionPreprocessComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(openIESectionAddPreprocessesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(openIESectionExtractionLabel)
                                .addGap(63, 63, 63))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(openIESectionExtractionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(openIESectionAddExtractionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openIESectionPostprocessLabel)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(openIESectionPostprocessComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openIESectionAddPostprocessesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator11)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(openIESectionExecutionPipelineLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openIESectionConfigurePipelineElementButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(openIESectionRemovePipelineElementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(openIESectionExecutePipelineElementButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startingDirectoryLabel)
                            .addComponent(browseStartingDirectoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openIESectionCrawlerLabel)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(openIESectionCrawlerComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openIESectionAddCrawlersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(startingDirectoryLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(browseStartingDirectoryButton))
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(openIESectionCrawlerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openIESectionCrawlerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(openIESectionAddCrawlersButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(openIESectionPreprocessLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(openIESectionPreprocessComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(openIESectionAddPreprocessesButton)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(openIESectionExtractionLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(openIESectionExtractionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(openIESectionAddExtractionButton)))
                        .addComponent(jSeparator10, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addComponent(jSeparator13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(openIESectionPostprocessLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openIESectionPostprocessComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(openIESectionAddPostprocessesButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openIESectionExecutionPipelineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(openIESectionRemovePipelineElementButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openIESectionConfigurePipelineElementButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(openIESectionExecutePipelineElementButton))
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Open IE", jPanel5);

        loadCrawlerButton.setText("Browse");
        loadCrawlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadCrawlerButtonActionPerformed(evt);
            }
        });

        configureCrawlerButton.setText("Configure");
        configureCrawlerButton.setEnabled(false);
        configureCrawlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureCrawlerButtonActionPerformed(evt);
            }
        });

        crawlerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        runCrawlerButton.setText("Run Crawler Pipeline");
        runCrawlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runCrawlerButtonActionPerformed(evt);
            }
        });

        crawlerPipelineLabel.setText("Crawler Pipeline");

        addCrawlerButton.setText("+");
        addCrawlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCrawlerButtonActionPerformed(evt);
            }
        });

        crawlerListLabel.setText("Pipeline Elements");

        removeCrawlerButton.setText("Remove From Pipeline");
        removeCrawlerButton.setEnabled(false);
        removeCrawlerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCrawlerButtonActionPerformed(evt);
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
                        .addComponent(crawlerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addCrawlerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadCrawlerButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(crawlerListLabel)
                            .addComponent(crawlerPipelineLabel)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(runCrawlerButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeCrawlerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(configureCrawlerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crawlerListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crawlerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCrawlerButton)
                    .addComponent(loadCrawlerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crawlerPipelineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(removeCrawlerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configureCrawlerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(runCrawlerButton))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Crawl", jPanel1);

        preprocessorListLabel.setText("Pipeline Elements");

        preprocessorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IPreprocessorHandler.class).toArray()));

        preprocessorPipelineLabel.setText("Preprocessor Pipeline");

        addPreprocessorButton.setText("+");
        addPreprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPreprocessorButtonActionPerformed(evt);
            }
        });

        removePreprocessorButton.setText("Remove From Pipeline");
        removePreprocessorButton.setEnabled(false);
        removePreprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePreprocessorButtonActionPerformed(evt);
            }
        });

        loadPreprocessorButton.setText("Browse");
        loadPreprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadPreprocessorButtonActionPerformed(evt);
            }
        });
        crawlerPipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {
                Crawler selectedCrawler = (Crawler) crawlerPipelineDragDropList.getSelectedValue();
                if (selectedCrawler != null && selectedCrawler.getCrawlerhandler().getAvailableConfigurations() != null) {
                    configureCrawlerButton.setEnabled(true);
                } else {
                    configureCrawlerButton.setEnabled(false);
                }
            }
        });

        crawlerComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(ICrawlerHandler.class).toArray()));

        configurePreprocessorButton.setText("Configure");
        configurePreprocessorButton.setEnabled(false);
        configurePreprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configurePreprocessorButtonActionPerformed(evt);
            }
        });
        jScrollPane1.setViewportView(crawlerPipelineDragDropList);


        crawlerPipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {
                Crawler selectedCrawler = (Crawler) crawlerPipelineDragDropList.getSelectedValue();

                if (selectedCrawler != null) {
                    if (selectedCrawler.getCrawlerhandler().getAvailableConfigurations() != null) {
                        configureCrawlerButton.setEnabled(true);
                    } else {
                        configureCrawlerButton.setEnabled(false);
                    }

                    removeCrawlerButton.setEnabled(true);
                }
            }
        });
        preprocessorPipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {
                Preprocessor selectedPreprocessor = (Preprocessor) preprocessorPipelineDragDropList.getSelectedValue();

                if (selectedPreprocessor != null) {
                    if (selectedPreprocessor.getPreprocessorHandler().getAvailableConfigurations() != null) {
                        configurePreprocessorButton.setEnabled(true);
                    } else {
                        configurePreprocessorButton.setEnabled(false);
                    }

                    String pluginName = selectedPreprocessor.getPreprocessorHandler().getPluginName();
                    int nFileReader = 0, nFileWriter = 0;

                    for (int i=0;i<preprocessorPipelineDragDropList.getModel().getSize();i++) {
                        if (((Preprocessor)preprocessorPipelineDragDropList.getModel().getElementAt(i)).getPreprocessorHandler().getPluginName().contains("File Reader")) {
                            nFileReader++;
                        } else if (((Preprocessor)preprocessorPipelineDragDropList.getModel().getElementAt(i)).getPreprocessorHandler().getPluginName().contains("File Writer")) {
                            nFileWriter++;
                        }
                    }

                    if (pluginName.contains("File Reader")) {
                        if (nFileReader > 1) removePreprocessorButton.setEnabled(true);
                        else removePreprocessorButton.setEnabled(false);
                    }

                    if (pluginName.contains("File Writer")) {
                        if (nFileWriter > 1) removePreprocessorButton.setEnabled(true);
                        else removePreprocessorButton.setEnabled(false);
                    }
                }
            }
        });

        runPreprocessorButton.setText("Run Preprocessor Pipeline");
        runPreprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runPreprocessorButtonActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(preprocessorPipelineDragDropList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(preprocessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPreprocessorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadPreprocessorButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(preprocessorListLabel)
                                    .addComponent(preprocessorPipelineLabel))
                                .addGap(277, 277, 277))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(runPreprocessorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removePreprocessorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(configurePreprocessorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(preprocessorListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(preprocessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPreprocessorButton)
                    .addComponent(loadPreprocessorButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(preprocessorPipelineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(removePreprocessorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configurePreprocessorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(runPreprocessorButton))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Preprocess", jPanel2);

        extractorListLabel.setText("Pipeline Elements");

        extractorPipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {
                Extractor selectedExtractor = (Extractor) extractorPipelineDragDropList.getSelectedValue();
                if (selectedExtractor != null && selectedExtractor.getExtractorHandler().getAvailableConfigurations() != null) {
                    configureExtractorButton.setEnabled(true);
                } else {
                    configureExtractorButton.setEnabled(false);
                }

                removeExtractorButton.setEnabled(true);
            }
        });

        extractorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IExtractorHandler.class).toArray()));

        extractorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extractorComboBoxActionPerformed(evt);
            }
        });

        extractorPipelineLabel.setText("Extractor Pipeline");

        addExtractorButton.setText("+");
        addExtractorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExtractorButtonActionPerformed(evt);
            }
        });

        removeExtractorButton.setText("Remove From Pipeline");
        removeExtractorButton.setEnabled(false);
        removeExtractorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeExtractorButtonActionPerformed(evt);
            }
        });

        loadExtractorButton.setText("Browse");
        loadExtractorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadExtractorButtonActionPerformed(evt);
            }
        });

        configureExtractorButton.setText("Configure");
        configureExtractorButton.setEnabled(false);
        configureExtractorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureExtractorButtonActionPerformed(evt);
            }
        });

        runExtractorButton.setText("Run Extractor Pipeline");
        runExtractorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runExtractorButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(extractorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addExtractorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadExtractorButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(extractorListLabel)
                            .addComponent(extractorPipelineLabel)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(runExtractorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removeExtractorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(configureExtractorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(extractorListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extractorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addExtractorButton)
                    .addComponent(loadExtractorButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(extractorPipelineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(removeExtractorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configureExtractorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(runExtractorButton))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Extract", jPanel3);

        postprocessorListLabel.setText("Pipeline Elements");
        jScrollPane4.setViewportView(extractorPipelineDragDropList);

        postprocessorPipelineDragDropList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent ev) {
                Postprocessor selectedPostprocessor = (Postprocessor) postprocessorPipelineDragDropList.getSelectedValue();
                if (selectedPostprocessor != null && selectedPostprocessor.getPostprocessorHandler().getAvailableConfigurations() != null) {
                    configurePostprocessorButton.setEnabled(true);
                } else {
                    configurePostprocessorButton.setEnabled(false);
                }

                removePostprocessorButton.setEnabled(true);
            }
        });

        postprocessorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(pluginLoader.getExtensions(IPostprocessorHandler.class).toArray()));

        postprocessorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postprocessorComboBoxActionPerformed(evt);
            }
        });

        postprocessorPipelineLabel.setText("Postprocessor Pipeline");

        addPostprocessorButton.setText("+");
        addPostprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPostprocessorButtonActionPerformed(evt);
            }
        });

        removePostprocessorButton.setText("Remove From Pipeline");
        removePostprocessorButton.setEnabled(false);
        removePostprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removePostprocessorButtonActionPerformed(evt);
            }
        });

        loadPostprocessorButton.setText("Browse");
        loadPostprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadPostprocessorButtonActionPerformed(evt);
            }
        });

        configurePostprocessorButton.setText("Configure");
        configurePostprocessorButton.setEnabled(false);
        configurePostprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configurePostprocessorButtonActionPerformed(evt);
            }
        });

        runPostprocessorButton.setText("Run Postprocessor Pipeline");
        runPostprocessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runPostprocessorButtonActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(postprocessorPipelineDragDropList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(postprocessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPostprocessorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadPostprocessorButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(postprocessorListLabel)
                            .addComponent(postprocessorPipelineLabel)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(runPostprocessorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(removePostprocessorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(configurePostprocessorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(postprocessorListLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(postprocessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addPostprocessorButton)
                    .addComponent(loadPostprocessorButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postprocessorPipelineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(removePostprocessorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(configurePostprocessorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(runPostprocessorButton))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Postprocess", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        pack();
        setLocationRelativeTo(null);
        setTitle("Sistem Open IE Bahasa Indonesia");
    }// </editor-fold>//GEN-END:initComponents

    private void configureCrawlerButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:

        Crawler selectedCrawler = (Crawler) crawlerPipelineDragDropList.getSelectedValue();

        if (selectedCrawler != null) {
            // Show configuration dialog
            new ConfigDialog(selectedCrawler.getCrawlerhandler().getAvailableConfigurations()).setVisible(true);
        }

    }                                                 

    private void removeCrawlerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCrawlerButton1ActionPerformed
        // TODO add your handling code here:

        Crawler selectedCrawler = (Crawler) crawlerPipelineDragDropList.getSelectedValue();
        crawlPipelineListModel.removeElement(selectedCrawler);

    }//GEN-LAST:event_addCrawlerButton1ActionPerformed

    private void addCrawlerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCrawlerButtonActionPerformed
        // TODO add your handling code here:

        ICrawlerHandler crawlerHandler = (ICrawlerHandler) pluginLoader.getExtensions(ICrawlerHandler.class).get(crawlerComboBox.getSelectedIndex());
        Crawler crawler = new Crawler().setCrawlerhandler(SerializationUtils.clone(crawlerHandler));

        crawlPipelineListModel.addElement(crawler);
        crawlerPipelineDragDropList.printItems();
    }//GEN-LAST:event_addCrawlerButtonActionPerformed

    private void runCrawlerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runCrawlerButtonActionPerformed
        // TODO add your handling code here:

        CrawlerPipeline crawlerPipeline = new CrawlerPipeline();
        OpenIePipeline openIePipeline = new OpenIePipeline();

        for (int i = 0; i< crawlPipelineListModel.size(); i++) {
            Crawler crawler = (Crawler) crawlPipelineListModel.get(i);
            crawlerPipeline.addPipelineElement(crawler);
        }

        openIePipeline.addPipelineElement(crawlerPipeline);

        JFrame crawlerProgress = new CrawlerProgress(crawlerPipeline);
        crawlerProgress.setVisible(true);

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws InterruptedException {
                try {
                    openIePipeline.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "";
            }

            @Override
            protected void done() {
                ((CrawlerProgress) crawlerProgress).stopTimer();
                crawlerProgress.dispose();

                int nFileWriter = 0;

                for (ICrawlerPipelineElement crawlerPipelineElement: crawlerPipeline.getCrawlerPipelineElements()) {
                    if (((Crawler) crawlerPipelineElement).getCrawlerhandler().getPluginName().equalsIgnoreCase("Crawler File Writer")) {
                        nFileWriter++;

                        try {
                            Desktop.getDesktop().open(new File(((Crawler) crawlerPipelineElement).getCrawlerhandler().getAvailableConfigurations().get("Output Directory")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (nFileWriter == 0) { // Open default folder
                    for (Object iCrawlerHandler: pluginLoader.getExtensions(ICrawlerHandler.class)) {
                        ICrawlerHandler crawlerHandler = (ICrawlerHandler) iCrawlerHandler;
                        String pluginName = crawlerHandler.getPluginName();

                        if (pluginName.equalsIgnoreCase("Crawler File Writer")) {
                            Crawler crawler = new Crawler().setCrawlerhandler(crawlerHandler);
                            try {
                                Desktop.getDesktop().open(new File((crawler.getCrawlerhandler().getAvailableConfigurations().get("Output Directory"))));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        worker.execute();

    }//GEN-LAST:event_runCrawlerButtonActionPerformed

    private void loadCrawlerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadCrawlerButtonActionPerformed
        // TODO add your handling code here:

        loadPlugin();

    }//GEN-LAST:event_loadCrawlerButtonActionPerformed

    private void addPreprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPreprocessorButtonActionPerformed
        // TODO add your handling code here:

        IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) pluginLoader.getExtensions(IPreprocessorHandler.class).get(preprocessorComboBox.getSelectedIndex());
        Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(SerializationUtils.clone(preprocessorHandler));

        preprocessPipelineListModel.addElement(preprocessor);
        preprocessorPipelineDragDropList.printItems();

    }//GEN-LAST:event_addPreprocessorButtonActionPerformed

    private void removePreprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePreprocessorButtonActionPerformed
        // TODO add your handling code here:

        Preprocessor selectedPreprocessor = (Preprocessor) preprocessorPipelineDragDropList.getSelectedValue();
        preprocessPipelineListModel.removeElement(selectedPreprocessor);

    }//GEN-LAST:event_removePreprocessorButtonActionPerformed

    private void loadPreprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadPreprocessorButtonActionPerformed
        // TODO add your handling code here:

        loadPlugin();

    }//GEN-LAST:event_loadPreprocessorButtonActionPerformed

    private void runPreprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runPreprocessorButtonActionPerformed
        // TODO add your handling code here:

        PreprocessorPipeline preprocessorPipeline = new PreprocessorPipeline();
        OpenIePipeline openIePipeline = new OpenIePipeline();

        for (int i = 0; i< preprocessPipelineListModel.size(); i++) {
            IPreprocessorPipelineElement preprocessorPipelineElement = (IPreprocessorPipelineElement) preprocessPipelineListModel.get(i);
            preprocessorPipeline.addPipelineElement(preprocessorPipelineElement);
        }

        openIePipeline.addPipelineElement(preprocessorPipeline);

        JFrame preprocessorProgress = new PreprocessorProgress(preprocessorPipeline);
        preprocessorProgress.setVisible(true);

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws InterruptedException {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    openIePipeline.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "";
            }

            @Override
            protected void done() {
                ((PreprocessorProgress) preprocessorProgress).stopTimer();
                preprocessorProgress.dispose();

                int nFileWriter = 0;

                for (IPreprocessorPipelineElement preprocessorPipelineElement: preprocessorPipeline.getPreprocessorPipelineElements()) {
                    if (((Preprocessor) preprocessorPipelineElement).getPreprocessorHandler().getPluginName().equalsIgnoreCase("Preprocessor File Writer")) {
                        nFileWriter++;

                        try {
                            Desktop.getDesktop().open(new File(((Preprocessor) preprocessorPipelineElement).getPreprocessorHandler().getAvailableConfigurations().get("Output Directory")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (nFileWriter == 0) { // Open default folder
                    for (Object iPreprocessorHandler: pluginLoader.getExtensions(IPreprocessorHandler.class)) {
                        IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) iPreprocessorHandler;
                        String pluginName = preprocessorHandler.getPluginName();

                        if (pluginName.equalsIgnoreCase("Preprocessor File Writer")) {
                            Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(preprocessorHandler);
                            try {
                                Desktop.getDesktop().open(new File((preprocessor.getPreprocessorHandler().getAvailableConfigurations().get("Output Directory"))));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        worker.execute();

    }//GEN-LAST:event_runPreprocessorButtonActionPerformed

    private void configurePreprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configurePreprocessorButtonActionPerformed
        // TODO add your handling code here:

        Preprocessor selectedPreprocessor = (Preprocessor) preprocessorPipelineDragDropList.getSelectedValue();

        if (selectedPreprocessor != null) {
            // Show configuration dialog
            new ConfigDialog(selectedPreprocessor.getPreprocessorHandler().getAvailableConfigurations()).setVisible(true);
        }

    }//GEN-LAST:event_configurePreprocessorButtonActionPerformed

    private void addPostprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPostprocessorButtonActionPerformed
        // TODO add your handling code here:

        IPostprocessorHandler postprocessorHandler = (IPostprocessorHandler) pluginLoader.getExtensions(IPostprocessorHandler.class).get(postprocessorComboBox.getSelectedIndex());
        Postprocessor postprocessor = new Postprocessor().setPostprocessorHandler(SerializationUtils.clone(postprocessorHandler));

        postprocessPipelineListModel.addElement(postprocessor);
        postprocessorPipelineDragDropList.printItems();

    }//GEN-LAST:event_addPostprocessorButtonActionPerformed

    private void removePostprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removePostprocessorButtonActionPerformed
        // TODO add your handling code here:

        Postprocessor selectedPostprocessor = (Postprocessor) postprocessorPipelineDragDropList.getSelectedValue();
        postprocessPipelineListModel.removeElement(selectedPostprocessor);

    }//GEN-LAST:event_removePostprocessorButtonActionPerformed

    private void loadPostprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadPostprocessorButtonActionPerformed
        // TODO add your handling code here:

        loadPlugin();

    }//GEN-LAST:event_loadPostprocessorButtonActionPerformed

    private void runPostprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runPostprocessorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_runPostprocessorButtonActionPerformed

    private void addExtractorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExtractorButtonActionPerformed
        // TODO add your handling code here:

        IExtractorHandler extractorHandler = (IExtractorHandler) pluginLoader.getExtensions(IExtractorHandler.class).get(extractorComboBox.getSelectedIndex());
        Extractor extractor = new Extractor().setExtractorHandler(SerializationUtils.clone(extractorHandler));

        extractPipelineListModel.addElement(extractor);
        extractorPipelineDragDropList.printItems();

    }//GEN-LAST:event_addExtractorButtonActionPerformed

    private void removeExtractorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExtractorButtonActionPerformed
        // TODO add your handling code here:

        IExtractorHandler selectedExtractorHandler = (IExtractorHandler) extractorPipelineDragDropList.getSelectedValue();
        extractPipelineListModel.removeElement(selectedExtractorHandler);

    }//GEN-LAST:event_removeExtractorButtonActionPerformed

    private void loadExtractorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadExtractorButtonActionPerformed
        // TODO add your handling code here:

        loadPlugin();

    }//GEN-LAST:event_loadExtractorButtonActionPerformed

    private void runExtractorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runExtractorButtonActionPerformed
        // TODO add your handling code here:

        ExtractorPipeline extractorPipeline = new ExtractorPipeline();
        OpenIePipeline openIePipeline = new OpenIePipeline();

        for (int i = 0; i< extractPipelineListModel.size(); i++) {
            IExtractorPipelineElement extractorPipelineElement = (IExtractorPipelineElement) extractPipelineListModel.get(i);
            extractorPipeline.addPipelineElement(extractorPipelineElement);
        }

        openIePipeline.addPipelineElement(extractorPipeline);

        try {
            openIePipeline.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_runExtractorButtonActionPerformed

    private void configurePostprocessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configurePostprocessorButtonActionPerformed
        // TODO add your handling code here:

        Postprocessor selectedPostprocessor = (Postprocessor) postprocessorPipelineDragDropList.getSelectedValue();

        if (selectedPostprocessor != null) {
            // Show configuration dialog
            new ConfigDialog(selectedPostprocessor.getPostprocessorHandler().getAvailableConfigurations()).setVisible(true);
        }

    }//GEN-LAST:event_configurePostprocessorButtonActionPerformed

    private void postprocessorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postprocessorComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_postprocessorComboBoxActionPerformed

    private void configureExtractorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureExtractorButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_configureExtractorButtonActionPerformed

    private void extractorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extractorComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_extractorComboBoxActionPerformed

    private void browseStartingDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseStartingDirectoryButtonActionPerformed
        // TODO add your handling code here:

        browseStartingDirectory();
    }//GEN-LAST:event_browseStartingDirectoryButtonActionPerformed

    private void openIESectionAddCrawlersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionAddCrawlersButtonActionPerformed
        // TODO add your handling code here:

        ICrawlerHandler crawlerHandler = (ICrawlerHandler) pluginLoader.getExtensions(ICrawlerHandler.class).get(openIESectionCrawlerComboBox.getSelectedIndex());
        Crawler crawler = new Crawler().setCrawlerhandler(SerializationUtils.clone(crawlerHandler));

        openIePipelineListModel.addElement(crawler);
        openIePipelineDragDropList.printItems();

    }//GEN-LAST:event_openIESectionAddCrawlersButtonActionPerformed

    private void openIESectionAddPreprocessesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionAddPreprocessesButtonActionPerformed
        // TODO add your handling code here:

        IPreprocessorHandler preprocessorHandler = (IPreprocessorHandler) pluginLoader.getExtensions(IPreprocessorHandler.class).get(openIESectionPreprocessComboBox.getSelectedIndex());
        Preprocessor preprocessor = new Preprocessor().setPreprocessorHandler(SerializationUtils.clone(preprocessorHandler));

        openIePipelineListModel.addElement(preprocessor);
        openIePipelineDragDropList.printItems();

    }//GEN-LAST:event_openIESectionAddPreprocessesButtonActionPerformed

    private void openIESectionAddExtractionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionAddExtractionButtonActionPerformed
        // TODO add your handling code here:

        IExtractorHandler extractorHandler = (IExtractorHandler) pluginLoader.getExtensions(IExtractorHandler.class).get(openIESectionExtractionComboBox.getSelectedIndex());
        Extractor extractor = new Extractor().setExtractorHandler(SerializationUtils.clone(extractorHandler));

        openIePipelineListModel.addElement(extractor);
        openIePipelineDragDropList.printItems();

    }//GEN-LAST:event_openIESectionAddExtractionButtonActionPerformed

    private void openIESectionAddPostprocessesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionAddPostprocessesButtonActionPerformed
        // TODO add your handling code here:

        IPostprocessorHandler postprocessorHandler = (IPostprocessorHandler) pluginLoader.getExtensions(IPostprocessorHandler.class).get(openIESectionPostprocessComboBox.getSelectedIndex());
        Postprocessor postprocessor = new Postprocessor().setPostprocessorHandler(SerializationUtils.clone(postprocessorHandler));

        openIePipelineListModel.addElement(postprocessor);
        openIePipelineDragDropList.printItems();

    }//GEN-LAST:event_openIESectionAddPostprocessesButtonActionPerformed

    private void openIESectionRemovePipelineElementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionRemovePipelineElementButtonActionPerformed
        // TODO add your handling code here:

        openIePipelineListModel.removeElement(openIePipelineDragDropList.getSelectedValue());
    }//GEN-LAST:event_openIESectionRemovePipelineElementButtonActionPerformed

    private void openIESectionConfigurePipelineElementButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionConfigurePipelineElementButton1ActionPerformed
        // TODO add your handling code here:

        Object selectedPipelineElement = openIePipelineDragDropList.getSelectedValue();

        if (selectedPipelineElement != null) {
            if (selectedPipelineElement instanceof Extractor) {
                new ConfigDialog(((Extractor)selectedPipelineElement).getExtractorHandler().getAvailableConfigurations()).setVisible(true);
            } else if (selectedPipelineElement instanceof Preprocessor) {
                new ConfigDialog(((Preprocessor)selectedPipelineElement).getPreprocessorHandler().getAvailableConfigurations()).setVisible(true);
            } else if (selectedPipelineElement instanceof Crawler) {
                new ConfigDialog(((Crawler)selectedPipelineElement).getCrawlerhandler().getAvailableConfigurations()).setVisible(true);
            } else if (selectedPipelineElement instanceof Postprocessor) {
                new ConfigDialog(((Postprocessor)selectedPipelineElement).getPostprocessorHandler().getAvailableConfigurations()).setVisible(true);
            }
        }

    }//GEN-LAST:event_openIESectionConfigurePipelineElementButton1ActionPerformed

    private void openIESectionExecutePipelineElementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openIESectionExecutePipelineElementButtonActionPerformed
        // TODO add your handling code here:

        CrawlerPipeline crawlerPipeline = new CrawlerPipeline();
        PreprocessorPipeline preprocessorPipeline = new PreprocessorPipeline();
        ExtractorPipeline extractorPipeline = new ExtractorPipeline();
        OpenIePipeline openIePipeline = new OpenIePipeline();

        for (int i = 0; i< openIePipelineListModel.size(); i++) {
            Object selectedPipelineElement = openIePipelineListModel.get(i);

            System.out.println(selectedPipelineElement);

            if (selectedPipelineElement instanceof IExtractorPipelineElement) {
                IExtractorPipelineElement extractorPipelineElement = (IExtractorPipelineElement) selectedPipelineElement;
                extractorPipeline.addPipelineElement(extractorPipelineElement);
            } else if (selectedPipelineElement instanceof IPreprocessorPipelineElement) {
                IPreprocessorPipelineElement preprocessorPipelineElement = (IPreprocessorPipelineElement) selectedPipelineElement;
                preprocessorPipeline.addPipelineElement(preprocessorPipelineElement);
            } else if (selectedPipelineElement instanceof ICrawlerPipelineElement) {
                ICrawlerPipelineElement crawlerPipelineElement = (ICrawlerPipelineElement) selectedPipelineElement;
                crawlerPipeline.addPipelineElement(crawlerPipelineElement);
            }
        }

        openIePipeline.addPipelineElement(crawlerPipeline);
        openIePipeline.addPipelineElement(preprocessorPipeline);
        openIePipeline.addPipelineElement(extractorPipeline);

        try {
            openIePipeline.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_openIESectionExecutePipelineElementButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){ }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpenIeJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCrawlerButton;
    private javax.swing.JButton addExtractorButton;
    private javax.swing.JComboBox<Object> crawlerComboBox;
    private javax.swing.JButton addPostprocessorButton;
    private javax.swing.JButton addPreprocessorButton;
    private javax.swing.JButton browseStartingDirectoryButton;
    private javax.swing.JButton configureCrawlerButton;
    private javax.swing.JButton configureExtractorButton;
    private javax.swing.JButton configurePostprocessorButton;
    private javax.swing.JButton configurePreprocessorButton;
    private javax.swing.JLabel crawlerListLabel;
    private javax.swing.JLabel crawlerPipelineLabel;
    private javax.swing.JComboBox<Object> extractorComboBox;
    private javax.swing.JLabel extractorListLabel;
    private javax.swing.JLabel extractorPipelineLabel;
    private javax.swing.JPanel jPanel1;
    private id.ac.itb.gui.dragdroplist.DragDropList crawlerPipelineDragDropList;
    private id.ac.itb.gui.dragdroplist.DragDropList preprocessorPipelineDragDropList;
    private id.ac.itb.gui.dragdroplist.DragDropList openIePipelineDragDropList;
    private id.ac.itb.gui.dragdroplist.DragDropList extractorPipelineDragDropList;
    private id.ac.itb.gui.dragdroplist.DragDropList postprocessorPipelineDragDropList;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loadCrawlerButton;
    private javax.swing.JButton loadExtractorButton;
    private javax.swing.JButton loadPostprocessorButton;
    private javax.swing.JButton loadPreprocessorButton;
    private javax.swing.JButton openIESectionAddCrawlersButton;
    private javax.swing.JButton openIESectionAddExtractionButton;
    private javax.swing.JButton openIESectionAddPostprocessesButton;
    private javax.swing.JButton openIESectionAddPreprocessesButton;
    private javax.swing.JComboBox<Object> postprocessorComboBox;
    private javax.swing.JButton openIESectionConfigurePipelineElementButton1;
    private javax.swing.JComboBox<Object> openIESectionCrawlerComboBox;
    private javax.swing.JLabel openIESectionCrawlerLabel;
    private javax.swing.JButton openIESectionExecutePipelineElementButton;
    private javax.swing.JLabel openIESectionExecutionPipelineLabel;
    private javax.swing.JComboBox<Object> openIESectionExtractionComboBox;
    private javax.swing.JLabel openIESectionExtractionLabel;
    private javax.swing.JComboBox<Object> openIESectionPostprocessComboBox;
    private javax.swing.JLabel openIESectionPostprocessLabel;
    private javax.swing.JComboBox<Object> openIESectionPreprocessComboBox;
    private javax.swing.JLabel openIESectionPreprocessLabel;
    private javax.swing.JButton openIESectionRemovePipelineElementButton;
    private javax.swing.JLabel postprocessorListLabel;
    private javax.swing.JLabel postprocessorPipelineLabel;
    private javax.swing.JComboBox<Object> preprocessorComboBox;
    private javax.swing.JLabel preprocessorListLabel;
    private javax.swing.JLabel preprocessorPipelineLabel;
    private javax.swing.JButton removeCrawlerButton;
    private javax.swing.JButton removeExtractorButton;
    private javax.swing.JButton removePostprocessorButton;
    private javax.swing.JButton removePreprocessorButton;
    private javax.swing.JButton runCrawlerButton;
    private javax.swing.JButton runExtractorButton;
    private javax.swing.JButton runPostprocessorButton;
    private javax.swing.JButton runPreprocessorButton;
    private javax.swing.JLabel startingDirectoryLabel;
    // End of variables declaration//GEN-END:variables
}
