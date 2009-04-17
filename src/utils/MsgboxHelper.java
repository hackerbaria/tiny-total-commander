/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import javax.swing.*;
/**
 * Message box util
 * @author Hung Cuong <nhc.hcmuns at gmail.com>
 */
public class MsgboxHelper
{
    /**
     * Show an error msg to user
     */
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, Konstant.APP_NAME, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Cofirm user choice
     */
    public static Boolean confirm(String msg) {
        int option = JOptionPane.showConfirmDialog(null, msg, Konstant.APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION);
        if(option == JOptionPane.YES_OPTION)
            return true;

        return false; // NO_OPTION or CANCEL_OPTION
    }

    /**
     * Inform a msg to user
     */
    public static void inform(String msg) {
        JOptionPane.showMessageDialog(null, msg, Konstant.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
    }
}
