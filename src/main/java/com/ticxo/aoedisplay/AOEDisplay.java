package com.ticxo.aoedisplay;

import com.ticxo.aoedisplay.image.ImageManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class AOEDisplay extends JavaPlugin {

	public static AOEDisplay instance;

	private ImageManager imageManager;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;

		Bukkit.getPluginManager().registerEvents(new TestListener(), this);

		imageManager = new ImageManager();
		imageManager.loadImages();

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
