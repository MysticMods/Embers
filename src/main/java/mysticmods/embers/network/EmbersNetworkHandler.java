package mysticmods.embers.network;

import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class EmbersNetworkHandler {

    public static void handleCaminiteForgePayload(CaminiteForgeToggleAlloyData msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            if (player != null) {
                Level level = player.level();
                if (level.getBlockEntity(msg.pos()) instanceof CaminiteForgeBlockEntity be) {
                    be.toggleAlloyMode(player); // Your method to update state
                }
            }
        });
    }
}
