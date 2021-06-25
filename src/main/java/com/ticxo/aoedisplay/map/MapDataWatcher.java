package com.ticxo.aoedisplay.map;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;

import java.util.Optional;

public class MapDataWatcher {

	private static final DataWatcherObject<Byte> bitMask;
	private static final DataWatcherObject<Integer> airTick;
	private static final DataWatcherObject<Optional<IChatBaseComponent>> customName;
	private static final DataWatcherObject<Boolean> customNameVisibility;
	private static final DataWatcherObject<Boolean> isSilent;
	private static final DataWatcherObject<Boolean> noGravity;
	private static final DataWatcherObject<EntityPose> pose;
	private static final DataWatcherObject<ItemStack> item;
	private static final DataWatcherObject<Integer> itemRotation;

	static {
		bitMask = new DataWatcherObject<>(0, DataWatcherRegistry.a);
		airTick = new DataWatcherObject<>(1, DataWatcherRegistry.b);
		customName = new DataWatcherObject<>(2, DataWatcherRegistry.f);
		customNameVisibility = new DataWatcherObject<>(3, DataWatcherRegistry.i);
		isSilent = new DataWatcherObject<>(4, DataWatcherRegistry.i);
		noGravity = new DataWatcherObject<>(5, DataWatcherRegistry.i);
		pose = new DataWatcherObject<>(6, DataWatcherRegistry.s);
		item = new DataWatcherObject<>(7, DataWatcherRegistry.g);
		itemRotation = new DataWatcherObject<>(8, DataWatcherRegistry.b);
	}

	private final DataWatcher dataWatcher;

	public MapDataWatcher() {
		this(ItemStack.b);
	}

	public MapDataWatcher(org.bukkit.inventory.ItemStack itemStack) {
		this(CraftItemStack.asNMSCopy(itemStack));
	}

	public MapDataWatcher(ItemStack itemStack) {
		dataWatcher = new DataWatcher(null);
		//dataWatcher.register(bitMask, (byte) 0);
		dataWatcher.register(bitMask, (byte) (1 << 5));
		dataWatcher.register(airTick, 300);
		dataWatcher.register(customName, Optional.empty());
		dataWatcher.register(customNameVisibility, false);
		dataWatcher.register(isSilent, true);
		dataWatcher.register(noGravity, true);
		dataWatcher.register(pose, EntityPose.STANDING);
		dataWatcher.register(item, itemStack);
		dataWatcher.register(itemRotation, 0);
	}

	public DataWatcher getDataWatcher() {
		return dataWatcher;
	}

}
