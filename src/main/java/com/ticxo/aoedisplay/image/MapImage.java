package com.ticxo.aoedisplay.image;

import lombok.Getter;

@Getter
public class MapImage {

	private final int width;
	private final int height;
	private final boolean[] image;
	private byte[] colors;

	public MapImage(int width, int height) {
		this.width = width;
		this.height = height;
		image = new boolean[width * height];
		colors = new byte[width * height];
	}

	public void setPixel(int x, int y, boolean pixel) {
		image[x + y * width] = pixel;
	}
	public void setColor(int x, int y, byte color) {
		colors[x + y * width] = color;
	}

	public void setColors(byte[] colors) {
		this.colors = colors;
	}

	public boolean getPixel(int x, int y) {
		return image[x + y * width];
	}

	public byte getColor(int x, int y) {
		return colors[x + y * width];
	}

}
