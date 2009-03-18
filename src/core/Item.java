/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Item represents both File and Folder
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public abstract class Item {

    /**
     * Item path
     */
    protected String _Path;

    /**
     * Sub items (folder only)
     */
    protected ArrayList<Item> _SubItems;

    /**
     * Processor
     */
    protected java.io.File _Item;

    /**
     * Constructor
     * @param path
     */
    public Item(String path){
        _Path = path;
        _SubItems = new ArrayList<Item>();
        _Item = new java.io.File(_Path);
    }

    /**
     * Get all sub items (folder only)
     * @return
     */
    public ArrayList<Item> getSubItems() {
        return _SubItems;
    }

    /**
     * Add new item to item (folder only)
     * @param item
     */
    public abstract void add(Item item);

    /**
     * Remove an item from item (folder only)
     * @param item
     */
    public abstract void remove(Item item);

    /**
     * Rename an item
     * @param newName
     */
    public void rename(String newName) {
        _Item.renameTo(new java.io.File(newName));
    }

    /**
     * Copy an item
     */
    public abstract void copy();

    /**
     * Move an item to another place
     * @param newPath
     */
    public abstract void move(String newPath);

    /**
     * Delete item itself
     */
    public abstract void delete();

    /**
     * Execute an item (open if file, step inside if folder)
     */
    public abstract void execute() throws IOException;

    /**
     * Item has child or not
     * @return
     */
    public abstract Boolean hasChild();

    /**
     * Override toString method
     * @return
     */
    @Override
    public String toString() {
        return _Path;
    }
}
