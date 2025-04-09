package mysticmods.embers.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class MalleableMetal {

    public static final Codec<MalleableMetal> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            TagKey.hashedCodec(Registries.BLOCK).fieldOf("ore_tag").forGetter(MalleableMetal::getOreTag),
            Ingredient.CODEC.fieldOf("nugget").forGetter(MalleableMetal::getNugget),
            Ingredient.CODEC.fieldOf("ingot").forGetter(MalleableMetal::getIngot),
            Ingredient.CODEC.fieldOf("raw_ore").forGetter(MalleableMetal::getRawOre)
    ).apply(inst, MalleableMetal::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TagKey<Block>> BLOCK_TAG_STREAM_CODEC =
            StreamCodec.of(
                    (buf, tag) -> buf.writeResourceLocation(tag.location()),
                    buf -> TagKey.create(Registries.BLOCK, buf.readResourceLocation())
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, MalleableMetal> STREAM_CODEC =
            StreamCodec.composite(
                    BLOCK_TAG_STREAM_CODEC, MalleableMetal::getOreTag,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getNugget,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getIngot,
                    Ingredient.CONTENTS_STREAM_CODEC, MalleableMetal::getRawOre,
                    MalleableMetal::new
            );

    private final TagKey<Block> oreTag;
    private final Ingredient nugget;
    private final Ingredient ingot;
    private final Ingredient rawOre;

    public MalleableMetal(TagKey<Block> oreTag, Ingredient nugget, Ingredient ingot, Ingredient rawOre) {
        this.oreTag = oreTag;
        this.nugget = nugget;
        this.ingot = ingot;
        this.rawOre = rawOre;
    }

    public TagKey<Block> getOreTag() {
        return oreTag;
    }

    public Ingredient getNugget() {
        return nugget;
    }

    public Ingredient getIngot() {
        return ingot;
    }

    public Ingredient getRawOre() {
        return rawOre;
    }
}
