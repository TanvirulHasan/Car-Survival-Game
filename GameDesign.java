import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GameDesign extends JPanel implements ActionListener, KeyListener {

	Color Teal = new Color(0,128,128);
	Color Grey = new Color(105,105,105);
	double speed = 2.0;
	int height = 120;
	int width = 50;
	int frameWidth = 600;
	int frameHeight = 700;
	int move = 20;
	Rectangle car;
	Random rand;
	BufferedImage carMain,carOther;
	Timer timer;
	ArrayList<Rectangle> oppositeCars;

	public GameDesign() throws IOException{ //constructor
		carMain = ImageIO.read(getClass().getResource(".\\files\\car.png")); //Main car
		carOther = ImageIO.read(getClass().getResource(".\\files\\revcar.png")); // Opposite car

		timer = new Timer(15,this);
		rand = new Random();
		oppositeCars = new ArrayList<>();
		car = new Rectangle(frameWidth/2-95,frameHeight-100,width,height);
		addKeyListener(this);
		setFocusable(true);
		addOppositeCars(true);
		addOppositeCars(true);
		addOppositeCars(true);
		addOppositeCars(true);
		timer.start();
	}

	public void addOppositeCars(boolean exist){
		int positionX = rand.nextInt()%2;
		int x = 0;
		int y = 0;
		if(positionX==0)
			x = frameWidth/2-90;
		else
			x = frameWidth/2+10;

		if(exist == true){
			oppositeCars.add(new Rectangle(x,y-(oppositeCars.size()*350),width,height));
		}
		else{
			oppositeCars.add(new Rectangle(x,(oppositeCars.get(oppositeCars.size()-1).y)-350,width,height));
		}
	}
	public int count = 0;  // a counter varaible
	public void actionPerformed(ActionEvent e) {
		Rectangle rect;
		count++;
		for(int i=0;i<oppositeCars.size();i++){
			rect = oppositeCars.get(i);
			rect.y += speed;
			if( (count)%1200 == 0)
				speed += 0.5;
		}

		for(Rectangle r: oppositeCars){
			if(r.intersects(car)){
				System.out.println(oppositeCars.size());
				new GameOver(count); //when opposite car intersects Main car
				timer.stop();
			}
		}
		repaint(); //it will repaint in every frame cause something will be changed in every frane
		for (int i=0;i<oppositeCars.size();i++){
			rect = oppositeCars.get(i);
			if(rect.y+rect.height-100>frameHeight){ //remove car if it goes outside of frame
				oppositeCars.remove(rect);
				addOppositeCars(false);
			}
		}
	}
	public void paintComponent(Graphics g){ //design
		g.setColor(Teal);
		g.fillRect(0,0,frameWidth,frameHeight);
		g.setColor(Grey);
		g.fillRect(frameWidth/2-120,0,240,frameHeight);
		//line on left side
		g.setColor(Color.white);
		g.drawLine(frameWidth/2-120,0,frameWidth/2-120,frameHeight);
		g.drawLine(frameWidth/2-119,0,frameWidth/2-119,frameHeight);
		//line on right side
		g.drawLine(frameWidth/2+120,0,frameWidth/2+120,frameHeight);
		g.drawLine(frameWidth/2+119,0,frameWidth/2+119,frameHeight);
		//line on middle
		g.setColor(Color.yellow);
		g.drawLine(frameWidth/2,0,frameWidth/2,frameHeight);
		g.drawLine(frameWidth/2-1,0,frameWidth/2-1,frameHeight);
		g.drawLine(frameWidth/2-2,0,frameWidth/2-2,frameHeight);
		g.drawLine(frameWidth/2+1,0,frameWidth/2+1,frameHeight);
		g.drawLine(frameWidth/2+2,0,frameWidth/2+2,frameHeight);
		//line ends
		g.drawImage(carMain,car.x,car.y-60,null); //Main car image
		for(Rectangle rect:oppositeCars){
			g.drawImage(carOther,rect.x,rect.y-55,null); //Opposite car image
		}
	}
	public void keyPressed(KeyEvent e) { //event on key press
		int key = e.getKeyCode();
		switch (key){
			case KeyEvent.VK_UP:
				moveup();
				break;
			case KeyEvent.VK_DOWN:
				movedown();
				break;
			case KeyEvent.VK_LEFT:
				moveleft();
				break;
			case KeyEvent.VK_RIGHT:
				moveright();
				break;
			default:
				break;
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}

	public void moveup(){
		if(car.y-move>=0)
			car.y-=move;
	}
	public void movedown(){
		if(car.y+car.height+move<=frameHeight+1)
			car.y+=move;
	}
	public void moveleft(){
		if(car.x-move>=frameWidth/2-140)
			car.x-=move;
	}
	public void moveright(){
		if(car.x-move<=frameWidth/2+20)
			car.x+=move;
	}
}
