/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import core.XFile;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumn;
import utils.PathHelper;
import utils.FileResource;
import utils.FtpResource;

/**
 *
 * @author pmchanh
 */
public class XTab extends JPanel implements FocusListener {

    private JPanel _currentPathPanel;
    private JLabel _currentPathLabel;    
    private XTable _dirTable;
    private XTableModel _model;
    private JScrollPane _scrollpane;
    private Color _focusColor = Color.BLUE;
    private Color _lostfocusColor = Color.decode("#66CCFF");
    private Boolean _ftpMode;
    private FtpResource _ftpResource;


    public void setftpMode(Boolean value){
        _ftpMode = value;
    }
    public Boolean getftpMode(){
        return _ftpMode;
    }
    public void setFtpResource(FtpResource _ftpResource) {
        this._ftpResource = _ftpResource;
    }

    public XTable getDirTable() {
        return _dirTable;
    }

    public XTableModel getModel(){
        return _model;
    }

    public XTab()
    {
        super();
        InitializeComponent();
    }

    public void InitializeComponent()
    {
        this.setLayout(new BorderLayout());
        _ftpMode = false;
        createHeader();
        createBody();
    }

    /**
     * create header tab
     */
    public void createHeader()
    {
        _currentPathPanel = new JPanel(new BorderLayout());
        _currentPathPanel.setBackground(_lostfocusColor);
        _currentPathLabel = new JLabel("C");
        _currentPathLabel.setForeground(Color.WHITE);
        _currentPathPanel.add(_currentPathLabel, BorderLayout.CENTER);

        this.add(_currentPathPanel, BorderLayout.NORTH);
    }

    public void setCurrentPath(String workingDir) {
        _currentPathLabel.setText(workingDir);
    }
    
    public String getCurrentPath(){
        return _currentPathLabel.getText();
    }

    /**
     * create bodytab
     */
    public void createBody()
    {
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
        this.add(_scrollpane, BorderLayout.CENTER);
    }
    /**
     * set column width
     * @param index
     * @param width
     */
    private void setColumnWidth(int index, int width) {
        TableColumn col = _dirTable.getColumnModel().getColumn(index);
        col.setPreferredWidth(width);
    }

     /**
     * Xu ly su kien click vao mot dong trong table hien danh sach file
     * @param e
     */
    private void dirTableRowClicked(MouseEvent e) {
        if(e.getClickCount() == 1) {        // single click
            // ignore :)

        } else if(e.getClickCount() == 2) { // double click
              if(_ftpMode) {
                try {
                    ProcessRowClickInFtpMode();
                } catch (Exception ex) {
                    Logger.getLogger(XTab.class.getName()).log(Level.SEVERE, null, ex);
                }
              } else {
                  ProcessRowClickInNormalMode();
              }
              addTableEvent(new XComponentEvent(getCurrentPath(), true));
        }
    }

    /*
     * xu ly double click tren row o che do ftpmode
    */
    private void ProcessRowClickInFtpMode() throws Exception {
        // get selected row
           int rowSelectedIndex = _dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           if(name.equals("[...]")){
               _ftpResource.goUp();
               refreshTable(_ftpResource.getWorkingDir());
               setCurrentPath(_ftpResource.getWorkingDir());
           } else {
               _ftpResource.changeDir(name);
               refreshTable(_ftpResource.getWorkingDir());
               setCurrentPath(_ftpResource.getWorkingDir());
           }
    }
     /**
     * Refresh lai table hien danh sach file sau moi lan chon item
     * @param pathname
     */
    public void refreshTable(String pathname) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Vector v = new Vector();
        if(_ftpMode) {
            try {
                v = _ftpResource.getAllFiles(pathname);
            } catch (Exception ex) {
                Logger.getLogger(XTab.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
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
    
    /**
     * select all row
     */
    public void selectAllRow() {
        _dirTable.selectAll();
    }

    /**
     * unselect any row in table
     */
    public void deSelectAll() {
        _dirTable.clearSelection();
    }

    /**
     *  xu ly double click tren row o che do binh thuong ( khong phai ftp)
     */
    private void ProcessRowClickInNormalMode()
    {       
          // get selected row
           int rowSelectedIndex = _dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           String fullpath = getCurrentPath();

           if(name.equals("[...]")) {
               // double click on [...] => up one level
               String parent = PathHelper.getParentPath(fullpath);
               refreshTable(parent);
               setCurrentPath(parent);
           } else {
               // double click on file or folder ...
               fullpath = fullpath + name;
               String extention = (String) _model.getValueAt(rowSelectedIndex, 1);
               if(extention.length() > 1) {
                   fullpath = fullpath + "." + extention;
               }

                if(PathHelper.isFolder(fullpath)) {
                    // double click on folder => go inside
                    refreshTable(fullpath);
                    setCurrentPath(fullpath + "\\");
               } else {
                    try {
                        // double click on file => lauch file
                        XFile.execute(fullpath);
                    } catch (IOException ex) {
                        Logger.getLogger(XPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
           }
    }

     /**
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
                selectedItems.add(getCurrentPath() + name + "." + ext);
            } else {
                selectedItems.add(getCurrentPath() + name);
            }
        }
        return selectedItems;
    }

    /**
     * lay duong dan day du cua file duoc chon
     */
    public String getSelectedItemPath() {
       int rowSelectedIndex = _dirTable.getSelectedRow();
       TextImageObj tmodel = (TextImageObj) _model.getValueAt(rowSelectedIndex, 0);
       String name = (String) tmodel.getText();
       String extention = (String) _model.getValueAt(rowSelectedIndex, 1);
       if(extention.length() > 1) {
           return getCurrentPath() + name + "." + extention;
       }

       return getCurrentPath() + name;
    }

    public void addFocusListener(XComponentEventListener pel) {
        this.listenerList.add(XComponentEventListener.class, pel);
    }

    private void addGlobalFocusEvent(XComponentEvent pe) {
        Object[] listeners = this.listenerList.getListenerList();
        for(int i = 0; i < listeners.length; i+= 2)
            if(listeners[i] == XComponentEventListener.class)
                ((XComponentEventListener)listeners[i+1]).myEventOccurred(pe);
    }
    
    private void addTableEvent(XComponentEvent pe) {
        Object[] listeners = this.listenerList.getListenerList();
        for(int i = 0; i < listeners.length; i+= 2)
            if(listeners[i] == XComponentEventListener.class)
                ((XComponentEventListener)listeners[i+1]).myTableEventOccurred(pe);
    }
    
    public void focusGained(FocusEvent e) {
         addGlobalFocusEvent(new XComponentEvent(this, true));
    }

    public void focusLost(FocusEvent e) {
        addGlobalFocusEvent(new XComponentEvent(this, false));
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
}
