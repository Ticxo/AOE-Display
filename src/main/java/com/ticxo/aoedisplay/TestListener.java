package com.ticxo.aoedisplay;

import com.ticxo.aoedisplay.map.renderer.ImageRenderer;
import com.ticxo.aoedisplay.image.MapImage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if(event.getItem() != null && event.getItem().getType() == Material.BLAZE_ROD) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				MapImage map = AOEDisplay.instance.getImageManager().getImage("magic_circle_2");
				ImageRenderer renderer = new ImageRenderer(map);
				Location inter = event.getInteractionPoint();
				if(inter == null)
					return;
				renderer.generateCentred(player, inter);
			}
		}

	}

}
