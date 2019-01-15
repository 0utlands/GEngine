/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2.ObjectInformation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author George
 */
public class Triangle {
    private List<Vertex> vertices;
    private List<Vertex> verticesInWorld;
    private Color color;
    private double xTranslation;
    private double yTranslation;
    private double zTranslation;
    private double xRotation;
    private double yRotation;
    private double zRotation;
    private Mat4x4 rotMatX;
    private Mat4x4 rotMatY;
    private Mat4x4 rotMatZ;
    private Mat4x4 transMat;
    public Triangle(Vertex v1,Vertex v2,Vertex v3, Color color){
        vertices = new ArrayList<>();
        verticesInWorld = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        verticesInWorld.add(new Vertex(0,0,0));
        verticesInWorld.add(new Vertex(0,0,0));
        verticesInWorld.add(new Vertex(0,0,0));
        this.color = color;
        rotMatX = new Mat4x4();
        rotMatY = new Mat4x4();
        rotMatZ = new Mat4x4();
        transMat = new Mat4x4();
        xTranslation = 0;
        yTranslation = 0;
        zTranslation = 0;
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }
    public void setWorldSpace(double xTranslation, double yTranslation, double zTranslation, double xRotation, double yRotation, double zRotation){
        this.xTranslation = xTranslation;
        this.yTranslation = yTranslation;
        this.zTranslation = zTranslation;
        if(xRotation != 0){
            this.xRotation = xRotation;
            xRotation = Math.toRadians(xRotation);
        }
        if(yRotation != 0){
            this.yRotation = yRotation;
            yRotation = Math.toRadians(yRotation);
        }
        if(zRotation != 0){
            this.zRotation = zRotation;
            zRotation = Math.toRadians(zRotation);
        }

    }
    public List<Vertex> getWorldVertices(){
        rotMatX.grid[0][0] = 1; rotMatX.grid[0][1] = 0; rotMatX.grid[0][2] = 0; rotMatX.grid[0][3] = 0;
        rotMatX.grid[1][0] = 0; rotMatX.grid[1][1] = Math.cos(xRotation); rotMatX.grid[1][2] = -Math.sin(xRotation); rotMatX.grid[1][3] = 0;
        rotMatX.grid[2][0] = 0; rotMatX.grid[2][1] = Math.sin(xRotation); rotMatX.grid[2][2] = Math.cos(xRotation); rotMatX.grid[2][3] = 0;
        rotMatX.grid[3][0] = 0; rotMatX.grid[3][1] = 0; rotMatX.grid[3][2] = 0; rotMatX.grid[3][3] = 1;
        
        rotMatY.grid[0][0] = Math.cos(yRotation); rotMatY.grid[0][1] = 0; rotMatY.grid[0][2] = Math.sin(yRotation); rotMatY.grid[0][3] = 0;
        rotMatY.grid[1][0] = 0; rotMatY.grid[1][1] = 1; rotMatY.grid[1][2] = 0; rotMatY.grid[1][3] = 0;
        rotMatY.grid[2][0] = -Math.sin(yRotation); rotMatY.grid[2][1] = 0; rotMatY.grid[2][2] = Math.cos(yRotation); rotMatY.grid[2][3] = 0;
        rotMatY.grid[3][0] = 0; rotMatY.grid[3][1] = 0; rotMatY.grid[3][2] = 0; rotMatY.grid[3][3] = 1;
        
        rotMatZ.grid[0][0] = Math.cos(zRotation); rotMatZ.grid[0][1] = -Math.sin(zRotation); rotMatZ.grid[0][2] = 0; rotMatZ.grid[0][3] = 0;
        rotMatZ.grid[1][0] = Math.sin(zRotation); rotMatZ.grid[1][1] = Math.cos(zRotation); rotMatZ.grid[1][2] = 0; rotMatZ.grid[1][3] = 0;
        rotMatZ.grid[2][0] = 0; rotMatZ.grid[2][1] = 0; rotMatZ.grid[2][2] = 1; rotMatZ.grid[2][3] = 0;
        rotMatZ.grid[3][0] = 0; rotMatZ.grid[3][1] = 0; rotMatZ.grid[3][2] = 0; rotMatZ.grid[3][3] = 1;
        
        transMat.grid[0][0] = 1; transMat.grid[0][1] = 0; transMat.grid[0][2] = 0; transMat.grid[0][3] = xTranslation;
        transMat.grid[1][0] = 0; transMat.grid[1][1] = 1; transMat.grid[1][2] = 0; transMat.grid[1][3] = yTranslation;
        transMat.grid[2][0] = 0; transMat.grid[2][1] = 0; transMat.grid[2][2] = 1; transMat.grid[2][3] = zTranslation;
        transMat.grid[3][0] = 0; transMat.grid[3][1] = 0; transMat.grid[3][2] = 0; transMat.grid[3][3] = 1;

        verticesInWorld.get(0).setCoords(vertices.get(0).getX(), vertices.get(0).getY(), vertices.get(0).getZ());
        verticesInWorld.get(1).setCoords(vertices.get(1).getX(), vertices.get(1).getY(), vertices.get(1).getZ());
        verticesInWorld.get(2).setCoords(vertices.get(2).getX(), vertices.get(2).getY(), vertices.get(2).getZ());
        
        for(Vertex i : verticesInWorld){
            double[] rX = rotMatX.matMultiplyVector(i.getX(), i.getY(), i.getZ(), 1);
            double[] rY = rotMatY.matMultiplyVector(rX[0], rX[1], rX[2], 1);
            double[] rZ = rotMatZ.matMultiplyVector(rY[0], rY[1], rY[2], 1);
            double[] t = transMat.matMultiplyVector(rZ[0], rZ[1], rZ[2], 1);
            i.setCoords(t[0], t[1], t[2]);
            
        } 
        return verticesInWorld;
    }
    public void changeWorldSpace(double xTranslationC, double yTranslationC, double zTranslationC, double xRotationC, double yRotationC, double zRotationC){
        xTranslation += xTranslationC;
        yTranslation += yTranslationC;
        zTranslation += zTranslationC;
        if(xRotationC != 0){
            xRotation += Math.toRadians(xRotationC);
            if(xRotation > 2 * Math.PI){
                xRotation -= 2 * Math.PI;
            }
        }
        if(yRotationC != 0){
            yRotation += Math.toRadians(yRotationC);
            if(yRotation > 2 * Math.PI){
                yRotation -= 2 * Math.PI;
            }
        }
        if(zRotationC != 0){
            zRotation += Math.toRadians(zRotationC);
            if(zRotation > 2 * Math.PI){
                zRotation -= 2 * Math.PI;
            }
        }
    }
    public Color getColor(){
        return color;
    }
}
