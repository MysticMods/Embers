package epicsquid.embers.entity;

import epicsquid.embers.capability.EmberCapability;
import epicsquid.embers.setup.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnGlobalEntityPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class EmberEntity extends Entity  {

    // Position of the Entity in the world
    private double x, y, z;
    private boolean master = false;
    public LazyOptional<EmberCapability> emberCapability = LazyOptional.of(this::createHandler);

    public EmberEntity(EntityType<? extends EmberEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == EmberCapability.EMBER_CAPABILITY) {
            return emberCapability.cast();
        }
        return super.getCapability(cap);
    }
    @Override
    protected void registerData() {

    }

    @Override
    public void tick() {
        emberCapability.ifPresent(h -> {
            //System.out.println(h.getEmber());
        });
        super.tick();
    }

    public EmberCapability createHandler(){
        return new EmberCapability(10000, 0);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.x = compound.getDouble("x");
        this.y = compound.getDouble("y");
        this.z = compound.getDouble("z");
        this.master = compound.getBoolean("master");

        CompoundNBT capTag = compound.getCompound("emberCap");
        emberCapability.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(capTag));

        this.setPosition(x, y, z);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putDouble("x", x);
        compound.putDouble("y", y);
        compound.putDouble("z", z);
        compound.putBoolean("master", master);

        emberCapability.ifPresent(h -> {
            CompoundNBT emberCompound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compound.put("emberCap", emberCompound);
        });

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }
}