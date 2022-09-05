package mysticmods.embers.api.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeatedMetal extends INBTSerializable<CompoundTag> {

    /**
     * Gets the heat of the current ItemStack that this capability is attached to.
     * @return heat amount of the stack.
     */
    int getStackHeat();
    void setStackHeat(int stackHeat);

    /**
     * Gets the max heat of the ItemStack
     * @return the maximum heat of the current metal in the ItemStack
     */
    int getMaxHeat();
    void setMaxHeat(int maxHeat);

    /**
     * Gets the item stack that is tied to the heat
     * @return an ItemStack with a metal.
     */
    ItemStack getMetal();
    void setMetalStack(ItemStack stack);

    void copyFromCapability(IHeatedMetal cap);

}
