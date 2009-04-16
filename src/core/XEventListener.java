/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.util.EventListener;

/**
 * Event Listener
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public interface XEventListener extends EventListener{
    /**
     * Event occurred
     * @param evt
     */
    public void myEventOccurred(XEvent evt);
}
