package mysticmods.embers.core.machines.forge;

import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CaminiteForgeBlock extends Block implements EntityBlock {

	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public CaminiteForgeBlock(Properties props) {
		super(props);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(LIT);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
		return new CaminiteForgeEntity(EmbersBlockEntities.CAMINITE_FORGE.get(), pPos, pState);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
		return TickBlockEntity.getTicker(level);
	}


	@Override
	@Nonnull
	public InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
		if (pLevel.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			MenuProvider menuprovider = new SimpleMenuProvider(CaminiteForgeMenu.getServerContainer((CaminiteForgeEntity) pLevel.getBlockEntity(pPos), pPos), Component.empty());
			if (pPlayer instanceof ServerPlayer serverPlayer) {
				NetworkHooks.openScreen(serverPlayer, menuprovider, buf -> buf.writeBlockPos(pPos));
			}
			return InteractionResult.CONSUME;
		}
	}


}
