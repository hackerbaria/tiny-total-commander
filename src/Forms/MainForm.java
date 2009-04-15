/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Forms;

import ExtendComponent.*;
import core.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import utils.*;
/**
 *
 * @author pmchanh
 */
public class MainForm extends JFrame implements ActionListener{
    public int width = 700;
    public int height = 500;

    private JPanel headPanel;
    private JPanel footPanel;
    private JPanel mainPanel;
    private BorderLayout borderLayout;    
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    private  FtpResource ftp = null;
    // constructor
    public MainForm()
    {
        super();
        InitializeComponent();
       // readDiskListToCombobox();4

    }

    // <editor-fold defaultstate="collapsed" desc="Initialize component">
    private void InitializeComponent()
    {
        this.setLocation((d.width-width)/2, (d.height-height)/2);
        this.setSize(width, height);
        this.setTitle("Tiny Total Commander");
        
       Container container = this.getContentPane();
       borderLayout = new BorderLayout();
       container.setLayout(borderLayout);

       headPanel = createHeadPanel();
       container.add(headPanel, BorderLayout.NORTH);

       mainPanel = createMainPanel();
       container.add(mainPanel, BorderLayout.CENTER);

       footPanel = createFootPanel();
       container.add(footPanel, BorderLayout.SOUTH);

     /*  addWindowListener(new WindowAdapter() {
            @Override
           public void windowOpened(WindowEvent e)
           {
                leftPanel.requestFocus();
           }
       });*/        
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Create headpanel">
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu markMenu;
    private JMenu commandMenu;
    private JMenu showMenu;
    private JMenu configMenu;
    private JMenu helpMenu;
    private JMenu temporaryMenu;
    private JToolBar mainToolbar;
    private XButton btnFTP;
    private XButton btnFTPDiscnn;

    private JPanel createHeadPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // setup menu
        menuBar = new JMenuBar();
        panel.add(menuBar, BorderLayout.NORTH);

        // menu file
        fileMenu = new JMenu("File");
        
        fileMenu.setMnemonic(KeyEvent.VK_F);
       
        menuBar.add(fileMenu);

        fileMenu.add(createMenuItem("Change Attributes", "Change_Attribute"));
        fileMenu.add(createMenuItem("Properties", "Properties",KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, ActionEvent.ALT_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem("Pack...","Pack",
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK)));
        fileMenu.add(createMenuItem("Unpack","Unpack",
                KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.ALT_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem("Quit", "Exit",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK)));

       // menuItem = new JMenuItem("change attributes...");

        // ~menu file

        // menu mark
        markMenu = new JMenu("Mark");
        markMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(markMenu);

        markMenu.add(createMenuItem("Select all", "selectall", 
                KeyStroke.getKeyStroke(KeyEvent.VK_ADD, ActionEvent.CTRL_MASK)));
        markMenu.add(createMenuItem("UnSelect all", "unselectall",
                KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK)));
        // ~menu mark

        // command menu
        commandMenu = new JMenu("Commands");
        menuBar.add(commandMenu);

        commandMenu.add(createMenuItem("Search", "search",
                KeyStroke.getKeyStroke(KeyEvent.VK_F7, ActionEvent.ALT_MASK)));
        commandMenu.add(createMenuItem("System Information","systeminfo"));
        commandMenu.add(new JSeparator());
        commandMenu.add(createMenuItem("Open Desktop folder", "opendesktop"));
        // ~command menu

        // show menu
        showMenu = new JMenu("Show");
        menuBar.add(showMenu);

