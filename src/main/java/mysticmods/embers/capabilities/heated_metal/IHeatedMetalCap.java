package mysticmods.embers.capabilities.heated_metal;

import mysticmods.embers.registries.MalleableMetal;
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
     * Gets the malleable metal of the Itemstack
     *
     * @return an MalleableMetal.
     */
    MalleableMetal getMalleableMetal();

    void setMalleableMetal(MalleableMetal malleableMetal);

    /**
     * Gets the amount ingots of the heated metal
     *
     * @return an int.
     */
    int getIngots();
    void addIngots(int ingotAmount);

    /**
     * Gets the amount nuggets of the heated metal
     *
     * @return an int.
     */
    int getNuggets();
    void addNuggets(int nuggetAmount);

    void copyFromCapability(IHeatedMetalCap cap);

    void clearCap();


}