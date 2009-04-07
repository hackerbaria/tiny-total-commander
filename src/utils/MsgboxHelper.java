/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;
import javax.swing.*;
/**
 *
 * @author pmchanh
 */
public class MsgboxHelper
{
    /**
     * Show error to user
     * @param msg
     */
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Cofirm user choice
     * @param msg
     * @return
     */
    public static int confirm(String msg) {
        return JOptionPane.showConfirmDialog(null, msg);
    }

    /**
     * Inform a msg to user
     * @param msg
     */
    public static void inform(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
