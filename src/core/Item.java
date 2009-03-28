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
     * Get content (file only)
     * @return
     */
    public abstract String getContent() throws IOException;

    /**
     * Rename an item
     * @param newName
     */
    public void rename(String newName) {
        String partialPath = _Path.substring(0, _Path.lastIndexOf("/"));
        _Item.renameTo(new java.io.File(partialPath + newName));
    }

    /**
     * Create an item
     */
    public abstract void create();

    /**
     * Copy an item
     */
    public abstract void copy(String anotherLocation) throws IOException;

    /**
     * Move an item to another place
     * @param newPath
     */
    public abstract void move(String newPath) throws IOException;

    /**
     * Delete item itself
     */
    public abstract void delete();

    /**
     * Execute an item (open if file, step inside if folder)
     */
    public abstract void execute() throws IOException;

    /**
     * Zip item
     * @throws java.io.IOException
     */
    public abstract void zip() throws IOException;

    /**
     * Unzip item
     * @throws java.io.IOException
     */
    public abstract void unzip() throws IOException;

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
