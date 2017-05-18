# NoDamI
This mod aims to improve PvE and PvP by removing the redundant damage immunity system implemeted by Mojang long ago (more info: http://minecraft.gamepedia.com/Damage#Damage_immunity)

## Current Features:
### Removes damage immunity granted after taking damage
  - If an entity takes any damage in Minecraft, that entity would be immune to damage for 0.5 seconds (10 ticks) unless the damage dealt during immunity is higher than the hit that granted it
  - This system was in place so things won't die instantly to spam clicking pre 1.9
  - However, in 1.9, since a proper attack speed system got implemented, landing hit matters a lot more, and the damage immunity system made it very clunky for multiple player (or zombies) to gang up on something
    - Boss fights became very clunky, as one player would try to land a hit, only for it to be nullified because some guy with an 20+ damage axe landed it first
    - The original purpose of the damage immunity is now fulfilled by this new attack speed system
    - This mod completely removes this old system by setting the damage immunity timer to 0 on update, and to compensate for any potential one-shots due to DOTs that relied on the old system to be balanced (such as lava and cactus, but there are more), the damage is cut down by 20 times per tick to match the old values.

## Supported Versions:
	- 1.10.2
	- 1.11.2