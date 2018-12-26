package profhugo.nodami.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import profhugo.nodami.config.NodamiConfig;

public class EntityHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityHurt(LivingHurtEvent event) {
		if (!event.isCanceled()) {
			EntityLivingBase entity = event.getEntityLiving();
			if (entity.getEntityWorld().isRemote) {
				return;
			}
			if (NodamiConfig.excludePlayers && entity instanceof EntityPlayer) {
				return;
			}
			if (NodamiConfig.excludeAllMobs && !(entity instanceof EntityPlayer)) {
				return;
			}
			for (String id : NodamiConfig.dmgReceiveExcludedEntities) {
				ResourceLocation loc = EntityList.getKey(entity.getClass());
				if (loc == null)
					break;
				int starIndex = id.indexOf('*');
				if (starIndex != -1) {
					if (loc.toString().indexOf(id.substring(0, starIndex)) != -1) {
						return;
					}
				} else if (loc.toString().equals(id)) {
					return;
				}
			}
			DamageSource source = event.getSource();
			// May have more DoTs missing in this list
			if (NodamiConfig.enviromentalHazardsBalancing && (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA)
					|| source.equals(DamageSource.CACTUS) || source.equals(DamageSource.LIGHTNING_BOLT)
					|| source.equals(DamageSource.IN_WALL))) {
				return;
			}
			
			// Mobs that do damage on collusion but have no attack timer
			for (String id : NodamiConfig.attackExcludedEntities) {
				if (source.getTrueSource() == null)
					break;
				ResourceLocation loc = EntityList.getKey(source.getTrueSource().getClass());
				if (loc == null)
					break;
				int starIndex = id.indexOf('*');
				if (starIndex != -1) {
					if (loc.toString().indexOf(id.substring(0, starIndex)) != -1) {
						return;
					}
				} else if (loc.toString().equals(id)) {
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
