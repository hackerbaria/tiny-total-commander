/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.ArrayList;
import java.util.EventObject;

/**
 * Custom event
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class XEvent extends EventObject {
    /**
     * Data list
     */
    private ArrayList<String> _dataList = new ArrayList<String>();

    public XEvent(String data) {
        super(data);
        _dataList.add(data);
    }

    /**
     * Get data list
     * @return
     */
    public ArrayList<String> getDataList() {
        return _dataList;
    }

    /**
     * Get data (the first item in data list)
     * @return
     */
    public String getData() {
        return _dataList.get(0);
    }

    /**
     * Add data to data list
     * @param data
     */
    public void addData(String data) {
        _dataList.add(data);
    }
}
