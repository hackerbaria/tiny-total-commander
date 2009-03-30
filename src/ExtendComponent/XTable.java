/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author pmchanh
 */
public class XTable extends  JTable{
    
    public XTable (XTableModel model)
    {
        super(model);
        // render cell 0 để có thể hiển thị được đối tượng TextImageObj trong cell này
        getColumn(this.getColumnName(0)).setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               JLabel label = new JLabel();
               TextImageObj obj = (TextImageObj) value;
               label.setText(obj.getText());
               label.setIcon(obj.getIcon());
               label.setOpaque(true);
               label.setForeground(table.getForeground());
               label.setBackground(table.getBackground());
               if(isSelected){
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
               }
               return label;
            }
        });        
    }
    
}
