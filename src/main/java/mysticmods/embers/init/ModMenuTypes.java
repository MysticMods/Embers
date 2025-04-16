package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeAlloyMenu;
import mysticmods.embers.machines.caminite_smelter.menu.CaminiteSmelterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Embers.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CaminiteSmelterMenu>> CAMINITE_SMELTER = MENU_TYPES.register(
            "caminite_smelter",
            () -> IMenuTypeExtension.create(CaminiteSmelterMenu::new)
    );

    public static final DeferredHolder<MenuType<?>, MenuType<CaminiteForgeAlloyMenu>> CAMINITE_FORGE = MENU_TYPES.register(
            "caminite_forge",
            () -> IMenuTypeExtension.create(CaminiteForgeAlloyMenu::new)
    );

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}