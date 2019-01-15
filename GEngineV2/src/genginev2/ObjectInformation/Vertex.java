/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2.ObjectInformation;

/**
 *
 * @author George
 */
public class Vertex {
    private double x;
    private double y;
    private double z;
    private double w;
    public Vertex(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        w = 1;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public double getW(){
        return w;
    }
    public double[] getVertex(){
        double[] localCoords = {x,y,z};
        return localCoords;
    }
    public void setCoords(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
