package epicsquid.embers.capability;

public class EmberCapability implements IEmberCapability {

  private int capacity;
  private int ember;

  public EmberCapability(int capacity, int ember) {
    this.capacity = capacity;
    this.ember = ember;
  }

  @Override
  public int getCapacity() {
    return capacity;
  }

  @Override
  public int getEmber() {
    return ember;
  }

  @Override
  public int addEmber(int amount, boolean simulate) {
    int currentAmount = ember;
    int leftover = 0;

    currentAmount += amount;
    if (currentAmount > capacity) {
      leftover = currentAmount - capacity;
      currentAmount = capacity;
    }

    if (!simulate) {
      ember = currentAmount;
    }
    return leftover;
  }

  @Override
  public int removeEmber(int amount, boolean simulate) {
    int currentAmount = ember;
    int removed = amount;

    currentAmount -= amount;
    if (currentAmount < 0) {
      removed = amount + currentAmount;
      currentAmount = 0;
    }

    if (!simulate) {
      ember = currentAmount;
    }
    return removed;
  }
}
