package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.menu.CaminiteForgeMenu;
import com.mystic.embers.menu.CaminiteForgeScreen;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;

public class ModMenus {
	private static final Registrate REGISTRATE = Embers.registrate();
	public static final MenuEntry<CaminiteForgeMenu> CAMINITE_FORGE = REGISTRATE.menu("caminite_forge", CaminiteForgeMenu::factory, () -> CaminiteForgeScreen::new).register();

	public static void classload() {
	}
}
