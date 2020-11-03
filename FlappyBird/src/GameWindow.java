import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JPanel {
	
	
	//VARIABLES
	public static final int UP_ARROW = 38;
	public static final int DOWN_ARROW = 40;
	//background variables
		public int windowW = 488, windowH = 712;
		public Image backgroundImage;
	//bird variables
		public Image birdImage;
		public int birdW = 40, birdH = 30;
		public int birdX = (windowW/2) - (birdW/2), birdY = (windowH / 2) - birdH;
	
	//CONSTRUCTOR
	public GameWindow() throws IOException {
		
		//customize the panel
		this.setPreferredSize(new Dimension(windowW, windowH));
		backgroundImage = ImageIO.read(new File("background-day.png"));

		//make frame to hold panel
		JFrame mainFrame = new JFrame("Flappy Bird");
		mainFrame.setSize(windowW, windowH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.add(this);
		mainFrame.setVisible(true);

		//setup bird
		birdImage = ImageIO.read(new File("birdMid.png"));
		
		//setup key listener
		mainFrame.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == UP_ARROW) { 
					if(birdY < 0) {
						birdY--;
					}
					
				} else if (e.getKeyCode() == DOWN_ARROW) { 
					if(birdY > windowH) {
						birdY++;
					}
				}
			}
			
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}	
			
		});
		
	}
	
	//METHODS
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, windowW, windowH, null);
		g.drawImage(birdImage, birdX, birdY, birdW, birdH, null);
	}
	
	//MAIN
	public static void main(String[] args) throws IOException {
		
		GameWindow myGame = new GameWindow();
		
	}
}










