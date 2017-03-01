package id.ac.itb.gui.progressbar;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.*;
import javax.swing.border.Border;
/**
 * Created by elvanowen on 3/2/17.
 */

public class ProgressBar {
    private JProgressBar progressBar;
    private int progress;

    public ProgressBar(String title) {
        JFrame f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Crawling...");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        f.setSize(300, 100);
        f.setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                while (progress <= 100) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            progressBar.setValue(progress++);
                        }
                    });
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            }
        }).start();
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }
}