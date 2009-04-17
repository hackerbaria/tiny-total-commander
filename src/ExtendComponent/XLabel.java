/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import javax.swing.JLabel;

/**
 *
 * @author pmchanh
 */
public class XLabel extends JLabel{
    private Object _tag;
    public XLabel (){
        super();
    }

    public Object getTag() {
        return _tag;
    }

    public void setTag(Object _tag) {
        this._tag = _tag;
    }





}
