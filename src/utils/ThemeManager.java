/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import Forms.MainForm;
import java.awt.Component;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class ThemeManager {

    private static String currLookAndFeelName = "";
    private static String currThemeName = "";

    private static String currLookAndFeel = "";

    public static void setSystemDefault(Component com) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(com);
        currLookAndFeelName = "System";
    }

    public static Boolean changeLookAndFeel(String newLookAndFeelName, String newThemeName){
        if(!currLookAndFeelName.equals(newLookAndFeelName) ||
                !currThemeName.equals(newThemeName)){

            if (newLookAndFeelName.equals("Metal")) {
                currLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                currLookAndFeelName = "Metal";
            }

            else if (newLookAndFeelName.equals("System")) {
                currLookAndFeel = UIManager.getSystemLookAndFeelClassName();
                currLookAndFeelName = "System";
            }

            else if (newLookAndFeelName.equals("Motif")) {
                currLookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                currLookAndFeelName = "Motif";
            }

            else if (newLookAndFeelName.equals("GTK")) {
                currLookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                currLookAndFeelName = "GTK";
            }

            else if (newLookAndFeelName.equals("Windows")) {
                currLookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                currLookAndFeelName = "Windows";
            }

            else if (newLookAndFeelName.equals("Nimbus")){
                currLookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
                currLookAndFeelName = "Nimbus";
            }

            else {
                currLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                currLookAndFeelName = "Metal";
            }

            try {

                UIManager.setLookAndFeel(currLookAndFeel);

                // If L&F = "Metal", set the theme

                if (currLookAndFeel.equals("Metal")) {
                  if (newThemeName.equals("Ocean")){
                     MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                  }
                  else {
                     MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                  }

                  currThemeName = newThemeName;
                  UIManager.setLookAndFeel(new MetalLookAndFeel());
                }

            }

            catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + currLookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            }

            catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + currLookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            }

            catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + currLookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }


}
