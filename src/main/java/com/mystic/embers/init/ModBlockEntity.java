package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.blockentity.CaminiteForgeEntity;
import com.mystic.embers.blockentity.EmberDiffuserEntity;
import com.mystic.embers.blockentity.CaminiteUnfiredForgeEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntity {
    private static final Registrate REGISTRATE = Embers.registrate();

    public static final BlockEntityEntry<EmberDiffuserEntity> EMBER_DIFFUSER = REGISTRATE.blockEntity("ember_diffuser", EmberDiffuserEntity::new).validBlocks(ModBlocks.EMBER_DIFFUSER).register();
    public static final BlockEntityEntry<CaminiteUnfiredForgeEntity> UNFIRED_CAMINITE_FORGE = REGISTRATE.blockEntity("unfired_caminite_forge", CaminiteUnfiredForgeEntity::new).validBlocks(ModBlocks.UNFIRED_CAMINITE_FORGE).register();
    public static final BlockEntityEntry<CaminiteForgeEntity> CAMINITE_FORGE = REGISTRATE.blockEntity("caminite_forge", CaminiteForgeEntity::new).validBlocks(ModBlocks.CAMINITE_FORGE).register();

    public static void classload() { }
}
