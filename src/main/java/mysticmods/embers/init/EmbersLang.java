package mysticmods.embers.init;

import mysticmods.embers.Embers;
import com.tterrag.registrate.Registrate;
import net.minecraft.network.chat.MutableComponent;

public class EmbersLang {

	private static final Registrate REGISTRATE = Embers.registrate();

	public static final MutableComponent CREATIVE_TAB = REGISTRATE.addRawLang("itemGroup." + Embers.MOD_ID, "Embers");

	public static void init() {

	}

}