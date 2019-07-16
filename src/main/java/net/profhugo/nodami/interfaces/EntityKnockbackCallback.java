package net.profhugo.nodami.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface EntityKnockbackCallback {
	Event<EntityKnockbackCallback> EVENT = EventFactory.createArrayBacked(EntityKnockbackCallback.class,
			(listeners) -> (entity, source, amp, dx, dz) -> {
				for (EntityKnockbackCallback event : listeners) {
					ActionResult result = event.takeKnockback(entity, source, amp, dx, dz);
					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	ActionResult takeKnockback(LivingEntity entity, Entity source, float amp, double dx, double dz);
}