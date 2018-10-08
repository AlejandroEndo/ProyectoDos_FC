package ejecutable;

import funcional.*;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {

	private PImage duque;
	private PImage trump;

	private HighPass hp;
	private LowPass lp;

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

		duque = loadImage("../data/duque.jpg");
		trump = loadImage("../data/trump.jpg");

		duque.resize(duque.width / 2, duque.height / 2);
		trump.resize(trump.width / 2, trump.height / 2);

		hp = new HighPass(this, trump, 0);
		lp = new LowPass(this, duque, width - duque.width);
	}

	@Override
	public void draw() {
		background(255);
		hp.display();
		lp.display();
	}
	
	@Override
	public void keyPressed() {
		hp.key();
	}

}
