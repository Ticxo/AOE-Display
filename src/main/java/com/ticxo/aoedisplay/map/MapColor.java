package com.ticxo.aoedisplay.map;

import lombok.Getter;

@Getter
public enum MapColor {

	NONE(0),
	RED(4),
	ORANGE(15),
	YELLOW(18),
	GREEN(33),
	CYAN(31),
	BLUE(12),
	VIOLET(24);

	private final byte deepest;
	private final byte deep;
	private final byte bright;
	private final byte brightest;

	MapColor(int id) {
		this.deepest = (byte) (id * 4 + 3);
		this.deep = (byte) (id * 4);
		this.bright = (byte) (id * 4 + 1);
		this.brightest = (byte) (id * 4 + 2);
	}

}
