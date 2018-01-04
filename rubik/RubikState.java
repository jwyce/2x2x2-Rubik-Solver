package rubik;

import java.util.Arrays;
import java.util.HashMap;

public class RubikState {

	/**
	 * 24 positions for each color
	 * w - white
	 * y - yellow
	 * g - green
	 * b - blue
	 * o - orange
	 * r - red
	 * each consecutive 4 indeces are a face labeled starting from
	 * top left going around clockwise
	 * i:0-3 top face
	 * i:4-7 left face
	 * i:8-11 front face
	 * i:12-15 right face
	 * i:16-19 bottom face
	 * i:20-23 back face
	 */
	char[] positions;
	
	public RubikState() {
		// create a solved cube
		positions = new char[24];
		for (int i = 0; i < 4; i++)
			positions[i] = 'w';
		for (int i = 4; i < 8; i++)
			positions[i] = 'o';
		for (int i = 8; i < 12; i++)
			positions[i] = 'g';
		for (int i = 12; i < 16; i++)
			positions[i] = 'r';
		for (int i = 16; i < 20; i++)
			positions[i] = 'y';
		for (int i = 20; i < 24; i++)
			positions[i] = 'b';
	}
	
	public RubikState(char[] positions) {
		this.positions = positions;
	}
	
	/**
	 * Permutations (Moves) show how the positions array indeces move
	 * F - front rotated clockwise
	 * F' - front counter clockwise
	 * R - right clockwise
	 * R' - right counter clockwise
	 * U - up clockwise
	 * U' - up counter clockwise
	 */
	private static int[] F = {0,1,5,6,4,16,17,7,11,8,9,10,3,13,14,2,15,12,18,19,20,21,22,23};
	private static int[] Fi = perm_inverse(F);
	private static int[] U = {3,0,1,2,8,9,6,7,12,13,10,11,20,21,14,15,16,17,18,19,4,5,22,23};
	private static int[] Ui = perm_inverse(U);
	private static int[] R = {0,9,10,3,4,5,6,7,8,17,18,11,15,12,13,14,16,23,20,19,2,21,22,1};
	private static int[] Ri = perm_inverse(R);
	
	public static HashMap<int[], String> quarter_twists() {
		HashMap<int[], String> quarter_twists = new HashMap<>();
		quarter_twists.put(F, "F");
		quarter_twists.put(Fi, "F'");
		quarter_twists.put(U, "U");
		quarter_twists.put(Ui, "U'");
		quarter_twists.put(R, "R");
		quarter_twists.put(Ri, "R'");
		return quarter_twists;
	}
	
	public static int[] perm_inverse(int[] p) {
		int n = p.length;
		int[] g = new int[n];
		for (int i = 1; i < n; i++) {
			g[p[i]] = i;
		}
		return g;
	}

	public char[] perm_apply(int[] perm) {
		char[] newPositions = new char[24];
		for (int i = 0; i < 24; i++) {
			newPositions[i] = positions[perm[i]];
		}
		return newPositions;
	}
	
	public HashMap<String, RubikState> getReachableStates(){
		HashMap<String, RubikState> moves = new HashMap<>();
		addBasicMove("F'", F, moves);
		addBasicMove("F", Fi, moves);
		addBasicMove("U'", U, moves);
		addBasicMove("U", Ui, moves);
		addBasicMove("R'", R, moves);
		addBasicMove("R", Ri, moves);
		return moves;
	}
	
	private void addBasicMove(String name, int[] perm, HashMap<String, RubikState> moves) {
		RubikState state = new RubikState(perm_apply(perm));
		moves.put(name, state);
	}
	
	public void scramble(String scramble) {
		if (scramble == null) return;
		String[] moves = scramble.toUpperCase().split(" ");
		for (String move : moves) {
			switch(move) {
			case "F":
				positions = perm_apply(F);
				break;
			case "F'":
				positions = perm_apply(perm_inverse(F));
				break;
			case "F2":
				positions = perm_apply(F);
				positions = perm_apply(F);
				break;
			case "U":
				positions = perm_apply(U);
				break;
			case "U'":
				positions = perm_apply(perm_inverse(U));
				break;
			case "U2":
				positions = perm_apply(U);
				positions = perm_apply(U);
				break;
			case "R":
				positions = perm_apply(R);
				break;
			case "R'":
				positions = perm_apply(perm_inverse(R));
				break;
			case "R2":
				positions = perm_apply(R);
				positions = perm_apply(R);
				break;
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Arrays.toString(this.positions).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().getSimpleName().equals("RubikState")) return false;
		RubikState state = (RubikState) obj;
		if (state.positions.length != this.positions.length) return false;
		for (int i = 0; i < this.positions.length; i++) {
			if (this.positions[i] != state.positions[i]) 
				return false;
		}
		return true;
	}

}
