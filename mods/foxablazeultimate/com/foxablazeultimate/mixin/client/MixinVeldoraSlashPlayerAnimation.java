package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.VeldoraSlashAnimation;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class MixinVeldoraSlashPlayerAnimation<T extends LivingEntity> extends HumanoidModel<T> {
   protected MixinVeldoraSlashPlayerAnimation(ModelPart root) {
      super(root);
   }

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
   private void foxablazeultimate$applyVeldoraSlashPose(
      T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback
   ) {
      if (entity instanceof AbstractClientPlayer) {
         float partialTick = ageInTicks - entity.tickCount;
         VeldoraSlashAnimation.Timing timing = VeldoraSlashAnimation.getTiming(entity, partialTick);
         if (timing != null) {
            VeldoraSlashAnimation.apply((PlayerModel<?>)this, timing);
         }
      }
   }
}
