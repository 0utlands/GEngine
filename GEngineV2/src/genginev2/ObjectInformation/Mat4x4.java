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
public class Mat4x4 {
    public double[][] grid;
    private double[] vector;
    public Mat4x4(){
        grid = new double[4][4];
        vector = new double[4];
    }
    public double[][] matMultiplyMat(){
        return grid;
    }
    public double[] matMultiplyVector(double x, double y, double z, double w){
        vector[0] = (x * grid[0][0]) + (y * grid[0][1]) + (z * grid[0][2]) + (w * grid[0][3]);
        vector[1] = (x * grid[1][0]) + (y * grid[1][1]) + (z * grid[1][2]) + (w * grid[1][3]);
        vector[2] = (x * grid[2][0]) + (y * grid[2][1]) + (z * grid[2][2]) + (w * grid[2][3]);
        vector[3] = (x * grid[3][0]) + (y * grid[3][1]) + (z * grid[3][2]) + (w * grid[3][3]);
        return vector;
    }
}
