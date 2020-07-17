package profhugo.nodami.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import profhugo.nodami.config.NodamiConfig;

public class EntityHandler {

	@SubscribeEvent(priority = EventPriority.LOW)
	public void onEntityHurt(LivingHurtEvent event) {
		if (!event.isCanceled()) {
			EntityLivingBase entity = event.getEntityLiving();
			if (entity.getEntityWorld().isRemote) {
				return;
			}
			DamageSource source = event.getSource();
			Entity trueSource = source.getTrueSource();
			if (NodamiConfig.debugMode && entity instanceof EntityPlayer) {
				String trueSourceName;
				if (trueSource != null && EntityList.getKey(trueSource.getClass()) != null) {
					trueSourceName = EntityList.getKey(trueSource.getClass()).toString();
				} else {
					trueSourceName = "null";
				}
				String message = String.format("Type of damage received: %s\nAmount: %.3f\nTrue Source (mob id): %s\n",
						source.getDamageType(), event.getAmount(), trueSourceName);
				((EntityPlayer) entity).sendMessage(new TextComponentString(message));
			}
			
			if (NodamiConfig.excludePlayers && entity instanceof EntityPlayer) {
				return;
			}
			
			if (NodamiConfig.excludeAllMobs && !(entity instanceof EntityPlayer)) {
				return;
			}
			
			ResourceLocation loc = EntityList.getKey(entity.getClass());
			if (loc != null && NodamiConfig.dmgReceiveExcludedEntities.contains(loc.toString())) {
				return;
			}
			
			// May have more DoTs missing in this list
			// Not anymore (/s)
			if (NodamiConfig.damageSrcWhitelist.contains(source.getDamageType())) {
				return;
			}

			// Mobs that do damage on collusion but have no attack timer
			if (trueSource != null) {
				loc = EntityList.getKey(trueSource.getClass());
				if (loc != null && NodamiConfig.attackExcludedEntities.contains(loc.toString())) {
					return;
				}
			}
			
			
			entity.hurtResistantTime = NodamiConfig.iFrameInterval;
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerAttack(AttackEntityEvent event) {
		if (!event.isCanceled()) {
			EntityPlayer player = event.getEntityPlayer();
			if (player.getEntityWorld().isRemote) {
				return;
			}
			float str = player.getCooledAttackStrength(0);
			if (str <= NodamiConfig.attackCancelThreshold) {
				event.setCanceled(true);
				return;
			}
			if (str <= NodamiConfig.knockbackCancelThreshold) {
				// Don't worry, it's only magic
				player.hurtTime = -1;
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onKnockback(LivingKnockBackEvent event) {
		if (!event.isCanceled()) {
			Entity attacker = event.getAttacker();
			if (attacker != null && !attacker.getEntityWorld().isRemote) {
				// IT'S ONLY MAGIC
				if (attacker instanceof EntityPlayer && ((EntityPlayer) attacker).hurtTime == -1) {
					event.setCanceled(true);
					((EntityPlayer) attacker).hurtTime = 0;
				}
			}
		}
	}

}
