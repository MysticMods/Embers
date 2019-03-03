package epicsquid.embers;

import epicsquid.embers.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Embers.MODID, version = Embers.VERSION, name = Embers.NAME, dependencies = Embers.DEPENDENCIES)
public class Embers {
  public static final String MODID = "embers-2";
  public static final String DOMAIN = "embers";
  public static final String NAME = "Embers";
  public static final String VERSION = "@VERSION@";
  public static final String DEPENDENCIES = "required-before:mysticallib;required-before:mysticalworld@";

  public static ModContainer CONTAINER = null;

  @SidedProxy(clientSide = "epicsquid.embers.proxy.ClientProxy", serverSide = "epicsquid.embers.proxy.CommonProxy")
  public static CommonProxy proxy;

  @Instance(MODID) public static Embers instance;

//  public static CreativeTabs tab = new CreativeTabs("embers") {
//    @Override
//    public String getTabLabel() {
//      return "embers";
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public ItemStack getTabIconItem() {
//      return new ItemStack(ModItems.carapace, 1);
//    }
//  };

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    CONTAINER = Loader.instance().activeModContainer();
    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    proxy.preInit(event);
  }

  public static Embers getInstance() {
    return instance;
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
