package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.api.EmbersTags;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.world.level.block.Blocks;

public class ModTags {
    static {

        final Registrate REGISTRATE = Embers.registrate();
        REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, b -> {

        });

        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, b -> {
            b.tag(EmbersTags.Blocks.EMBER_HEAT_BLOCK).add(Blocks.LAVA);
            b.tag(EmbersTags.Blocks.EMBER_ACCEPTING);
            b.tag(EmbersTags.Blocks.EMBER_GENERATOR);
            // Internal mod tags
            //b.tag(EmbersTags.Blocks.EMBER_HEAT_BLOCK).addTags(Blocks.EARTH_SOIL, Blocks.AIR_SOIL, Blocks.FIRE_SOIL, Blocks.WATER_SOIL, Blocks.ELEMENTAL_SOIL);

        });

        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, b -> {

        });
    }

    public static void classload() {
    }
}