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
    private Vector _data;
    private String[] _column;
    
    public XTableModel(Vector data, String[] column){
        _data = data;
        _column = column;
       
    }

    public int getRowCount() {
        return _data.size();
    }

    public int getColumnCount() {
        return _column.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Object[])_data.get(rowIndex))[columnIndex];
    }

    @Override
    public String getColumnName(int col) {
        return _column[col];
    }
    
    public void addRow(Vector row){
        _data.addElement(row);
        this.fireTableDataChanged();
    }

    public void delRow(int numrow){
        _data.remove(numrow);
        this.fireTableDataChanged();

    }
    
    public void fillData(Vector v)
    {
        _data = v;
        this.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
     /*   if (column == 0) {
            return true;
        } else {
            return false;
        }*/
        return false;
    }
   
    @Override
    public void setValueAt(Object obj, int row, int col)
    {
        _data.remove(row);
        _data.addElement(obj);
        this.fireTableCellUpdated(row, col);
    }

    
}
