package com.java.app;

public class Edge{

    private int v1;
    private int v2;
    private boolean visited;

    public Edge(int v1, int v2){
        this.v1 = v1;
        this.v2 = v2;
        this.visited = false;
    }

    public int getV1(){
        return v1;
    }
    public void setV1(int v1){
        this.v1 = v1;
    }

    public int getV2(){
        return v1;
    }
    public void setV2(int v1){
        this.v1 = v1;
    }

    public boolean getVisited(){
        return visited;
    }
    public void setVisited(boolean visited){
        this.visited = visited;
    }
}