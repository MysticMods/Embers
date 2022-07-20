package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.blockentity.EmberDiffuserEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntity {
    private static final Registrate REGISTRATE = Embers.registrate();

    public static final BlockEntityEntry<EmberDiffuserEntity> EMBER_DIFFUSER = REGISTRATE.blockEntity("ember_diffuser", EmberDiffuserEntity::new).validBlocks(ModBlocks.EMBER_DIFFUSER).register();

    public static void classload() { }
}
