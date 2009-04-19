/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Text image object
 * @author pmchanh
 */
public class TextImageObj {
    private String _text;
    private Icon _icon;
    private Object _extend;
    public String getText() {
        return _text;
    }

    public Icon getIcon() {
        return _icon;
    }

    public TextImageObj(String text, Icon icon, Object extend) {
        _text = text;
        _icon = icon;
        _extend = extend;
    }

    public Object getExtend() {
        return _extend;
    }

    public void setExtend(Object _extend) {
        this._extend = _extend;
    }

    public static TextImageObj createEmptyObj() {
        Icon icon = new ImageIcon("");
        return new TextImageObj("[...]",icon,"");
    }
}
