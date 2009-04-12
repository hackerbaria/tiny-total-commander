/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.EventListener;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public interface MyEventListener extends EventListener{
    public void myEventOccurred(MyEvent evt);
}
