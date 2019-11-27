package epicsquid.embers.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

public class EntityEmber extends Entity  {

    // Position of the Entity in the world
    private double x, y, z;

    public EntityEmber(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }


    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.x = compound.getDouble("x");
        this.y = compound.getDouble("y");
        this.z = compound.getDouble("z");
        this.setPosition(x, y, z);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putDouble("x", x);
        compound.putDouble("y", y);
        compound.putDouble("z", z);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}