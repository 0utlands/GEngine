/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genginev2.ObjectInformation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 480591
 */
public class ObjectData {
    private List<Triangle> renderTriangles;
    public ObjectData(){  
        renderTriangles = new ArrayList<>();
    }
    public void inputTriangle(Triangle inputTriangle){
        renderTriangles.add(inputTriangle);
    }
    public List<Triangle> getTriangles(){
        return renderTriangles;
    }
}
