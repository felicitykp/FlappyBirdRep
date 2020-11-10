import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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
		public int windowW = 488, windowH = 600;
		public double pipeSpace = windowW * 0.5;
		public Image backgroundImage;
		public Image gameOverImage;
		public int gameOverW = windowW - 20, gameOverH = 97;
		public boolean gameOver = false;
		public int score = 0;
		public Image score1Image, score2Image;
		public int scoreW = 24, scoreH = 36;
	//bird variables
		public Image birdImage;
		public double birdW = 40, birdH = 30;
		public double birdX = (windowW/2) - (birdW/2), birdY = (windowH / 2) - birdH;
		public double birdVel = 0;
		public double gravity = 0.5;
	//pipe variables
		public Image pipeTop, pipeBottom;
		public double pipeW = 52, pipeH = 320;
		public double[] pipeX = {windowW, (windowW + pipeSpace), (windowW + (pipeSpace * 2)), (windowW + (pipeSpace * 3))};
		public double[] pipeY = {0, windowH - pipeH, 0, windowH - pipeH};
		public double pipeVel = 4;
	
	//CONSTRUCTOR
	public GameWindow() throws IOException, InterruptedException {
		
		//customize the panel
		this.setPreferredSize(new Dimension(windowW, windowH));
		 
		//load initial Images
		backgroundImage = ImageIO.read(new File("background-day.png"));
		birdImage = ImageIO.read(new File("birdMid.png"));
		pipeTop = ImageIO.read(new File("pipeTop.png"));
		pipeBottom = ImageIO.read(new File("pipeBottom.png"));
		gameOverImage = ImageIO.read(new File("gameover.png"));
		score1Image = ImageIO.read(new File("0.png"));
		score2Image = ImageIO.read(new File("0.png"));
		

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
		while(!gameOver) {
			moveBird();
			movePipe();
			updateScore();
			mainFrame.getContentPane().repaint();
			Thread.sleep(33);
		} 
		
		mainFrame.getContentPane().repaint();
		
	}
	
	//METHODS
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, windowW, windowH, null);
		g.drawImage(birdImage, (int)(birdX), (int)(birdY), (int)(birdW), (int)(birdH), null);
		g.drawImage(pipeTop, (int)(pipeX[0]), (int)(pipeY[0]), (int)(pipeW), (int)(pipeH), null);
		g.drawImage(pipeBottom, (int)(pipeX[1]), (int)(pipeY[1]), (int)(pipeW), (int)(pipeH), null);
		g.drawImage(pipeTop, (int)(pipeX[2]), (int)(pipeY[2]), (int)(pipeW), (int)(pipeH), null);
		g.drawImage(pipeBottom, (int)(pipeX[3]), (int)(pipeY[3]), (int)(pipeW), (int)(pipeH), null);
		g.drawImage(score1Image, 10, 10, scoreW, scoreH, null);
		g.drawImage(score2Image, scoreW + 13, 10, scoreW, scoreH, null);
		
		if(gameOver) {
			g.drawImage(gameOverImage, 10, (windowH / 2) - gameOverH, gameOverW, gameOverH, null);
		}
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
		
		//create rectangle variables for the bird and pipes
		Rectangle bird = new Rectangle((int)(birdX), (int)(birdY), (int)(birdW), (int)(birdH));
		Rectangle[] pipes = { new Rectangle((int)(pipeX[0]), (int)(pipeY[0]), (int)(pipeW), (int)(pipeH)),
								new Rectangle((int)(pipeX[1]), (int)(pipeY[1]), (int)(pipeW), (int)(pipeH)),
								new Rectangle((int)(pipeX[2]), (int)(pipeY[2]), (int)(pipeW), (int)(pipeH)),
								new Rectangle((int)(pipeX[3]), (int)(pipeY[3]), (int)(pipeW), (int)(pipeH))};
		
		//check intersection
		if(bird.intersects(pipes[0])) {
			gameOver = true;
		} else if (bird.intersects(pipes[1])) {
			gameOver = true;
		} else if (bird.intersects(pipes[2])) {
			gameOver = true;
		} else if (bird.intersects(pipes[3])) {
			gameOver = true;
		}
		
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
		
		//have the pipes moves across the screen
		pipeX[0] -= pipeVel;
		pipeX[1] -= pipeVel;
		pipeX[2] -= pipeVel;
		pipeX[3] -= pipeVel;
		
		//resets a pipe once it's off the screen && generate random y
		if (pipeX[0] <= 0 - pipeW) {
			pipeX[0] = windowW + (pipeSpace * 2) - pipeW;
			pipeY[0] = 0 - (Math.random() * (pipeH * 0.4));
		} else if (pipeX[1] <= 0 - pipeW) {
			pipeX[1] = windowW + (pipeSpace * 2) - pipeW;
			pipeY[1] = (windowH - pipeH) + (Math.random() * (pipeH * 0.4));
		} else if (pipeX[2] <= 0 - pipeW) {
			pipeX[2] = windowW + (pipeSpace * 2) - pipeW;
			pipeY[2] = 0 - (Math.random() * (pipeH * 0.4));
		} else if (pipeX[3] <= 0 - pipeW) {
			pipeX[3] = windowW + (pipeSpace * 2) - pipeW;
			pipeY[3] = (windowH - pipeH) + (Math.random() * (pipeH * 0.4));
		}
		
		if (pipeX[0] == birdX) {
			score++;
		} else if (pipeX[1] == birdX) {
			score++;
		} else if (pipeX[2] == birdX) {
			score++;
		} else if (pipeX[3] == birdX) {
			score++;
		}
		
	}
	
	public void updateScore() throws IOException {
		
		//one's place
		if(score % 10 == 1) {
			score2Image = ImageIO.read(new File("1.png"));
		} else if (score % 10 == 2) {
			score2Image = ImageIO.read(new File("2.png"));
		} else if (score % 10 == 3) {
			score2Image = ImageIO.read(new File("3.png"));
		} else if (score % 10 == 4) {
			score2Image = ImageIO.read(new File("4.png"));
		} else if (score % 10 == 5) {
			score2Image = ImageIO.read(new File("5.png"));
		} else if (score % 10 == 6) {
			score2Image = ImageIO.read(new File("6.png"));
		} else if (score % 10 == 7) {
			score2Image = ImageIO.read(new File("7.png"));
		} else if (score % 10 == 8) {
			score2Image = ImageIO.read(new File("8.png"));
		} else if (score % 10 == 9) {
			score2Image = ImageIO.read(new File("9.png"));
		} else if (score % 10 == 0) {
			score2Image = ImageIO.read(new File("0.png"));
		}
		
		//tens place
		if(score < 10) {
			score1Image = ImageIO.read(new File("0.png"));
		} else if (score < 20) {
			score1Image = ImageIO.read(new File("1.png"));
		} else if (score < 30) {
			score1Image = ImageIO.read(new File("2.png"));
		} else if (score < 40) {
			score1Image = ImageIO.read(new File("3.png"));
		} else if (score < 50) {
			score1Image = ImageIO.read(new File("4.png"));
		} else if (score < 60) {
			score1Image = ImageIO.read(new File("5.png"));
		} else if (score < 70) {
			score1Image = ImageIO.read(new File("6.png"));
		} else if (score < 80) {
			score1Image = ImageIO.read(new File("7.png"));
		} else if (score < 90) {
			score1Image = ImageIO.read(new File("8.png"));
		} else if (score < 100) {
			score1Image = ImageIO.read(new File("9.png"));
		} else if (score >= 100) {
			gameOver = true;
		}
	}
	
	//MAIN
	public static void main(String[] args) throws IOException, InterruptedException {
		GameWindow myGame = new GameWindow();
	}
}










