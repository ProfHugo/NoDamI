# NoDamI

## About:
This mod aims to improve PvE and PvP by removing/changing the redundant damage immunity system implemeted by Mojang long ago, without breaking the game.
(more info on damage immunity here:http://minecraft.gamepedia.com/Damage#Damage_immunity)

 

## Features:

- Damage events in Minecraft no longer give i-frames by default.

    - Enviromental hazards that relies on i-frames to be balanced (ex: lava) still gives i-frames.

    - Mobs that relies on i-frames to do balanced damage (ex: the slime family) still gives i-frames (by default, see below).

    -  Attacks that are charged 10% or less are canceled by default to prevent glass cannon playstyles.

    - Attacks that are charged 75% or less does not knockback by default.

 

## Config:

This mod generates a config file for users to change parts of the mod as they see fit. There are config options for the following:

- Toggles for core functionalities of this mod

- List of entities that need to give iFrames on attacking

- List of entities that need to receive iFrames on receiving attacks

- How much does players' attacks need to be charged to do anything (default: 10%, or 0.1)

- How much does players' attacks need to be charged to have knockback (default: 75%, or 0.75)

 

To add an entity to either of the two lists, you need to use its savegame id (modname:mobname). For example, the first list, by default, has vanilla Minecraft's slime (minecraft:slime), TCon's blue slime (tconstruct:blueslime), and Thaumcraft's thaumic slime (thaumcraft:thaumslime).

 

The fastest way to find a mob's id is by looking at it with Hwyla and Wawla installed. Alternatively, you can also try to summon it using console command and trying to auto complete its id as you're doing it to find its id.

 

In addition, you can also add an entire mod's entities by adding in its modid, followed by : and *. For example, adding thaumcraft:* excludes all entities in Thaumcraft from giving or receiving i-frames (depending on which list you added it to). grimoireofgaia:* is added to the "give list" by default.

 

##But Why?

Before 1.9, players used to be able to attack pretty much whenever they wanted, and to pervent insta-kills, Mojang implemented a system of conditional damage immunity where, after taking damage, the entity would be immune to any further damage of equal or lesser amounts for 0.5 seconds, and if the incoming damage is higher, the difference gets counted. Therefor, damage output was only limited to how fast you could spam click and get damage in whenever something is out of i-frames. 

 

While this worked very well in keeping things balanced, with the introductions of attack timers in 1.9, landing hits become much more important, and missing out on a hit or two could easily mean losing the fight. Damage immunity quickly becomes an issue with players have weapons with higher attack speeds (above 2.0), as players would be punished for timing their hits properly, which shouldn't be the case. In addition, in non-1v1 situations, if one member were to land a hit at the same moment as another, a huge amount of damage would be lost to damage immunity frames, which could be very disadvantageous for the party that tries to zone in on a single target. 

 

Often times in vanilla Minecraft, charging into a pack of mobs isn't very risky, as you can only take damage once every half a second, meaning you could quickly mow down the enemies while tanking the bulk of the hits in i-frames. By removing damage immunity, everything would hit you, and by everything, I mean everything. Being ganged up on is very devastating without damage immunity, as every mob would now be able to land a hit on you. This encourages players to be more careful in engaging their enemies, both in PvP and PvE, as being outnumbered often means that the "lone-wolf" is dead. 

In conclusion, insta-gibs by spam-clicking is no longer an issue in combat, with the introduction of attack timers, and the obstructions that the previously implemented damage immunity system introduced post-1.9 made combat clunky and unintuitive. Removing it make sures that every single hit counts in the game, and smooths out the overall combat experience while making things more challanging.

 

PS: To top that all off, most gun mods doesn't even function properly with damage immunity, since only 2 pellets can hit per second. If you want your machine guns and shotguns to actually do damage, this mod is for you.

 

 

##Modpack Policy:

You can use this mod however you want. However, please put the name of this mod and my name onto the modlist of the modpack. While not needed, it'd be nice to let me know if you're going to use this in a modpack, especially if it's not hosted on Curseforge.

## Supported Versions:
	- 1.7.10
	- 1.12.2

