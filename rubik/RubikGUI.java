package rubik;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RubikGUI extends JFrame {

	private static final long serialVersionUID = -4226981410944431646L;
	private RubikNet rubiknet = new RubikNet(new RubikState());
	private JButton solve = new JButton("Solve!");
	private JTextField input = new JTextField(1);
	private JLabel solution = new JLabel(), time = new JLabel();
	
	public RubikGUI() {
		this.buildFrame();
		this.addComponents();
		this.setContentPane(rubiknet);
		this.setVisible(true);
	}
	
	private void buildFrame() {
		this.setTitle("2x2x2 Rubik's Cube Solver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(950, 700);
		this.setResizable(false);
		this.requestFocus();
	}
	
	private void addComponents() {
		solve.setFont(new Font("Impact", Font.PLAIN, 22));
		solve.addActionListener(buttonListener);
		solve.setSize(100, 50);
		solve.setLocation(460, 25);
		
		input.setFont(new Font("Ariel", Font.PLAIN, 16));
		input.setBackground(Color.GRAY.darker());
		input.setForeground(Color.WHITE);
		input.setText("Enter Scramble Here");
		input.addActionListener(inputListtener);
		input.setSize(300, 30);
		input.setLocation(440, 90);
		
		solution.setFont(new Font("Ariel", Font.BOLD, 20));
		solution.setSize(500, 30);
		solution.setLocation(440, 125);
		time.setFont(new Font("Ariel", Font.BOLD, 20));
		time.setSize(500, 30);
		time.setLocation(440, 150);
		
		rubiknet.add(solve);
		rubiknet.add(input);
		rubiknet.add(solution);
		rubiknet.add(time);
	}
	
	ActionListener buttonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			long startTime = System.currentTimeMillis();
			solution.setText(Solver.shortestPath(rubiknet.state));
			time.setText("time elapsed: " + (System.currentTimeMillis() - startTime)/1000 + "s.");
			rubiknet.state.execute_move_seq(solution.getText());
			rubiknet.repaint();
		}
	};
	ActionListener inputListtener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String scramble = e.getActionCommand();
			if (scramble.matches("[furFUR]['2]?( [furFUR]['2]?)*")) {
				rubiknet.state.execute_move_seq(scramble);
				rubiknet.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Please try again.", "Not Official WCA Scramble!", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	
}
