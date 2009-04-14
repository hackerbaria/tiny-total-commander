/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.ArrayList;
import java.util.EventObject;

/**
 *
 * @author pmchanh
 */
public class MySEvent extends EventObject {
     private ArrayList _data;

    public MySEvent(ArrayList data) {
        super(data);
        _data = data;
    }

    public ArrayList getData() {
        return _data;
    }
}
