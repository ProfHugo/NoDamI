package profhugo.nodami.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onEntityHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource source = event.getSource();
		//May have more DoTs missing in this list
		if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.CACTUS)
				|| source.equals(DamageSource.LIGHTNING_BOLT) || source.equals(DamageSource.IN_WALL)) {
			event.setAmount(event.getAmount() / 10);
		}
		entity.hurtResistantTime = 0;
		entity.hurtTime = 1;

	}

}
