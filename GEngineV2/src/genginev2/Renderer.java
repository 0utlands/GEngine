/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2;

import genginev2.ObjectInformation.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author George
 */
public class Renderer extends JPanel{
    public ObjectData data;
    public Interface window;
    private int iHeight;
    private int iWidth;
    private int cHeight;
    private int cWidth;
    private int xTrans;
    private int yTrans;
    private Mat4x4 projectionMat;
    private double raw1[];
    private double raw2[];
    private double raw3[];
    private double screen1[];
    private double screen2[];
    private double screen3[];
    private double ndc1[];
    private double ndc2[];
    private double ndc3[];
    private double raster1[];
    private double raster2[];
    private double raster3[];
    double[] zBuffer;
    BufferedImage img;
    
    public Renderer(ObjectData data, Interface window, double fov, double near, double far, double aspect){
        this.data = data;
        this.window = window;
        projectionMat = new Mat4x4();
        setProjectionMat(fov, near, far, aspect);
    }
    private void setProjectionMat(double fov, double near, double far, double aspect){
        double q = 1 / Math.tan(Math.toRadians(fov)/2);
        double a = q / aspect;
        double b = (far+near)/(near-far);
        double c = (2*far*near)/(near-far);
        projectionMat.grid[0][0] = a; projectionMat.grid[0][1] = 0; projectionMat.grid[0][2] = 0; projectionMat.grid[0][3] = 0;
        projectionMat.grid[1][0] = 0; projectionMat.grid[1][1] = q; projectionMat.grid[1][2] = 0; projectionMat.grid[1][3] = 0;
        projectionMat.grid[2][0] = 0; projectionMat.grid[2][1] = 0; projectionMat.grid[2][2] = b; projectionMat.grid[2][3] = c;
        projectionMat.grid[3][0] = 0; projectionMat.grid[3][1] = 0; projectionMat.grid[3][2] = -1; projectionMat.grid[3][3] = 0;
    }
    @Override
    public void paint (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        setBackground(Color.black);
        iHeight = getSize().height;
        iWidth = getSize().width;
        xTrans = iWidth / 2;
        yTrans = iHeight / 2;
        cHeight = 2;
        cWidth = 2;
        BufferedImage img = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_ARGB);
        double[] zBuffer = new double[iWidth * iHeight];
        for(int e=0; e < zBuffer.length; e++){
            zBuffer[e] = Double.NEGATIVE_INFINITY;
        }
        
        for(Triangle i : data.getTriangles()){
            List<Vertex> vertices = i.getWorldVertices();
            Vertex v1 = vertices.get(0);
            Vertex v2 = vertices.get(1);
            Vertex v3 = vertices.get(2);      
            raw1 = v1.getVertex();
            raw2 = v2.getVertex();
            raw3 = v3.getVertex();
            
            //Convert points to screen space.
            double[] screen1 = {(raw1[0]/-raw1[2]), (raw1[1]/-raw1[2]), raw1[2]};
            double[] screen2 = {(raw2[0]/-raw2[2]), (raw2[1]/-raw2[2]), raw2[2]};
            double[] screen3 = {(raw3[0]/-raw3[2]), (raw3[1]/-raw3[2]), raw3[2]};
            
            //Screen space to NDC space.
            double[] ndc1 = {((screen1[0] + (cWidth/2)) / cWidth), ((screen1[1] + (cHeight/2)) / cHeight), screen1[2]};
            double[] ndc2 = {((screen2[0] + (cWidth/2)) / cWidth), ((screen2[1] + (cHeight/2)) / cHeight), screen2[2]};
            double[] ndc3 = {((screen3[0] + (cWidth/2)) / cWidth), ((screen3[1] + (cHeight/2)) / cHeight), screen3[2]};
            
            //NDC space to raster space
            double[] raster1 = {Math.floor(ndc1[0] * iWidth), Math.floor((1-ndc1[1]) * iHeight), ndc1[2]};
            double[] raster2 = {Math.floor(ndc2[0] * iWidth), Math.floor((1-ndc2[1]) * iHeight), ndc2[2]};
            double[] raster3 = {Math.floor(ndc3[0] * iWidth), Math.floor((1-ndc3[1]) * iHeight), ndc3[2]};
            
            //Rasterizer.
            int minX = (int) Math.max(0, Math.ceil(Math.min(raster1[0], Math.min(raster2[0], raster3[0]))));
            int maxX = (int) Math.min(img.getWidth() - 1,Math.floor(Math.max(raster1[0], Math.max(raster2[0], raster3[0]))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(raster1[1], Math.min(raster2[1], raster3[1]))));
            int maxY = (int) Math.min(img.getHeight() - 1,Math.floor(Math.max(raster1[1], Math.max(raster2[1], raster3[1]))));
            
            double triangleArea = (raster1[1] - raster3[1]) * (raster2[0] - raster3[0]) + (raster2[1] - raster3[1]) * (raster3[0] - raster1[0]);
            
            for(int x = minX; x<= maxX; x++){
                for(int y = minY; y<= maxY; y++){
                    double b1 = ((y - raster3[1]) * (raster2[0] - raster3[0]) + (raster2[1] - raster3[1]) * (raster3[0] - x)) / triangleArea;
                    double b2 = ((y - raster1[1]) * (raster3[0] - raster1[0]) + (raster3[1] - raster1[1]) * (raster1[0] - x)) / triangleArea;
                    double b3 = ((y - raster2[1]) * (raster1[0] - raster2[0]) + (raster1[1] - raster2[1]) * (raster2[0] - x)) / triangleArea;
                    
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                        double depth = b1 * raster1[2] + b2 * raster2[2] + b3 * raster3[2];
                        if(zBuffer[y * img.getWidth() + x] < depth){
                            img.setRGB((x), (y), i.getColor().getRGB());
                            zBuffer[y * img.getWidth() + x] = depth;
                        }
                    }
                }
            }
            g2.drawImage(img, 0, 0, null); 
            //Drawing lines between vertices(TO BE REPLACED).
            /*g2.setColor(i.getColor());
            g2.drawLine((int)raster1[0], (int)raster1[1], (int)raster2[0], (int)raster2[1]);
            g2.drawLine((int)raster2[0], (int)raster2[1], (int)raster3[0], (int)raster3[1]);
            g2.drawLine((int)raster3[0], (int)raster3[1], (int)raster1[0], (int)raster1[1]);*/
            
            
        }
    }
}
