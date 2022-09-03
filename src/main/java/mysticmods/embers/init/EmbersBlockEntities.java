package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.core.machines.forge.CaminiteForgeEntity;
import mysticmods.embers.core.machines.crystallizer.CrystallizerEntity;
import mysticmods.embers.core.machines.brazier.BrazierEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class EmbersBlockEntities {
	private static final Registrate REGISTRATE = Embers.registrate();

	public static final BlockEntityEntry<BrazierEntity> BRAZIER = REGISTRATE.blockEntity("brazier", BrazierEntity::new).validBlocks(EmbersBlocks.BRAZIER).register();
	public static final BlockEntityEntry<CaminiteForgeEntity> CAMINITE_FORGE = REGISTRATE.blockEntity("caminite_forge", CaminiteForgeEntity::new).validBlocks(EmbersBlocks.CAMINITE_FORGE).register();
	public static final BlockEntityEntry<CrystallizerEntity> CRYSTALLIZER = REGISTRATE.blockEntity("ember_crystallizer", CrystallizerEntity::new).validBlocks(EmbersBlocks.EMBER_CRYSTALLIZER).register();

	public static void init() {
	}
}
