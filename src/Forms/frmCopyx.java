/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Forms;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.Toolkit;


/**
 *
 * @author Spazee
 */
public class frmCopyx extends JFrame{
    private int Width = 396;
    private int Height = 223;

    private JButton btnOK;
    private JButton btnCancel;
    private JLabel lbMessage1;
    private JLabel lbMessage2;
    private JTextField txtFileLink;
    private JComboBox cbFileType;

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    //  contructor
    public frmCopyx(){
        initComponents();

    }

    private void initComponents(){
        
        btnOK = new JButton();
        btnCancel = new JButton();
        lbMessage1 = new JLabel();
        lbMessage2 = new JLabel();
        txtFileLink = new JTextField();
        cbFileType = new JComboBox();

        setSize(Width, Height);
        setLocation((d.width - Width)/2, (d.height - Height)/2);
        setTitle("Copy");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        cbFileType.setEditable(true);

        lbMessage1.setText("Copy To :");
        lbMessage2.setText("Only Files of this type : ");

        btnOK.setText("OK");
        btnCancel.setText("Cancel");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFileLink, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE))

                );

        layout.linkSize(SwingConstants.HORIZONTAL, btnOK, btnCancel);
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(lbMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFileLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)));
    }
}
