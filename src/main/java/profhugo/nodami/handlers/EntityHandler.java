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
		if (source.equals(DamageSource.inFire) || source.equals(DamageSource.lava) || source.equals(DamageSource.cactus)
				|| source.equals(DamageSource.lightningBolt) || source.equals(DamageSource.inWall)) {
			event.setAmount(event.getAmount() / 20);
		}
		entity.hurtResistantTime = 0;
		entity.hurtTime = 1;

	}

}
