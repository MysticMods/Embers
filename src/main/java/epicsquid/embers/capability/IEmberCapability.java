package epicsquid.embers.capability;

public interface IEmberCapability {

    /**
     * The amount of ember the block/item/entity can store total
     *
     * @return The amount of Ember storable
     */
    int getCapacity();

    /**
     * The current amount of ember in the thing
     *
     * @return The current ember amount
     */
    int getEmber();

    /**
     * Add ember from the thing
     *
     * @param amount The amount of ember to add
     * @param simulate True if the operation should be simulated
     * @return The amount of ember that is left over after being added
     */
    int addEmber(int amount, boolean simulate);

    /**
     * Remove ember from the thing
     *
     * @param amount The amount of ember to remove
     * @param simulate True if the operation should be simulated
     * @return The amount of ember that was removed
     */
    int removeEmber(int amount, boolean simulate);


}