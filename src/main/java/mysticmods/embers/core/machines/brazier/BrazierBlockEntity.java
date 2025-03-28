package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.core.base.EmberEmitterBlockEntity;
import mysticmods.embers.core.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.core.utils.BlockEntityUtil;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class BrazierBlockEntity extends EmberEmitterBlockEntity {

    private final EmberEmitter emitter;
    private final ItemStackHandler itemHandler;

    public boolean running = false;

    public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.BRAZIER.get(), pos, blockState);

        itemHandler = new ItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return 16;
            }
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() == Items.COAL;
            }
        };


        // TODO make a helper method for this
        BlockPos lowerBound = getBlockPos().offset(-3, -3, -3);
        BlockPos upperBound = getBlockPos().offset(3, 3, 3);
        emitter = new EmberEmitter(new int[]{100, 100, 100, 50}, getBlockPos(), new BoundingBox(lowerBound.getX(), lowerBound.getY(), lowerBound.getZ(), upperBound.getX(), upperBound.getY(), upperBound.getZ()), () -> running);

    }

    public void updateViaState() {
        setChanged();
        BlockEntityUtil.updateViaState(this);
    }

    public InteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        return InteractionResult.PASS;
    }

    public InteractionResult onUseWithoutItem(Player pPlayer) {
        return InteractionResult.PASS;
    }

    public InteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);
        if (this.itemHandler.isItemValid(0, playerStack)) {
            ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
            player.setItemInHand(hand, returnStack);
            updateViaState();
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull EmberEmitter getEmitter() {
        return emitter;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
