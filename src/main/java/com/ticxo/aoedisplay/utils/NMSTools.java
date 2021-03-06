package com.ticxo.aoedisplay.utils;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumGamemode;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NMSTools {

	public static EntityPlayer getNMSPlayer(Player player) {
		return ((CraftPlayer) player).getHandle();
	}

	public static void hideHotbar(EntityPlayer player) {

		player.a(EnumGamemode.CREATIVE);
		PacketPlayOutPlayerInfo info = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, player);
		player.a(EnumGamemode.SPECTATOR);
		player.playerConnection.networkManager.sendPacket(info);

	}

	public static void sendPackets(Player player, Packet<?>... packets) {
		EntityPlayer nmsPlayer = getNMSPlayer(player);

		for(Packet<?> packet : packets) {
			nmsPlayer.playerConnection.sendPacket(packet);
		}
	}

	public static void setLocation(Entity entity, Location location) {
		net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		nmsEntity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

}
