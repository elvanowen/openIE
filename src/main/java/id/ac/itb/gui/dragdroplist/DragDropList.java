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
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class DragDropList extends JList {
    DefaultListModel<Object> model;
    private Integer fromIdx, toIdx;
    private List<Object> items;

    public DragDropList() {
        super(new DefaultListModel<Object>());
        model = (DefaultListModel<Object>) getModel();
        setDragEnabled(true);
        setDropMode(DropMode.INSERT);

        setTransferHandler(new DropHandler(this));

        new DragListener(this);
    }

    public void setItems(List<Object> objects) {
        this.items = objects;

        for(Object object: objects) {
            model.addElement(object);
        }
    }

    void moveItem(Integer fromIdx, Integer toIdx) {

        Object elementToBeRemoved = model.getElementAt(fromIdx);
        items.add(toIdx, model.getElementAt(fromIdx));
        model.add(toIdx, model.getElementAt(fromIdx));

        if (fromIdx > toIdx) {
            items.remove(model.lastIndexOf(elementToBeRemoved));
            model.remove(model.lastIndexOf(elementToBeRemoved));
        } else {
            items.remove(model.indexOf(elementToBeRemoved));
            model.remove(model.indexOf(elementToBeRemoved));
        }
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
        f.add(new JScrollPane(new DragDropList()));
        f.setSize(300,300);
        f.setVisible(true);
    }
}

