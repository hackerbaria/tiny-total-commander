/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.shfarr.ui.plaf.fh.FhLookAndFeel;
import java.awt.Component;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.infonode.gui.laf.InfoNodeLookAndFeel;
import net.sourceforge.napkinlaf.NapkinLookAndFeel;
import org.fife.plaf.Office2003.Office2003LookAndFeel;
import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;

/**
 * Theme manager
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class ThemeManager {
    private static String[] _supportedLnFs = new String[] {
       "Metal",
       "System",
       "Nimbus",
       "Motif",
       "Napkin",
       "Liquid",
       "InfoNode",
       "FH",
       "Office",
       "Substance"
    };

    /**
     * Get supported lnf list
     */
    public static String[] getSupportedLnFs() {
        return _supportedLnFs;
    }

    /**
     * Change look and feel
     */
    public static void changeLookAndFeel(String lafName, Component comp) throws Exception {
        if(lafName.equals("Metal")) {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("System")) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("Nimbus")) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(comp);
            
        } else if(lafName.equals("Motif")) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            SwingUtilities.updateComponentTreeUI(comp);
            
        } else if(lafName.equals("Napkin")) {
            UIManager.setLookAndFeel(NapkinLookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("Liquid")) {
            UIManager.setLookAndFeel(LiquidLookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("InfoNode")) {
            UIManager.setLookAndFeel(InfoNodeLookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("FH")) {
            UIManager.setLookAndFeel(FhLookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("Office")) {
            UIManager.setLookAndFeel(Office2003LookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);

        } else if(lafName.equals("Substance")) {
            UIManager.setLookAndFeel(SubstanceMagmaLookAndFeel.class.getName());
            SwingUtilities.updateComponentTreeUI(comp);
        }

    }


}
