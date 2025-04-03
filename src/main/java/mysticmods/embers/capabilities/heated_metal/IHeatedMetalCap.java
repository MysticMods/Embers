package mysticmods.embers.capabilities.heated_metal;

import net.minecraft.world.item.ItemStack;

public interface IHeatedMetalCap {

    /**
     * Gets the heat of the current ItemStack that this capability is attached to.
     *
     * @return heat amount of the stack.
     */
    int getStackHeat();

    void setStackHeat(int stackHeat);

    void removeStackHeat(int amount);

    /**
     * Gets the max heat of the ItemStack
     *
     * @return the maximum heat of the current metal in the ItemStack
     */
    int getMaxHeat();

    void setMaxHeat(int maxHeat);

    /**
     * Gets the item stack that is tied to the heat
     *
     * @return an ItemStack with a metal.
     */
    ItemStack getMetal();

    void setMetalStack(ItemStack stack);

    void copyFromCapability(IHeatedMetalCap cap);

    void clearCap();


}