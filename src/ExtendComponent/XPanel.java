/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ExtendComponent;

import utils.DiskResource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import utils.FileResource;

/**
 *
 * @author pmchanh
 */
public class XPanel extends JPanel{
    public XPanel()
    {
        super();
        initlizeComponent();
        readDiskList();
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

    public void initlizeComponent()
    {
        this.setLayout(new BorderLayout());

        head = new JPanel(new FlowLayout(FlowLayout.LEFT));
        body = new JPanel(new BorderLayout());        
       // foot = new JPanel(new BorderLayout());

        this.add(head, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
       // this.add(foot, BorderLayout.SOUTH);

          diskList = new JComboBox();
          diskList.setFont(new Font("Arial", Font.PLAIN,10));
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
        
        containPane = new JPanel(new BorderLayout());

        currentPathPanel = new JPanel(new BorderLayout());
        currentPathPanel.setBackground(Color.BLUE);
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
    private void setColumnWidth(int index, int width)
    {
        TableColumn col = dirTable.getColumnModel().getColumn(index);
        col.setPreferredWidth(width);
    }
    private void diskSelectedChange(ActionEvent e)
    {
        // get path
        String selectedDisk = (String) diskList.getSelectedItem();
        int u = selectedDisk.indexOf("[-");
        int v = selectedDisk.indexOf("-]");
        String path = selectedDisk.substring(u+2, v) + ":\\";
        diskInfo.setText(DiskResource.getInfo(path));
        //currentPathLabel.setText(" " + path + "*.*");
        currentPathLabel.setText(path);
        refreshTable(path);
    }
    private void readDiskList()
    {
        String[] disks = DiskResource.getAll();
        diskList.removeAllItems();
        for(int i = 0; i < disks.length; i++)
            diskList.insertItemAt(disks[i], i);
        diskList.setSelectedIndex(0);
    }
    private void refreshTable(String pathname)
    {
        removeAllRow();        
        Vector v = FileResource.listFile(pathname);        
        model.fillData(v);
    }
    
    private void removeAllRow()
    {
        while(model.getRowCount() > 0)
           model.delRow(0);
    }

    private void dirTableRowClicked(MouseEvent e) {
           if(e.getClickCount() == 2)
           {
               // get Selected Row
               int rowSelectedIndex = dirTable.getSelectedRow();
               TextImageObj tmodel = (TextImageObj) model.getValueAt(rowSelectedIndex, 0);
               String name = (String) tmodel.getText();
               String fullpath = currentPathLabel.getText() ;
               if(name.equals("[...]"))
               {
                   int u = fullpath.lastIndexOf("\\\\");
                   String parent = fullpath.substring(0,u);
                   refreshTable(parent);
                   currentPathLabel.setText(parent + "\\");
               }
               else {
                   fullpath = fullpath + "\\" + name;
                   String extention = (String) model.getValueAt(rowSelectedIndex, 1);
                   if(extention.length() > 1)
                       fullpath = fullpath + "." + extention;
                   File fileSelected = new File(fullpath);
                   if(fileSelected.isDirectory())
                   {
                       refreshTable(fullpath);
                       currentPathLabel.setText(fullpath + "\\");
                   }
                   else
                   {
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
