package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.core.base.EmberEmitterBlockEntity;
import mysticmods.embers.core.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class BrazierBlockEntity extends EmberEmitterBlockEntity {
    private final EmberEmitter emitter;
    public boolean running = false;

    public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.BRAZIER.get(), pos, blockState);

        // TODO make a helper method for this
        BlockPos lowerBound = getBlockPos().offset(-3, -3, -3);
        BlockPos upperBound = getBlockPos().offset(3, 3, 3);
        emitter = new EmberEmitter(new int[]{100, 100, 100, 50}, getBlockPos(), new BoundingBox(lowerBound.getX(), lowerBound.getY(), lowerBound.getZ(), upperBound.getX(), upperBound.getY(), upperBound.getZ()), () -> running);

    }

    @Override
    public @NotNull EmberEmitter getEmitter() {
        return emitter;
    }
}
