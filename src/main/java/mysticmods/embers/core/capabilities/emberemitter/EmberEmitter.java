package mysticmods.embers.core.capabilities.emberemitter;

import mysticmods.embers.Embers;
import mysticmods.embers.core.capabilities.emberlevel.IEmberLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Supplier;

public class EmberEmitter implements IEmberEmitter, INBTSerializable<CompoundTag> {

    private int[] intensities;
    private BlockPos pos;
    private BoundingBox box;

    private final Supplier<Boolean> checkIsActive;

    public EmberEmitter(int[] intensities, BlockPos pos, BoundingBox box) {
        this(intensities, pos, box, () -> true);
    }

    public EmberEmitter(int[] intensities, BlockPos pos, BoundingBox box, Supplier<Boolean> checkIsActive) {
        this.intensities = intensities;
        this.pos = pos;
        this.box = box;
        this.checkIsActive = checkIsActive;
    }

    @Override
    public int[] getIntensities() {
        return intensities;
    }

    @Override
    public void initEmitter(@NotNull IEmberLevel levelEmber) {
        if (isActive()) {
            levelEmber.setEmberForBoundingBox(pos, box, intensities);
        }
    }

    @Override
    @NotNull
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    @NotNull
    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean isActive() {
        return checkIsActive.get();
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("intensities", intensities);
        BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).result().ifPresent(t -> tag.put("pos", t));
        BoundingBox.CODEC.encodeStart(NbtOps.INSTANCE, box).result().ifPresent(t -> tag.put("box", t));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        intensities = nbt.getIntArray("intensities");
        box = BoundingBox.CODEC.parse(NbtOps.INSTANCE, nbt.get("box"))
                .getOrThrow((s) -> {
                    Embers.LOGGER.warn("Emitter missing bounding box: {}", s);
                    return new IllegalStateException("Emitter missing bounding box: " + s);
                });
        pos = BlockPos.CODEC.parse(NbtOps.INSTANCE, nbt.get("pos"))
                .getOrThrow((s) -> {
                    Embers.LOGGER.warn("Emitter missing pos: {}", s);
                    return new IllegalStateException("Emitter missing pos: " + s);
                });
    }
}
