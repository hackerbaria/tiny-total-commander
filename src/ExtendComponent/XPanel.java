/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import utils.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 *
 * @author pmchanh
 */
public class XPanel extends JPanel implements FocusListener {
    public XPanel() {
        super();
        initlizeComponent();
        readDiskList();
        setFocusable(false);
    }

    private JPanel _head;
    private JPanel _body;
    private JPanel _foot;

    private JComboBox _diskList;
    private JLabel _diskInfo;

    private JTabbedPane _tabPane;
    private JPanel _currentPathPanel;
    private JLabel _currentPathLabel;
    private JPanel _containPane;
    private XTable _dirTable;
    private XTableModel _model;
    private JScrollPane _scrollpane;
    private Color _focusColor = Color.BLUE;
    private Color _lostfocusColor = Color.decode("#66CCFF");
    
 // <editor-fold defaultstate="collapsed" desc="Properties">
    public JPanel getBody() {
        return _body;
    }

    public JPanel getContainPane() {
        return _containPane;
    }

    public JLabel getCurrentPathLabel() {
        return _currentPathLabel;
    }

    public JPanel getCurrentPathPanel() {
        return _currentPathPanel;
    }

    public XTable getDirTable() {
        return _dirTable;
    }

    public JLabel getDiskInfo() {
        return _diskInfo;
    }

    public JComboBox getDiskList() {
        return _diskList;
    }

    public Color getFocusColor() {
        return _focusColor;
    }

    public JPanel getFoot() {
        return _foot;
    }

    public JPanel getHead() {
        return _head;
    }

    public Color getLostfocusColor() {
        return _lostfocusColor;
    }

    public XTableModel getModel() {
        return _model;
    }

    public JScrollPane getScrollpane() {
        return _scrollpane;
    }

    public JTabbedPane getTabPane() {
        return _tabPane;
    }  
    
    // </editor-fold>

    public void addFocusListener(XPanelEventListener pel) {
        this.listenerList.add(XPanelEventListener.class, pel);
    }

    private void addGlobalFocusEvent(XPanelEvent pe) {
        Object[] listeners = this.listenerList.getListenerList();
        for(int i = 0; i < listeners.length; i+= 2)
            if(listeners[i] == XPanelEventListener.class)
                ((XPanelEventListener)listeners[i+1]).myEventOccurred(pe);
    }
    
    public void focusGained(FocusEvent e) {
       // displayMessage("Focus gained", e);       
        addGlobalFocusEvent(new XPanelEvent(this, true));

    }
    public void focusLost(FocusEvent e) {
       // displayMessage("Focus lost", e);        
        addGlobalFocusEvent(new XPanelEvent(this, false));
    }  

    /**
     * Tô background cho biet component nay dang duoc focus
     */
    public void focusRender() {
        _currentPathPanel.setBackground(_focusColor);
        _dirTable.setRowSelectionAllowed(true);
    }

    /**
     * Tô lai mau background cho biet component mat focus
     */
    public void lostfocusRender() {
        _currentPathPanel.setBackground(_lostfocusColor);
        _dirTable.setRowSelectionAllowed(false);
    }

