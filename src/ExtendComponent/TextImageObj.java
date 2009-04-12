/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Đây là đối tượng sẽ được add vào cell(0) của table danh sách tập tin
 * vì ô này cần hiển thị icon của tập tin và tên của tập tin
 * @author pmchanh
 */
public class TextImageObj {
    private String _text;
    private Icon _icon;
    
    public String getText() {
        return _text;
    }

    public Icon getIcon() {
        return _icon;
    }

    public TextImageObj(String text, Icon iconData) {
        _text = text;
        _icon = iconData;
    }

    public static TextImageObj createEmptyObj() {
        Icon icon = new ImageIcon("Resource/up.gif");
        return new TextImageObj("[...]",icon);
    }
}
