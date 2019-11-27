package epicsquid.embers.blocks;

import java.util.function.Supplier;

public interface ITile<T> {

    Supplier<T> getTile();

}
