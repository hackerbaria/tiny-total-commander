/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.util.EventObject;

/**
 *
 * @author pmchanh
 */
public class XPanelEvent extends EventObject {
    private XPanel _obj;
    private Boolean _isFocus;

    public XPanelEvent(XPanel obj, Boolean isFocus) {
        super(obj);
        this._obj = obj;
        this._isFocus = isFocus;
    }

    public XPanel get_obj() {
        return _obj;
    }

    public void set_obj(XPanel _obj) {
        this._obj = _obj;
    }

    public Boolean get_isFocus() {
        return _isFocus;
    }

    public void set_isFocus(Boolean _isFocus) {
        this._isFocus = _isFocus;
    }

   
    

}
