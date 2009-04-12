/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.EventObject;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class MyEvent extends EventObject {
    private String _data;

    public MyEvent(String data) {
        super(data);
        _data = data;
    }

    public String getData() {
        return _data;
    }
}
