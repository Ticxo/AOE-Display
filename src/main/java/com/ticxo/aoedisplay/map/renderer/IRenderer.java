package com.ticxo.aoedisplay.map.renderer;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IRenderer {

	void generateCentred(Player player, Location location);
	void generateCorner(Player player, Location location);

	void remove(Player player);

}
