/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.util.EventListener;

/**
 * Component event listener
 * @author pmchanh
 */
public interface XComponentEventListener extends EventListener {
    public void myEventOccurred(XComponentEvent evt);
    public void myTableEventOccurred(XComponentEvent evt);
   
}

