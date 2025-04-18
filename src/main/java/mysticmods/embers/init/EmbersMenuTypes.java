package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeAlloyMenu;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Embers.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CaminiteForgeMenu>> CAMINITE_FORGE = MENU_TYPES.register(
            "caminite_forge",
            () -> IMenuTypeExtension.create(CaminiteForgeMenu::new)
    );

    public static final DeferredHolder<MenuType<?>, MenuType<CaminiteForgeAlloyMenu>> CAMINITE_FORGE_ALLOY = MENU_TYPES.register(
            "caminite_forge_alloy",
            () -> IMenuTypeExtension.create(CaminiteForgeAlloyMenu::new)
    );

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}