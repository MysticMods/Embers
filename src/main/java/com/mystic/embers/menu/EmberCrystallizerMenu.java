package com.mystic.embers.menu;

import com.mystic.embers.blockentity.EmberCrystallizerEntity;
import com.mystic.embers.menu.base.EmberMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EmberCrystallizerMenu extends EmberMenu<EmberCrystallizerEntity> {

	protected EmberCrystallizerMenu(@Nullable MenuType<?> pMenuType, @Nullable EmberCrystallizerEntity entity, Inventory playerInventory, int pContainerId) {
		super(pMenuType, entity, playerInventory, pContainerId);
	}

	@Override
	public boolean canQuickMoveStack(Player player, ItemStack stack) {
		return false;
	}
}
