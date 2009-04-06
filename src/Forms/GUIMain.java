/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Forms;
import javax.swing.*;
/**
 *
 * @author Spazee
 */
public class GUIMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       frmNewFile form = new frmNewFile();
       form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       form.setVisible(true);
       //form.show();
    }
}
