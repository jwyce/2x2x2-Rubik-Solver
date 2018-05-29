package rubik;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;

public class Solver {
	
	public static void main(String[] args) {
		new RubikGUI();
	}
	
	/*
     * Implements a 2-way breadth-first search on the RubikState implicit graph
     * returns the shortest path (solution) which is under 11 moves
     * (the diameter of a 2x2x2 state graph is 11 if half twists count as a single move, 14 if they count as two)
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
	    bqueue.add(src);
	    
	    // bfs visit from both ends of graph
	    while (true) {
	        end = fqueue.remove();
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
