/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author George
 */
public class Interface extends javax.swing.JFrame{
    
    //Component declaration.
    public JPanel mainP;
    public JPanel menuP;
    public JPanel gameP;
    public JButton menuB1;
    public JLabel menuL1;
    
    public Interface(){
        initComponents();
    }
    
    private void initComponents(){
        //Component creation.
        mainP = new JPanel();
        
        menuP = new JPanel();
        menuB1 = new JButton();
        menuL1 = new JLabel();
        
        gameP = new JPanel();

        //Main window properties.
        setSize(600,600);
        setVisible(true);
        setTitle("GEngine");
        setIconImage(new ImageIcon("icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Main window sorting.
        add(mainP);
        
        //Component layouts.
        mainP.setLayout(new CardLayout());
        menuP.setLayout(new BoxLayout(menuP, BoxLayout.Y_AXIS));
        gameP.setLayout(new BorderLayout());
        
        //Component sorting.
        mainP.add(menuP, "Menu");
        mainP.add(gameP, "Game");
        menuP.add(menuL1);
        menuP.add(menuB1);
        
        //Component properties.
        mainP.setVisible(true);
        mainP.setSize(getContentPane().getSize());
        menuP.setBackground(Color.black);
        menuL1.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuL1.setText("GEngine");
        menuL1.setFont(menuL1.getFont().deriveFont (64.0f));
        menuL1.setForeground(Color.white);
        menuB1.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuB1.setText("Start");
    }
}
