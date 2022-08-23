package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.core.machines.forge.CaminiteForgeEntity;
import com.mystic.embers.core.machines.forge.CaminiteUnfiredForgeEntity;
import com.mystic.embers.core.machines.crystallizer.EmberCrystallizerEntity;
import com.mystic.embers.core.machines.diffuser.EmberDiffuserEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class EmbersBlockEntities {
	private static final Registrate REGISTRATE = Embers.registrate();

	public static final BlockEntityEntry<EmberDiffuserEntity> EMBER_DIFFUSER = REGISTRATE.blockEntity("ember_diffuser", EmberDiffuserEntity::new).validBlocks(EmbersBlocks.EMBER_DIFFUSER).register();
	public static final BlockEntityEntry<CaminiteUnfiredForgeEntity> UNFIRED_CAMINITE_FORGE = REGISTRATE.blockEntity("unfired_caminite_forge", CaminiteUnfiredForgeEntity::new).validBlocks(EmbersBlocks.UNFIRED_CAMINITE_FORGE).register();
	public static final BlockEntityEntry<CaminiteForgeEntity> CAMINITE_FORGE = REGISTRATE.blockEntity("caminite_forge", CaminiteForgeEntity::new).validBlocks(EmbersBlocks.CAMINITE_FORGE).register();
	public static final BlockEntityEntry<EmberCrystallizerEntity> EMBER_CRYSTALLIZER = REGISTRATE.blockEntity("ember_crystallizer", EmberCrystallizerEntity::new).validBlocks(EmbersBlocks.EMBER_CRYSTALLIZER).register();

	public static void init() {
	}
}
