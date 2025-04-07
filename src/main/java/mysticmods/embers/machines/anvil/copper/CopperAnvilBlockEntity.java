package mysticmods.embers.machines.anvil.copper;

import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.heated_metal.HeatedMetalCap;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.machines.anvil.AnvilItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class CopperAnvilBlockEntity extends LodestoneBlockEntity {

    private final EmberIntensity ember = new EmberIntensity(100, 100,null);
    public final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };

    public CopperAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.COPPER_ANVIL.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);


        if (playerStack.getItem() == EmbersItems.IRON_HAMMER.get()) {
            if (this.itemHandler.getFilledSlotAmount() == 1 && this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).isPresent()) {
                this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> smithIngot(cap, level));
                return ItemInteractionResult.SUCCESS;
            }
        }

        player.setItemInHand(hand, this.itemHandler.addItemStack(playerStack));
        return ItemInteractionResult.SUCCESS;
    }

    public void smithIngot(HeatedMetalCap cap, Level level) {
        System.out.println(cap.getMetal().getItem());
//        if (MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).isPresent()) {
//            MetalFamilyRegistry.MetalFamily family = MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).get();
//            int nuggets = 0;
//            float f = EntityType.ITEM.getHeight() / 2.0F;
//            int heatProcentage = cap.getStackHeat() / (cap.getMaxHeat() / 100);
//            for (int i = heatProcentage; i > 25; i -= 25) {
//                nuggets++;
//            }
//            for (int i = nuggets; i > 0; i--) {
//                ItemStack nuggetStack = new ItemStack(family.nugget);
//                double d0 = (double) ((float) getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
//                double d1 = (double) ((float) getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double) f;
//                double d2 = (double) ((float) getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
//                ItemEntity entity = new ItemEntity(level, d0, d1, d2, nuggetStack);
//                entity.setDefaultPickUpDelay();
//                level.addFreshEntity(entity);
//            }
//
//            ItemStack ingotStacl = new ItemStack(family.ingot);
//            double d0 = (double) ((float) getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
//            double d1 = (double) ((float) getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double) f;
//            double d2 = (double) ((float) getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
//            ItemEntity entity = new ItemEntity(level, d0, d1, d2, ingotStacl);
//            entity.setDefaultPickUpDelay();
//            level.addFreshEntity(entity);
//
//            this.itemHandler.getStackInSlot(0).shrink(1);
//        }
    }

    public EmberIntensity getEmber() {
        return ember;
    }

    public AnvilItemHandler getItemHandler() {
        return itemHandler;
    }
}
