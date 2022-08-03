package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class ModFluids {
    private static final Registrate REGISTRATE = Embers.registrate();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_IRON = noBucketMolten("molten_iron", 0xFFC70039);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GOLD = noBucketMolten("molten_gold", 0xFFFFD700);
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_COPPER = noBucketMolten("molten_copper", 0xFFb87333);

    private static FluidEntry<ForgeFlowingFluid.Flowing> noBucketMolten(String name, int color){
        return REGISTRATE.fluid(name,new ResourceLocation(Embers.MODID,"block/fluid/molten/flowing"), new ResourceLocation(Embers.MODID,"block/fluid/molten/still"))
                .attributes(a -> a.luminosity(15).color(color))
                .properties(ForgeFlowingFluid.Properties::canMultiply)
                .noBucket()
//            .bucket()
//                .model((ctx, prov) -> prov.withExistingParent(ctx.getName(), prov.mcLoc("item/water_bucket")))
//                .build()
//            .removeTag(FluidTags.WATER)
                .register();
    }


    public static void classload() { }
}
