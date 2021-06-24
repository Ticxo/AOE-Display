package com.ticxo.aoedisplay.image;

import lombok.Getter;

@Getter
public class MapImage {

	private final int width;
	private final int height;
	private final boolean[] image;

	public MapImage(int width, int height) {
		this.width = width;
		this.height = height;
		image = new boolean[width * height];
	}

	public void setPixel(int x, int y, boolean pixel) {
		image[x + y * width] = pixel;
	}

	public boolean getPixel(int x, int y) {
		return image[x + y * width];
	}

}
