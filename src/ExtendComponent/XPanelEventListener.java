/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.util.EventListener;

/**
 *
 * @author pmchanh
 */
public interface XPanelEventListener extends EventListener {
    public void myEventOccurred(XPanelEvent evt);
}

