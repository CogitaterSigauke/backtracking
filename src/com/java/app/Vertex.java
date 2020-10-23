package com.java.app;

import java.util.List;
import java.util.ArrayList;

public class Vertex{

    private int vertexNumber;
    private int pos;
    private List<Vertex> edgeVertices = new ArrayList<Vertex> ();

    public Vertex(int vertexNumber, int pos){
        this.vertexNumber = vertexNumber;
        this.pos = pos;
    }

    public int getVertexNumber(){
        return vertexNumber;
    }
    public List<Vertex> getEdgeVertices(){
        return edgeVertices;
    }
    public void putEdge(Vertex vertex){
        this.edgeVertices.add(vertex);
    }
    public int getPos(){
        return pos;
    }
    public void setPos(int pos){
        this.pos = pos;
    }
}