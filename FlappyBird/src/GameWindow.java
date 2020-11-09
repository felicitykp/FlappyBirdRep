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
	//background variables
		public int windowW = 488, windowH = 712;
		public Image backgroundImage;
	//bird variables
		public Image birdImage;
		public double birdW = 40, birdH = 30;
		public double birdX = (windowW/2) - (birdW/2), birdY = (windowH / 2) - birdH;
		public double birdVel = 0;
		public double gravity = 0.5;
	//pipe variables
		public Image pipeTop, pipeBottom;
		public double pipeW = 52, pipeH = 320;
		public double pipeX1 =  (windowW * 0.75), pipeY1 = 0;
		public double pipeX2 = (windowW + (windowW * 0.25)), pipeY2 = windowH - pipeH;
		public double pipeVel = 3;
	
	//CONSTRUCTOR
	public GameWindow() throws IOException, InterruptedException {
		
		//customize the panel
		this.setPreferredSize(new Dimension(windowW, windowH));
		 
		//load initial Images
		backgroundImage = ImageIO.read(new File("background-day.png"));
		birdImage = ImageIO.read(new File("birdMid.png"));
		pipeTop = ImageIO.read(new File("pipeTop.png"));
		pipeBottom = ImageIO.read(new File("pipeBottom.png"));

		//make frame to hold panel
		JFrame mainFrame = new JFrame("Flappy Bird");
		mainFrame.setSize(windowW, windowH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.add(this);
		mainFrame.setVisible(true);
		
		//setup key listener
		mainFrame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == ' ') { 
					if(birdY > 0) {
						birdVel = -9;
					} 
				} 
				mainFrame.getContentPane().repaint();
			}
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}	
		});
		
		//run the code
		while(true) {
			moveBird();
			movePipe();
			mainFrame.getContentPane().repaint();
			Thread.sleep(33);
		}
		
	}
	
	//METHODS
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, windowW, windowH, null);
		g.drawImage(birdImage, (int)(birdX), (int)(birdY), (int)(birdW), (int)(birdH), null);
		g.drawImage(pipeTop, (int)(pipeX1), (int)(pipeY1), (int)(pipeW), (int)(pipeH), null);
		g.drawImage(pipeBottom, (int)(pipeX2), (int)(pipeY2), (int)(pipeW), (int)(pipeH), null);
	}
	
	public void moveBird() throws IOException {
		//have the velocity be constantly changed by gravity
		birdVel += gravity;
		//change the pic based on the velocity
		setBirdPic();
		if(birdY < windowH-(birdH*2) && birdY > 0) {
			birdY += birdVel;
		} else if(birdY >= windowH-(birdH*2) && birdVel < 0) {
			birdY += birdVel;
		} else if(birdY <= 0 && birdVel > 0) {
			birdY += birdVel;
		}
	}
	
	public void CheckCollison() {
		
	}
	
	public void setBirdPic() throws IOException {
		if(birdVel < -0.5) {
			birdImage = ImageIO.read(new File("birdUp.png"));
		} else if(birdVel > 0.5) {
			birdImage = ImageIO.read(new File("birdDown.png"));
		} else {
			birdImage = ImageIO.read(new File("birdMid.png"));
		}
	}
	
	public void movePipe() {
		pipeX1 -= pipeVel;
		pipeX2 -= pipeVel;
	}
	
	
	//MAIN
	public static void main(String[] args) throws IOException, InterruptedException {
		GameWindow myGame = new GameWindow();
	}
}










