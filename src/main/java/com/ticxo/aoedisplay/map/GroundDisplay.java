package com.ticxo.aoedisplay.map;

import com.ticxo.aoedisplay.packet.WrapperPacketOutMap;
import com.ticxo.aoedisplay.utils.NMSTools;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GroundDisplay {

	private static final ItemStack map = new ItemStack(Material.FILLED_MAP);
	private static final MapMeta mapMeta = (MapMeta) map.getItemMeta();

	private static final WrapperPacketOutMap packet;

	private final int id;
	private final byte[] data = new byte[256];
	private final Location location;

	private EntityItemFrame frame;
	private boolean colored;

	static {
		packet = new WrapperPacketOutMap();
		packet.setTracking(false);
		packet.setLocked(true);
		packet.setIcons(new ArrayList<>());
		packet.setFromTo(0, 0, 128, 128);
	}

	public GroundDisplay(Location location, int id) {

		this.id = id;
		Arrays.fill(data, (byte) 0);
		this.location = location;
	}

	public void setColor(int x, int y, byte color) {
		colored = true;
		x = Math.min(x, 15);
		y = Math.min(y, 15);
		data[x + 16 * y] = color;
	}

	public byte getColor(int x, int y) {
		x = Math.min(x, 15);
		y = Math.min(y, 15);
		return data[x + 16 * y];
	}

	public void showDisplay(Player player) {

		if(!colored)
			return;

		mapMeta.setMapId(id);
		map.setItemMeta(mapMeta);

		World world = ((CraftWorld) location.getWorld()).getHandle();

		frame = new EntityItemFrame(world, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), EnumDirection.UP);
		// frame.setInvisible(true);
		frame.setSilent(true);
		frame.setItem(CraftItemStack.asNMSCopy(map));

		PacketPlayOutSpawnEntity spawn = new PacketPlayOutSpawnEntity(frame, 1);
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(frame.getId(), frame.getDataWatcher(), true);

		NMSTools.sendPackets(player, spawn, meta);

		updateDisplay(player);
	}

	public void hideDisplay(Player player) {

		if(!colored)
			return;
		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(frame.getId());

		NMSTools.sendPackets(player, destroy);
	}

	public void updateDisplay(Player player) {

		packet.setMapId(id);

		byte[] colorData = new byte[packet.getDataSize()];
		for(int i = 0; i < data.length; ++i) {
			byte color = data[i];
			int mod = (i % 16) * 8;
			int div = (i / 16) * 8;
			for(int y = 0; y < 8; ++y) {
				for(int x = 0; x < 8; ++x) {
					colorData[(x + mod) + (y + div) * 128] = color;
				}
			}
		}
		packet.setData(colorData);

		NMSTools.sendPackets(player, packet.generate());

	}

}
