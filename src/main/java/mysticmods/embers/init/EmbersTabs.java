package mysticmods.embers.init;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static mysticmods.embers.EmbersMod.CREATIVE_MODE_TABS;

public class EmbersTabs {

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
                output.accept(EmbersItems.DAWNSTONE_INGOT.get());
                output.accept(EmbersItems.EMBER_CRYSTAL.get());
                output.accept(EmbersItems.QUICKSILVER.get());
                output.accept(EmbersItems.VERMILLIONITE_CHUNK.get());

                //Blocks
                output.accept(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM.get());
                output.accept(EmbersItems.BRAZIER_BLOCK_ITEM.get());
            }).build());

    public static void init() {
    }

}
