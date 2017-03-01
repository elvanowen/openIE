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
public class IDragDropListItem {

    private String displayName;

    public IDragDropListItem(String displayName) {
        this.setDisplayName(displayName);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String toString() {
        return this.getDisplayName();
    }
}
