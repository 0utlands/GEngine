/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2;

import genginev2.ObjectInformation.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author George
 */
public class Listeners {
    
    Interface window;
    Renderer renderer;
    
    public Listeners(Interface window, Renderer renderer){
        this.window = window;
        this.renderer = renderer;
        initListeners();
    }
    
    private void initListeners(){
        //Menu button listener.
        window.menuB1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CardLayout card = (CardLayout) window.mainP.getLayout();
                card.show(window.mainP, "Game");
            }
        });
        renderer.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                
                if (e.getWheelRotation() < 0){
                    for(Triangle i : renderer.data.getTriangles()){
                        i.changeWorldSpace(0, 0, 0, 0, 1, 0);
                    }
                }
                else{
                    for(Triangle i : renderer.data.getTriangles()){
                        i.changeWorldSpace(0, 0, 0, 0, -1, 0);
                    }
                }
                renderer.repaint();
            }
        
        });
        renderer.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch( keyCode ) { 
                    case KeyEvent.VK_W:
                        for(Triangle i : renderer.data.getTriangles()){
                            i.changeWorldSpace(0, 0, 3, 0, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_S:
                        for(Triangle i : renderer.data.getTriangles()){
                            i.changeWorldSpace(0, 0, -3, 0, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_A:
                        for(Triangle i : renderer.data.getTriangles()){
                            i.changeWorldSpace(-3, 0, 0, 0, 0, 0);
                        }
                        break;
                    case KeyEvent.VK_D :
                        for(Triangle i : renderer.data.getTriangles()){
                            i.changeWorldSpace(3, 0, 0, 0, 0, 0);
                        }
                        break;
                 }
                renderer.repaint();
            } 
        });
    }
    
}
