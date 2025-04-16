package mysticmods.embers.machines.brazier;

import mysticmods.embers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class BrazierBlock extends LodestoneEntityBlock<BrazierBlockEntity> {

    private static final VoxelShape SHAPE_DOWN = Shapes.box(0, 0, 0, 1, 0.6, 1).optimize();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public BrazierBlock(Properties properties) {
        super(properties);
        setBlockEntity(ModBlockEntities.BRAZIER);
        registerDefaultState(getStateDefinition().any().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE_DOWN;
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
