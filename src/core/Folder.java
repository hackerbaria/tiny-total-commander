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
public class Folder extends Item{

    public Folder(String path){
        super(path);
    }

    @Override
    public void add(Item item) {
        _SubItems.add(item);
    }

    @Override
    public void remove(Item item) {
        _SubItems.remove(item);
    }

    @Override
    public void execute() {
        // open folder, go inside (one step)
        java.io.File folder = new java.io.File(_Path);
        if(folder.exists()) {
            for(java.io.File item : folder.listFiles()) {
                if(item.isFile()) {
                    _SubItems.add(new File(item.getPath()));
                }
                else if(item.isDirectory()) {
                    _SubItems.add(new Folder(item.getPath()));
                    //getSubItems(list, item);
                }
            }
        }     
    }

    @Override
    public void rename(String newName) {
        java.io.File folder = new java.io.File(_Path);
        folder.renameTo(new java.io.File(newName));
    }

    @Override
    public void delete() {
        java.io.File folder = new java.io.File(_Path);
        if(folder.exists()) {
            deleteFiles(folder);  // delete all sub items
            folder.delete();      // delete folder itself
        }
    }
    
    @Override
    public void copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void move(String newPath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * create a folder or multiple folders(sub folders)
     * @param path
     * @return
     */
    public static Folder create(String path){
        // create folder
        java.io.File folder = new java.io.File(path);
        if(path.contains("/")) {   // folder and sub folders
            folder.mkdirs();
        } else {                   // single folder
            folder.mkdir();
        }

        //TODO: create multi folders, separator |

        return new Folder(path);
    }

    @Override
    public Boolean hasChild() {
        return true;
    }

    private void deleteFiles(java.io.File folder) {
        for (java.io.File item : folder.listFiles()) {
            if(item.isDirectory()) {
                deleteFiles(item);
            }

            item.delete();
        }
    }

}
