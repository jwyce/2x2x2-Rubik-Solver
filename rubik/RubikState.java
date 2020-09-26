package rubik;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class RubikState {

	/**
	 * 24 positions for each color
	 * w - white
	 * y - yellow
	 * g - green
	 * b - blue
	 * o - orange
	 * r - red
	 * each consecutive 4 indices are a face labeled starting from
	 * top left going around clockwise
	 * i:0-3 top face
	 * i:4-7 left face
	 * i:8-11 front face
	 * i:12-15 right face
	 * i:16-19 bottom face
	 * i:20-23 back face
	 */
	char[] positions;
	boolean isNullState;
	
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
		this.isNullState = false;
	}
	
	public RubikState(char[] positions) {
		this.positions = positions;
		this.isNullState = false;
	}
	
	public RubikState(boolean nullState) {
        this.isNullState = nullState;
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
    private static int[] Fi = permInverse(F);
    private static int[] U = {3,0,1,2,8,9,6,7,12,13,10,11,20,21,14,15,16,17,18,19,4,5,22,23};
    private static int[] Ui = permInverse(U);
    private static int[] R = {0,9,10,3,4,5,6,7,8,17,18,11,15,12,13,14,16,23,20,19,2,21,22,1};
    private static int[] Ri = permInverse(R);
	
	public static int[] permInverse(int[] p) {
		int n = p.length;
		int[] g = new int[n];
		for (int i = 1; i < n; i++) {
			g[p[i]] = i;
		}
		return g;
	}

	public char[] permApply(int[] perm) {
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
		RubikState state = new RubikState(permApply(perm));
		moves.put(name, state);
	}
	
	public void executeMoveSeq(String seq) {
		if (seq == null) return;
		String[] moves = seq.toUpperCase().split(" ");
		for (String move : moves) {
			switch(move) {
			case "F":
				positions = permApply(F);
				break;
			case "F'":
				positions = permApply(Fi);
				break;
			case "F2":
				positions = permApply(F);
				positions = permApply(F);
				break;
			case "U":
				positions = permApply(U);
				break;
			case "U'":
				positions = permApply(Ui);
				break;
			case "U2":
				positions = permApply(U);
				positions = permApply(U);
				break;
			case "R":
				positions = permApply(R);
				break;
			case "R'":
				positions = permApply(Ri);
				break;
			case "R2":
				positions = permApply(R);
				positions = permApply(R);
				break;
			}
		}
	}
	
	public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
	    int random = start + rnd.nextInt(end - start + 1 - exclude.length);
	    for (int ex : exclude) {
	        if (random < ex) {
	            break;
	        }
	        random++;
	    }
	    return random;
	}
	
	public String randomize() {
	    String scramble = "";
	    Random rand = new Random();
	    int prevMoveGroup = -1;
	    String[][] moves = {
	            {"F", "F'", "F2"}, 
	            {"U", "U'", "U2"}, 
	            {"R", "R'", "R2"}
	    };
	    
	    for (int i = 0; i < 17; i++) {
	        prevMoveGroup = getRandomWithExclusion(rand, 0, moves.length - 1, prevMoveGroup);
	        String move = moves[prevMoveGroup][rand.nextInt(moves[0].length)];
	        scramble += move + " ";
	    }
	    
	    scramble = scramble.substring(0, scramble.length() - 2);
	    executeMoveSeq(scramble);
	    
	    return scramble;
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