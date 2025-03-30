package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrazierBlock extends Block implements EntityBlock {

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0, 0, 0, 1, 0.6, 1).optimize();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BrazierBlock(Properties properties) {
        super(properties);

        registerDefaultState(getStateDefinition().any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new BrazierBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (type == EmbersBlockEntities.BRAZIER.get()) {
            return (lvl, pos, st, be) -> {
                if (be instanceof BrazierBlockEntity brazier) {
                    BrazierBlockEntity.tick(lvl, pos, st, brazier);
                }
            };
        }
        return null;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_DOWN;
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack pStack, @NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof BrazierBlockEntity blockEntity) {
            var earlyResult = blockEntity.onUseWithItem(pPlayer, pStack, pHand);
            return earlyResult.consumesAction() ? earlyResult : blockEntity.onUse(pPlayer, pHand);
        }
        return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if(state.getValue(LIT)) {
            return 15;
        } else {
            return 0;
        }
    }
}
