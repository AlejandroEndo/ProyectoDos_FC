package funcional;

import processing.core.PApplet;
import processing.core.PImage;

public class LowPass {

	private PApplet app;

	private PImage img;

	private int x;

	public LowPass(PApplet app, PImage img, int x) {
		this.app = app;
		this.img = img;
		this.x = x;
		
		grayScale(img);
	}

	public void display() {
		app.image(img, x, 0);
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
}
