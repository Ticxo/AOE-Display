package com.ticxo.aoedisplay.image;

import com.ticxo.aoedisplay.AOEDisplay;
import com.ticxo.aoedisplay.map.MapPalette;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageManager {

	private final File resource;
	private final Map<String, MapImage> images = new ConcurrentHashMap<>();

	public ImageManager() {
		resource = new File(AOEDisplay.instance.getDataFolder(), "images");
		if(!resource.exists())
			resource.mkdirs();
	}

	public void loadImages() {
		Bukkit.getScheduler().runTaskAsynchronously(AOEDisplay.instance, () -> {
			if(resource == null)
				return;
			File[] files = resource.listFiles();
			if(files == null)
				return;
			for(File image : files) {
				if(image.isDirectory() || !FilenameUtils.isExtension(image.getName(), "png"))
					continue;

				String id = FilenameUtils.removeExtension(image.getName());
				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(bufferedImage == null)
					continue;

				MapImage mapImage = new MapImage(bufferedImage.getWidth(), bufferedImage.getHeight());
				for(int y = 0; y < bufferedImage.getHeight(); ++y) {
					for(int x = 0; x < bufferedImage.getWidth(); ++x) {
						int color = bufferedImage.getRGB(x, y);
						int alpha = (color >> 24) & 0xff;
						byte colorPixel = MapPalette.matchColor(new Color(color, true));

						mapImage.setPixel(x, y, alpha > 0);
						mapImage.setColor(x,y, colorPixel);
					}
				}
				// This is from BKCommons
				// mapImage.setColors(MapColorPalette.convertImage(bufferedImage));

				images.put(id, mapImage);
			}
			System.out.println("All images loaded. Good luck have fun.");
		});
	}

	public MapImage getImage(String id) {
		return images.get(id);
	}

}
