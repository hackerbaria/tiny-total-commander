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
    private JMenu languageMenu;
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

        //  Load Menu moi lan thay doi ngon ngu
        //
        loadMenu();

        
        /*
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

        // language menu
        languageMenu = new JMenu("Languages");
        menuBar.add(languageMenu);

        languageMenu.add(createMenuItem("English", "Change_English"));
        languageMenu.add(createMenuItem("United State", "Change_UnitedState"));
        languageMenu.add(createMenuItem("Spain", "Change_Spanish"));
        languageMenu.add(createMenuItem("Viet Nam", "Change_VietNamese"));
        // ~language menu

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
        temporaryMenu.add(createMenuItem("Zip File", "Zip_File"));
        temporaryMenu.add(createMenuItem("Unzip File", "Unzip_File"));
        temporaryMenu.add(createMenuItem("Append Zip File", "Append_Zip"));
        temporaryMenu.add(createMenuItem("Explore Zip File", "Explore_Zip"));
        //~temporary menu

        */

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

        fileMenu.add(createMenuItem(LangManager.TranslateLang("NewFile"), "New_File",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.SHIFT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("ViewFile"), "View_File",
                KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Rename"), "Rename",
                KeyStroke.getKeyStroke(KeyEvent.VK_F6, ActionEvent.SHIFT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Delete"), "Delete",
                KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("Copy"), "Copy",
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("menuFile_Pack"),"Pack",
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.ALT_MASK)));
        fileMenu.add(createMenuItem(LangManager.TranslateLang("menuFile_Unpack"),"Unpack",
                KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.ALT_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(createMenuItem(LangManager.TranslateLang("menuFile_Quit"), "Exit",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK)));

       // menuItem = new JMenuItem("change attributes...");

        // ~menu file

        // menu mark
        markMenu = new JMenu(LangManager.TranslateLang("memuMark"));
        markMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(markMenu);

        markMenu.add(createMenuItem(LangManager.TranslateLang("menuMark_SelectAll"), "selectall",
                KeyStroke.getKeyStroke(KeyEvent.VK_ADD, ActionEvent.CTRL_MASK)));
        markMenu.add(createMenuItem(LangManager.TranslateLang("menuMark_UnSelectAll"), "unselectall",
                KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, ActionEvent.CTRL_MASK)));
        // ~menu mark

        // command menu
        commandMenu = new JMenu(LangManager.TranslateLang("menuCommands"));
        menuBar.add(commandMenu);

        commandMenu.add(createMenuItem(LangManager.TranslateLang("menuCommands_Search"), "search",
                KeyStroke.getKeyStroke(KeyEvent.VK_F7, ActionEvent.ALT_MASK)));
        commandMenu.add(createMenuItem(LangManager.TranslateLang("menuCommands_SysInfo"),"systeminfo"));
        commandMenu.add(new JSeparator());
        commandMenu.add(createMenuItem(LangManager.TranslateLang("menuCommands_Desktop"), "opendesktop"));

        // ~command menu

        // show menu
        showMenu = new JMenu(LangManager.TranslateLang("menuShow"));
        menuBar.add(showMenu);

        showMenu.add(createMenuItem(LangManager.TranslateLang("menuShow_Brief"), "brief",
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("menuShow_Full"), "full",
                KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("menuShow_Thumbnail"), "thumbnail",
                KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK)));
        showMenu.add(createMenuItem(LangManager.TranslateLang("menuShow_NewTab"), "newtab",
                KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK)));
        // ~show menu

        // config menu
        configMenu = new JMenu(LangManager.TranslateLang("menuConf"));
        menuBar.add(configMenu);
        configMenu.add(createMenuItem(LangManager.TranslateLang("menuConf_Opt"), "option"));


        // ~config menu

        // help menu
        helpMenu = new JMenu(LangManager.TranslateLang("menuHelp"));
        menuBar.add(helpMenu);

        helpMenu.add(createMenuItem(LangManager.TranslateLang("menuHelp_Index"), "index", KeyEvent.VK_F1));
        helpMenu.add(createMenuItem(LangManager.TranslateLang("menuHelp_Keybard"), "keyboard"));
        helpMenu.add(new JSeparator());
        helpMenu.add(createMenuItem(LangManager.TranslateLang("menuHelp_About"), "about"));
        // ~help menu

        // language menu
        languageMenu = new JMenu(LangManager.TranslateLang("menuLang"));
        menuBar.add(languageMenu);

        languageMenu.add(createMenuItem(LangManager.TranslateLang("menuLang_En"), "Change_English"));
        languageMenu.add(createMenuItem(LangManager.TranslateLang("menuLang_VN"), "Change_VietNamese"));
        // ~language menu

        // temporary menu
        temporaryMenu = new JMenu(LangManager.TranslateLang("memuTemp"));
        menuBar.add(temporaryMenu);

        
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_RenameFile"), "Rename_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_Delete"), "Delete_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_Copy"), "Copy_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_Move"), "Move_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_Edit"), "Edit_File"));
        temporaryMenu.add(new JSeparator());
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_NewFolder"), "New_Folder"));
        temporaryMenu.add(new JSeparator());
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_Zip"), "Zip_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_UnZip"), "Unzip_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_AppendZip"), "Append_Zip"));
        temporaryMenu.add(new JSeparator());
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_SplitFile"), "Split_File"));
        temporaryMenu.add(createMenuItem(LangManager.TranslateLang("menuTemp_MergeFile"), "Merge_File"));
        //~temporary menu
        
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
        
        rightPanel = new ExtendComponent.XPanel();
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
    private JPanel createFootPanel()
    {
       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       c.fill = GridBagConstraints.HORIZONTAL;
       c.gridx = 0;
       c.gridy = 0;
       c.weightx = 0.5;

       //JButton btn1 = createButton("F3 View",KeyEvent.VK_F3);
       JButton btn1 = createButton(LangManager.TranslateLang("btn1_MainForm"),KeyEvent.VK_F3);

       panel.add(btn1,c);

       c.gridx = 1;
       //JButton btn2 = createButton("F4 Edit", KeyEvent.VK_F4);
       JButton btn2 = createButton(LangManager.TranslateLang("btn2_MainForm"), KeyEvent.VK_F4);
       panel.add(btn2,c);

       c.gridx = 2;
       //JButton btn3 = createButton("F5 Copy", KeyEvent.VK_F5);
       JButton btn3 = createButton(LangManager.TranslateLang("btn3_MainForm"), KeyEvent.VK_F5);
       panel.add(btn3,c);

       c.gridx = 3;
       //JButton btn4 = createButton("F6 Move",KeyEvent.VK_F6);
       JButton btn4 = createButton(LangManager.TranslateLang("btn4_MainForm"),KeyEvent.VK_F6);
       panel.add(btn4,c);

        c.gridx = 4;
       //JButton btn5 = createButton("F7 New Folder",KeyEvent.VK_F7);
       JButton btn5 = createButton(LangManager.TranslateLang("btn5_MainForm"),KeyEvent.VK_F7);
       btn5.setActionCommand("New_Folder");
       panel.add(btn5,c);
       btn5.addActionListener(this);

        c.gridx = 5;
       //JButton btn6 = createButton("F8 Delete",KeyEvent.VK_F8);
        JButton btn6 = createButton(LangManager.TranslateLang("btn6_MainForm"),KeyEvent.VK_F8);
       panel.add(btn6,c);

        c.gridx = 6;
       //JButton btn7 = createButton("Alt+F4 Exit",KeyEvent.VK_F9 );
        JButton btn7 = createButton(LangManager.TranslateLang("btn7_MainForm"),KeyEvent.VK_F9 );
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
        } else if(command.equals("Rename")) {
            rename();
        } else if(command.equals("Delete")) {
            delete();
        } else if(command.equals("Copy")) {
            copy();
        } else if(command.equals("Move_File")) {
            moveFilesFolders();
        } else if(command.equals("Edit_File")) {
            editFile();
        } else if(command.equals("Zip_File")) {
            zipFilesFolders();
        } else if(command.equals("Unzip_File")) {
            unzipFile();
        } else if(command.equals("Append_Zip")) {
            appendZip();
        } else if(command.equals("Explore_Zip")) {
            exploreZip();
        } else if(command.equals("Split_File")) {
            splitFile();
        } else if(command.equals("Merge_File")){
            mergeFile();
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
        } else if (command.equals("selectall")){
            focusPanel.selectAllRow();
        } else if (command.equals("unselectall")){            
            focusPanel.deSelectAll();
        } else if (command.equals("newtab")){
            focusPanel.createNewTab();
        } else if (command.equals("Change_English")){
            if(LangManager.ChangeLanguage("english")) {
                changeLanguage();
            }
        } else if (command.equals("Change_Spanish")){
            if(LangManager.ChangeLanguage("spain")) {
                changeLanguage();
            }
        } else if (command.equals("Change_French")){
            if(LangManager.ChangeLanguage("french")) {
                changeLanguage();
            }
        } else if (command.equals("Change_VietNamese")){
            if(LangManager.ChangeLanguage("vietnam")) {
                changeLanguage();
            }
        } else if (command.equals("brief")){
            focusPanel.setBriefView();
        } else if (command.equals("full")){
            focusPanel.setFullView();
        } else if (command.equals("opendesktop")){
            focusPanel.openDeskTop();
        } else if (command.equals("thumbnail")){
            focusPanel.setThumbnailView();
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
                    XFile.create(fullPath); // create file
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
                refresh();
            }
        });
    }

    private void newFolder() {
        //MiniForm frm = new MiniForm();
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frm.setLabelText("New Directory
        frm.setLabelText(LangManager.TranslateLang("label1_frmNewFolder"));
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = getCurrentPath() + evt.getData();
                try {
                    XFolder.create(fullPath);
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
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
                    XFolder.rename(getSelectedItemPath(), fullPath);
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
                    XFolder.delete(item);
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
                        XFolder.copy(item, path + PathHelper.getFileName(item));
                    } catch (IOException ex) {
                        MsgboxHelper.showError(ex.getMessage());
                    }
                }
                
                refresh();

            }
        });
    }

    private void moveFilesFolders() {
        //MiniForm frm = new MiniForm();
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frm.setLabelText("Move To");
        frm.setLabelText(LangManager.TranslateLang("label1_frmMove"));
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);
        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String path = evt.getData();

                ArrayList<String> selectedItems = focusPanel.getSelectedItems();
                for(String item : selectedItems) {
                    if(FileResource.isFile(item)) {
                        // move file
                        try {
                            XFile.move(item, path + PathHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if(FileResource.isFolder(item)) {
                        // move folder
                        try {
                            XFolder.move(item, path + PathHelper.getFileName(item));
                        } catch (IOException ex) {
                            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
            XFile.execute(getSelectedItemPath());
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void zipFilesFolders() {
        //MiniForm frm = new MiniForm();
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frm.setTextboxText("Pack file(s) to the archive");
        frm.setTextboxText(LangManager.TranslateLang("label1_frmZip"));
        frm.setTextboxText(getLostFocusPath() + PathHelper.getFileNameWithoutExt(getSelectedItemPath()) + ".zip");
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.zip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                refresh();
            }
        });
    }

    private void appendZip() {
        MiniForm frm = new MiniForm();
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setTextboxText("Pack file(s) to the archive");
        frm.setTextboxText(getLostFocusPath() + PathHelper.getFileNameWithoutExt(getSelectedItemPath()) + ".zip");
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.appendZip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }

                refresh();
            }
        });
    }

    private void unzipFile() {
        //MiniForm frm = new MiniForm();
        MiniForm frm = new MiniForm(LangManager);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frm.setTextboxText("Unpack specific files from archive to");
        frm.setTextboxText(LangManager.TranslateLang("label1_frmUnzip"));
        frm.setTextboxText(getLostFocusPath());
        frm.setVisible(true);

        frm.addMyEventListener(new XEventListener() {

            public void myEventOccurred(XEvent evt) {
                String fullPath = evt.getData();
                try {
                    XZipper.unzip(getSelectedItemPath(), fullPath);
                } catch (Exception ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void splitFile() {
        try {
            XSplitter.split(getSelectedItemPath(), getLostFocusPath());
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        refresh();
    }

    private void mergeFile() {
        try {
            XSplitter.merge(focusPanel.getSelectedItems(), getLostFocusPath());
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        refresh();
    }

    private void ftp()
    {
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
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    utils.MsgboxHelper.showError(ex.getMessage());
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

        // Thay doi text tren Menu
        loadMenu();
        //enuBar.paintImmediately(headPanel.getX(), headPanel.getY(), headPanel.getWidth(), headPanel.getHeight());

    }
    //</editor-fold>
}
