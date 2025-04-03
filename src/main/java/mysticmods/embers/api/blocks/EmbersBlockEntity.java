package mysticmods.embers.api.blocks;

import mysticmods.embers.core.machines.brazier.BrazierBlockEntity;
import mysticmods.embers.core.utils.BlockEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class EmbersBlockEntity extends BlockEntity {

    public EmbersBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity blockEntity) {
    }

    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public InteractionResult onUseWithoutItem(Player pPlayer) {
        return InteractionResult.PASS;
    }

    public ItemInteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void onBlockBroken() {

    }

    public void updateViaState() {
        setChanged();
        BlockEntityUtil.updateViaState(this);
    }

    protected void dropItemHandler(ItemStackHandler itemHandler){
        if(!level.isClientSide()){
            ItemStack stack = itemHandler.getStackInSlot(0);
            if(!stack.isEmpty()){
                Block.popResource(level, getBlockPos(), stack);
                itemHandler.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
    }

}
