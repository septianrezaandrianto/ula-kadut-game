import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class UlaKadutPanel extends JPanel implements ActionListener {

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int GAME_DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyPart = 6;
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	UlaKadutPanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdpater());
		startGame();
		
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(GAME_DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		draw(graphic);
	}
	
	public void draw(Graphics graphic) {
		
		if(running) {
//			for (int i=0; i <SCREEN_HEIGHT/UNIT_SIZE ;i++) {
//				graphic.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				graphic.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			
			graphic.setColor(Color.red);
			graphic.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for (int i= bodyPart; i >0; i--) {
				if(i==0) {
					graphic.setColor(Color.green);
					graphic.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					graphic.setColor(new Color(45,180,0));
					graphic.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					graphic.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			graphic.setColor(Color.red);
			graphic.setFont(new Font("Ink Free", Font.BOLD, 30));
			
			FontMetrics score = getFontMetrics(graphic.getFont());
			graphic.drawString("Nilai maneh : " + appleEaten, SCREEN_WIDTH - score.stringWidth("Nilai maneh : " + appleEaten), graphic.getFont().getSize());
		
		}
		else {
			gameOver(graphic);
		}
		
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move() {
		for (int i=bodyPart; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch (direction ) {
		case 'U': 
			y[0]=y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] =y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0]=x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		
		if (x[0] == appleX && y[0] == appleY) {
			bodyPart++;
			appleEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		
//		This code for handling if head collides the body
		for (int i=bodyPart; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}			
		}
		
//		This code for handling if head collides left border
		if (x[0] < 0) {
			running = false;
		}
		
//		This code for handling if head collides right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		
//		this code for handling if head collides top border
		if (y[0] <0) {
			running = false;
		}
		
//		this code for handle if head collides down border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if (!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics graphic) {
//		Score
		graphic.setColor(Color.red);
		graphic.setFont(new Font("Ink Free", Font.BOLD, 30));
		
		FontMetrics score = getFontMetrics(graphic.getFont());
		graphic.drawString("Nilai maneh : " + appleEaten, SCREEN_WIDTH - score.stringWidth("Nilai maneh : " + appleEaten), graphic.getFont().getSize());
	
//		Game over
		graphic.setColor(Color.red);
		graphic.setFont(new Font("Ink Free", Font.BOLD, 82));
		
		FontMetrics overGame = getFontMetrics(graphic.getFont());
		graphic.drawString("Modar maneh", SCREEN_WIDTH - overGame.stringWidth("Modar maneh"), SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();		
	}
	
	
	public class MyKeyAdpater extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent key) {
			
			switch (key.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';					
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;			
			}

		}		
	}
 		

}
