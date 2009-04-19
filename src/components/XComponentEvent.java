/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.util.EventObject;

/**
 * Component event
 * @author pmchanh
 */
public class XComponentEvent extends EventObject {
    private Object _obj;
    private Boolean _isFocus;

    public XComponentEvent(Object obj, Boolean isFocus) {
        super(obj);
        _obj = obj;
        _isFocus = isFocus;
    }

    public Object getObj() {
        return _obj;
    }

    public Boolean getIsFocus() {
        return _isFocus;
    }
}
