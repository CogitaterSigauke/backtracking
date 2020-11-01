package com.java.app;

import java.util.List;
import java.util.ArrayList;

public class Vertex{

    private int vertexNumber;
    private int pos;
    private List<Vertex> edgeVertices = new ArrayList<Vertex> ();
    private List<Edge> edges = new ArrayList<> ();

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
    public List<Edge> getEdges(){
        return edges;
    }
    public void putEdge(Vertex vertex){
        this.edgeVertices.add(vertex);
    }
    public void putEdge(Edge edge){
        this.edges.add(edge);
    }
    
    public int getPos(){
        return pos;
    }
    public void setPos(int pos){
        this.pos = pos;
    }
}