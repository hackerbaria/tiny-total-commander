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
public class Misc
{
    public static void showErrorMessageBox(String content)
    {
        JOptionPane.showMessageDialog(null, content, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
