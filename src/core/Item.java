/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.ArrayList;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public abstract class Item {
    protected String _Path;
    protected ArrayList<Item> _SubItems;

    public Item(String path){
        _Path = path;
        _SubItems = new ArrayList<Item>();
    }

    public abstract void add(Item item);
    public abstract void remove(Item item);
    public abstract void rename(String newName);
    public abstract void copy();
    public abstract void move(String newPath);
    public abstract void delete();

    public abstract void execute();

    @Override
    public String toString() {
        return _Path;
    }
}
