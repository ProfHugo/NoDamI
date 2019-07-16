package net.profhugo.nodami.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerAttackCallback {
	Event<PlayerAttackCallback> EVENT = EventFactory.createArrayBacked(PlayerAttackCallback.class,
			(listeners) -> (player, target) -> {
				for (PlayerAttackCallback event : listeners) {
					ActionResult result = event.attackEntity(player, target);
					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.PASS;
			});

	ActionResult attackEntity(PlayerEntity player, Entity target);

}
