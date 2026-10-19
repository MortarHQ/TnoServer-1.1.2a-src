package com.foxablazeultimate.client;

import com.foxablazeultimate.ability.skill.veldora.VeldoraSkill;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public final class VeldoraSlashAnimation {
   private static final float OVERHEAD_X = -3.25F;
   private static final float OVERHEAD_RIGHT_Y = -0.08F;
   private static final float OVERHEAD_LEFT_Y = 0.08F;
   private static final float OVERHEAD_RIGHT_Z = -0.34F;
   private static final float OVERHEAD_LEFT_Z = 0.34F;
   private static final float STRIKE_X = -0.85F;
   private static final float STRIKE_RIGHT_Y = -0.12F;
   private static final float STRIKE_LEFT_Y = 0.12F;
   private static final float STRIKE_RIGHT_Z = -0.22F;
   private static final float STRIKE_LEFT_Z = 0.22F;

   private VeldoraSlashAnimation() {
   }

   @Nullable
   public static VeldoraSlashAnimation.Timing getTiming(LivingEntity entity, float partialTick) {
      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get()).orElse(null);
      if (instance != null && VeldoraSkill.isSlashAnimationActive(instance)) {
         long startTime = VeldoraSkill.getSlashAnimationStartTime(instance);
         float elapsedTicks = Math.max(0.0F, (float)(entity.level().getGameTime() - startTime) + Mth.clamp(partialTick, 0.0F, 1.0F));
         return new VeldoraSlashAnimation.Timing(
            elapsedTicks, VeldoraSkill.getSlashAnimationRaiseTicks(instance), VeldoraSkill.getSlashAnimationSwingTicks(instance)
         );
      } else {
         return null;
      }
   }

   public static boolean isActive(LivingEntity entity) {
      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)FoxAblazeUltimateSkills.VELDORA.get()).orElse(null);
      return instance != null && VeldoraSkill.isSlashAnimationActive(instance);
   }

   public static void apply(PlayerModel<?> model, VeldoraSlashAnimation.Timing timing) {
      float elapsed = timing.elapsedTicks();
      int raiseTicks = Math.max(1, timing.raiseTicks());
      int swingTicks = Math.max(1, timing.swingTicks());
      if (elapsed < raiseTicks) {
         float progress = smoothStep(elapsed / raiseTicks);
         blendTo(model.rightArm, -3.25F, -0.08F, -0.34F, progress);
         blendTo(model.leftArm, -3.25F, 0.08F, 0.34F, progress);
      } else {
         float progress = smoothStep(Mth.clamp((elapsed - raiseTicks) / swingTicks, 0.0F, 1.0F));
         setRotation(model.rightArm, Mth.lerp(progress, -3.25F, -0.85F), Mth.lerp(progress, -0.08F, -0.12F), Mth.lerp(progress, -0.34F, -0.22F));
         setRotation(model.leftArm, Mth.lerp(progress, -3.25F, -0.85F), Mth.lerp(progress, 0.08F, 0.12F), Mth.lerp(progress, 0.34F, 0.22F));
      }

      model.rightSleeve.copyFrom(model.rightArm);
      model.leftSleeve.copyFrom(model.leftArm);
   }

   private static void blendTo(ModelPart part, float xRot, float yRot, float zRot, float progress) {
      setRotation(part, Mth.lerp(progress, part.xRot, xRot), Mth.lerp(progress, part.yRot, yRot), Mth.lerp(progress, part.zRot, zRot));
   }

   private static void setRotation(ModelPart part, float xRot, float yRot, float zRot) {
      part.xRot = xRot;
      part.yRot = yRot;
      part.zRot = zRot;
   }

   private static float smoothStep(float value) {
      float clamped = Mth.clamp(value, 0.0F, 1.0F);
      return clamped * clamped * (3.0F - 2.0F * clamped);
   }

   public record Timing(float elapsedTicks, int raiseTicks, int swingTicks) {
   }
}
