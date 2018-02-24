package rubik;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

public class Solver {
	
	public static void main(String[] args) {
		new RubikGUI();
	}
	
	/*
	 * Implements a breadth-first search on the RubikState implicit graph
	 * returns the shortest path (solution) which is under 11 moves
	 * (the diameter of a 2x2x2 state graph is 14 if half twists count as 2 moves, 11 otherwise)
	 */
	public static String shortestPath(RubikState state) {
		HashMap<RubikState, String> parents = bfs_visit(state, new RubikState());
		String solutionPath = parents.get(state);
		RubikState next = new RubikState(state.positions);
		next.execute_move_seq(solutionPath);
		
		while (parents.get(next) != null) {
			solutionPath += " " + parents.get(next);
			next = new RubikState(state.positions);
			next.execute_move_seq(solutionPath);
		}
		
		return solutionPath.replaceAll("(([RUF])'?) \\1", "$22");
	}
	
	private static HashMap<RubikState, String> bfs_visit(RubikState source, RubikState destination) {
		HashMap<RubikState, String> parents = new HashMap<>();
		Queue<RubikState> queue = new LinkedList<RubikState>();
		parents.put(destination, null);
		queue.add(destination);
		
		while (true) {
			destination = queue.remove();
			for (Entry<String, RubikState> move : destination.getReachableStates().entrySet()) {
				if (!parents.containsKey(move.getValue())) {
					parents.put(move.getValue(), move.getKey()); 
					queue.add(move.getValue());
				}
				if (move.getValue().equals(source))
					return parents;
			}
		}
	}

}
