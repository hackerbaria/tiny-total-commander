/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

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

    /**
     * Create a folder or multiple folders(sub folders)
     * @param path
     * @return
     */
    public static Folder Create(String path){
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
    public void Execute() {
        //TODO: open folder, step inside
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Rename(String newName) {
        java.io.File folder = new java.io.File(_Path);
        folder.renameTo(new java.io.File(newName));
    }

    @Override
    public void Delete() {
        java.io.File folder = new java.io.File(_Path);
        folder.delete();

        //TODO: delete non-emply folder
    }

    @Override
    public void Copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Move(String newPath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
