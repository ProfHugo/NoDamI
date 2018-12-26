package profhugo.nodami.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class NodamiConfig {

	private static Configuration config = null;

	public static float attackCancelThreshold, knockbackCancelThreshold;
	
	public static String[] attackExcludedEntities, dmgReceiveExcludedEntities;
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "nodami.cfg");
		config = new Configuration(configFile);
		syncConfig();

	}

	public static Configuration getConfig() {
		return config;
	}
	
	private static void syncConfig() {
		attackCancelThreshold = config.getFloat("attackCancelThreshold", "Thresholds", 0.1f, -0.1f, 1, "How weak a player's attack can be before it gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, players cannot attack), or -0.1 (disables this feature)");
		knockbackCancelThreshold = config.getFloat("knockbackCancelThreshold", "Thresholds", 0.75f, -0.1f, 1, "How weak a player's attack can be before the knockback gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, no knockback), or -0.1 (disables this feature)");
		attackExcludedEntities = config.getStringList("attackExcludedEntities", "Exclusions", new String[] {"minecraft:slime", "tconstruct:blueslime", "thaumcraft:thaumslime"}, "List of entities that need to give iFrames on attacking");
		dmgReceiveExcludedEntities = config.getStringList("damageReceiveExcludedEntities", "Exclusions", new String[0], "List of entities that need to receive iFrames on receiving attacks or relies on iFrames");
	
		if (config.hasChanged()) {
            config.save();
        }
	}

}
