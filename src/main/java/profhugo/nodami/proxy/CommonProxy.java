package profhugo.nodami.proxy;

import net.minecraftforge.common.MinecraftForge;
import profhugo.nodami.config.NodamiConfig;
import profhugo.nodami.handlers.EntityHandler;

public class CommonProxy {

	public void preInit() {
		NodamiConfig.preInit();
	}

	public void init() {
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
	}

	public void postInit() {

	}

}
