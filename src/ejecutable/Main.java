package ejecutable;

import funcional.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

	private PImage duque;
	private PImage trump;
	
	private PImage img;

	private HighPass hp;
	private LowPass lp;

	private int index;
	private int altura;
	private int alpha;

	public static void main(String[] args) {
		PApplet.main("ejecutable.Main");
	}

	@Override
	public void settings() {
		size(1290, 540);
	}

	@Override
	public void setup() {
		colorMode(RGB, 255, 255, 255, 100);

		alpha = 70;

		duque = loadImage("../data/duque.jpg");
		trump = loadImage("../data/trump.jpg");

		duque.resize(duque.width / 2, duque.height / 2);
		trump.resize(trump.width / 2, trump.height / 2);

		hp = new HighPass(this, trump, 0, alpha);
		lp = new LowPass(this, duque, width - duque.width, 100 - alpha);
	}

	@Override
	public void draw() {
		background(255);
		hp.display();
		lp.display();

		hp.setAlpha(alpha);
		lp.setAlpha(100 - alpha);
	}

	private void pyramid() {
		loadPixels();
		for (int i = 0; i < trump.width; i++) {
			for (int j = 0; j < trump.height; j++) {
				
			}
		}
		
	}
	
	@Override
	public void keyPressed() {
		hp.key();
		lp.key();

		if (keyCode == RIGHT) {
			alpha += 5;
		} else if (keyCode == LEFT) {
			alpha -= 5;
		}

	}

}
