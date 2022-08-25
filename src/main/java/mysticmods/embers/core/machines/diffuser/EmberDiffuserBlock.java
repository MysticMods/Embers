package mysticmods.embers.core.machines.diffuser;

import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class EmberDiffuserBlock extends Block implements EntityBlock {

	private static final VoxelShape SHAPE_DOWN = Shapes.box(0, 0, 0, 1, 0.85, 1);

	public EmberDiffuserBlock(Properties pProperties) {
		super(pProperties);
	}


	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
		return new EmberDiffuserEntity(EmbersBlockEntities.EMBER_DIFFUSER.get(), pPos, pState);

	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return TickBlockEntity::clientTick;
		} else {
			return TickBlockEntity::serverTick;
		}
	}

	@Override
	public VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
		return SHAPE_DOWN;
	}
}
