/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Forms;

import ExtendComponent.XPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
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
    private XPanel activeXPanel;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

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
    private JMenuItem menuItem;
    private JToolBar mainToolbar;
    
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
        //~setup menu

        //setup toolbar
        mainToolbar = new JToolBar();
        mainToolbar.setFloatable(false);
        mainToolbar.setRollover(true);
        mainToolbar.setName("toolbar");

        JButton btn = new JButton("Button");
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        JButton btn2 = new JButton("Button2");
        mainToolbar.add(btn);
        mainToolbar.add(btn2);

        panel.add(mainToolbar, BorderLayout.SOUTH);
        //~setup toolbar
        
        return panel;
    }
    private JMenuItem createMenuItem(String text,String commandText, KeyStroke stroke)
    {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(commandText);
        item.setAccelerator(stroke);
        item.addActionListener(this);
        return item;
    }
    private JMenuItem createMenuItem(String text,String commandText, int hotKey)
    {
        JMenuItem item = new JMenuItem(text);
        item.setActionCommand(commandText);
        item.setMnemonic(hotKey);
        item.addActionListener(this);
        return item;
    }
    private JMenuItem createMenuItem(String text,String commandText)
    {
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

        leftPanel = new ExtendComponent.XPanel();        
        rightPanel = new ExtendComponent.XPanel();       
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        panel.add(splitPane, BorderLayout.CENTER);
        return panel;        
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
       panel.add(btn5,c);

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
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        if(command.equals("Exit"))
        {
            System.exit(0);
        }
    }
    
    //</editor-fold>
}
