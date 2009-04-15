/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import core.XFile;
import javax.swing.event.ChangeEvent;
import utils.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeListener;
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
    private Color _focusColor = Color.BLUE;
    private Color _lostfocusColor = Color.decode("#66CCFF");
    private XTab _activeTab;
    
 // <editor-fold defaultstate="collapsed" desc="Properties">
    public JPanel getBody() {
        return _body;
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
    
    public XTab getTab(int index)
    {
        return (XTab)_tabPane.getComponentAt(index);
    }

    public XTab getActiveTab()
    {
        return getTab(getCurrentIndex());
    }

    public int getCurrentIndex()
    {
        return _tabPane.getSelectedIndex();
    }
    // </editor-fold>

     public void addFocusListener(XComponentEventListener pel) {
        this.listenerList.add(XComponentEventListener.class, pel);
    }
    /*
     *   xu ly su kien nhan focus
     */
    public void focusGained(FocusEvent e) {
       // displayMessage("Focus gained", e);
        addGlobalFocusEvent(new XComponentEvent(this, true));
        
    }
    private void addGlobalFocusEvent(XComponentEvent pe) {
        Object[] listeners = this.listenerList.getListenerList();
        for(int i = 0; i < listeners.length; i+= 2)
            if(listeners[i] == XComponentEventListener.class)
                ((XComponentEventListener)listeners[i+1]).myEventOccurred(pe);
    }
    /*
     *  xu ly su kien mat focus
     */
    public void focusLost(FocusEvent e) {
       // displayMessage("Focus lost", e);
        addGlobalFocusEvent(new XComponentEvent(this, true));        
    }  
/**
     * Tô background cho biet component nay dang duoc focus
     */
    public void focusRender() {
       ((XTab)_tabPane.getComponentAt(
                 _tabPane.getSelectedIndex())).focusRender();
    }

    /**
     * Tô lai mau background cho biet component mat focus
     */
    public void lostfocusRender() {
       ((XTab)_tabPane.getComponentAt(
                 _tabPane.getSelectedIndex())).lostfocusRender();
    }
    /**
     *  thiet lap duong dan hien hanh
     */
    public void setCurrentPath(String path) {

        this._tabPane.setTitleAt(_tabPane.getSelectedIndex(),
                FileHelper.getParentName(path));
        ((XTab)_tabPane.getComponentAt(
                _tabPane.getSelectedIndex())).setCurrentPath(path);
    }

     /**
      *  lay duong dan hien hanh
      */
     public String getCurrentPath() {
         return getActiveTab().getCurrentPath();
         
    }

    /**
     * Initialize component
     */
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
        initTab();
        /*_tabPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

            }
        });*/
        _tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        _body.add(_tabPane);

      //  foot.setBackground(Color.gray);
    }
    public void createNewTab()
    {
        if(_activeTab.getftpMode())
        {
            initTab();
            setCurrentPath("C:\\");
            refresh("C:\\");            
        }
        else {
            XTab newTab = new XTab();
            newTab.addFocusListener(new XComponentEventListener() {

                public void myEventOccurred(XComponentEvent evt) {
                   // addGlobalFocusEvent(new XComponentEvent(this, evt.get_isFocus()));
                    dummy(evt.get_isFocus());
                }

                public void myTableEventOccurred(XComponentEvent evt) {
                     String path = (String)evt.get_obj();
                     setCurrentPath(path);
                }
            });
            //_tabPane.setTabComponentAt(_tabPane.getTabCount()-1, newTab);

            _tabPane.add(newTab);
            String previousPath = getCurrentPath();
            _activeTab = newTab;

            _tabPane.setTitleAt(_tabPane.getTabCount()-1,
                    FileHelper.getParentName(previousPath));
            _activeTab.setCurrentPath(previousPath);
            refresh(previousPath);
        }
    }

    public void initTab(){
         XTab newTab = new XTab();
        newTab.addFocusListener(new XComponentEventListener() {

            public void myEventOccurred(XComponentEvent evt) {               
                dummy(evt.get_isFocus());               
            }

            public void myTableEventOccurred(XComponentEvent evt) {
               String path = (String)evt.get_obj();
               setCurrentPath(path);
            }
        });
        _activeTab = newTab;
        _tabPane.add(newTab);
        _tabPane.setSelectedComponent(newTab);
    }

    private void dummy(Boolean status)
    {
        addGlobalFocusEvent(new XComponentEvent(this, status));
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

             getActiveTab().refreshTable(path);
             // lấy thông tin của ổ đĩa vừa chọn
             _diskInfo.setText(DiskResource.getInfo(path));
             // set lại đường dẫn hiện hành trong tab pane
             //_currentPathLabel.setText(path);
             setCurrentPath(path);
             
        }
        catch(Exception ex) {
            // lấy ổ đĩa trong đường dẫn hiện hành ở label màu xanh trong tabpane
            //String s = _currentPathLabel.getText().substring(0, 1);
            String s = getCurrentPath().substring(0, 1);
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
   

    public String getSelectedItemFileName(Boolean withExtension) {
        int rowSelectedIndex = getActiveTab()
                                .getDirTable()
                                .getSelectedRow();
        TextImageObj tmodel = (TextImageObj)getActiveTab()
                                .getModel()
                                .getValueAt(rowSelectedIndex, 0);
        String name = (String) tmodel.getText();
        String ext = (String)getActiveTab()
                                .getModel()
                                .getValueAt(rowSelectedIndex, 1);
        if(ext.length() > 1 && withExtension == true) {
           return name + "." + ext;
        }
        return name; // without extension
    }

    public void refresh(String path)
    {
        _activeTab.refreshTable(path);
    }
}
