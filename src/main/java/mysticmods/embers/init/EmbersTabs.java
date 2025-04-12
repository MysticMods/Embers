package mysticmods.embers.init;

import mysticmods.embers.Embers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersTabs {


    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Embers.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EMBERS_CREATIVE_TAB = CREATIVE_MODE_TABS.register("embers_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.embers"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EmbersItems.ASHEN_STEEL_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                //Items
                output.accept(EmbersItems.ASHEN_STEEL_INGOT.get());
                output.accept(EmbersItems.CAMINITE_BLEND.get());
                output.accept(EmbersItems.CAMINITE_BRICK.get());
                output.accept(EmbersItems.CINDERSTEEL_INGOT.get());
                output.accept(EmbersItems.CINNABAR.get());
                output.accept(EmbersItems.COPPER_NUGGET.get());
                output.accept(EmbersItems.CRYSTAL_EMBER_SEED.get());
                output.accept(EmbersItems.DAWNSTONE_INGOT.get());
                output.accept(EmbersItems.DAWNSTONE_NUGGET.get());
                output.accept(EmbersItems.EMBER_CRYSTAL.get());
                output.accept(EmbersItems.EMBER_SHARD.get());
                output.accept(EmbersItems.QUICKSILVER.get());
                output.accept(EmbersItems.SMOLDERING_CRYSTAL_BLEND.get());
                output.accept(EmbersItems.VERMILLIONITE_CHUNK.get());
                output.accept(EmbersItems.IRON_HAMMER.get());

                //Blocks
                output.accept(EmbersItems.BRAZIER_BLOCK_ITEM.get());
                output.accept(EmbersItems.BUDDING_EMBER_BLOCK_ITEM.get());
                output.accept(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM.get());
                output.accept(EmbersItems.CAMINITE_FORGE_BLOCK_ITEM.get());
                output.accept(EmbersItems.CAMINITE_MOLD_BLOCK_ITEM.get());
                output.accept(EmbersItems.COPPER_ANVIL_BLOCK_ITEM.get());
                output.accept(EmbersItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.get());
            }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

}
