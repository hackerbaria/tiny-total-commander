/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Forms;
import javax.swing.*;
/**
 *
 * @author pmchanh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
       //frmCopy form = new frmCopy("ss");
       MainForm form = new MainForm();
       form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       form.setExtendedState(JFrame.MAXIMIZED_BOTH);

       //form.pack();
       form.setVisible(true);
    }

}
