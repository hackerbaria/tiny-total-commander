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
public class Drive {
    public static ArrayList<Folder> getAllDrives() {
        ArrayList<Folder> driveList = new ArrayList<Folder>();

        java.io.File[] drives = java.io.File.listRoots();
        for(java.io.File drive : drives) {
            driveList.add(new Folder(drive.getPath()));
        }

        return driveList;
    }
}
