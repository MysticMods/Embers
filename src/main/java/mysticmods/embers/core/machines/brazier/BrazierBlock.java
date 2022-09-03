package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import javax.annotation.Nonnull;

public class BrazierBlock extends LodestoneEntityBlock<BrazierEntity> {

	private static final VoxelShape SHAPE_DOWN = Shapes.box(0, 0, 0, 1, 0.85, 1);

	public BrazierBlock(Properties pProperties) {
		super(pProperties);
		setBlockEntity(EmbersBlockEntities.BRAZIER);
	}

	@Override
	@Nonnull
	public VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
		return SHAPE_DOWN;
	}
}
