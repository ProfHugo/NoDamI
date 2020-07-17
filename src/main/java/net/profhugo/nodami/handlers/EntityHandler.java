package net.profhugo.nodami.handlers;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.profhugo.nodami.config.NodamiConfig;

public class EntityHandler {

	private static UUID debugUUID = new UUID(0L, 0L);
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityHurt(LivingHurtEvent event) {
		if (!event.isCanceled()) {
			LivingEntity entity = event.getEntityLiving();
			if (entity.getEntityWorld().isRemote()) {
				return;
			}
			DamageSource source = event.getSource();
			Entity trueSource = source.getTrueSource();
			ResourceLocation trueSourceloc = trueSource != null ? EntityType.getKey(trueSource.getType()) : null;
			if (NodamiConfig.MISC.debugMode && entity instanceof PlayerEntity) {
				String trueSourceName;
				if (trueSource != null && trueSourceloc != null) {
					trueSourceName = trueSourceloc.toString();
				} else {
					trueSourceName = "null";
				}
				String message = String.format("Type of damage received: %s\nAmount: %.3f\nTrue Source (mob id): %s\n",
						source.getDamageType(), event.getAmount(), trueSourceName);
				((PlayerEntity) entity).sendMessage(new StringTextComponent(message), debugUUID);
			}
			
			if (NodamiConfig.CORE.excludePlayers && entity instanceof PlayerEntity) {
				return;
			}
			if (NodamiConfig.CORE.excludeAllMobs && !(entity instanceof PlayerEntity)) {
				return;
			}
			ResourceLocation loc = EntityType.getKey(entity.getType());
			if (loc != null && NodamiConfig.EXCLUSIONS.dmgReceiveExcludedEntities.contains(loc.toString())) {
				return;
			}
			// May have more DoTs missing in this list
			// Not anymore (/s)
			if (NodamiConfig.EXCLUSIONS.damageSrcWhitelist.contains(source.getDamageType())) {
				return;
			}
			
			// Mobs that do damage on collusion but have no attack timer
			if (trueSource != null) {
				if (trueSourceloc != null && NodamiConfig.EXCLUSIONS.attackExcludedEntities.contains(trueSourceloc.toString())) {
					return;
				}
			}
			
			// known issue: values <=10 are quantized to 0. idk why
			entity.hurtResistantTime = NodamiConfig.CORE.iFrameInterval;
			
		}

	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onPlayerAttack(AttackEntityEvent event) {
		if (!event.isCanceled()) {
			PlayerEntity player = event.getPlayer();
			if (player.getEntityWorld().isRemote()) {
				return;
			}
			float str = player.getCooledAttackStrength(0);
			if (str <= NodamiConfig.THRESHOLDS.attackCancelThreshold) {
				event.setCanceled(true);
				return;
			}
			if (str <= NodamiConfig.THRESHOLDS.knockbackCancelThreshold) {
				Entity target = event.getTarget();
				// Don't worry, it's only magic
				if (target != null && target instanceof LivingEntity) {
					((LivingEntity)target).isSwingInProgress = true;
				}
			}
		}
	}


	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onKnockback(LivingKnockBackEvent event) {
		if (!event.isCanceled()) {
			LivingEntity entity = event.getEntityLiving();
			if (entity.isSwingInProgress) {
				event.setCanceled(true);
				entity.isSwingInProgress = false;
			}
		}
		
	}

}
