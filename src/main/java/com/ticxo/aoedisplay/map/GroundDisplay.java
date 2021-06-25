package com.ticxo.aoedisplay.map;

import com.ticxo.aoedisplay.packet.WrapperPacketOutMap;
import com.ticxo.aoedisplay.utils.NMSTools;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GroundDisplay {

	private static final ItemStack map = new ItemStack(Material.FILLED_MAP);
	private static final MapMeta mapMeta = (MapMeta) map.getItemMeta();
	private static final Vec3D noMot = new Vec3D(0, 0, 0);
	private static final WrapperPacketOutMap packet;

	private final int id;
	private final byte[] data = new byte[256];

	private boolean requireUpdate = false;

	static {
		packet = new WrapperPacketOutMap();
		packet.setTracking(false);
		packet.setLocked(true);
		packet.setIcons(new ArrayList<>());
		packet.setFromTo(0, 0, 128, 128);
	}

	public GroundDisplay(int id) {
		this.id = id;
		Arrays.fill(data, (byte) 0);
	}

	public void setColor(int x, int y, byte color) {
		int id = x + 16 * y;
		if(data[id] == color)
			return;
		data[id] = color;

		requireUpdate = true;
	}

	public byte getColor(int x, int y) {
		return data[x + 16 * y];
	}

	public void showDisplay(Player player, Location location) {

		mapMeta.setMapId(id);
		map.setItemMeta(mapMeta);

		PacketPlayOutSpawnEntity spawn = new PacketPlayOutSpawnEntity(id, MathHelper.a(Entity.SHARED_RANDOM), location.getBlockX(), location.getBlockY(), location.getBlockZ(), -90, 0, EntityTypes.ITEM_FRAME, 1, noMot);
		PacketPlayOutEntityMetadata meta = new PacketPlayOutEntityMetadata(id, new MapDataWatcher(map).getDataWatcher(), true);

		NMSTools.sendPackets(player, spawn, meta);

	}

	public void hideDisplay(Player player) {

		PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(id);

		NMSTools.sendPackets(player, destroy);

	}

	public void updateDisplay(Player player) {

		if(!requireUpdate)
			return;
		requireUpdate = false;

		packet.setMapId(id);

		byte[] colorData = new byte[packet.getDataSize()];

		for(int id = 0; id < data.length; ++id) {
			byte color = data[id];
			int mod = (id % 16) * 8;
			int div = (id / 16) * 8;

			for(int y = 0; y < 8; ++y) {
				for(int x = 0; x < 8; ++x) {
					colorData[(x + mod) + (y + div) * 128] = color;
				}
			}
		}

		packet.setData(colorData);

		NMSTools.sendPackets(player, packet.generate());

		Arrays.fill(data, (byte) 0);

	}

}
