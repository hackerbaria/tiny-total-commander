/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.swing.JButton;

/**
 * Button
 * @author pmchanh
 */
public class XButton extends JButton {
    public XButton(String label, String command) {
        super(label);
        setBorderPainted(false);
        setFocusPainted(false);
        setActionCommand(command);
    }
}
