package ejecutable;

import funcional.*;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {

	private PFont font;
	
	private PImage duque;
	private PImage trump;

	private PImage img;

	private PImage[] escalas;

	private HighPass hp;
	private LowPass lp;

	private int altura;
	private int alpha;

	private int matrixsize;

	float[][] matrix = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 }, };

	public static void main(String[] args) {
		PApplet.main("ejecutable.Main");
	}

	@Override
	public void settings() {
		size(1290, 810);
	}

	@Override
	public void setup() {
		colorMode(RGB, 255, 255, 255, 100);

		font = createFont("../data/opensans.ttf", 24);
		textFont(font);
		textAlign(RIGHT, BOTTOM);
		
		alpha = 70;

		matrixsize = 3;

		escalas = new PImage[30];

		duque = loadImage("../data/duque.jpg");
		trump = loadImage("../data/trump.jpg");

		duque.resize(duque.width / 2, duque.height / 2);
		trump.resize(trump.width / 2, trump.height / 2);

		hp = new HighPass(this, trump, 0, alpha);
		lp = new LowPass(this, duque, width - duque.width, 100 - alpha);

		pyramid();
	}

	@Override
	public void draw() {
		background(0);
		hp.display();
		lp.display();

		hp.setAlpha(alpha);
		lp.setAlpha(100 - alpha);

		altura = 0;
		
		for (int i = 0; i < escalas.length; i++) {
			image(escalas[i], altura, img.height);
			altura += escalas[i].width;
		}
		
		fill(255);
		text("High pass ->TRUMP: " + hp.getAlpha() + "% \n Low Pass -> DUQUE: " + lp.getAlpha() + "%", width, height);

//		pyramid();
//		image(img, img.width, img.height);
	}

	private void create() {
		img = createImage(duque.width, duque.height, RGB);
		img = get(trump.width, 0, trump.width, trump.height);
	}

	private void pyramid() {
		create();
		img.loadPixels();
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + j * img.width;

				PVector c = convolution(i, j, matrix, matrixsize, img);

				img.pixels[index] = color(c.x, c.y, c.z);
			}
		}
		img.updatePixels();

		for (int i = 0; i < escalas.length; i++) {
			escalas[i] = img.copy();
			escalas[i].resize(escalas[i].width / (i + 2), escalas[i].height / (i + 2));
		}

//		image(img, trump.width, img.height * index + altura);
	}

	private PVector convolution(int x, int y, float[][] matrix, int matrixsize, PImage img) {
		float rtotal = 0.0f;
		float gtotal = 0.0f;
		float btotal = 0.0f;
		int offset = matrixsize / 2;

		for (int i = 0; i < matrixsize; i++) {
			for (int j = 0; j < matrixsize; j++) {
				int xloc = x + i - offset;
				int yloc = y + j - offset;
				int loc = xloc + img.width * yloc;

				loc = constrain(loc, 0, img.pixels.length - 1);

				rtotal += (red(img.pixels[loc]) * matrix[i][j]);
				gtotal += (green(img.pixels[loc]) * matrix[i][j]);
				btotal += (blue(img.pixels[loc]) * matrix[i][j]);
			}
		}

		rtotal = constrain(rtotal / 16, 0, 255);
		gtotal = constrain(gtotal / 16, 0, 255);
		btotal = constrain(btotal / 16, 0, 255);

		return new PVector(rtotal, gtotal, btotal);
	}

	@Override
	public void keyPressed() {
		hp.key();
		lp.key();

		if (keyCode == RIGHT) {
			alpha += 5;
			pyramid();
		} else if (keyCode == LEFT) {
			alpha -= 5;
			pyramid();
		}

	}

}
