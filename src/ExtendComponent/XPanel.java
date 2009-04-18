/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import java.util.logging.Level;
import java.util.logging.Logger;
import utils.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

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
    private XTab _activeTab;
    
 // <editor-fold defaultstate="collapsed" desc="Properties">
    
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

    /**
     * Add focus listener
     */
    public void addFocusListener(XComponentEventListener pel) {
        this.listenerList.add(XComponentEventListener.class, pel);
    }
     
    /*
     *   xu ly su kien nhan focus
     */
    public void focusGained(FocusEvent e) {
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
        addGlobalFocusEvent(new XComponentEvent(this, false));
    }
    
    /**
     * Tô background cho biet component nay dang duoc focus
     */
    public void focusRender() {
        getActiveTab().focusRender();
    }

    /**
     * Tô lai mau background cho biet component mat focus
     */
    public void lostfocusRender() {
       getActiveTab().lostfocusRender();
    }
    /**
     *  thiet lap duong dan hien hanh
     */
    public void setCurrentPath(String path) {
        this._tabPane.setTitleAt(_tabPane.getSelectedIndex(),
                PathHelper.getParentName(path));
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
      * Get selected item path
      */
     public String getSelectedItemPath() {
         return getActiveTab().getSelectedItemPath();
     }

     /**
      * Get selected items
      */
     public ArrayList<String> getSelectedItems() {
         return getActiveTab().getSelectedItems();
     }

     /**
      * Set ftp mode
      */
     public void setftpMode(Boolean value){
        getActiveTab().setftpMode(value);
     }

     /**
      * Set ftp resource
      */
     public void setFtpResource(FtpResource _ftpResource) {
        getActiveTab().setFtpResource(_ftpResource);
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

    /**
     * Create a new tab
     */
    public void createNewTab()
    {
        if(_activeTab.getftpMode()) {
            initTab();
            setCurrentPath("C:\\");
            refresh("C:\\");
        } else {
            XTab newTab = new XTab();
            newTab.addFocusListener(new XComponentEventListener() {

                public void myEventOccurred(XComponentEvent evt) {
                   // addGlobalFocusEvent(new XComponentEvent(this, evt.getIsFocus()));
                    dummy(evt.getIsFocus());
                }

                public void myTableEventOccurred(XComponentEvent evt) {
                     String path = (String)evt.getObj();
                     setCurrentPath(path);
                }
            });
            //_tabPane.setTabComponentAt(_tabPane.getTabCount()-1, newTab);

            _tabPane.add(newTab);
            String previousPath = getCurrentPath();
            _activeTab = newTab;

            _tabPane.setTitleAt(_tabPane.getTabCount()-1,
                    PathHelper.getParentName(previousPath));
            _activeTab.setCurrentPath(previousPath);
            refresh(previousPath);
        }
    }

    private void initTab(){
        XTab newTab = new XTab();
        newTab.addFocusListener(new XComponentEventListener() {

            public void myEventOccurred(XComponentEvent evt) {               
                dummy(evt.getIsFocus());
            }

            public void myTableEventOccurred(XComponentEvent evt) {
               String path = (String)evt.getObj();
               setCurrentPath(path);
            }
        });
        _activeTab = newTab;
        _tabPane.add(newTab);
        _tabPane.setSelectedComponent(newTab);
    }

    private void dummy(Boolean status) {
        addGlobalFocusEvent(new XComponentEvent(this, status));
    }
    
    private void diskSelectedChange(ActionEvent e) {
        // get path (selected disk)
        if(_activeTab.getftpMode()){
            boolean confirm = utils.MsgboxHelper.confirm("Do you want to disconnect form current ftp server?");
            if (confirm) // OK! disconnect
                disconnectFromFtp(_activeTab.getFtpResource());
            else // NO, no
                return; 
        }
        String path = DiskResource.getSelectedDisk(_diskList);
        try {
             _diskInfo.setText(DiskResource.getInfo(path));
             setCurrentPath(path);
             getActiveTab().refresh(path);
            
        } catch(Exception ex) {
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
     * Refresh panel
     */
    public void refresh(String path){
        _activeTab.refresh(path);
    }

    //<editor-fold defaultstate="collapsed" desc="Set view type">

   /**
     * Set brief view
     */
    public void setBriefView(){
        getActiveTab().setBriefView();
    }

    /**
     * Set full view
     */
    public void setFullView(){
        getActiveTab().setFullView();
    }

    /**
     * Set thumbnail view
     */
    public void setThumbnailView(){
        getActiveTab().setThumbnailView();
    }

    /**
     * Set tree view
     */
    public void setTreeView() {
        getActiveTab().setTreeView();
    }

    //</editor-fold>

    /**
     * disconnect form ftp
     */

    public void disconnectFromFtp(FtpResource ftp){
        try {
            if (ftp != null) {
                ftp.disConnect();
            }
            setftpMode(false);
            refresh("C:\\");
            setCurrentPath("C:\\");
        } catch (Exception ex) {
            Logger.getLogger(XPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * open desktop folder
     */
    public void openDeskTop(){
        String path = _activeTab.openDeskTop();
        setCurrentPath(path);
    }
}
