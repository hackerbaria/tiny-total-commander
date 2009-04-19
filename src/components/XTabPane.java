/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.awt.Color;
import javax.swing.*;
import utils.PathHelper;

/**
 * Tab pane
 * @author pmchanh
 */
public class XTabPane extends JTabbedPane {

    private JPanel _currentPathPanel;
    private JLabel _currentPathLabel;
    private JPanel _containPane;
    private XTable _dirTable;
    private XTableModel _model;
    private JScrollPane _scrollpane;
    private Color _focusColor = Color.BLUE;
    private Color _lostfocusColor = Color.decode("#66CCFF");

    /**
     *  thiet lap duong dan hien hanh
     */
    public void setCurrentPath(String path) {
        this._currentPathLabel.setText(path);
        this.setTitleAt(this.getSelectedIndex(),
                PathHelper.getParentName(path));
    }

     /**
      *  lay duong dan hien hanh
      */
     public String getCurrentPath() {
        return this._currentPathLabel.getText();
    }

     /**
     * Tô background cho biet component nay dang duoc focus
     */
    public void focusRender() {
        _currentPathPanel.setBackground(_focusColor);
        _dirTable.setRowSelectionAllowed(true);
    }
    
}
