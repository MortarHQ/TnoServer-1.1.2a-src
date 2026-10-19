package io.github.Memoires.mysticism.mixin.entities;

import io.github.Memoires.mysticism.event.MysticismEntityEvent;
import io.github.Memoires.mysticism.event.MysticismPlayerEvent;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.effect.IMysticismEffect;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
   @Inject(method = "isCrouching()Z", at = @At("TAIL"))
   private void myst$onCrouch(CallbackInfoReturnable<Boolean> cir) {
      Entity self = (Entity)this;
      if (self instanceof Player player) {
         ((MysticismPlayerEvent.PlayerCrouch)MysticismPlayerEvent.PLAYER_CROUCH.invoker()).onCrouch(player);
      }
   }

   @Inject(method = "isShiftKeyDown()Z", at = @At("TAIL"))
   private void myst$onSetShiftKeyDown(CallbackInfoReturnable<Boolean> cir) {
      Entity self = (Entity)this;
      if (self instanceof Player player) {
         List<Entity> list = player.getPassengers();
         ((MysticismEntityEvent.EntityForceDismount)MysticismEntityEvent.ENTITY_FORCE_DISMOUNT.invoker()).onForceDismount(player, list);
      }
   }

   @Inject(method = "isOnFire()Z", at = @At("RETURN"), cancellable = true)
   public void isOnFire(CallbackInfoReturnable<Boolean> cir) {
      Entity entity = (Entity)this;
      if (!((Boolean)cir.getReturnValue()).equals(true) && entity instanceof LivingEntity living) {
         IMysticismEffect effectStorage = MysticismStorages.getEffectDataFrom(living);
         if (effectStorage != null && effectStorage.isOnBrimstoneFlames()) {
            cir.setReturnValue(true);
         }
      }
   }
}