    public void initlizeComponent() {
        this.setLayout(new BorderLayout());

        _head = new JPanel(new FlowLayout(FlowLayout.LEFT));
        _body = new JPanel(new BorderLayout());
       // foot = new JPanel(new BorderLayout());

        this.add(_head, BorderLayout.NORTH);
        this.add(_body, BorderLayout.CENTER);
       // this.add(foot, BorderLayout.SOUTH);

          _diskList = new JComboBox();
          _diskList.setFont(new Font("Arial", Font.PLAIN,10));
          _diskList.addFocusListener(this);
          _diskList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                diskSelectedChange(e);
            }
        });
        
        _diskInfo = new JLabel("label");
        _head.add(_diskList);
        _head.add(_diskInfo);

        _tabPane = new JTabbedPane();
        _tabPane.setLayout(new BorderLayout());
        _tabPane.setBackground(Color.WHITE);
        _tabPane.addFocusListener(this);

        
        _containPane = new JPanel(new BorderLayout());

        _currentPathPanel = new JPanel(new BorderLayout());
        _currentPathPanel.setBackground(_lostfocusColor);
        _currentPathLabel = new JLabel("C");
        _currentPathLabel.setForeground(Color.WHITE);
        _currentPathPanel.add(_currentPathLabel, BorderLayout.CENTER);
        
        _containPane.add(_currentPathPanel, BorderLayout.NORTH);
        

        String[] columnName = {"Name","Ext","Size","Date","Attr"};
        Object[] obj = {new TextImageObj("", null),"2","3","4","5"};
        Vector temp = new Vector();
        temp.add(obj);
        _model = new XTableModel(temp, columnName);
        _dirTable = new XTable(_model);
        _dirTable.setRowHeight(20);
        _dirTable.setShowGrid(false);
        _dirTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _dirTable.addFocusListener(this);
        
        _dirTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                dirTableRowClicked(e);
            }
        });

        setColumnWidth(0, 200);
        _scrollpane = new JScrollPane(_dirTable);
        _containPane.add(_scrollpane, BorderLayout.CENTER);
        _tabPane.addTab("Tab",_containPane);
        _tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        _body.add(_tabPane);

      //  foot.setBackground(Color.gray);
    }

    /**
     *
     * @param index
     * @param width
     */
    private void setColumnWidth(int index, int width) {
        TableColumn col = _dirTable.getColumnModel().getColumn(index);
        col.setPreferredWidth(width);
    }

    /**
     *
     * @param e
     */
    private void diskSelectedChange(ActionEvent e) {
        // get path
        String selectedDisk = (String) _diskList.getSelectedItem();
        int u = selectedDisk.indexOf("[-");
        int v = selectedDisk.indexOf("-]");
        String path = selectedDisk.substring(u+2, v) + ":\\"; 
        //currentPathLabel.setText(" " + path + "*.*");
        try {

             refreshTable(path);
             // lấy thông tin của ổ đĩa vừa chọn
             _diskInfo.setText(DiskResource.getInfo(path));
             // set lại đường dẫn hiện hành trong tab pane
             _currentPathLabel.setText(path);
        }
        catch(Exception ex) {
            // lấy ổ đĩa trong đường dẫn hiện hành ở label màu xanh trong tabpane
            String s = _currentPathLabel.getText().substring(0, 1);
            // nếu drive không đọc được thì gán tên ổ đĩa đang được select lại
            // thành ổ đĩa trước khi select change
            _diskList.setSelectedIndex(findIndex(s));
            // xuất thông báo lỗi không đọc được ổ đĩa
            MsgboxHelper.showError("Drive not found!");
        }
    }

    //
    /**
     * Tìm chỉ số của ổ đĩa trong danh sách ổ đĩa trong combobox dirList
     */
    private int findIndex(String text) {
        for(int i = 0; i < _diskList.getItemCount(); i++) {
            String item = (String)_diskList.getItemAt(i);
            if(item.indexOf(text) > 0)
                return i;
        }
        return 0;
    }

    /**
     * Doc danh sach drive trong may tinh
     */
    private void readDiskList() {
        String[] disks = DiskResource.getAll();
        _diskList.removeAllItems();
        for(int i = 0; i < disks.length; i++)
            _diskList.insertItemAt(disks[i], i);
        _diskList.setSelectedIndex(0);
    }

    /**
     * Refresh lai table hien danh sach file sau moi lan chon item
     * @param pathname
     */
    public void refreshTable(String pathname) {
        Vector v = FileResource.listFiles(pathname);
        if(v.size() > 0) {
            removeAllRow();
            _model.fillData(v);
        }        
    }

    /**
     * Xoa tat ca cac row trong table hien danh sach file
     */
    private void removeAllRow() {
        while(_model.getRowCount() > 0) {
           _model.delRow(0);
        }
    }
    
    /**
     * Xu ly su kien click vao mot dong trong table hien danh sach file
     * @param e
     */
    private void dirTableRowClicked(MouseEvent e) {
        if(e.getClickCount() == 1) {        // single click
            // ignore :)
            
        } else if(e.getClickCount() == 2) { // double click

           // get selected row
           int rowSelectedIndex = _dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           String fullpath = _currentPathLabel.getText();

           if(name.equals("[...]")) {
               int u = fullpath.lastIndexOf("\\\\");
               String parent = fullpath.substring(0,u);
               refreshTable(parent);
               _currentPathLabel.setText(parent + "\\");
           } else {
               fullpath = fullpath + "\\" + name;
               String extention = (String) _model.getValueAt(rowSelectedIndex, 1);

               if(extention.length() > 1) {
                   fullpath = fullpath + "." + extention;
               }
               
               File fileSelected = new File(fullpath);
               if(fileSelected.isDirectory()) {
                   refreshTable(fullpath);
                   _currentPathLabel.setText(fullpath + "\\");
               } else {
                   try {
                       Desktop.getDesktop().open(fileSelected);
                   } catch (IOException ex) {
                       Logger.getLogger(XPanel.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }
           }
        }
    } 
}
