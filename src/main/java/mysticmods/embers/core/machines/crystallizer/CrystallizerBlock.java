package mysticmods.embers.core.machines.crystallizer;

import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import javax.annotation.Nonnull;

public class CrystallizerBlock extends LodestoneEntityBlock<CrystallizerEntity> {

	public CrystallizerBlock(Properties pProperties) {
		super(pProperties);
		setBlockEntity(EmbersBlockEntities.CRYSTALLIZER);
	}
}
