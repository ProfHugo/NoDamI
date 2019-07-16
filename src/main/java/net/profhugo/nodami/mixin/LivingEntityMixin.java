package net.profhugo.nodami.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import net.profhugo.nodami.interfaces.EntityHurtCallback;
import net.profhugo.nodami.interfaces.EntityKnockbackCallback;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(at = @At("TAIL"), method = "applyDamage", cancellable = true)
	private void onEntityHurt(final DamageSource source, final float amount, CallbackInfo info) {
		ActionResult result = EntityHurtCallback.EVENT.invoker().hurtEntity((LivingEntity) (Object) this, source,
				amount);
		if (result == ActionResult.FAIL) {
			info.cancel();
		}
	}

	@Inject(at = @At("HEAD"), method = "takeKnockback", cancellable = true)
	private void onTakingKnockback(final Entity source, float amp, double dx, double dz, CallbackInfo info) {
		ActionResult result = EntityKnockbackCallback.EVENT.invoker().takeKnockback((LivingEntity) (Object) this,
				source, amp, dx, dz);
		if (result == ActionResult.FAIL) {
			info.cancel();
		}

	}
}
