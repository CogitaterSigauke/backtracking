package com.java.app;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException; 

public class Main{

    private static boolean finished = false;
    private static int max = Integer.MAX_VALUE;
    private static List<Integer> res = new ArrayList<Integer>();

    public static void main(String [] args) throws FileNotFoundException {

        File file = new File(args[0]); 
        Scanner sc = new Scanner(file); 

        int numVertices = sc.nextInt(), numEdges = sc.nextInt(); 
        System.out.println("Vertices: "+numVertices + "\nEdges: " + numEdges);
        int x = 0;

        Vertex [] graph = new Vertex [numVertices];
        Map<Integer, Integer> map = new HashMap<>();

        Vertex even = new Vertex(-1, -1);
        Vertex odd = new Vertex(-1, -1);
        int index = 0;

        while (sc.hasNextInt()) 
        { 
            // Read an int value 
            int num = sc.nextInt();
            
            if(x % 2 == 0){
                if(!map.containsKey(num)){
                    even = new Vertex(num, index);
                    graph[index] = even;
                    map.put(num, index);
                    index++;
                }else{
                    even = graph[map.get(num)];
                }
                // System.out.print(num + " --- ");

            }else{
                if(!map.containsKey(num)){
                    odd = new Vertex(num, index);
                    graph[index] = odd;
                    map.put(num, index);
                    index++;
                }else{
                    odd = graph[map.get(num)];
                }
                // System.out.println(num);  
                even.putEdge(odd);
                odd.putEdge(even);
            }
            
            x++;
            
        } 

        // printGraph(graph);
        System.out.println("SIZE = " + map.size() + "  " + graph.length);
        backtrack(res, graph, map);
        printFinalSolution(graph);

    }

    public static void printFinalSolution(Vertex[] graph){
        
        System.out.println("\nMinimun Bandwith:  "+max+"\n---------------------");
        
        for(int i=0; i<res.size(); i++){
            System.out.print(res.get(i) + "  ");
        }
        System.out.println();
    }

    public static void processSolution(List<Integer> a, Vertex[] graph, Map<Integer, Integer> map){
        int currMax = maxEdgeLength(a, graph, map);
        if(currMax < max){
            max = currMax;
            res = new ArrayList<Integer>(a);
            System.out.println("BANDWITH: " + max);
            System.out.println("--------------");   
            for(int i=0; i<res.size(); i++){
                System.out.print(res.get(i) + "  ");
            }
            System.out.println();
            System.out.println();
        }

        if(max <= 1){
            finished = true;
        }
    }

    public static void backtrack(List<Integer> a, Vertex [] graph, Map<Integer, Integer> map){
        
        int [] candidates;
        int nCandicates;

        if(isASolution(a, graph)){
            processSolution(a, graph, map);
        }
        else{

            candidates = constructCandidates(new ArrayList<Integer>(a), graph);
            nCandicates = candidates.length;
            
            for(int i=0; i<nCandicates; i++){

                // a.add(candidates[i]);
                List<Integer> temp = new ArrayList<Integer>(a);
                temp.add(candidates[i]);
                backtrack(temp, graph, map);
                if(finished){
                    break;
                }
            }

        }

    }


    public static void printGraph(Vertex [] graph){
        
        for(int i=0; i<graph.length; i++){
            
            Vertex v = graph[i];
            List<Vertex> e = v.getEdgeVertices();

            for(int j = 0; j<e.size(); j++){
                
                System.out.println(v.getPos()+"{ "+ v.getVertexNumber() + ";" + e.get(j).getVertexNumber() + " }");
            }

        }

    }
    public static int[] constructCandidates(List<Integer> a, Vertex[] graph){
        int[] candidates = new int[graph.length - a.size()];
        int j = 0;
        for(int i=0; i<graph.length; i++){
            if(!a.contains(graph[i].getVertexNumber())){
                candidates[j] = graph[i].getVertexNumber();
                j++;
            }
        }
        return candidates;
    }


    public static boolean isASolution(List<Integer> a, Vertex [] graph){
        return a.size() == graph.length;
    }

    public static int maxEdgeLength(List<Integer> a, Vertex [] graph, Map<Integer, Integer> map){
        for(int i=0; i<graph.length; i++){
            int z = map.get(a.get(i));
            graph[z].setPos(i);
        }
        int currMax = 0;
        for(int i=0; i<graph.length; i++){
            Vertex v = graph[i];
            List<Vertex> e = v.getEdgeVertices();

            for(int j = 0; j<e.size(); j++){

                int l = Math.abs(v.getPos() -  e.get(j).getPos()); 

                if(currMax < l){
                    currMax = l;
                }

            }
        }
        return currMax;       

    }
}
