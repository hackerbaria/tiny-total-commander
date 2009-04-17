/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author pmchanh
 */
public class XTable extends  JTable{

    private ArrayList removedColumn;
    public XTable (ExtendComponent.XTableModel model)
    {
        super(model);
        removedColumn = new ArrayList();
        // render cell 0 để có thể hiển thị được đối tượng TextImageObj trong cell này
        getColumn(this.getColumnName(0)).setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               XLabel label = new XLabel();
               TextImageObj obj = (TextImageObj) value;
               label.setText(obj.getText());
               label.setIcon(obj.getIcon());
               label.setTag(obj.getExtend());
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

        // render cell 1
        /*getColumn(this.getColumnName(1)).setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setText((String)value);
                label.setOpaque(true);
                label.setForeground(table.getForeground());
                label.setBackground(table.getBackground());
                 if(isSelected){
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
               }
                return label;
            }
        });*/

         // render cell 2
      /*  getColumn(this.getColumnName(2)).setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setText((String)value);
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

         // render cell 3
        getColumn(this.getColumnName(3)).setCellRenderer(new TableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setText((String)value);
                label.setOpaque(true);
                label.setForeground(table.getForeground());
                label.setBackground(table.getBackground());
                 if(isSelected){
                    label.setBackground(table.getSelectionBackground());
                    label.setForeground(table.getSelectionForeground());
               }
                return label;
            }
        });*/
    }
   
    public void hideAll()
    {
        if(!removedColumn.isEmpty())
            return;
        int col = this.getModel().getColumnCount();
        for(int i = col-1; i > 0; i--)
        {
            TableColumn column = getColumnModel().getColumn(i);
            removeColumn(column);
            removedColumn.add(column);
        }
    }

    public void showAll()
    {
         if(removedColumn.size() == 0)
             return;
         for(int i = removedColumn.size()-1; i >= 0;i--)
             addColumn((TableColumn) removedColumn.get(i));
         removedColumn.clear();

    }
}
