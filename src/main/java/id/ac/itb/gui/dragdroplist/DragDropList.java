/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui.dragdroplist;

/**
 *
 * @author elvanowen
 */
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class DragDropList extends JList {
    DefaultListModel listModel;
    private Integer fromIdx, toIdx;

    public DragDropList(DefaultListModel listModel) {
        super(listModel);

        setDragEnabled(true);
        setDropMode(DropMode.INSERT);
        setTransferHandler(new DropHandler(this));
        new DragListener(this);

        this.listModel = listModel;
    }

    public void printItems() {
        System.out.println("items");
        if (this.listModel.size() > 0) {
            System.out.println(this.listModel);
        } else {
            System.out.println(this.listModel);
        }
    }

    void moveItem(Integer fromIdx, Integer toIdx) {
        Object elementToBeRemoved = listModel.getElementAt(fromIdx);
        listModel.add(toIdx, listModel.getElementAt(fromIdx));

        if (fromIdx > toIdx) {
            listModel.remove(listModel.lastIndexOf(elementToBeRemoved));
        } else {
            listModel.remove(listModel.indexOf(elementToBeRemoved));
        }

        System.out.println(this.listModel);
    }

    void setFromIdx(Integer fromIdx) {
        this.fromIdx = fromIdx;
    }

    void setToIdx(Integer toIdx) {
        this.toIdx = toIdx;
    }

    Integer getFromIdx() {
        return this.fromIdx;
    }

    Integer getToIdx() {
        return this.toIdx;
    }

    public static void main(String[] a){
        JFrame f = new JFrame();
        f.add(new JScrollPane(new DragDropList(new DefaultListModel())));
        f.setSize(300,300);
        f.setVisible(true);
    }
}

