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
import java.util.Iterator;


public class Main{

    private static boolean finished = false;
    private static int max = Integer.MAX_VALUE;
    private static Map<Integer,Integer> res = new HashMap<>();
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

            }else{
                if(!map.containsKey(num)){
                    odd = new Vertex(num, index);
                    graph[index] = odd;
                    map.put(num, index);
                    index++;
                }else{
                    odd = graph[map.get(num)];
                }
                even.putEdge(odd);
                odd.putEdge(even);
            }
            
            x++;
            
        } 

        // printGraph(graph);
        System.out.println("SIZE = " + map.size() + "  " + graph.length);
        Map<Integer,Integer> maxs = new HashMap<>();
        Map<Integer,Integer> aMap = new HashMap<>();
        Random random = ThreadLocalRandom.current();

        dfs(graph, res, graph[random.nextInt(graph.length)] );
        max = initialMaxEdge(res, graph, map);
        backtrack(maxs, aMap, 0, graph, map);
        printFinalSolution(graph);

    }

    public static void printFinalSolution(Vertex[] graph){
        
        System.out.println("\nMinimun Bandwith:  "+max+"\n---------------------");
        int [] ordered = new int[res.size()];
        for(int i: res.keySet()){
            ordered[res.get(i)] = i;
        }
        for(int i = 0; i<ordered.length; i++){
            System.out.print(ordered[i] + "  ");
        }
        System.out.println();
    }

    public static void processSolution(Map<Integer,Integer> maxs, Map<Integer,Integer> aMap, Vertex[] graph, Map<Integer, Integer> map){
        int currMax = maxEdge(maxs);

        if(currMax < max){
            max = currMax;
            res = new HashMap<>(aMap);
            System.out.println("BW:  "+max);
        }
        if(max <= 1){
            finished = true;
        }
    }

    public static void backtrack(Map<Integer,Integer> maxs, Map<Integer,Integer> aMap, int size, Vertex [] graph, Map<Integer, Integer> map){
        
        int [] candidates;
        int nCandicates;

        if(isASolution(aMap, graph)){
            processSolution(maxs, aMap, graph, map);
        }
        else{

            candidates = constructCandidates(aMap, graph);
            nCandicates = candidates.length;
            
            for(int i=0; i<nCandicates; i++){

                Iterator iter = aMap.entrySet().iterator();

                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry)iter.next();
                    int index = (int)entry.getValue();
                    if (index >= size)
                        iter.remove();
                }

                Iterator iter2 = maxs.entrySet().iterator();

                while (iter2.hasNext()) {
                    Map.Entry entry = (Map.Entry)iter2.next();
                    int index = (int)entry.getKey();
                    if (index >= size)
                        iter2.remove();
                }

                aMap.put(candidates[i], size);
                int num = currMaxEdge(aMap, graph, map);
                maxs.put(size, num);
                // if there is an edge greater than max on the recently input break
                if(max <= num){
                    break;
                }
                backtrack(maxs, aMap, size+1, graph, map);
                if(finished){
                    break;
                }
            }
        }
    }
    public static boolean dfs(Vertex[] graph, Map<Integer,Integer> aMap, Vertex vertex){
        if(aMap.size() == graph.length){
            return true;
        }

        boolean done = false;
        List<Vertex> e = vertex.getEdgeVertices();
        for(int i = 0; i<e.size(); i++){

            Vertex v = e.get(i);
            if(v.getPos() != -10){
                v.setPos(-10);
                aMap.put(v.getVertexNumber(), aMap.size());
                done = dfs(graph, aMap, v);
            }
            
            if(done)
                break;
        }
        return done;


    }
    public static int[] constructCandidates(Map<Integer,Integer> aMap, Vertex[] graph){

        int[] candidates = new int[graph.length - aMap.size()];
        int j = 0;
        for(int i=0; i<graph.length; i++){
            if(!aMap.containsKey(graph[i].getVertexNumber())){
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

    public static boolean isASolution(Map<Integer,Integer> aMap, Vertex [] graph){
        return aMap.size() == graph.length;
    }
    // check for a max edge of greater than max
    public static int currMaxEdge(Map<Integer,Integer> aMap, Vertex [] graph, Map<Integer, Integer> map){
        
        int index = -1;

        Vertex v = new Vertex(-1,-1);

        for(int i : aMap.keySet()){

            int z = map.get(i); //index of vertex in graph
            int t = aMap.get(i); // index of vertex in current sequence
            graph[z].setPos(t); //set position of vertex

            if(t > index){
                index = t;
                v =  graph[z];
            }
            
        }

        int currMax = 0;

        List<Vertex> e = v.getEdgeVertices();

        for(int j = 0; j<e.size(); j++){

            if(aMap.containsKey(e.get(j).getVertexNumber())){

                int l = Math.abs(v.getPos() -  e.get(j).getPos()); 
                currMax = Math.max(l, currMax);
            }
        }
       
        
        return currMax; 
    }
    // check for a max edge of greater than max
    public static int maxEdge(Map<Integer,Integer> maxs){
        
        int currMax = 0;
        for(int i : maxs.keySet()){
            currMax = Math.max(maxs.get(i), currMax);
        }
        return currMax; 
    }

        // check for a max edge of greater than max
    public static int initialMaxEdge(Map<Integer,Integer> aMap, Vertex [] graph, Map<Integer, Integer> map){
        
        for(int i : aMap.keySet()){

            int z = map.get(i); //index of vertex in graph
            int t = aMap.get(i); // index of vertex in current sequence
            graph[z].setPos(t); //set position of vertex
        }

        int currMax = 0;

        for(int i=0; i<graph.length; i++){

            Vertex v = graph[i];
            List<Vertex> e = v.getEdgeVertices();

            for(int j = 0; j<e.size(); j++){

                if(aMap.containsKey(e.get(j).getVertexNumber())){

                    int l = Math.abs(v.getPos() -  e.get(j).getPos()); 
                    currMax = Math.max(l, currMax);
                }
            }
        }    
        
        return currMax; 
    }

}
