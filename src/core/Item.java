/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

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

    public Item(String path){
        _Path = path;
        _SubItems = new ArrayList<Item>();
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
    public abstract void rename(String newName);

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
    public abstract void execute();

    /**
     * Override toString method
     * @return
     */
    @Override
    public String toString() {
        return _Path;
    }
}
