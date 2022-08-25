package mysticmods.embers.core.machines.crystallizer;

import mysticmods.embers.core.utils.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EmberCrystallizerEntity extends BlockEntity implements TickBlockEntity {

	public EmberCrystallizerEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
	}

	@Override
	public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		CompoundTag tag = pkt.getTag();
		if (tag != null) {
			load(tag);
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag pTag) {
		super.saveAdditional(pTag);
	}

	@Override
	public void load(@NotNull CompoundTag pTag) {
		super.load(pTag);

	}
}
