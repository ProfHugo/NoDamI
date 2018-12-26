package profhugo.nodami;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import profhugo.nodami.proxy.CommonProxy;

@Mod(modid = NoDamI.MODID, version = NoDamI.VERSION, acceptableRemoteVersions = "*")
public class NoDamI
{
	public static final String MODID = "nodami";
	public static final String NAME = "No Damage Immunity";
	public static final String VERSION = "1.0";

	@Mod.Instance(MODID)
	public static NoDamI instance;

	@SidedProxy(serverSide = "profhugo.nodami.proxy.CommonProxy", clientSide = "profhugo.nodami.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(NAME + " is loading!");
		proxy.preInit();

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
}
