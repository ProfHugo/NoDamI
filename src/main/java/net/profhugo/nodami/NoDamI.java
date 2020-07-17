package net.profhugo.nodami;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.profhugo.nodami.config.NodamiConfig;
import net.profhugo.nodami.handlers.EntityHandler;

@Mod(NoDamI.MODID)
public class NoDamI {

	public static final String MODID = "nodami";

	public static NoDamI instance;

	// public static CommonProxy proxy = DistExecutor.runForDist(() ->
	// ClientProxy::new, () -> CommonProxy::new);

	public static Logger logger = LogManager.getLogger(MODID);

	public static IEventBus modEventBus;
	
	public NoDamI() {
		logger.info("NoDamI constructed!");

		logger.info("NoDamI is on preInit!");
		
		modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		// register the config
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NodamiConfig.SPEC);
		
		// Register the setup method for modloading
		modEventBus.addListener(this::serverInit);

		// Register ourselves for server and other 	game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

	}

	@SubscribeEvent
	public void serverInit(FMLServerStartingEvent event) {
		logger.info("NodamI: Serverside operations started.");
		NodamiConfig.cacheValues();
		MinecraftForge.EVENT_BUS.register(new EntityHandler());
		modEventBus.register(NodamiConfig.class);
	}

}
