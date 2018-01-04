package rubik;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RubikNet extends JPanel implements Renderable {

	private static final long serialVersionUID = -7420893625446291398L;
	private static final int CUBLETTE_LENGTH = 75;
	RubikState state;
	
	public RubikNet(RubikState state) {
		this.state = state;
		this.setBackground(Color.GRAY);
		this.setLayout(null);
		this.repaint();
	}

	@Override
	public void render(Graphics2D g) {
		// top
		drawCublette(g, state.positions[0], 200, 50);
		drawCublette(g, state.positions[1], 275, 50);
		drawCublette(g, state.positions[2], 275, 125);
		drawCublette(g, state.positions[3], 200, 125);
		
		// left 
		drawCublette(g, state.positions[4], 25, 225);
		drawCublette(g, state.positions[5], 100, 225);
		drawCublette(g, state.positions[6], 100, 300);
		drawCublette(g, state.positions[7], 25, 300);
		
		// front
		drawCublette(g, state.positions[8], 200, 225);
		drawCublette(g, state.positions[9], 275, 225);
		drawCublette(g, state.positions[10], 275, 300);
		drawCublette(g, state.positions[11], 200, 300);
		
		// right
		drawCublette(g, state.positions[12], 375, 225);
		drawCublette(g, state.positions[13], 450, 225);
		drawCublette(g, state.positions[14], 450, 300);
		drawCublette(g, state.positions[15], 375, 300);
		
		// bottom
		drawCublette(g, state.positions[16], 200, 400);
		drawCublette(g, state.positions[17], 275, 400);
		drawCublette(g, state.positions[18], 275, 475);
		drawCublette(g, state.positions[19], 200, 475);
		
		// back
		drawCublette(g, state.positions[20], 550, 225);
		drawCublette(g, state.positions[21], 625, 225);
		drawCublette(g, state.positions[22], 625, 300);
		drawCublette(g, state.positions[23], 550, 300);
	}
	
	private void drawCublette(Graphics2D g, char c, int x, int y) {
		switch(c) {
		case 'w':
			g.setColor(Color.WHITE);
			break;
		case 'g':
			g.setColor(Color.GREEN);
			break;
		case 'o':
			g.setColor(new Color(255,125,0));
			break;
		case 'r':
			g.setColor(Color.RED);
			break;
		case 'y':
			g.setColor(Color.YELLOW);
			break;
		case 'b':
			g.setColor(Color.BLUE);
			break;
		}
		
		g.fillRect(x, y, CUBLETTE_LENGTH, CUBLETTE_LENGTH);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.drawRect(x, y, CUBLETTE_LENGTH, CUBLETTE_LENGTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		RubikNet.this.render((Graphics2D) g);
	}

}
