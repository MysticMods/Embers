package mysticmods.embers.init;

import mysticmods.embers.Embers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Embers.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EMBERS_CREATIVE_TAB = CREATIVE_MODE_TABS.register("embers_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.embers"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItems.ASHEN_STEEL_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                //Items
                output.accept(ModItems.ASHEN_STEEL_INGOT.get());
                output.accept(ModItems.CAMINITE_BLEND.get());
                output.accept(ModItems.CAMINITE_BRICK.get());
                output.accept(ModItems.CINDERSTEEL_INGOT.get());
                output.accept(ModItems.CINNABAR.get());
                output.accept(ModItems.COPPER_NUGGET.get());
                output.accept(ModItems.CRYSTAL_EMBER_SEED.get());
                output.accept(ModItems.DAWNSTONE_INGOT.get());
                output.accept(ModItems.DAWNSTONE_NUGGET.get());
                output.accept(ModItems.EMBER_CRYSTAL.get());
                output.accept(ModItems.EMBER_SHARD.get());
                output.accept(ModItems.QUICKSILVER.get());
                output.accept(ModItems.SMOLDERING_CRYSTAL_BLEND.get());
                output.accept(ModItems.VERMILLIONITE_CHUNK.get());
                output.accept(ModItems.IRON_HAMMER.get());

                //Blocks
                output.accept(ModItems.BRAZIER_BLOCK_ITEM.get());
                output.accept(ModItems.BUDDING_EMBER_BLOCK_ITEM.get());
                output.accept(ModItems.CAMINITE_BRICK_BLOCK_ITEM.get());
                output.accept(ModItems.CAMINITE_FORGE_BLOCK_ITEM.get());
                output.accept(ModItems.CAMINITE_MOLD_BLOCK_ITEM.get());
                output.accept(ModItems.CAMINITE_SMELTER_BLOCK_ITEM.get());
                output.accept(ModItems.COPPER_ANVIL_BLOCK_ITEM.get());
                output.accept(ModItems.EMBER_CLUSTER_BLOCK_ITEM.get());
                output.accept(ModItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.get());
            }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

}
