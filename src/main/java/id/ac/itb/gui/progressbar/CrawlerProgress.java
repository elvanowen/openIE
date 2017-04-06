/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui.progressbar;

import id.ac.itb.openie.crawler.Crawler;
import id.ac.itb.openie.crawler.CrawlerPipeline;
import id.ac.itb.openie.crawler.ICrawlerPipelineElement;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 *
 * @author elvanowen
 */
public class CrawlerProgress extends javax.swing.JFrame {

    CrawlerPipeline crawlerPipeline;
    private Timer processTimer = null;
    private int tick;

    /**
     * Creates new form CrawlerProgress
     */
    public CrawlerProgress() {
        initComponents();
    }

    public CrawlerProgress(CrawlerPipeline crawlerPipeline) {
        this.crawlerPipeline = crawlerPipeline;
        initComponents();
    }

    private void showProgressLabel() {
        int totalPagesFetched = 0, totalPagesToBeFetched = 0;
        String crawlerName = "";
        tick = (tick % 4) + 1;
        String trail = StringUtils.repeat(".", tick) + StringUtils.repeat(" ", 4 - tick);

        if (crawlerPipeline.getCurrentlyRunningCrawler() != null) {
            crawlerName = ((Crawler)crawlerPipeline.getCurrentlyRunningCrawler()).getCrawlerhandler().getPluginName();
        }

        for (ICrawlerPipelineElement crawlerPipelineElement: crawlerPipeline.getCrawlerPipelineElements()) {
            totalPagesFetched += ((Crawler) crawlerPipelineElement).getTotalDocumentCrawled();
            String maxPagesToFetch = ((Crawler) crawlerPipelineElement).getCrawlerhandler().getAvailableConfigurations().get("Max Pages to Fetch");

            if (maxPagesToFetch != null) {
                totalPagesToBeFetched += Integer.valueOf(maxPagesToFetch);
            }
        }

        if (totalPagesToBeFetched > 0 && totalPagesFetched == totalPagesToBeFetched) {
            totalDocumentLabel.setText("Fetches Completed. Processing" + trail);
        } else {
            totalDocumentLabel.setText(" " + totalPagesFetched + " / " + totalPagesToBeFetched + " pages ");
        }

        updateProgressBar(totalPagesFetched, totalPagesToBeFetched);

        int totalProcessedCrawler = crawlerPipeline.getTotalProcessedCrawler();
        int totalCrawler = crawlerPipeline.getCrawlerPipelineElements().size();

        if (crawlerName.equalsIgnoreCase("")) {
            totalCrawlerLabel.setText("Setting up crawlers" + trail + StringUtils.repeat(" ", 40));
        } else {
            totalCrawlerLabel.setText("Crawling " + crawlerName + " ( " + totalProcessedCrawler + " / " + totalCrawler + " crawler" + (totalCrawler > 1 ? "s" : "") + " )");
        }
    }

    private void updateProgressBar(int numerator, int denominator) {
        crawlerProgressBar.setMinimum(0);
        crawlerProgressBar.setMaximum(denominator);
        crawlerProgressBar.setValue(numerator);
    }

    public void stopTimer() {
        processTimer.stop();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crawlerProgressBar = new javax.swing.JProgressBar();
        currentRunningCrawlerLabel = new javax.swing.JLabel();
        totalDocumentLabel = new javax.swing.JLabel();
        totalCrawlerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        totalCrawlerLabel.setText("Setting up crawlers");

        showProgressLabel();

        processTimer = new Timer(1000, e -> {
            showProgressLabel();
        });
        processTimer.start();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crawlerProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(currentRunningCrawlerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalCrawlerLabel)
                        .addGap(0, 252, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(totalDocumentLabel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentRunningCrawlerLabel)
                    .addComponent(totalCrawlerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crawlerProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalDocumentLabel)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
        setTitle("Crawler Progress");
    }// </editor-fold>//GEN-END:initComponents

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
                new CrawlerProgress().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar crawlerProgressBar;
    private javax.swing.JLabel currentRunningCrawlerLabel;
    private javax.swing.JLabel totalCrawlerLabel;
    private javax.swing.JLabel totalDocumentLabel;
    // End of variables declaration//GEN-END:variables
}
