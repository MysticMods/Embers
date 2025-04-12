package mysticmods.embers.utils;


import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

public class SDUtil {

    public static EmberLevel getLevelEmbersData(Level level) {
        if (level.isClientSide()) {
            return null;
        }

        if (level.getServer() == null) {
            return null;
        }

        ServerLevel dimension = level.getServer().getLevel(level.dimension());
        if(dimension == null){
            return null;
        }

        return dimension.getDataStorage().computeIfAbsent(new SavedData.Factory<>(EmberLevel::create, EmberLevel::load), "embers");
    }

}
