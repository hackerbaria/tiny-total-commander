/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.awt.Component;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class ThemeManager {
    public static void setSystemDefault(Component com) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(com);
    }
}
