package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item.Properties> EIG = () -> new Item.Properties().group(Embers.GROUP);
    public static final Supplier<Item.Properties> EMG = () -> new Item.Properties().group(Embers.GROUP);
    
    public static RegistryObject<AxeItem> CAMINITE_AXE = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_axe", Embers.REGISTRY.axe(AxeItem::new, ModMaterials.CAMINITE, EMG));
    public static RegistryObject<HoeItem> CAMINITE_HOE = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_hoe", Embers.REGISTRY.hoe(HoeItem::new, ModMaterials.CAMINITE, EMG));
    public static RegistryObject<PickaxeItem> CAMINITE_PICKAXE = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_pickaxe", Embers.REGISTRY.pickaxe(PickaxeItem::new, ModMaterials.CAMINITE, EMG));
    public static RegistryObject<ShovelItem> CAMINITE_SHOVEL = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_shovel", Embers.REGISTRY.shovel(ShovelItem::new, ModMaterials.CAMINITE, EMG));
    public static RegistryObject<SwordItem> CAMINITE_SWORD = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_sword", Embers.REGISTRY.sword(SwordItem::new, ModMaterials.CAMINITE, EMG));

    public static RegistryObject<ArmorItem> CAMINITE_HELMET = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_helmet", Embers.REGISTRY.armor(ArmorItem::new, ModMaterials.CAMINITE, EquipmentSlotType.HEAD, EMG));
    public static RegistryObject<ArmorItem> CAMINITE_CHESTPLATE = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_chestplate", Embers.REGISTRY.armor(ArmorItem::new, ModMaterials.CAMINITE, EquipmentSlotType.CHEST, EMG));
    public static RegistryObject<ArmorItem> CAMINITE_LEGGINGS = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_leggings", Embers.REGISTRY.armor(ArmorItem::new, ModMaterials.CAMINITE, EquipmentSlotType.LEGS, EMG));
    public static RegistryObject<ArmorItem> CAMINITE_BOOTS = Embers.REGISTRY.registerItem(ModMaterials.CAMINITE.getInternalName() + "_boots", Embers.REGISTRY.armor(ArmorItem::new, ModMaterials.CAMINITE, EquipmentSlotType.FEET, EMG));


}
