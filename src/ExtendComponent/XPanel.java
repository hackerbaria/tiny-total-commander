/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import core.XFile;
import utils.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    private Boolean _ftpMode;
    private FtpResource _ftpResource;
    
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

    public void setftpMode(Boolean value){
        _ftpMode = value;
    }

    public void set_ftpResource(FtpResource _ftpResource) {
        this._ftpResource = _ftpResource;
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

    /*
     *   xu ly su kien nhan focus
     */
    public void focusGained(FocusEvent e) {
       // displayMessage("Focus gained", e);       
        addGlobalFocusEvent(new XPanelEvent(this, true));

    }
    /*
     *  xu ly su kien mat focus
     */
    public void focusLost(FocusEvent e) {
       // displayMessage("Focus lost", e);        
        addGlobalFocusEvent(new XPanelEvent(this, false));
    }  

    /*
     *  thiet lap duong dan hien hanh
     */
     public void set_currentPath(String path) {
        this._currentPathLabel.setText(path);
    }

     /*
     *  lay duong dan hien hanh
     */
     public String get_currentPath() {
        return this._currentPathLabel.getText();
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

        _ftpMode = false;
        
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
        _dirTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        _dirTable.addFocusListener(this);
        _dirTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                dirTableRowClicked(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

    // TODO: multiple tabs - Chánh

    // TODO: support rename on panel - Chánh

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
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Vector v = new Vector();
        if(_ftpMode)
        {
            v = _ftpResource.getAllFiles(pathname);
        }
        else{
            v = FileResource.listFiles(pathname);            
        }
        if(v.size() > 0) {
                removeAllRow();
                _model.fillData(v);
            }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Xoa tat ca cac row trong table hien danh sach file
     */
    private void removeAllRow() {
        while(_model.getRowCount() > 0) {
           _model.delRow(0);
        }
    }

    /*
     * ham tra ve mang cac filename dang bi select
     */
    public ArrayList<String> getSelectedItems() {
        ArrayList<String> selectedItems = new ArrayList<String>();
        
        int[] selectedRows = _dirTable.getSelectedRows();
        for(int row : selectedRows) {
            TextImageObj tmodel = (TextImageObj) _model.getValueAt(row, 0);
            String name = (String) tmodel.getText();
            String ext = (String) _model.getValueAt(row, 1);
            if(ext.length() > 1) {
                selectedItems.add(_currentPathLabel.getText() + name + "." + ext);
            } else {
                selectedItems.add(_currentPathLabel.getText() + name);
            }
        }
        return selectedItems;
    }

    /*
     * lay duong dan day du cua file duoc chon
     */
    public String getSelectedItemPath() {
       int rowSelectedIndex = _dirTable.getSelectedRow();
       TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
       String name = (String) tmodel.getText();
       String extention = (String) _model.getValueAt(rowSelectedIndex, 1);
       if(extention.length() > 1) {
           return _currentPathLabel.getText() + name + "." + extention;
       }
       
       return _currentPathLabel.getText() + name;
    }

    public String getSelectedItemFileName(Boolean withExtension) {
        int rowSelectedIndex = _dirTable.getSelectedRow();
        TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
        String name = (String) tmodel.getText();
        String ext = (String) _model.getValueAt(rowSelectedIndex, 1);
        if(ext.length() > 1 && withExtension == true) {
           return name + "." + ext;
        }
        return name; // without extension
    }
    
    /**
     * Xu ly su kien click vao mot dong trong table hien danh sach file
     * @param e
     */
    private void dirTableRowClicked(MouseEvent e) {
        if(e.getClickCount() == 1) {        // single click
            // ignore :)
            
        } else if(e.getClickCount() == 2) { // double click
            
              if(_ftpMode)
                  ProcessRowClickInFtpMode();
              else
                  ProcessRowClickInNormalMode();
        }
    }
    
    /*
     * xu ly double click tren row o che do ftpmode
    */


    private void ProcessRowClickInFtpMode()
    {
        // get selected row
           int rowSelectedIndex = _dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           if(name.equals("[...]")){
               _ftpResource.goUp();
               refreshTable(_ftpResource.get_workingDir());
               _currentPathLabel.setText(_ftpResource.get_workingDir());
           }
           else{
               _ftpResource.changeDir(name);
               refreshTable(_ftpResource.get_workingDir());
               _currentPathLabel.setText(_ftpResource.get_workingDir());
           }
    }

    /*
     *  xu ly double click tren row o che do binh thuong ( khong phai ftp)
     */
    private void ProcessRowClickInNormalMode()
    {
          // get selected row
           int rowSelectedIndex = _dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           String fullpath = _currentPathLabel.getText();
           
           if(name.equals("[...]")) {
               // double click on [...] => up one level
               String parent = FileHelper.geParentPath(fullpath);
               refreshTable(parent);
               _currentPathLabel.setText(parent + "\\");
           } else {
               // double click on file or folder ...
               fullpath = fullpath + "\\" + name;
               String extention = (String) _model.getValueAt(rowSelectedIndex, 1);
               if(extention.length() > 1) {
                   fullpath = fullpath + "." + extention;
               }

               File fileSelected = new File(fullpath);
               if(fileSelected.isDirectory()) {
                 if(FileHelper.isFolder(fullpath)) {
                   // double click on folder => go inside
                   refreshTable(fullpath);
                   _currentPathLabel.setText(fullpath + "\\");
               }
               }else {
                    try {
                        // double click on file => lauch file
                        XFile.execute(fullpath);
                    } catch (IOException ex) {
                        Logger.getLogger(XPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
           }
    }

    public void selectAllRow()
    {
        _dirTable.selectAll();
    }
    public void DeSelectAll()
    {
        _dirTable.clearSelection();
    }
}
