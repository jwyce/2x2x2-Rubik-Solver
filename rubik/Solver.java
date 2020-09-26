package rubik;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;

public class Solver {
	
    public static final int GODS_NUMBER = 14;
    
	public static void main(String[] args) {
		new RubikGUI();
	}
	
	/*
     * Implements a 2-way breadth-first search on the RubikState implicit graph
     * returns the shortest path (solution) which is under 20 moves
     * (the diameter of a 2x2x2 state graph is 14)
     */
	public static String solve(RubikState state) {
	    HashMap<RubikState, String> forwardParents = new HashMap<RubikState, String>();
	    HashMap<RubikState, String> backwardParents = new HashMap<RubikState, String>();
	    ArrayDeque<RubikState> fqueue = new ArrayDeque<RubikState>();
	    ArrayDeque<RubikState> bqueue = new ArrayDeque<RubikState>();
	    RubikState src = state, end = new RubikState();
	    forwardParents.put(end, null);
	    backwardParents.put(src, null);
	    fqueue.add(end);
	    fqueue.add(new RubikState(true));
	    bqueue.add(src);
	    
	    // check if cube already solved
	    if (end.equals(src))
	        return "Already solved";
	    
	    // bfs visit from both ends of graph
	    int graphRadius = GODS_NUMBER/2;
	    for (int i = 0; i <= graphRadius; i++)
	    {
	        while (true) {
	            end = fqueue.remove();
	            if (end.isNullState) {
	                fqueue.add(new RubikState(true));
	                break;
	            }
	            
	            for (Entry<String, RubikState> move : end.getReachableStates().entrySet()) {
	                if (!forwardParents.containsKey(move.getValue())) {
	                    forwardParents.put(move.getValue(), move.getKey()); 
	                    fqueue.add(move.getValue());
	                }
	            }
	            
	            src = bqueue.remove();
	            
	            for (Entry<String, RubikState> move : src.getReachableStates().entrySet()) {
	                if (!backwardParents.containsKey(move.getValue())) {
	                    backwardParents.put(move.getValue(), move.getKey()); 
	                    bqueue.add(move.getValue());
	                }
	                // same state discovered in both ends of search
	                if (forwardParents.containsKey(move.getValue())) {
	                    String endpath = path(move.getValue(), forwardParents);
	                    String srcpath = path(move.getValue(), backwardParents);
	                    srcpath = reverse(srcpath);
	                    String solutionPath = srcpath + " " + endpath;
	                    return solutionPath.replaceAll("(([RUF])'?) \\1", "$22");
	                }
	            }
	        }
        }
	    
	    return "No solution, impossible configuration";
	   
	}
	
	private static String reverse(String path) {
	    path += " ";
	    String reverse = "";
	    
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == ' ')
                reverse += "' ";
            else if (path.charAt(i) != '\'')
                reverse += path.charAt(i);
            else {
                reverse += " ";
                i++;
            }
        }
        
        String ar[] = reverse.split(" ");
        for (int i = 0; i < ar.length/2; i++) {
            String temp = ar[i];
            ar[i] = ar[ar.length-1-i];
            ar[ar.length-1-i] = temp;
        }
        
        return String.join(" ", ar);
	}
	
	private static String path(RubikState state, HashMap<RubikState, String> parents) {
	    String path = parents.get(state);
        RubikState next = new RubikState(state.positions);
        next.executeMoveSeq(path);
        
        while (parents.get(next) != null) {
            path += " " + parents.get(next);
            next = new RubikState(state.positions);
            next.executeMoveSeq(path);
        }
        
        return path;
	}
	
}	
