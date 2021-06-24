package com.ticxo.aoedisplay.map.renderer;

import com.ticxo.aoedisplay.image.MapImage;
import com.ticxo.aoedisplay.map.GroundDisplay;
import com.ticxo.aoedisplay.map.MapColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ImageRenderer implements IRenderer {

	private final MapImage image;

	public ImageRenderer(MapImage image) {
		this.image = image;
	}

	@Override
	public void generateCentred(Player player, Location location) {
		double locXOffset = image.getWidth() * 0.03125;
		double locYOffset = image.getHeight() * 0.03125;

		generateCorner(player, location.add(-locXOffset, 0, -locYOffset));

	}

	@Override
	public void generateCorner(Player player, Location upperCorner) {
		Location upperBlock = upperCorner.toBlockLocation();
		double offsetX = upperCorner.getX() - upperCorner.getBlockX();
		double offsetZ = upperCorner.getZ() - upperCorner.getBlockZ();
		int mapOffsetX = (int) (offsetX * 16);
		int mapOffsetZ = (int) (offsetZ * 16);

		int mapWidth = (int) Math.ceil(offsetX + image.getWidth() / 16d);
		int mapHeight = (int) Math.ceil(offsetZ + image.getHeight() / 16d);

		GroundDisplay[] maps = new GroundDisplay[mapHeight * mapWidth];
		for(int x = 0; x < mapWidth; ++x) {
			for(int z = 0; z < mapHeight; ++z) {
				int id = x + z * mapWidth;
				GroundDisplay map = new GroundDisplay(upperBlock.clone().add(x, 0, z), -30 - id);
				maps[id] = map;
			}
		}

		for(int x = 0; x < image.getWidth(); ++x) {
			for (int z = 0; z < image.getHeight(); ++z) {
				// Map each pixel on its respective map
				if(image.getPixel(x, z)) {
					int id = (int) (offsetX + x / 16d) + (int) (offsetZ + z / 16d) * mapWidth;
					maps[id].setColor((mapOffsetX + x) % 16, (mapOffsetZ + z) % 16, MapColor.RED.getBright());
				}
			}
		}

		for(GroundDisplay map : maps) {
			map.showDisplay(player);
		}
	}

}
