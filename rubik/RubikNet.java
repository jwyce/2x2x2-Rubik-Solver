package rubik;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class RubikNet extends JPanel implements Renderable {

	private static final long serialVersionUID = -7420893625446291398L;
	private static final int CUBLETTE_LENGTH = 75;
	RubikState state;
	List<RubikCublet> cublets = new ArrayList<RubikCublet>();
	
	public RubikNet(RubikState state) {
		this.state = state;
		this.addMouseListener(mouseInput);
		this.setBackground(new Color(36, 36, 36));
		this.setLayout(null);
		this.repaint();
	}

	@Override
	public void render(Graphics2D g) {
	    // top
	    cublets.add(drawCublette(g, 0, 200, 50));
	    cublets.add(drawCublette(g, 1, 275, 50));
	    cublets.add(drawCublette(g, 2, 275, 125));
	    cublets.add(drawCublette(g, 3, 200, 125));
        
        // left 
	    cublets.add(drawCublette(g, 4, 25, 225));
	    cublets.add(drawCublette(g, 5, 100, 225));
	    cublets.add(drawCublette(g, 6, 100, 300));
	    cublets.add(drawCublette(g, 7, 25, 300));
        
        // front
	    cublets.add(drawCublette(g, 8, 200, 225));
	    cublets.add(drawCublette(g, 9, 275, 225));
	    cublets.add(drawCublette(g, 10, 275, 300));
	    cublets.add(drawCublette(g, 11, 200, 300));
        
        // right
	    cublets.add(drawCublette(g, 12, 375, 225));
	    cublets.add(drawCublette(g, 13, 450, 225));
	    cublets.add(drawCublette(g, 14, 450, 300));
	    cublets.add(drawCublette(g, 15, 375, 300));
        
        // bottom
	    cublets.add(drawCublette(g, 16, 200, 400));
	    cublets.add(drawCublette(g, 17, 275, 400));
	    cublets.add(drawCublette(g, 18, 275, 475));
	    cublets.add(drawCublette(g, 19, 200, 475));
        
        // back
	    cublets.add(drawCublette(g, 20, 550, 225));
	    cublets.add(drawCublette(g, 21, 625, 225));
	    cublets.add(drawCublette(g, 22, 625, 300));
	    cublets.add(drawCublette(g, 23, 550, 300));
	}
	
	private RubikCublet drawCublette(Graphics2D g, int i, int x, int y) {
	    int color = -1;
	    char c = state.positions[i];
		switch(c) {
		case 'w':
		    color = 0;
			g.setColor(Color.WHITE);
			break;
		case 'g':
		    color = 1;
			g.setColor(Color.GREEN);
			break;
		case 'o':
		    color = 2;
			g.setColor(new Color(255,125,0));
			break;
		case 'r':
		    color = 3;
			g.setColor(Color.RED);
			break;
		case 'y':
		    color = 4;
			g.setColor(Color.YELLOW);
			break;
		case 'b':
		    color = 5;
			g.setColor(Color.BLUE);
			break;
		}
		
		g.fillRect(x, y, CUBLETTE_LENGTH, CUBLETTE_LENGTH);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.drawRect(x, y, CUBLETTE_LENGTH, CUBLETTE_LENGTH);
		
		return new RubikCublet(i, x, y, color);
	}
	
	MouseListener mouseInput = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            
            for (RubikCublet cublet : cublets) {
                if (x >= cublet.netX && x <= cublet.netX + CUBLETTE_LENGTH && y >= cublet.netY && y <= cublet.netY + CUBLETTE_LENGTH) {
                    state.positions[cublet.stateIndex] = cublet.cycleColor();
                    RubikNet.this.repaint();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
	    
	};
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		RubikNet.this.render((Graphics2D) g);
	}

}

class RubikCublet {

    int stateIndex;
    int netX;
    int netY;
    int color;
    char colors[] = {'w', 'g', 'o', 'r', 'y', 'b'};
    
    public RubikCublet(int stateIndex, int netX, int netY, int color) {
        this.stateIndex = stateIndex;
        this.netX = netX;
        this.netY = netY;
        this.color = color;
    }
    
    public char cycleColor() {
        color++;
        color %= 6;
        return colors[color];
    }
}

