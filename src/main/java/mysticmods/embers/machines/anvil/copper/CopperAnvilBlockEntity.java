package mysticmods.embers.machines.anvil.copper;

import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersDataComponents;
import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.machines.anvil.AnvilItemHandler;
import mysticmods.embers.particles.options.EmbersParticleOptions;
import mysticmods.embers.particles.options.HammerSparkParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class CopperAnvilBlockEntity extends LodestoneBlockEntity {

    private final EmberIntensity ember = new EmberIntensity(100, 100,null);
    public final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public CopperAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.COPPER_ANVIL.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);

        if (playerStack.getItem() == EmbersItems.IRON_HAMMER.get()) {
            if (this.itemHandler.getFilledSlotAmount() == 1) {
                MalleableMetalDataComponent data = this.itemHandler.getStackInSlot(0).get(EmbersDataComponents.MALLEABLE_METAL);
                if(data != null){
                    smithIngot(this.itemHandler.getStackInSlot(0), data, player);
                    data = this.itemHandler.getStackInSlot(0).get(EmbersDataComponents.MALLEABLE_METAL);
                    if(data != null){
                        if(data.getIngots() == 0 && data.getNuggets() == 0){
                            this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                        }
                    }
                }
                return ItemInteractionResult.CONSUME;
            }
        }

        //If hand is empty return the itemstack from itemhandler to the players hand
        if(playerStack == ItemStack.EMPTY){
            player.setItemInHand(hand, this.itemHandler.extractItem(0, 1, false));
            return ItemInteractionResult.SUCCESS;
        }

        player.setItemInHand(hand, this.itemHandler.addItemStack(playerStack));
        return ItemInteractionResult.SUCCESS;
    }

    public void smithIngot(ItemStack stack, MalleableMetalDataComponent metalData, Player player) {
        if(metalData.getIngots() > 0){
            player.addItem(metalData.getMalleableMetal().ingot.getItems()[0].copy());
            metalData = metalData.removeIngots(1);
            stack.set(EmbersDataComponents.MALLEABLE_METAL, metalData);
        } else {
            ItemStack nuggets = metalData.getMalleableMetal().nugget.getItems()[0].copy();
            nuggets.grow(metalData.getNuggets() - 1);
            player.addItem(nuggets);
            metalData = metalData.removeNuggets(metalData.getNuggets());
            stack.set(EmbersDataComponents.MALLEABLE_METAL, metalData);
        }

        player.swing(InteractionHand.MAIN_HAND, true);player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ANVIL_BREAK,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );

        if(level.isClientSide()){
            for (int i = 0; i < 20; i++) {
                double speedX = (level.random.nextDouble() - 0.5) * 0.2;
                double speedY = level.random.nextDouble() * 0.3 + 0.1;
                double speedZ = (level.random.nextDouble() - 0.5) * 0.2;

                level.addParticle(
                        new HammerSparkParticleOptions(1, 0.5f, 0),
                        getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.9f, getBlockPos().getZ() + 0.5f,
                        speedX, speedY, speedZ
                );
            }
        }
    }

    public EmberIntensity getEmber() {
        return ember;
    }

    public AnvilItemHandler getItemHandler() {
        return itemHandler;
    }
}