        showMenu.add(createMenuItem("Brief", "brief",
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem("Full", "Full",
                KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem("New Folder Tab", "newtab",
                KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK)));
        // ~show menu

        // config menu
        configMenu = new JMenu("Configuration");
        menuBar.add(configMenu);
        configMenu.add(createMenuItem("Option", "option"));


        // ~config menu

        // help menu
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        helpMenu.add(createMenuItem("Index", "index", KeyEvent.VK_F1));
        helpMenu.add(createMenuItem("Keyboard", "keyboard"));
        helpMenu.add(new JSeparator());
        helpMenu.add(createMenuItem("About", "about"));
        // ~help menu

        // temporary menu
        temporaryMenu = new JMenu("Temporary");
        menuBar.add(temporaryMenu);

        temporaryMenu.add(createMenuItem("New File", "New_File"));
        temporaryMenu.add(createMenuItem("View File", "View_File"));
        temporaryMenu.add(createMenuItem("Rename File", "Rename_File"));
        temporaryMenu.add(createMenuItem("Delete File(s)/Folder(s)", "Delete_File"));
        temporaryMenu.add(createMenuItem("Copy File(s)/Folder(s)", "Copy_File"));
        temporaryMenu.add(createMenuItem("Move File(s)/Folder(s)", "Move_File"));
        temporaryMenu.add(createMenuItem("Edit File", "Edit_File"));
        temporaryMenu.add(new JSeparator());
        temporaryMenu.add(createMenuItem("New Folder", "New_Folder"));
        temporaryMenu.add(new JSeparator());
        temporaryMenu.add(createMenuItem("Zip file", "Zip_File"));
        temporaryMenu.add(createMenuItem("Unzip file", "Unzip_File"));
        //~temporary menu

        //setup toolbar
        mainToolbar = new JToolBar();
        mainToolbar.setFloatable(false);
        mainToolbar.setRollover(true);
        mainToolbar.setName("toolbar");

        btnFTP = new XButton("Ftp Connect","ftp");
        btnFTP.addActionListener(this);
        btnFTPDiscnn = new XButton("Ftp Disconnect","dftp");
        btnFTPDiscnn.setEnabled(false);
        btnFTPDiscnn.addActionListener(this);
        mainToolbar.add(btnFTP);
        mainToolbar.add(btnFTPDiscnn);
       
        panel.add(mainToolbar, BorderLayout.SOUTH);
        //~setup toolbar
        
        return panel;
    }

    private JMenuItem createMenuItem(String text,String commandText, KeyStroke stroke) {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(commandText);
        item.setAccelerator(stroke);
        item.addActionListener(this);
        return item;
    }

    private JMenuItem createMenuItem(String text,String commandText, int hotKey) {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(commandText);
        item.setMnemonic(hotKey);
        item.addActionListener(this);
        return item;
    }

    private JMenuItem createMenuItem(String text,String commandText) {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(commandText);
        item.addActionListener(this);
        return item;
    }
    
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Create mainpanel">
    
    private JSplitPane splitPane;
    private XPanel leftPanel;
    private XPanel rightPanel;
    private XPanel focusPanel;
    private JPanel createMainPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        leftPanel = new XPanel();
        leftPanel.focusRender();
        focusPanel = leftPanel;

