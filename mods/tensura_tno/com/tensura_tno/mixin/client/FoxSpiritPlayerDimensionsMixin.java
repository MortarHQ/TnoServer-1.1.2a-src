package com.tensura_tno.mixin.client;

import com.tensura_tno.race.fox_spirit.FoxSpiritPlayerFormHelper;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class FoxSpiritPlayerDimensionsMixin {
   @Inject(method = "getDefaultDimensions", at = @At("RETURN"), cancellable = true)
   private void tno$foxSpiritDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
      Player player = (Player)this;
      if (FoxSpiritPlayerFormHelper.shouldUseFoxForm(player)) {
         EntityDimensions original = (EntityDimensions)cir.getReturnValue();
         EntityDimensions replacement;
         if (pose == Pose.STANDING) {
            replacement = resize(original, 0.7F, 1.0F, 0.8F);
         } else if (pose == Pose.CROUCHING) {
            replacement = resize(original, 0.7F, 1.0F, 0.7F);
         } else if (pose != Pose.SWIMMING && pose != Pose.FALL_FLYING && pose != Pose.SPIN_ATTACK) {
            replacement = original;
         } else {
            replacement = resize(original, 0.7F, 1.0F, 0.45F);
         }

         cir.setReturnValue(replacement);
      }
   }

   private static EntityDimensions resize(EntityDimensions original, float width, float height, float eyeHeight) {
      float widthScale = width / original.width();
      float heightScale = height / original.height();
      return original.scale(widthScale, heightScale).withEyeHeight(eyeHeight);
   }
}
