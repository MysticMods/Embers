package epicsquid.embers.ember;

import epicsquid.embers.api.IEmberEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityEmberBase extends Entity implements IEmberEntity {

  // Position of the Entity in the world
  private double x, y, z;

  public EntityEmberBase(World worldIn) {
    super(worldIn);
  }

  @Override
  protected void readEntityFromNBT(NBTTagCompound compound) {
    this.x = compound.getDouble("x");
    this.y = compound.getDouble("y");
    this.z = compound.getDouble("z");
    this.setPosition(x, y, z);
  }

  @Override
  protected void writeEntityToNBT(NBTTagCompound compound) {
    compound.setDouble("x", x);
    compound.setDouble("y", y);
    compound.setDouble("z", z);
  }
}
