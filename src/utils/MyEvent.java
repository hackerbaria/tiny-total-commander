/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.ArrayList;
import java.util.EventObject;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class MyEvent extends EventObject {
    private ArrayList<String> _dataList = new ArrayList<String>();

    public MyEvent(String data) {
        super(data);
        _dataList.add(data);
    }

    public ArrayList<String> getDataList() {
        return _dataList;
    }

    public String getData() {
        return _dataList.get(0);
    }

    public void addData(String data) {
        _dataList.add(data);
    }
}
