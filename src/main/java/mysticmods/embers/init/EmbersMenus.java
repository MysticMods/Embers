package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;
import mysticmods.embers.Embers;
import mysticmods.embers.client.screen.CaminiteForgeScreen;
import mysticmods.embers.core.machines.forge.CaminiteForgeMenu;

public class EmbersMenus {
	private static final Registrate REGISTRATE = Embers.registrate();
	public static final MenuEntry<CaminiteForgeMenu> CAMINITE_FORGE = REGISTRATE.menu("caminite_forge", CaminiteForgeMenu::factory, () -> CaminiteForgeScreen::new).register();

	public static void init() {
	}
}
