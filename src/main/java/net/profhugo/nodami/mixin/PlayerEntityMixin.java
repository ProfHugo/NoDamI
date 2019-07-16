package net.profhugo.nodami.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.profhugo.nodami.interfaces.EntityHurtCallback;
import net.profhugo.nodami.interfaces.PlayerAttackCallback;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	
	@Inject(at = @At("TAIL"), method = "applyDamage", cancellable = true)
	private void onEntityHurt(final DamageSource source, final float amount, CallbackInfo info) {
		ActionResult result = EntityHurtCallback.EVENT.invoker().hurtEntity((PlayerEntity) (Object) this, source,
				amount);
		if (result == ActionResult.FAIL) {
			info.cancel();
		}
	}
	
	@Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    private void onPlayerAttack(final Entity target, CallbackInfo info) {
        ActionResult result = PlayerAttackCallback.EVENT.invoker().attackEntity((PlayerEntity)(Object)this, target);
		if (result == ActionResult.FAIL) {
			info.cancel();
		}
    }

}
