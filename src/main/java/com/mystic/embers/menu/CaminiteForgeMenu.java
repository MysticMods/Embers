package com.mystic.embers.menu;

import com.mystic.embers.blockentity.CaminiteForgeEntity;
import com.mystic.embers.init.ModMenus;
import com.mystic.embers.menu.base.EmberMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class CaminiteForgeMenu extends EmberMenu<CaminiteForgeEntity> {


    protected CaminiteForgeMenu(@Nullable CaminiteForgeEntity entity, Inventory playerInventory, int pContainerId) {
        super(ModMenus.CAMINITE_FORGE.get(), entity, playerInventory, pContainerId);
        if(entity != null){
            this.entity = entity;
            entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(c ->{
                addSlot(new SlotItemHandler(c, 0, 79, 7));
                addSlot(new SlotItemHandler(c, 1, 79, 58));
            });
        }
    }

    @Override
    public boolean canQuickMoveStack(Player player, ItemStack stack) {
        return player.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), player.getLevel()).isPresent();
    }

    public static CaminiteForgeMenu factory(MenuType<CaminiteForgeMenu> type, int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        BlockEntity entity = inventory.player.level.getBlockEntity(buf.readBlockPos());
        if (entity instanceof CaminiteForgeEntity castBlockEntity)
            return new CaminiteForgeMenu(castBlockEntity, inventory, pContainerId);
        return new CaminiteForgeMenu(null, inventory, pContainerId);
    }

    public static MenuConstructor getServerContainer(CaminiteForgeEntity entity, BlockPos pos){
        return (id, playerInv, player) -> new CaminiteForgeMenu(entity, playerInv, id);

    }
}
