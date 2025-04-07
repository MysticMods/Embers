package mysticmods.embers.network;

import io.netty.buffer.ByteBuf;
import mysticmods.embers.Embers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record CaminiteForgeToggleAlloyData(BlockPos pos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CaminiteForgeToggleAlloyData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Embers.MODID, "toggle_alloy_data"));

    public static final StreamCodec<ByteBuf, CaminiteForgeToggleAlloyData> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            CaminiteForgeToggleAlloyData::pos,
            CaminiteForgeToggleAlloyData::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
