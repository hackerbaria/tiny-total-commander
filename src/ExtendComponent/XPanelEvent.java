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


    public XPanelEvent(XPanel _obj, Boolean _isFocus) {
        super(_obj);
        this._obj = _obj;
        this._isFocus = _isFocus;
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
