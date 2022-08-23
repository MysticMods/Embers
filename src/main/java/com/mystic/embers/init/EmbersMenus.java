package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.core.machines.forge.CaminiteForgeMenu;
import com.mystic.embers.client.screen.CaminiteForgeScreen;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;

public class EmbersMenus {
	private static final Registrate REGISTRATE = Embers.registrate();
	public static final MenuEntry<CaminiteForgeMenu> CAMINITE_FORGE = REGISTRATE.menu("caminite_forge", CaminiteForgeMenu::factory, () -> CaminiteForgeScreen::new).register();

	public static void init() {
	}
}
