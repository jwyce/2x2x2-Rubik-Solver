package rubik;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class RubikGUI extends JFrame {

	private static final long serialVersionUID = -4226981410944431646L;
	private RubikNet rubiknet = new RubikNet(new RubikState());
	private JButton solve = new JButton("Solve");
	private JButton randomize = new JButton("Randomize");
	private JButton reset = new JButton("Reset");
	private JLabel solution = new JLabel(), time = new JLabel();
	private JLabel scramble = new JLabel();
	
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
		solve.setFont(new Font("Segoe UI", Font.BOLD, 16));
		solve.setBackground(Color.GRAY.darker());
		solve.setForeground(Color.WHITE);
		solve.setBorder(new LineBorder(Color.gray.darker().darker(), 1));
		solve.addMouseListener(solveButtonListener);
		solve.setFocusable(false);
		solve.setSize(100, 35);
		solve.setLocation(430, 70);
		
		randomize.setFont(new Font("Segoe UI", Font.BOLD, 16));
		randomize.setBackground(Color.GRAY.darker());
		randomize.setForeground(Color.WHITE);
		randomize.setBorder(new LineBorder(Color.gray.darker().darker(), 1));
		randomize.addMouseListener(randomButtonListener);
		randomize.setFocusable(false);
		randomize.setSize(100, 35);
		randomize.setLocation(550, 70);
		
		reset.setFont(new Font("Segoe UI", Font.BOLD, 16));
		reset.setBackground(Color.GRAY.darker());
        reset.setForeground(Color.WHITE);
        reset.setBorder(new LineBorder(Color.gray.darker().darker(), 1));
        reset.addMouseListener(resetButtonListener);
        reset.setFocusable(false);
        reset.setSize(100, 35);
        reset.setLocation(670, 70);
		
        scramble.setFont(new Font("Segoe UI", Font.BOLD, 16));
        scramble.setSize(500, 30);
        scramble.setForeground(Color.WHITE);
        scramble.setLocation(420, 125);
		solution.setFont(new Font("Segoe UI", Font.BOLD, 16));
		solution.setSize(500, 30);
		solution.setForeground(Color.WHITE);
		solution.setLocation(420, 150);
		time.setFont(new Font("Segoe UI", Font.BOLD, 16));
		time.setSize(500, 30);
		time.setForeground(Color.WHITE);
		time.setLocation(420, 175);
		
		rubiknet.add(solve);
		rubiknet.add(randomize);
		rubiknet.add(reset);
		rubiknet.add(scramble);
		rubiknet.add(solution);
		rubiknet.add(time);
	}
	
	MouseListener solveButtonListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            
            long startTime = System.currentTimeMillis();
            solution.setText("solution: " + Solver.solve(rubiknet.state));
            time.setText("time elapsed: " + (System.currentTimeMillis() - startTime) + "ms.");
            rubiknet.state.executeMoveSeq(solution.getText());
            rubiknet.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            solve.setBackground(new Color(117, 117, 117));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            solve.setBackground(Color.GRAY.darker());
        }
	};
	
	MouseListener randomButtonListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            scramble.setText("scramble: " + rubiknet.state.randomize());
            solution.setText("");
            time.setText("");
            rubiknet.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            randomize.setBackground(new Color(117, 117, 117));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            randomize.setBackground(Color.GRAY.darker());
        }
    };
    
    MouseListener resetButtonListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            rubiknet.state = new RubikState();
            scramble.setText("");
            solution.setText("");
            time.setText("");
            rubiknet.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            reset.setBackground(new Color(117, 117, 117));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            reset.setBackground(Color.GRAY.darker());
        }
    };
}