        leftPanel.addFocusListener(new XComponentEventListener() {
            public void myEventOccurred(XComponentEvent evt) {
                XuLyFocusXPanel(evt);
            }

            public void myTableEventOccurred(XComponentEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        rightPanel = new ExtendComponent.XPanel();
        rightPanel.addFocusListener(new XComponentEventListener() {

            public void myEventOccurred(XComponentEvent evt) {
                XuLyFocusXPanel(evt);
            }

            public void myTableEventOccurred(XComponentEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        splitPane.setOneTouchExpandable( true );        

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Get current path in main form
     * @return
     */
    public String getCurrentPath() {
        return focusPanel.getCurrentPath();
    }

    /**
     * Get selected item's path
     * @return
     */
    public String getSelectedItemPath() {
        return focusPanel.getSelectedItemPath();
    }

    /**
     * Get selected item file name (with or without extension)
     * @param withExt
     * @return
     */
    //public String getSelectedItemFileName(Boolean withExt) {
    //    return focusPanel.getActiveTab().getSelectedItemFileName(withExt);
    //}

    /**
     * Refresh 
     */
    private void refresh() {

        if(leftPanel.getCurrentPath().equals(
                                  focusPanel.getCurrentPath())) {
            leftPanel.refresh(getCurrentPath());
            rightPanel.refresh(getLostFocusPath());
        } else {
            leftPanel.refresh(getLostFocusPath());
            rightPanel.refresh(getCurrentPath());
        }
    }

    /**
     * Get item path on lost focus panel
     */
    private String getLostFocusPath() {
        if(leftPanel.getCurrentPath().equals(
                                focusPanel.getCurrentPath())) {
            return rightPanel.getCurrentPath();

        }
        return leftPanel.getCurrentPath();
    }

    public void XuLyFocusXPanel(XComponentEvent evt) {
        if(evt.getIsFocus() == true) {
            focusPanel.lostfocusRender();
            focusPanel = (XPanel) evt.getObj();
            focusPanel.focusRender();
        }
        else if (evt.getIsFocus() == false)
        {
            //if(this.getFocusOwner())
            //utils.MsgboxHelper.confirm(this.getT);
            ((XPanel)evt.getObj()).lostfocusRender();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Create footpanel">
    private JPanel createFootPanel()
    {
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;
       c.gridx = 0;
       c.gridy = 0;
       c.weightx = 0.5;

       JButton btn1 = createButton("F3 View",KeyEvent.VK_F3);

       panel.add(btn1,c);

       c.gridx = 1;
       JButton btn2 = createButton("F4 Edit", KeyEvent.VK_F4);
       panel.add(btn2,c);

       c.gridx = 2;
       JButton btn3 = createButton("F5 Copy", KeyEvent.VK_F5);
       panel.add(btn3,c);

       c.gridx = 3;
       JButton btn4 = createButton("F6 Move",KeyEvent.VK_F6);
       panel.add(btn4,c);

        c.gridx = 4;
       JButton btn5 = createButton("F7 New Folder",KeyEvent.VK_F7);
       btn5.setActionCommand("New_Folder");
       panel.add(btn5,c);
       btn5.addActionListener(this);

        c.gridx = 5;
       JButton btn6 = createButton("F8 Delete",KeyEvent.VK_F8);
       panel.add(btn6,c);

        c.gridx = 6;
       JButton btn7 = createButton("Alt+F4 Exit",KeyEvent.VK_F9 );
       panel.add(btn7,c);

       return panel;
    }

    private JButton createButton(String text, int hotKey)
    {
        JButton btn = new JButton(text);
        btn.setMnemonic(hotKey);
        btn.setBorderPainted(true);
        btn.setFocusPainted(true);

        return btn;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Event Processing">
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if(command.equals("New_File")) {
            newFile();
        } else if(command.equals("New_Folder")) {
            newFolder();
        } else if(command.equals("View_File")) {
            viewFile();
        } else if(command.equals("Rename_File")) {
            renameFile();
        } else if(command.equals("Delete_File")) {
            deleteFilesFolders();
        } else if(command.equals("Copy_File")) {
            copyFilesFolders();
        } else if(command.equals("Move_File")) {
            moveFilesFolders();
        } else if(command.equals("Edit_File")) {
            editFile();
        } else if(command.equals("Zip_File")) {
            zipFile();
        } else if(command.equals("Unzip_File")) {
            unzipFile();
        } else if(command.equals("Exit")) {
            System.exit(0);
        } else if(command.equals("ftp")) { 
                ftp();
        } else if (command.equals("dftp")){
                dftp();
        } else if (command.equals("selectall")){
            focusPanel.getActiveTab().selectAllRow();
        } else if (command.equals("unselectall")){            
            focusPanel.getActiveTab().DeSelectAll();
        } else if (command.equals("newtab")){
            focusPanel.createNewTab();
        }


    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Functions">

    private void newFile() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText("New File");
        frm.setVisible(true);
        
        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XFile.create(fullPath); // create file
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void newFolder() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText("New Directory");
        frm.setVisible(true);
        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XFolder.create(fullPath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }
            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void viewFile() {
        if(FileHelper.isFolder(getSelectedItemPath())) {
            MsgboxHelper.inform("No file selected.");
            return;
        }

        frmViewFile frm = new frmViewFile(getSelectedItemPath());
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setVisible(true);
    }

    private void renameFile() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText("New Name");
        frm.setTextboxText(FileHelper.getFileName(getSelectedItemPath()));
        frm.setVisible(true);
        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XFile.rename(getSelectedItemPath(), fullPath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void deleteFilesFolders() {        
        ArrayList<String> selectedItems = focusPanel.getActiveTab().getSelectedItems();
        String msg = "Do you really want to delete the " + selectedItems.size() + " selected item(s) \n";
        for(int i = 0; i < selectedItems.size(); ++i) {
            msg += FileHelper.getFileName(selectedItems.get(i)) + "\n";
            if(i >= 4) {       // so many items (>5 items) then ...
                msg += "...";  // truncate the rest of them
                break;
            }
        }
        // delete 'em all!
        if(MsgboxHelper.confirm(msg) == true) {
            for(String item : selectedItems) {
                if(FileHelper.isFile(item)) {
                    // delete file
                    try {
                        XFile.delete(item);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if(FileHelper.isFolder(item)) {
                    // delete folder
                    try {
                        XFolder.delete(item);
                    } catch (IOException ex) {
                        Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            refresh();
        }
        
    }

    private void copyFilesFolders() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText("Copy To");
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);
        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String path = evt.getData();

                ArrayList<String> selectedItems = focusPanel.getActiveTab().getSelectedItems();
                for(String item : selectedItems) {
                    if(FileHelper.isFile(item)) {
                        // copy file
                        try {
                            XFile.copy(item, path + FileHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if(FileHelper.isFolder(item)) {
                        // copy folder
                        try {
                            XFolder.copy(item, path + FileHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                refresh();

            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void moveFilesFolders() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText("Move To");
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);
        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String path = evt.getData();

                ArrayList<String> selectedItems = focusPanel.getActiveTab().getSelectedItems();
                for(String item : selectedItems) {
                    if(FileHelper.isFile(item)) {
                        // move file
                        try {
                            XFile.move(item, path + FileHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if(FileHelper.isFolder(item)) {
                        // move folder
                        try {
                            XFolder.move(item, path + FileHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                refresh();
            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void editFile() {
        if(FileHelper.isFolder(getSelectedItemPath())) {
            MsgboxHelper.inform("No files selected.");
            return;
        }
        try {
            XFile.execute(getSelectedItemPath());
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void zipFile() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setTextboxText("Pack file(s) to the archive");
        frm.setTextboxText(getLostFocusPath() + FileHelper.getFileNameWithoutExt(getSelectedItemPath()) + ".zip");
        frm.setVisible(true);

        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String fullPath = evt.getData();
                try {
                    XFile.zip(getSelectedItemPath(), fullPath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void unzipFile() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setTextboxText("Unpack specific files from archive to");
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);

        frm.addMyEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                String fullPath = evt.getData();
                try {
                    XFile.unzip(getSelectedItemPath(), fullPath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }

            public void mySEventOccurred(MySEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void ftp()
    {
        frmFtpConnection frm = new frmFtpConnection();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setVisible(true);
        frm.addMySEventListener(new MyEventListener() {

            public void myEventOccurred(MyEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }           
            public void mySEventOccurred(MySEvent evt) {
                //throw new UnsupportedOperationException("Not supported yet.");
                //utils.MsgboxHelper.inform("chanh");
                // get connection info
               
                String url = (String) evt.getData().get(0);
                String password =new String((char[])evt.getData().get(1));
                String username = (String) evt.getData().get(2);
                ftp = new FtpResource(url, password, username);
                try {
                    if (ftp.connect()) {
                        focusPanel.getActiveTab().setftpMode(true);
                        focusPanel.getActiveTab().setFtpResource(ftp);
                        focusPanel.getActiveTab().refreshTable("");
                        focusPanel.setCurrentPath(ftp.getWorkingDir());
                        btnFTPDiscnn.setEnabled(true);
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    utils.MsgboxHelper.showError(ex.getMessage());
                }
            }
        });
    }

    private void dftp() {
        if(ftp != null)
                ftp.disConnect();
        if(leftPanel.getCurrentPath().startsWith(ftp.getRootPath()))
        {
            leftPanel.getActiveTab().setftpMode(false);
            leftPanel.getActiveTab().refreshTable("C:\\");
            leftPanel.setCurrentPath("C:\\");
        }
        if(rightPanel.getCurrentPath().startsWith(ftp.getRootPath()))
        {
            rightPanel.getActiveTab().setftpMode(false);
            rightPanel.getActiveTab().refreshTable("C:\\");
            rightPanel.setCurrentPath("C:\\");
        }
        btnFTPDiscnn.setEnabled(false);
    }
    //</editor-fold>
}
