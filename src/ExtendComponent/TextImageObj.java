/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author pmchanh
 */
public class TextImageObj {


    private String text;
    public String getText()
    {
        return text;
    }
    private Icon icon;
    public Icon getIcon()
    {
        return icon;
    }

    public TextImageObj(String Text, Icon IconData)
    {
        text = Text;
        icon = IconData;
    }
    public static TextImageObj createEmptyObj()
    {
        Icon icon = new ImageIcon("/Resource/up.gif");
        return new TextImageObj("[...]",icon);
    }

}
