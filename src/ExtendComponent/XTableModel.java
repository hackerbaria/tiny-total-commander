/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author pmchanh
 * @ref: http://www.coderanch.com/t/333947/Swing-AWT-SWT-JFace/java/Add-Row-with-AbstractTableModel
 */
public class XTableModel extends AbstractTableModel{
    private Vector data;
    private String[] column;
    public XTableModel(Vector _data, String[] _column)
    {
        data = _data;
        column = _column;
    }
    public int getRowCount() {
                return data.size();
            }

            public int getColumnCount() {
                return column.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return ((Object[])data.get(rowIndex))[columnIndex];
            }
            @Override
            public String getColumnName(int col) {
                return column[col];
            }
            public void addRow(Vector row){
                data.addElement(row);
                this.fireTableDataChanged();
               
            }

            public void delRow(int numrow){
                data.remove(numrow);
                this.fireTableDataChanged();
            }
            public void fillData(Vector v)
            {
                data = v;
                this.fireTableDataChanged();
            }

}
