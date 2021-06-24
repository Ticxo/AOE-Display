package com.ticxo.aoedisplay.packet;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_16_R3.MapIcon;
import net.minecraft.server.v1_16_R3.PacketPlayOutMap;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class WrapperPacketOutMap {

	private int mapId;
	private byte scale;
	private boolean tracking;
	private boolean locked;
	private List<MapIcon> icons = new ArrayList<>();
	private int columns;
	private int rows;
	private int offsetX;
	private int offsetY;
	private byte[] data;

	public PacketPlayOutMap generate() {
		return new PacketPlayOutMap(getMapId(), getScale(), isTracking(), isLocked(), getIcons(), getData(), getOffsetX(), getOffsetY(), getColumns(), getRows());
	}

	public void setFromTo(int fromX, int fromY, int toX, int toY) {
		setOffsetX(fromX);
		setOffsetY(fromY);
		setColumns(toX - fromX);
		setRows(toY - fromY);
	}

	public int getDataSize() {
		return getOffsetX() + getColumns() + (getOffsetY() + getRows()) * 128;
	}

}
