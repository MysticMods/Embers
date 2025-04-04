package mysticmods.embers.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class MalleableMetal {

    public static final Codec<MalleableMetal> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Block.CODEC.fieldOf("ore").forGetter(MalleableMetal::getOre),
            Ingredient.CODEC.fieldOf("nugget").forGetter(MalleableMetal::getNugget),
            Ingredient.CODEC.fieldOf("ingot").forGetter(MalleableMetal::getIngot),
            Ingredient.CODEC.fieldOf("raw_ore").forGetter(MalleableMetal::getRaw_ore)
            ).apply(inst, MalleableMetal::new));


    public static final StreamCodec<RegistryFriendlyByteBuf, MalleableMetal> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.registry(Registries.BLOCK), MalleableMetal::getOre,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getNugget,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getIngot,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getRaw_ore,
                    MalleableMetal::new
            );

    public final Block ore;
    public final Ingredient nugget;
    public final Ingredient ingot;
    public final Ingredient raw_ore;

    public MalleableMetal(Block ore, Ingredient nugget, Ingredient ingot, Ingredient rawOre) {
        this.ore = ore;
        this.nugget = nugget;
        this.ingot = ingot;
        raw_ore = rawOre;
    }

    public Block getOre() {
        return ore;
    }

    public Ingredient getIngot() {
        return ingot;
    }

    public Ingredient getNugget() {
        return nugget;
    }

    public Ingredient getRaw_ore() {
        return raw_ore;
    }
}
