package net.profhugo.nodami.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.yaml.snakeyaml.Yaml;

import net.fabricmc.loader.api.FabricLoader;

public class NodamiConfig {
	
	public static int iFrameInterval;
	public static boolean excludePlayers, excludeAllMobs, debugMode;
	public static float attackCancelThreshold, knockbackCancelThreshold;
	
	public static String[] attackExcludedEntities, dmgReceiveExcludedEntities, damageSrcWhitelist;
	
	private static class ConfigCarrier {
		public int iFrameInterval;
		public boolean excludePlayers, excludeAllMobs, debugMode;
		public float attackCancelThreshold, knockbackCancelThreshold;
		public String[] attackExcludedEntities, dmgReceiveExcludedEntities, damageSrcWhitelist;
		
		private void setToDefault() {
			iFrameInterval = 0;
			excludePlayers = false;
			excludeAllMobs = false;
			attackCancelThreshold = 0.1f;
			knockbackCancelThreshold = 0.75f;
			attackExcludedEntities = new String[] {"minecraft:slime", "tconstruct:blueslime", "thaumcraft:thaumslime"};
			dmgReceiveExcludedEntities = new String[] {};
			damageSrcWhitelist = new String[] {"inFire", "lava", "cactus", "lightningBolt", "inWall", "hotFloor"};
			debugMode = false;
		}
	}
	
	public static void preInit() {
		File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "nodami.cfg");
		readConfig(configFile);
	}
	
	private static void setToCarrier(ConfigCarrier carrier) {
		iFrameInterval = carrier.iFrameInterval;
		excludePlayers = carrier.excludePlayers;
		excludeAllMobs = carrier.excludeAllMobs;
		attackCancelThreshold = carrier.attackCancelThreshold;
		knockbackCancelThreshold = carrier.knockbackCancelThreshold;
		attackExcludedEntities = carrier.attackExcludedEntities.clone();
		dmgReceiveExcludedEntities = carrier.dmgReceiveExcludedEntities.clone();
		damageSrcWhitelist = carrier.damageSrcWhitelist.clone();
		debugMode = carrier.debugMode;
	}

	private static void setToDefault() {
		ConfigCarrier temp = new ConfigCarrier();
		temp.setToDefault();
		setToCarrier(temp);
	}

	private static void readConfig(File configFile) {
		Yaml yamlParser = new Yaml();
		if (configFile.exists()) {
			System.out.println("NoDamI: Found existing config file. Reading...");
			FileInputStream fStream;
			try {
				fStream = new FileInputStream(configFile);
				ConfigCarrier cfg = yamlParser.load(fStream);
				setToCarrier(cfg);
				fStream.close();
				
				String dump = yamlParser.dump(cfg);
				int firstEntryIndex = dump.indexOf("attackCancelThreshold");
				if (dump.indexOf("attackCancelThreshold") > 0 || dump.indexOf("attackCancelThreshold") < dump.length()) {
					System.out.println("NoDamI: Done reading config files. Here are the loaded settings:");
					System.out.println("/n" + dump.substring(firstEntryIndex));
				} else {
					System.out.println("NoDamI: Warning, config file may potentially be corrupted. Loading default values.");
					setToDefault();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("NoDamI: Caught exception while reading config file. Loading default values.");
				setToDefault();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("NoDamI: Config file was potentially corrupted. ");
				setToDefault();
			}
		} else {
			System.out.println("NoDamI: Did not found config file. Writing first time config file...");
			ConfigCarrier firstTime = new ConfigCarrier();
			firstTime.setToDefault();
			try {
				FileWriter writer = new FileWriter(configFile);
				writer.write(yamlParser.dump(firstTime));
				writer.close();
				
				System.out.println("NoDamI: Done writing first time config file.");
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("NoDamI: Caught exception while writing first time config file. Loading default values.");
				setToDefault();
			}
			
		}
		/*
		iFrameInterval = getInt("iFrameInterval", "core", 0, 0, Integer.MAX_VALUE, "How many ticks of i-frames does an entity get when damaged, from 0 (default), to 2^31-1 (nothing can take damage)");
		excludePlayers = getBoolean("excludePlayers", "core", false, "Are players excluded from this mod (if true, players will always get 10 ticks of i-frames on being damaged");
		excludeAllMobs = getBoolean("excludeAllMobs", "core", false, "Are all mobs excluded from this mod (if true, mobs will always get 10 ticks of i-farmes on being damaged");
		
		attackCancelThreshold = getFloat("attackCancelThreshold", "thresholds", 0.1f, -0.1f, 1, "How weak a player's attack can be before it gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, players cannot attack), or -0.1 (disables this feature)");
		knockbackCancelThreshold = getFloat("knockbackCancelThreshold", "thresholds", 0.75f, -0.1f, 1, "How weak a player's attack can be before the knockback gets nullified, from 0 (0%, cancels multiple attacks on the same tick) to 1 (100%, no knockback), or -0.1 (disables this feature)");
		
		attackExcludedEntities = getStringList("attackExcludedEntities", "exclusions", new String[] {"minecraft:slime"}, "List of entities that need to give i-frames on attacking");
		dmgReceiveExcludedEntities = getStringList("damageReceiveExcludedEntities", "exclusions", new String[0], "List of entities that need to receive i-frames on receiving attacks or relies on iFrames");
		damageSrcWhitelist = getStringList("dmgSrcWhitelist", "exclusions", new String[] {"inFire", "lava", "cactus", "lightningBolt", "inWall", "hotFloor"}, "List of damage sources that need to give i-frames on doing damage (ex: lava).");
		
		debugMode = getBoolean("debugMode", "misc", false, "If true, turns on feature which sends a message when a player receives damage, containing information such as the name of the source and the quantity. Use this to find the name of the source you need to whitelist, or the id of the mob you want to exclude.");
		*/
		
	}
}
