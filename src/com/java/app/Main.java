//Cogitater Sigauke

package com.java.app;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
        List<Integer> maxs = new ArrayList<>();
        backtrack(res, maxs, graph, map);
        printFinalSolution(graph);

    }

    public static void printFinalSolution(Vertex[] graph){
        
        System.out.println("\nMinimun Bandwith:  "+max+"\n---------------------");
        
        for(int i=0; i<res.size(); i++){
            System.out.print(res.get(i) + "  ");
        }
        System.out.println();
    }

    public static void processSolution(List<Integer> a, List<Integer> maxs, Vertex[] graph, Map<Integer, Integer> map){
        int currMax = maxEdge(maxs);
        if(currMax < max){
            max = currMax;
            res = a;
            System.out.println("BW:  "+max);
        }
        if(max <= 1){
            finished = true;
        }
    }

    public static void backtrack(List<Integer> a, List<Integer> maxs, Vertex [] graph, Map<Integer, Integer> map){
        
        int [] candidates;
        int nCandicates;

        if(isASolution(a, graph)){
            processSolution(a, maxs, graph, map);
        }
        else{

            candidates = constructCandidates(a, graph);
            nCandicates = candidates.length;
            
            for(int i=0; i<nCandicates; i++){

                List<Integer> temp = new ArrayList<Integer>(a);
                List<Integer> temp2 = new ArrayList<Integer>(maxs);

                temp.add(candidates[i]);
                int num = currMaxEdge(temp, graph, map);
                
                // if there is an edge greater than max on the recently input break
                if(max <= num){
                    break;
                }
                temp2.add(num);
                backtrack(temp, temp2, graph, map);
                if(finished){
                    break;
                }
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

        shuffle(candidates);
        return candidates;
    }

    //randomize candidates array position to incease the spead
    public static void shuffle(int[] array){
        Random rand = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--){

            int index = rand.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static boolean isASolution(List<Integer> a, Vertex [] graph){
        return a.size() == graph.length;
    }
    // check for a max edge of greater than max
    public static int currMaxEdge(List<Integer> a, Vertex [] graph, Map<Integer, Integer> map){
        
        for(int i=0; i<a.size(); i++){
            int z = map.get(a.get(i));
            graph[z].setPos(i);
        }

        int currMax = 0;

        Vertex v = graph[map.get(a.get(a.size()-1))]; //only cjeck the adges of the new element
        List<Vertex> e = v.getEdgeVertices();

        for(int j = 0; j<e.size(); j++){

            if(a.contains(e.get(j).getVertexNumber())){

                int l = Math.abs(v.getPos() -  e.get(j).getPos()); 
                currMax = Math.max(l, currMax);
            }
        }
        
        return currMax; 
    }
    // check for a max edge of greater than max
    public static int maxEdge(List<Integer> maxs){
        
        int currMax = 0;
        for(int i=0; i<maxs.size(); i++){
            currMax = Math.max(maxs.get(i), currMax);
        }
        return currMax; 
    }
}
