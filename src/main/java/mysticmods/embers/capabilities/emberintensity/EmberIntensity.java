package mysticmods.embers.capabilities.emberintensity;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Function;

public class EmberIntensity implements IEmberIntensity, INBTSerializable<IntTag> {

    private int intensity;
    private int maxIntensity;
    private int minIntensity;
    private Runnable  updateEntity;

    //Add function reference to constructor
    public EmberIntensity(int minIntensity, int maxIntensity, Runnable  function) {
        this(0, minIntensity, maxIntensity, function);
    }

    public EmberIntensity(int intensity, int minIntensity, int maxIntensity, Runnable  function) {
        this.intensity = intensity;
        this.maxIntensity = maxIntensity;
        this.minIntensity = minIntensity;
        this.updateEntity = function;
    }

    public boolean hasEmberForOperation(){
        return intensity >= minIntensity;
    }

    public boolean isOverheated() {
        return intensity > maxIntensity;
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
        this.updateEntity.run();
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