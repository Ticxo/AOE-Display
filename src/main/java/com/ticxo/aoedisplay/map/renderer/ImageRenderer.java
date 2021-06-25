package com.ticxo.aoedisplay.map.renderer;

import com.ticxo.aoedisplay.image.MapImage;
import com.ticxo.aoedisplay.map.GroundDisplay;
import com.ticxo.aoedisplay.map.MapColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class ImageRenderer implements IRenderer {

	private final MapImage image;
	private final Map<Location, GroundDisplay> visibleMaps = new ConcurrentHashMap<>();
	private final Queue<GroundDisplay> unloadedMaps = new LinkedList<>();

	public ImageRenderer(MapImage image) {
		this.image = image;

		int maxWidth = (int) Math.ceil((image.getWidth() - 1) / 16f) + 1;
		int maxHeight = (int) Math.ceil((image.getHeight() - 1) / 16f) + 1;

		for(int i = 0; i < maxHeight * maxWidth; ++i) {
			unloadedMaps.add(new GroundDisplay(-30 - i));
		}
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
		upperBlock.setPitch(0);
		upperBlock.setYaw(0);
		double offsetX = upperCorner.getX() - upperCorner.getBlockX();
		double offsetZ = upperCorner.getZ() - upperCorner.getBlockZ();
		int mapOffsetX = (int) (offsetX * 16);
		int mapOffsetZ = (int) (offsetZ * 16);

		int mapWidth = (int) Math.ceil(offsetX + image.getWidth() / 16d);
		int mapHeight = (int) Math.ceil(offsetZ + image.getHeight() / 16d);

		visibleMaps.entrySet().removeIf(maps -> {
			Location loc = maps.getKey();
			boolean result = loc.getBlockX() < upperBlock.getBlockX() || loc.getBlockX() > upperBlock.getBlockX() + mapWidth - 1 ||
					loc.getBlockZ() < upperBlock.getBlockZ() || loc.getBlockZ() > upperBlock.getBlockZ() + mapHeight - 1;
			if(result) {
				maps.getValue().hideDisplay(player);
				unloadedMaps.add(maps.getValue());
			}
			return result;
		});

		for(int x = 0; x < image.getWidth(); ++x) {
			for (int z = 0; z < image.getHeight(); ++z) {
				int bX = (int) (offsetX + x / 16d);
				int bZ = (int) (offsetZ + z / 16d);
				Location loc = upperBlock.clone().add(bX, 0, bZ);
				GroundDisplay map = visibleMaps.get(loc);
				if(map == null) {
					map = unloadedMaps.poll();
					if(map == null)
						continue;
					map.showDisplay(player, loc);
					visibleMaps.put(loc, map);
				}
				map.setColor((mapOffsetX + x) % 16, (mapOffsetZ + z) % 16, image.getPixel(x, z) ? MapColor.RED.getBright() : MapColor.NONE.getBright());
			}
		}

		for(GroundDisplay map : visibleMaps.values()) {
			map.updateDisplay(player);
		}
	}

	@Override
	public void remove(Player player) {
		for(GroundDisplay map : visibleMaps.values()) {
			map.hideDisplay(player);
		}
	}

}
