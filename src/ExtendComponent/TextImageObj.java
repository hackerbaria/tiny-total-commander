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
// đây là đối tượng sẽ được add vào cell(0) của table danh sách tập tin
// vì ô này cần hiển thị icon của tập tin và tên của tập tin
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
        Icon icon = new ImageIcon("Resource/up.gif");
        return new TextImageObj("[...]",icon);
    }
}
