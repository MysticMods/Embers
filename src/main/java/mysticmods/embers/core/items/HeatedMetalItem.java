package mysticmods.embers.core.items;

import mysticmods.embers.core.capability.heatedmetals.HeatedMetalProvider;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class HeatedMetalItem extends Item {

	public HeatedMetalItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		pStack.getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> cap.removeStackHeat(1));

		super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new HeatedMetalProvider(0, 0, ItemStack.EMPTY);
	}

}
