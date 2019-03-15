package profhugo.nodami.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;

public class NodamiConfig {

	private static Configuration config = null;

	public static int iFrameInterval;
	public static boolean excludePlayers, excludeAllMobs, debugMode;
	public static float attackCancelThreshold, knockbackCancelThreshold;
	
	public static String[] attackExcludedEntities, dmgReceiveExcludedEntities, damageSrcWhitelist;
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "nodami.cfg");
		config = new Configuration(configFile);
		syncConfig();

	}

	public static Configuration getConfig() {
		return config;
	}
	
	private static void syncConfig() {
		iFrameInterval = config.getInt("iFrameInterval", "core", 0, 0, Integer.MAX_VALUE, "How many ticks of i-frames does an entity get when damaged, from 0 (default), to 2^31-1 (nothing can take damage)");
		excludePlayers = config.getBoolean("excludePlayers", "core", false, "Are players excluded from this mod (if true, players will always get 10 ticks of i-frames on being damaged");
		excludeAllMobs = config.getBoolean("excludeAllMobs", "core", false, "Are all mobs excluded from this mod (if true, mobs will always get 10 ticks of i-farmes on being damaged");
		
		attackCancelThreshold = config.getFloat("attackCancelThreshold", "thresholds", 0.1f, -0.1f, 1, "How weak a player's attack can be before it gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, players cannot attack), or -0.1 (disables this feature)");
		knockbackCancelThreshold = config.getFloat("knockbackCancelThreshold", "thresholds", 0.75f, -0.1f, 1, "How weak a player's attack can be before the knockback gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, no knockback), or -0.1 (disables this feature)");
		
		attackExcludedEntities = config.getStringList("attackExcludedEntities", "exclusions", new String[] {"minecraft:slime", "tconstruct:blueslime", "thaumcraft:thaumslime", "grimoireofgaia:*"}, "List of entities that need to give i-frames on attacking");
		dmgReceiveExcludedEntities = config.getStringList("damageReceiveExcludedEntities", "exclusions", new String[0], "List of entities that need to receive i-frames on receiving attacks or relies on iFrames");
		damageSrcWhitelist = config.getStringList("dmgSrcWhitelist", "exclusions", new String[] {"inFire", "lava", "cactus", "lightningBolt", "inWall", "hotFloor"}, "List of damage sources that need to give i-frames on doing damage (ex: lava).");
		
		debugMode = config.getBoolean("debugMode", "misc", false, "If true, turns on feature which sends a message when a player receives damage, containing information such as the name of the source and the quantity. Use this to find the name of the source you need to whitelist, or the id of the mob you want to exclude.");
		
		if (config.hasChanged()) {
            config.save();
        }
	}

}
