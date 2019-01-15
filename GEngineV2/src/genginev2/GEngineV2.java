/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2;

import genginev2.ObjectInformation.*;
import genginev2.ObjectImporting.*;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @author George
 */
public class GEngineV2 {

    public static void main(String[] args) {
        Interface window = new Interface();
        ObjectData objectData = new ObjectData();
        Random rand = new Random();
        Renderer renderer = new Renderer(objectData, window, 45, 1, 150, 1);
        window.gameP.add(renderer);
        Listeners input = new Listeners(window, renderer);
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(new Runnable() {
            public void run() {
                while(1>0){
                renderer.requestFocusInWindow();
                }
                
            }
        });
        //Test code.
        
        //objectData.inputTriangle(new Triangle(new Vertex(-25, -25, -25),new Vertex(0, 25, -25),new Vertex(25, -25, -25),Color.WHITE));
        
        objectData.inputTriangle(new Triangle(new Vertex(-25, -25, -25),new Vertex(0, 25, 0),new Vertex(25, -25, -25),Color.WHITE));
        objectData.getTriangles().get(0).setWorldSpace(0, 0, -100, 0, 0, 0);
        objectData.inputTriangle(new Triangle(new Vertex(-25, -25, 25),new Vertex(0, 25, 0),new Vertex(25, -25, 25),Color.RED));
        objectData.getTriangles().get(1).setWorldSpace(0, 0, -100, 0, 0, 0);
        objectData.inputTriangle(new Triangle(new Vertex(-25, -25, -25),new Vertex(0, 25, 0),new Vertex(-25, -25, 25),Color.BLUE));
        objectData.getTriangles().get(2).setWorldSpace(0, 0, -100, 0, 0, 0);
        objectData.inputTriangle(new Triangle(new Vertex(25, -25, -25),new Vertex(0, 25, 0),new Vertex(25, -25, 25),Color.GREEN));
        objectData.getTriangles().get(3).setWorldSpace(0, 0, -100, 0, 0, 0);
        
        /*while(1>0){
            try{
                Thread.sleep(50);

            } 
            catch(InterruptedException e){
                e.printStackTrace();
            }
            objectData.getTriangles().get(0).changeWorldSpace(0, 0, 0, 0, 1, 0);
            objectData.getTriangles().get(1).changeWorldSpace(0, 0, 0, 0, 1, 0);
            objectData.getTriangles().get(2).changeWorldSpace(0, 0, 0, 0, 1, 0);
            objectData.getTriangles().get(3).changeWorldSpace(0, 0, 0, 0, 1, 0);
            renderer.repaint();
        }*/
    }
    
}
