package mysticmods.embers.datagen;

import mysticmods.embers.init.EmbersItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public class EmbersModelProvider extends ModelProvider {

    public EmbersModelProvider(PackOutput output) {
        super(output, "embers");
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        EmbersItems.registerModels(blockModels, itemModels);
    }
}
