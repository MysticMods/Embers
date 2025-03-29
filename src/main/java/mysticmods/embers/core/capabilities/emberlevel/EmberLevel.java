package mysticmods.embers.core.capabilities.emberlevel;

import mysticmods.embers.Embers;
import mysticmods.embers.core.capabilities.emberemitter.IEmberEmitter;
import mysticmods.embers.core.capabilities.emberintensity.IEmberIntensity;
import mysticmods.embers.core.utils.BlockFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

public class EmberLevel extends SavedData implements IEmberLevel{

    private final Map<BlockPos, Integer> ember = new HashMap<>();
    private final Map<BlockPos, IEmberIntensity> emberListeners = new HashMap<>();
    private final Map<BoundingBox, IEmberEmitter> emitterListeners = new HashMap<>();

    @Override
    public int getEmberForPos(@NotNull BlockPos pos) {
        if (ember.get(pos) == null) return 0;
        return ember.get(pos);
    }

    @Override
    public void setEmberForPos(@NotNull BlockPos pos, int emberIn) {
        BlockPos immutablePos = pos.immutable(); // Always make sure its immutable first
        int validEmber = Math.max(emberIn, 0);
        if (validEmber == 0) {
            ember.remove(immutablePos);
        } else {
            ember.compute(immutablePos, (p, e) -> e != null ? Math.max(e, validEmber) : validEmber);
        }
        // Update listeners
        if (emberListeners.containsKey(immutablePos)) {
            emberListeners.get(immutablePos).setIntensity(validEmber);
        }
    }

    @Override
    public void setEmberForBoundingBox(@NotNull BlockPos emitter, @NotNull BoundingBox box, int[] emberPerRadius) {
        BlockPos.betweenClosedStream(box).forEach(pos -> {
            int radius = BlockFinder.distance(pos, emitter);
            if (radius < emberPerRadius.length) {
                setEmberForPos(pos, emberPerRadius[radius]);
            }
        });
    }

    @Override
    public void setEmberForRadius(@NotNull BlockPos center, int[] emberPerRadius) {
        int r = emberPerRadius.length - 1;
        setEmberForBoundingBox(center, BoundingBox.fromCorners(center.offset(-r, -r, -r), center.offset(r, r, r)), emberPerRadius);
    }

    @Override
    public void addEmberListener(@NotNull BlockPos pos, @NotNull IEmberIntensity intensity) {
        emberListeners.put(pos, intensity);

    }

    @Override
    public void addEmitterListener(@NotNull BoundingBox box, @NotNull IEmberEmitter emitter) {
        emitterListeners.put(box, emitter);
    }

    @Override
    public void clearEmberInBoundingBox(@NotNull BoundingBox box) {
        BlockPos.betweenClosedStream(box).forEach(pos -> setEmberForPos(pos, 0));
        // Update all remaining emitters
        emitterListeners.keySet().stream().filter(bb -> bb.intersects(box)).forEach(bb -> emitterListeners.get(bb).initEmitter(this));
    }


    public @UnknownNullability ListTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        ListTag tag = new ListTag();
        tag.addAll(ember.keySet().stream().map(p -> {
            CompoundTag emberPos = new CompoundTag();

            emberPos.put("pos", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, p).getOrThrow((s) -> {
                Embers.LOGGER.warn(s);
                return new IllegalStateException(s);
            }));

            emberPos.putInt("ember", ember.get(p));
            return emberPos;
        }).toList());
        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider provider, ListTag nbt) {
        nbt.forEach(t -> {
            if (t instanceof CompoundTag tag) {
                ember.put(BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("pos")).getOrThrow((s) -> {
                    Embers.LOGGER.warn(s);
                    return new IllegalStateException(s);
                }), tag.getInt("ember"));
            }
        });
    }

    public static EmberLevel load(CompoundTag tag, HolderLookup.Provider provider) {
        EmberLevel emberLevel = new EmberLevel();
        if (tag.contains("embers")) {
            ListTag listTag = tag.getList("embers", 10);
            emberLevel.deserializeNBT(provider, listTag);
        }
        return emberLevel;
    }

    @Override
    public @UnknownNullability CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("embers", serializeNBT(registries));
        return tag;
    }
}