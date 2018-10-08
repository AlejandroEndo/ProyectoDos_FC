package funcional;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class LowPass {

	private PApplet app;

	private PImage img;
	private PImage result;

	private int x;
	private int matrixValue;
	private int matrixSize;
	private int blurIntesity;
	
	private int alpha;
	
	private float[][] matrix = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
	
	public LowPass(PApplet app, PImage img, int x, int alpha) {
		this.app = app;
		this.img = img;
		this.x = x;
		this.alpha = alpha;

		blurIntesity = 20;
		matrixSize = 3;

		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				matrixValue += matrix[i][j];
			}
		}

		grayScale(img);

		result = img.copy();
		filter(this.img, result);
	}

	public void display() {
		app.image(img, x, 0);
		app.tint(255, alpha);
		app.image(result, img.width, 0);
		app.noTint();
	}
	private void filter(PImage img, PImage result) {
		result.loadPixels();
		for (int i = 0; i < blurIntesity; i++) {
			blur(result);
		}

		result.updatePixels();
	}

	private void blur(PImage img) {
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + j * img.width;

				PVector c = convolution(i, j, matrix, matrixSize, img);

				img.pixels[index] = app.color(c.x, c.y, c.z);
			}
		}
	}

	public void key() {
		if (app.keyCode == PConstants.DOWN) {
			filter(img, result);
			System.out.println("[DUQUE UP]");
		}
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

				loc = PApplet.constrain(loc, 0, img.pixels.length - 1);

				rtotal += (app.red(img.pixels[loc]) * matrix[i][j]);
				gtotal += (app.green(img.pixels[loc]) * matrix[i][j]);
				btotal += (app.blue(img.pixels[loc]) * matrix[i][j]);
			}
		}

		rtotal = PApplet.constrain(rtotal / matrixValue, 0, 255);
		gtotal = PApplet.constrain(gtotal / matrixValue, 0, 255);
		btotal = PApplet.constrain(btotal / matrixValue, 0, 255);

		return new PVector(rtotal, gtotal, btotal);
	}
	
	private void grayScale(PImage img) {
		img.loadPixels();
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + j * img.width;
				
				int g = (int) app.red(img.get(i, j));
				
				img.pixels[index] = app.color(g);
			}
		}
		img.updatePixels();
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}
