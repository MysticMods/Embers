package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import epicsquid.mysticallib.material.MaterialType;
import net.minecraft.util.SoundEvents;

public class ModMaterials {

    public static MaterialType CAMINITE = new MaterialType("caminite").itemMaterial(300, 8.0f, 3.0f, 2, 14).armorMaterial(33, new int[]{3, 6, 8, 3}, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f).setMinXP(1).setMaxXP(4).setModId(Embers.MODID).putDamageSpeed(
            MaterialType.Type.AXE, 6.0f, -3.1f);

}
