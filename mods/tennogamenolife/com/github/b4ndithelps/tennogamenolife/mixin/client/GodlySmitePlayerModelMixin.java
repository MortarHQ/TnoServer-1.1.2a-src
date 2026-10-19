package com.github.b4ndithelps.tennogamenolife.mixin.client;

import com.github.b4ndithelps.tennogamenolife.client.GodlySmiteAnimationState;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class GodlySmitePlayerModelMixin {
   @Unique
   private static final float RIGHT_ARM_END_ROTATION = (float)Math.toRadians(80.0);
   @Unique
   private static final float LEFT_ARM_END_ROTATION = (float)Math.toRadians(-80.0);

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
   private void tennogamenolife$applyGodlySmitePose(
      LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo
   ) {
      PlayerModel<?> model = (PlayerModel<?>)this;
      float partialTick = ageInTicks - entity.tickCount;
      float progress = GodlySmiteAnimationState.getChargeProgress(entity, partialTick);
      if (!(progress <= 0.0F)) {
         model.rightArm.zRot = RIGHT_ARM_END_ROTATION * progress;
         model.leftArm.zRot = LEFT_ARM_END_ROTATION * progress;
         copyArmToSleeve(model.rightArm, model.rightSleeve);
         copyArmToSleeve(model.leftArm, model.leftSleeve);
      }
   }

   @Unique
   private static void copyArmToSleeve(ModelPart arm, ModelPart sleeve) {
      sleeve.copyFrom(arm);
   }
}
