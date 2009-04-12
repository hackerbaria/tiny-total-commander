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
    public XPanel()
    {
        super();
        initlizeComponent();
        readDiskList();
        setFocusable(false);
    }

    private JPanel head;
    private JPanel body;
    private JPanel foot;

    private JComboBox diskList;
    private JLabel diskInfo;

    private JTabbedPane tabPane;
    private JPanel currentPathPanel;
    private JLabel currentPathLabel;
    private JPanel containPane;
    private XTable dirTable;
    private XTableModel model;
    private JScrollPane scrollpane;
    private Color focusColor = Color.BLUE;
    private Color lostfocusColor = Color.decode("#66CCFF");
    
 // <editor-fold defaultstate="collapsed" desc="Properties">
    public JPanel getBody() {
        return body;
    }

    public JPanel getContainPane() {
        return containPane;
    }

    public JLabel getCurrentPathLabel() {
        return currentPathLabel;
    }

    public JPanel getCurrentPathPanel() {
        return currentPathPanel;
    }

    public XTable getDirTable() {
        return dirTable;
    }

    public JLabel getDiskInfo() {
        return diskInfo;
    }

    public JComboBox getDiskList() {
        return diskList;
    }

    public Color getFocusColor() {
        return focusColor;
    }

    public JPanel getFoot() {
        return foot;
    }

    public JPanel getHead() {
        return head;
    }

    public Color getLostfocusColor() {
        return lostfocusColor;
    }

    public XTableModel getModel() {
        return model;
    }

    public JScrollPane getScrollpane() {
        return scrollpane;
    }

    public JTabbedPane getTabPane() {
        return tabPane;
    }  
    
    // </editor-fold>

    public void addFocusListener(XPanelEventListener pel)
    {
        this.listenerList.add(XPanelEventListener.class, pel);
    }

    private void addGlobalFocusEvent(XPanelEvent pe)
    {
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

    // to^ background cho biet component nay dang duoc focus
    public void focusRender()
    {
        currentPathPanel.setBackground(focusColor);
        dirTable.setRowSelectionAllowed(true);
    }

    // to^ lai mau background cho biet component mat focus
    public void lostfocusRender()
    {
        currentPathPanel.setBackground(lostfocusColor);
        dirTable.setRowSelectionAllowed(false);
    }

    public void initlizeComponent() {
        this.setLayout(new BorderLayout());

        head = new JPanel(new FlowLayout(FlowLayout.LEFT));
        body = new JPanel(new BorderLayout());        
       // foot = new JPanel(new BorderLayout());

        this.add(head, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
       // this.add(foot, BorderLayout.SOUTH);

          diskList = new JComboBox();
          diskList.setFont(new Font("Arial", Font.PLAIN,10));
          diskList.addFocusListener(this);
          diskList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                diskSelectedChange(e);
            }
        });
        
        diskInfo = new JLabel("label");
        head.add(diskList);
        head.add(diskInfo);

        tabPane = new JTabbedPane();
        tabPane.setLayout(new BorderLayout());
        tabPane.setBackground(Color.WHITE);
        tabPane.addFocusListener(this);

        
        containPane = new JPanel(new BorderLayout());

        currentPathPanel = new JPanel(new BorderLayout());
        currentPathPanel.setBackground(lostfocusColor);
        currentPathLabel = new JLabel("C");
        currentPathLabel.setForeground(Color.WHITE);
        currentPathPanel.add(currentPathLabel, BorderLayout.CENTER);       
        
        containPane.add(currentPathPanel, BorderLayout.NORTH);
        

        String[] columnName = {"Name","Ext","Size","Date","Attr"};
        Object[] obj = {new TextImageObj("", null),"2","3","4","5"};
        Vector temp = new Vector();
        temp.add(obj);
        model = new XTableModel(temp, columnName);
        dirTable = new XTable(model);
        dirTable.setRowHeight(20);
        dirTable.setShowGrid(false);
        dirTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        dirTable.addFocusListener(this);
        
        dirTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                dirTableRowClicked(e);
            }
        });

        setColumnWidth(0, 200);
        scrollpane = new JScrollPane(dirTable);
        containPane.add(scrollpane, BorderLayout.CENTER);
        tabPane.addTab("Tab",containPane);
        tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        body.add(tabPane);

      //  foot.setBackground(Color.gray);
    }

    private void setColumnWidth(int index, int width) {
        TableColumn col = dirTable.getColumnModel().getColumn(index);
        col.setPreferredWidth(width);
    }

    private void diskSelectedChange(ActionEvent e) {
        // get path
        String selectedDisk = (String) diskList.getSelectedItem();        
        int u = selectedDisk.indexOf("[-");
        int v = selectedDisk.indexOf("-]");
        String path = selectedDisk.substring(u+2, v) + ":\\"; 
        //currentPathLabel.setText(" " + path + "*.*");
        try {

             refreshTable(path);
             // lấy thông tin của ổ đĩa vừa chọn
             diskInfo.setText(DiskResource.getInfo(path));
             // set lại đường dẫn hiện hành trong tab pane
             currentPathLabel.setText(path);
        }
        catch(Exception ex) {
            // lấy ổ đĩa trong đường dẫn hiện hành ở label màu xanh trong tabpane
            String s = currentPathLabel.getText().substring(0, 1);
            // nếu drive không đọc được thì gán tên ổ đĩa đang được select lại
            // thành ổ đĩa trước khi select change
            diskList.setSelectedIndex(findIndex(s));
            // xuất thông báo lỗi không đọc được ổ đĩa
            MsgboxHelper.showError("Drive not found!");
        }
    }

    // tìm chỉ số của ổ đĩa trong danh sách ổ đĩa trong combobox dirList
    private int findIndex(String text)
    {
        for(int i = 0; i < diskList.getItemCount(); i++)
        {
            String item = (String)diskList.getItemAt(i);
            if(item.indexOf(text) > 0)
                return i;
        }
        return 0;
    }
// doc danh sach drive trong may tinh
    private void readDiskList()
    {
        String[] disks = DiskResource.getAll();
        diskList.removeAllItems();
        for(int i = 0; i < disks.length; i++)
            diskList.insertItemAt(disks[i], i);
        diskList.setSelectedIndex(0);
    }

    /**
     * Refresh lai table hien danh sach file sau moi lan chon item
     * @param pathname
     */
    private void refreshTable(String pathname) {
        Vector v = FileResource.listFiles(pathname);
        if(v.size() > 0) {
            removeAllRow();
            model.fillData(v);
        }        
    }

    /**
     * Xoa tat ca cac row trong table hien danh sach file
     */
    private void removeAllRow() {
        while(model.getRowCount() > 0) {
           model.delRow(0);
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
           int rowSelectedIndex = dirTable.getSelectedRow();
           TextImageObj tmodel = (TextImageObj) model.getValueAt(rowSelectedIndex, 0);
           String name = (String) tmodel.getText();
           String fullpath = currentPathLabel.getText();

           if(name.equals("[...]")) {
               int u = fullpath.lastIndexOf("\\\\");
               String parent = fullpath.substring(0,u);
               refreshTable(parent);
               currentPathLabel.setText(parent + "\\");
           } else {
               fullpath = fullpath + "\\" + name;
               String extention = (String) model.getValueAt(rowSelectedIndex, 1);

               if(extention.length() > 1) {
                   fullpath = fullpath + "." + extention;
               }
               
               File fileSelected = new File(fullpath);
               if(fileSelected.isDirectory()) {
                   refreshTable(fullpath);
                   currentPathLabel.setText(fullpath + "\\");
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
