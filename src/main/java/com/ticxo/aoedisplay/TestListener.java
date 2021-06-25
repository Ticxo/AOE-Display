package com.ticxo.aoedisplay;

import com.ticxo.aoedisplay.image.MapImage;
import com.ticxo.aoedisplay.map.renderer.ImageRenderer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TestListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		if(event.getItem() != null && event.getItem().getType() == Material.BLAZE_ROD) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				MapImage map = AOEDisplay.instance.getImageManager().getImage("magic_circle_3");
				ImageRenderer renderer = new ImageRenderer(map);

				BukkitRunnable runnable = new BukkitRunnable() {
					int i = 0;
					@Override
					public void run() {
						if(++i > 200)
							cancel();
						renderer.generateCentred(player, player.getLocation());
					}
				};
				runnable.runTaskTimerAsynchronously(AOEDisplay.instance, 0, 1);
			}
		}

	}

}
