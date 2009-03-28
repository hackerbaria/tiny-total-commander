/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.IOException;

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
        if(_Item.exists()) {
            for(java.io.File item : _Item.listFiles()) {
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
    public void delete() {
        if(_Item.exists()) {
            deleteFiles(_Item);  // delete all sub items
            _Item.delete();      // delete folder itself
        }
    }
    
    @Override
    public void copy(String anotherLocation) throws IOException {
        // TODO: copy folder

        // create folders
        // copy all files to new folder
    }

    private void copyInternal(java.io.File folder, String newLocation) throws IOException{
        
    }

    @Override
    public void move(String newPath) throws IOException{
        // make a copy then delete the old one
        this.copy(newPath);
        this.delete();
    }

    @Override
    public Boolean hasChild() {
        return true;
    }

    private void deleteFiles(java.io.File folder) {
        for (java.io.File item : folder.listFiles()) {
            // item is folder
            if(item.isDirectory()) {
                deleteFiles(item);
            }
            // item is file
            item.delete();
        }
    }

    @Override
    public void create() {
        // create folder
        if(_Path.contains("/")) {   // folder and sub folders
            _Item.mkdirs();
        } else {                    // single folder
            _Item.mkdir();
        }

        // TODO: create multi folders, separator |
    }

    @Override
    public String getContent() throws IOException {
        throw new UnsupportedOperationException("File only.");
    }

    @Override
    public void zip() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unzip() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
