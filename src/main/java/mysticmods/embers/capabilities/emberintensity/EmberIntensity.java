package mysticmods.embers.capabilities.emberintensity;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class EmberIntensity implements IEmberIntensity, INBTSerializable<IntTag> {

    private int intensity;
    private int maxIntensity;
    private int minIntensity;

    public EmberIntensity(int minIntensity, int maxIntensity) {
        this(0, minIntensity, maxIntensity);
    }

    public EmberIntensity(int intensity, int minIntensity, int maxIntensity) {
        this.intensity = intensity;
        this.maxIntensity = maxIntensity;
        this.minIntensity = minIntensity;
    }


    @Override
    public int getMinIntensity() {
        return minIntensity;
    }

    @Override
    public int getMaxIntensity() {
        return maxIntensity;
    }

    @Override
    public int getIntensity() {
        return intensity;
    }

    @Override
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    @Override
    public @UnknownNullability IntTag serializeNBT(HolderLookup.Provider provider) {
        return IntTag.valueOf(intensity);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, IntTag nbt) {
        intensity = nbt.getAsInt();
    }
}