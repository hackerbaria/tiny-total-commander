/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import javax.swing.*;
/**
 *
 * @author @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class MsgboxHelper
{
    /**
     * Show an error msg to user
     */
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, "Tiny Total Commander", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Cofirm user choice
     */
    public static Boolean confirm(String msg) {
        int option = JOptionPane.showConfirmDialog(null, msg, "Tiny Total Commander", JOptionPane.YES_NO_CANCEL_OPTION);
        if(option == JOptionPane.YES_OPTION)
            return true;

        return false; // NO_OPTION or CANCEL_OPTION
    }

    /**
     * Inform a msg to user
     */
    public static void inform(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Tiny Total Commander", JOptionPane.INFORMATION_MESSAGE);
    }
}
