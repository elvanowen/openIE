package id.ac.itb.gui.dragdroplist;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

/**
 * Created by elvanowen on 3/1/17.
 */
class DropHandler extends TransferHandler {
    DragDropList list;

    public DropHandler(DragDropList list) {
        this.list = list;
    }

    public boolean canImport(TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        if (dl.getIndex() == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        Transferable transferable = support.getTransferable();
        String indexString;
        try {
            indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            return false;
        }

        int index = Integer.parseInt(indexString);
        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        int dropTargetIndex = dl.getIndex();

        System.out.println(indexString + " : ");
        System.out.println(dropTargetIndex + " : ");
        System.out.println("inserted");

        this.list.setFromIdx(index);
        this.list.setToIdx(dropTargetIndex);

        return true;
    }
}
