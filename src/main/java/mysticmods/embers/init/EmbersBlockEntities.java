package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import mysticmods.embers.Embers;
import mysticmods.embers.client.renderer.blocks.CopperAnvilRenderer;
import mysticmods.embers.core.machines.anvil.copper.CopperAnvilEntity;
import mysticmods.embers.core.machines.brazier.BrazierEntity;
import mysticmods.embers.core.machines.crystallizer.CrystallizerEntity;
import mysticmods.embers.core.machines.forge.CaminiteForgeEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class EmbersBlockEntities {
	private static final Registrate REGISTRATE = Embers.registrate();

	public static final BlockEntityEntry<BrazierEntity> BRAZIER = REGISTRATE.blockEntity("brazier", BrazierEntity::new).validBlocks(EmbersBlocks.BRAZIER).register();
	public static final BlockEntityEntry<CaminiteForgeEntity> CAMINITE_FORGE = REGISTRATE.blockEntity("caminite_forge", CaminiteForgeEntity::new).validBlocks(EmbersBlocks.CAMINITE_FORGE).register();
	public static final BlockEntityEntry<CopperAnvilEntity> COPPER_ANVIL = REGISTRATE.blockEntity("copper_anvil", CopperAnvilEntity::new).validBlocks(EmbersBlocks.COPPER_ANVIL).register();
	public static final BlockEntityEntry<CrystallizerEntity> CRYSTALLIZER = REGISTRATE.blockEntity("ember_crystallizer", CrystallizerEntity::new).validBlocks(EmbersBlocks.CRYSTALLIZER).register();

	public static void init() {
	}

	@Mod.EventBusSubscriber(modid = Embers.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientOnly {
		@SubscribeEvent
		public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
			event.registerBlockEntityRenderer(COPPER_ANVIL.get(), CopperAnvilRenderer::new);
		}
	}
}
