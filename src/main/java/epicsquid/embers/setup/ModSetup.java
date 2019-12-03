package epicsquid.embers.setup;

import epicsquid.embers.client.EmbersBlockstateProvider;
import epicsquid.embers.client.EmbersItemModelProvider;
import epicsquid.embers.client.EmbersLangProvider;
import epicsquid.mysticalworld.client.data.MWLangProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {

	public void init(FMLCommonSetupEvent event) {

	}

	public void gatherData (GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		if (event.includeClient()) {
			gen.addProvider(new EmbersBlockstateProvider(gen, event.getExistingFileHelper()));
			gen.addProvider(new EmbersItemModelProvider(gen, event.getExistingFileHelper()));
			gen.addProvider(new EmbersLangProvider(gen));
		}
		if (event.includeServer()) {
		}
	}
}
