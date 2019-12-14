import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AstarSearchAlgo{


        //h scores is the stright-line distance from the current city to Bucharest
        public static void main(String[] args){

                //initialize the graph 
                Node n1 = new Node("S",7);
                Node n2 = new Node("A",6);
                Node n3 = new Node("B",2);
                Node n4 = new Node("C",1);
                Node n5 = new Node("D",0);
                
                
                n1.adjacencies = new Edge[]{
                        new Edge(n2,1),
                        new Edge(n3,4)
                };
                
                n2.adjacencies = new Edge[]{
                        new Edge(n3,2),
                        new Edge(n4,5),
                        new Edge(n5,12)
                };
                
                n3.adjacencies = new Edge[]{
                        
                        new Edge(n4,2)
                        
                };
                
                n4.adjacencies = new Edge[]{
                      
                        new Edge(n5,3)
                };
                n5.adjacencies = new Edge[]{
                      
                        new Edge(n5,0)
                };
                AstarSearch(n1,n5);

                List<Node> path = printPath(n5);

                        System.out.println("Path: " + path);
              
        }

        public static List<Node> printPath(Node target){
                List<Node> path = new ArrayList<Node>();
        // finding & store shortest node
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
        }

        public static void AstarSearch(Node source, Node goal){

                Set<Node> explored = new HashSet<Node>();

                PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                        new Comparator<Node>(){
                                 //override compare method
                 public int compare(Node i, Node j){
                    if(i.f_scores > j.f_scores){
                        return 1;
                    }

                    else if (i.f_scores < j.f_scores){
                        return -1;
                    }

                    else{
                        return 0;
                    }
                 }

                        }
                        );

                //cost from start
                source.g_scores = 0;

                queue.add(source);

                boolean found = false;

                while((!queue.isEmpty())&&(!found)){

                        //the node in having the lowest f_score value
                        Node current = queue.poll();

                        explored.add(current);

                        //goal found
                        if(current.value.equals(goal.value)){
                                found = true;
                        }

                        //check every child of current node
                        for(Edge e : current.adjacencies){
                                Node child = e.target;
                                double cost = e.cost;
                                double temp_g_scores = current.g_scores + cost;
                                double temp_f_scores = temp_g_scores + child.h_scores;


                                /*if child node has been evaluated and 
                                the newer f_score is higher, skip*/
                                
                                if((explored.contains(child)) && 
                                        (temp_f_scores >= child.f_scores)){
                                        continue;
                                }

                                /*else if child node is not in queue or 
                                newer f_score is lower*/
                                
                                else if((!queue.contains(child)) || 
                                        (temp_f_scores < child.f_scores)){

                                        child.parent = current;
                                        child.g_scores = temp_g_scores;
                                        child.f_scores = temp_f_scores;

                                        if(queue.contains(child)){
                                                queue.remove(child);
                                        }

                                        queue.add(child);

                                }

                        }

                }

        }
        
}

class Node{

        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;

        public Node(String val, double hVal){
                value = val;
                h_scores = hVal;
        }

        public String toString(){
                return value;
        }

}

class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
                target = targetNode;
                cost = costVal;
        }
}