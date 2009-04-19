/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package forms;

import components.*;
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

    private LanguageManager LangManager = new LanguageManager();
    // constructor
    public MainForm()
    {
        super();
        InitializeComponent();
       // readDiskListToCombobox();4
        //changeLanguage();

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
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Create headpanel">
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu commandMenu;
    private JMenu showMenu;
    private JMenu configMenu;
    private JMenu helpMenu;
    private JMenu languageMenu;
    private JMenu themeMenu;
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

        //  Load Menu moi lan thay doi ngon ngu
        loadMenu();

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

    private JMenuBar loadMenu(){

        //  Xoa Menu neu co
        menuBar.removeAll();
        
        // menu file
        fileMenu = new JMenu(LangManager.TranslateLang("File"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        fileMenu.add(createMenuItem(LangManager.TranslateLang("NewFile"), "NewFile",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.SHIFT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("NewFolder"), "NewFolder",
                KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("ViewFile"), "ViewFile",
                KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("EditFile"), "EditFile",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Copy"), "Copy",
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Rename"), "Rename",
                KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.SHIFT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Delete"), "Delete",
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Move"), "Move",
                KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("CompareFiles"), "CompareFiles"));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("SplitFile"), "SplitFile"));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("MergeFiles"), "MergeFiles"));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Zip"),"Zip",
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("AppendZip"),"AppendZip"));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("ExploreZip"),"ExploreZip"));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Unzip"),"Unzip",
                KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.ALT_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Exit"), "Exit",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK)));

        // ~menu file

        // command menu
        commandMenu = new JMenu(LangManager.TranslateLang("Commands"));
        menuBar.add(commandMenu);

        commandMenu.add(createMenuItem(LangManager.TranslateLang("Search"), "Search",
                KeyStroke.getKeyStroke(KeyEvent.VK_F7, ActionEvent.ALT_MASK)));
        //commandMenu.add(createMenuItem(LangManager.TranslateLang("SysInfo"),"SysInfo"));
        commandMenu.add(new JSeparator());
        commandMenu.add(createMenuItem(LangManager.TranslateLang("OpenDesktop"), "OpenDesktop"));

        // ~command menu

        // show menu
        showMenu = new JMenu(LangManager.TranslateLang("Show"));
        menuBar.add(showMenu);

        showMenu.add(createMenuItem(LangManager.TranslateLang("Brief"), "Brief",
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("Full"), "Full",
                KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("Thumbnail"), "Thumbnail",
                KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("Tree"), "Tree",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.CTRL_MASK)));
        showMenu.add(new JSeparator());
        showMenu.add(createMenuItem(LangManager.TranslateLang("NewTab"), "NewTab",
                KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK)));
        // ~show menu

        // config menu
        configMenu = new JMenu(LangManager.TranslateLang("Config"));
        menuBar.add(configMenu);
        configMenu.add(createMenuItem(LangManager.TranslateLang("Option"), "Option"));


        // ~config menu

        // help menu
        helpMenu = new JMenu(LangManager.TranslateLang("Help"));
        menuBar.add(helpMenu);

        helpMenu.add(createMenuItem(LangManager.TranslateLang("Index"), "Index"));
        helpMenu.add(createMenuItem(LangManager.TranslateLang("About"), "About"));
        // ~help menu

        // language menu
        languageMenu = new JMenu(LangManager.TranslateLang("Language"));
        menuBar.add(languageMenu);

        languageMenu.add(createMenuItem(LangManager.TranslateLang("English"), "English"));
        languageMenu.add(createMenuItem(LangManager.TranslateLang("Vietnamese"), "Vietnamese"));
        // ~language menu

        //theme
        themeMenu = new JMenu(LangManager.TranslateLang("Themes"));
        menuBar.add(themeMenu);

        String[] supportedLnf = ThemeManager.getSupportedLnFs();
        for(String lnfName : supportedLnf) {
            themeMenu.add(createMenuItem(lnfName, lnfName));
        }
        //~theme

        return menuBar;
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

    // <editor-fold defaultstate="collapsed" desc="Create mainpanel">
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
                throw new UnsupportedOperationException(LangManager.TranslateLang("alert_notsupport"));
            }
        });
        
        rightPanel = new XPanel();
        rightPanel.addFocusListener(new XComponentEventListener() {

            public void myEventOccurred(XComponentEvent evt) {
                XuLyFocusXPanel(evt);
            }

            public void myTableEventOccurred(XComponentEvent evt) {
                throw new UnsupportedOperationException(LangManager.TranslateLang("alert_notsupport"));
            }
        });
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        splitPane.setOneTouchExpandable( true );
        splitPane.setEnabled(false);

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    // </editor-fold>

    /**
     * Get current path in main form
     */
    public String getCurrentPath() {
        return focusPanel.getCurrentPath();
    }

    /**
     * Get selected item's path
     */
    public String getSelectedItemPath() {
        return focusPanel.getSelectedItemPath();
    }

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
    private JPanel createFootPanel() {
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;
       c.gridy = 0;
       c.weightx = 0.5;

       c.gridx = 0;
       JButton btnView = createButton(LangManager.TranslateLang("btn1_MainForm"));
       btnView.setActionCommand("ViewFile");
       btnView.addActionListener(this);
       panel.add(btnView,c);

       c.gridx = 1;
       JButton btnEdit = createButton(LangManager.TranslateLang("btn2_MainForm"));
       btnEdit.setActionCommand("EditFile");
       btnEdit.addActionListener(this);
       panel.add(btnEdit,c);

       c.gridx = 2;
       JButton btnCopy = createButton(LangManager.TranslateLang("btn3_MainForm"));
       btnCopy.setActionCommand("Copy");
       btnCopy.addActionListener(this);
       panel.add(btnCopy,c);

       c.gridx = 3;
       JButton btnMove = createButton(LangManager.TranslateLang("btn4_MainForm"));
       btnMove.setActionCommand("Move");
       btnMove.addActionListener(this);
       panel.add(btnMove,c);

       c.gridx = 4;
       JButton btn5 = createButton(LangManager.TranslateLang("btn5_MainForm"));
       btn5.setActionCommand("NewFolder");
       btn5.addActionListener(this);
       panel.add(btn5,c);

       c.gridx = 5;
       JButton btnDelete = createButton(LangManager.TranslateLang("btn6_MainForm"));
       btnDelete.setActionCommand("Delete");
       btnDelete.addActionListener(this);
       panel.add(btnDelete,c);

       c.gridx = 6;
       JButton btnExit = createButton(LangManager.TranslateLang("btn7_MainForm"));
       btnExit.setActionCommand("Exit");
       btnExit.addActionListener(this);
       panel.add(btnExit,c);

       return panel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBorderPainted(true);
        btn.setFocusPainted(true);
        return btn;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Event Processing">
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if(command.equals("NewFile")) {
            newFile();
        } else if(command.equals("NewFolder")) {
            newFolder();
        } else if(command.equals("ViewFile")) {
            viewFile();
        } else if(command.equals("Rename")) {
            rename();
        } else if(command.equals("Delete")) {
            delete();
        } else if(command.equals("Copy")) {
            copy();
        } else if(command.equals("Move")) {
            move();
        } else if(command.equals("EditFile")) {
            editFile();
        } else if(command.equals("Zip")) {
            zip();
        } else if(command.equals("Unzip")) {
            unzipFile();
        } else if(command.equals("AppendZip")) {
            appendZip();
        } else if(command.equals("ExploreZip")) {
            exploreZip();
        } else if(command.equals("SplitFile")) {
            splitFile();
        } else if(command.equals("MergeFiles")){
            mergeFiles();
        } else if(command.equals("CompareFiles")) {
            compareFiles();
        } else if(command.equals("Exit")) {
            System.exit(0);
        } else if(command.equals("ftp")) { 
                ftp();
        } else if (command.equals("dftp")){
            try {
                dftp();
            } catch (Exception ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (command.equals("English")){
            if(LangManager.ChangeLanguage("english")) {
                changeLanguage();
            }
        } else if (command.equals("Vietnamese")){
            if(LangManager.ChangeLanguage("vietnam")) {
                changeLanguage();
            }
        }

        // View
        
        else if (command.equals("Brief")){
            focusPanel.setBriefView();
        } else if (command.equals("Full")){
            focusPanel.setFullView();
        } else if (command.equals("Thumbnail")){
            focusPanel.setThumbnailView();
        } else if (command.equals("Tree")){
            focusPanel.setTreeView();
        } else if (command.equals("NewTab")){
            focusPanel.createNewTab();
        }

        // Commands

        else if(command.equals("Search")) {
            search();
        } else if (command.equals("OpenDesktop")){
            focusPanel.openDeskTop();
        } else {
            try {
                // themes
                ThemeManager.changeLookAndFeel(command, this);
            } catch (Exception ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Functions">

    private void newFile() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmNewFile"));
        frm.setVisible(true);
        
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XItem.createFile(fullPath); // create file
                } catch (IOException ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
                refresh();
            }
        });
    }

    private void newFolder() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmNewFolder"));
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XItem.createFolder(fullPath);
                } catch (IOException ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
                refresh();
            }
        });
    }

    private void viewFile() {
        if(FileResource.isFolder(getSelectedItemPath())) {
            MsgboxHelper.inform(LangManager.TranslateLang("alert_nofile"));
            return;
        }

        ViewFileForm frm = new ViewFileForm(getSelectedItemPath());
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setVisible(true);
    }

    private void rename() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmRenameFile"));
        frm.setTextboxText(PathHelper.getFileName(getSelectedItemPath()));
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XItem.rename(getSelectedItemPath(), fullPath);
                } catch (IOException ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
                refresh();
            }
        });
    }

    private void delete() {
        ArrayList<String> selectedItems = focusPanel.getSelectedItems();
        String msg = LangManager.TranslateLang("label1_frmDel") + " " + selectedItems.size() + " " + LangManager.TranslateLang("label2_frmDel");
        for(int i = 0; i < selectedItems.size(); ++i) {
            msg += PathHelper.getFileName(selectedItems.get(i)) + "\n";
            if(i >= 4) {       // so many items (>5 items) then ...
                msg += "...";  // truncate the rest of them
                break;
            }
        }
        // delete 'em all!
        if(MsgboxHelper.confirm(msg) == true) {
            for(String item : selectedItems) {
                try {
                    XItem.delete(item);
                } catch (IOException ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
            }
            refresh();
        }
        
    }

    private void copy() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmCopy"));
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String path = evt.getData();

                ArrayList<String> selectedItems = focusPanel.getSelectedItems();
                for(String item : selectedItems) { 
                    try {
                        XItem.copy(item, path + PathHelper.getFileName(item));
                    } catch (IOException ex) {
                        MsgboxHelper.showError(ex.getMessage());
                    }
                }
                
                refresh();

            }
        });
    }

    private void move() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmMove"));
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String path = evt.getData();

                ArrayList<String> selectedItems = focusPanel.getSelectedItems();
                for(String item : selectedItems) {
                    try {
                        XItem.move(item, path + PathHelper.getFileName(item));
                    } catch (IOException ex) {
                        MsgboxHelper.showError(ex.getMessage());
                    }
                }
                refresh();
            }
        });
    }

    private void editFile() {
        if(FileResource.isFolder(getSelectedItemPath())) {
            MsgboxHelper.inform(LangManager.TranslateLang("alert_nofile"));
            return;
        }
        try {
            XItem.executeFile(getSelectedItemPath());
        } catch (IOException ex) {
            MsgboxHelper.showError(ex.getMessage());
        }
    }

    private void zip() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmZip"));
        frm.setTextboxText(getLostFocusPath() + PathHelper.getFileNameWithoutExt(getSelectedItemPath()) + ".zip");
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.zip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
               
                refresh();
            }
        });
    }

    private void appendZip() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmAppendZip"));
        frm.setTextboxText(getLostFocusPath() + PathHelper.getFileNameWithoutExt(getSelectedItemPath()) + ".zip");
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.appendZip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }

                refresh();
            }
        });
    }

    private void unzipFile() {
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setLabelText(LangManager.TranslateLang("label1_frmUnzip"));
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.unzip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }

                refresh();
            }
        });
    }

    private void exploreZip() {
        try {
            String tempDir = FileResource.createTempDir();
            XZipper.unzip(getSelectedItemPath(), tempDir);
            focusPanel.setCurrentPath(tempDir);
            focusPanel.refresh(tempDir);
        } catch (Exception ex) {
            MsgboxHelper.showError(ex.getMessage());
        }
    }

    private void splitFile() {
        try {
            XSplitter.split(getSelectedItemPath(), getLostFocusPath());
        } catch (IOException ex) {
            MsgboxHelper.showError(ex.getMessage());
        }

        refresh();
    }

    private void mergeFiles() {
        try {
            XSplitter.merge(focusPanel.getSelectedItems(), getLostFocusPath());
        } catch (IOException ex) {
            MsgboxHelper.showError(ex.getMessage());
        }

        refresh();
    }

    private void search() {
        MsgboxHelper.inform(LangManager.TranslateLang("alert_notsupport"));
    }

    private void compareFiles() {
        CompareFilesForm frm = new CompareFilesForm(
                                        focusPanel.getSelectedItems().get(0),
                                        focusPanel.getSelectedItems().get(1));

        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frm.setVisible(true);
    }

    private void ftp() {
        FtpConnectionForm frm = new FtpConnectionForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {
       
            public void myEventOccurred(XEvent evt) {
                String url = evt.getDataList().get(0);        // url
                String username = evt.getDataList().get(1);   // username
                String password = evt.getDataList().get(2);   // password
                
                ftp = new FtpResource(url, username, password);
                try {
                    if (ftp.connect()) {
                        focusPanel.setftpMode(true);
                        focusPanel.setFtpResource(ftp);
                        focusPanel.refresh("");
                        focusPanel.setCurrentPath(ftp.getWorkingDir());
                        btnFTPDiscnn.setEnabled(true);
                    }
                } catch (Exception ex) {
                    MsgboxHelper.showError(ex.getMessage());
                }
            }
        });
    }

    private void dftp() throws Exception {
       
        if(leftPanel.getCurrentPath().startsWith(ftp.getRootPath())) {
           leftPanel.disconnectFromFtp(ftp);
        }
        if(rightPanel.getCurrentPath().startsWith(ftp.getRootPath())) {
           rightPanel.disconnectFromFtp(ftp);
        }
        btnFTPDiscnn.setEnabled(false);
    }

    private void changeLanguage(){

        // Thay doi text tren nut
        JButton btn1 = (JButton)footPanel.getComponent(0);
        btn1.setText(LangManager.TranslateLang("btn1_MainForm"));

        JButton btn2 = (JButton)footPanel.getComponent(1);
        btn2.setText(LangManager.TranslateLang("btn2_MainForm"));

        JButton btn3 = (JButton)footPanel.getComponent(2);
        btn3.setText(LangManager.TranslateLang("btn3_MainForm"));

        JButton btn4 = (JButton)footPanel.getComponent(3);
        btn4.setText(LangManager.TranslateLang("btn4_MainForm"));

        JButton btn5 = (JButton)footPanel.getComponent(4);
        btn5.setText(LangManager.TranslateLang("btn5_MainForm"));

        JButton btn6 = (JButton)footPanel.getComponent(5);
        btn6.setText(LangManager.TranslateLang("btn6_MainForm"));

        JButton btn7 = (JButton)footPanel.getComponent(6);
        btn7.setText(LangManager.TranslateLang("btn7_MainForm"));

        loadMenu();
    }
    //</editor-fold>
}
