package com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class EternalFourthProtectionSkill extends Skill {
   private static final double TARGET_RANGE = 16.0;
   private static final float SELF_RADIUS = 5.0F;
   private static final float MASTERED_SELF_RADIUS = 8.0F;
   private static final float TARGET_PADDING = 3.0F;
   private static final double MAGICULE_COST_RATIO = 0.25;
   private static final int DURATION_TICKS = 2400;
   private static final int COOLDOWN_SECONDS = 30;

   public EternalFourthProtectionSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/intrinsic/eternal_fourth_protection.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return EnergyHelper.getMaxMagicule(entity) * 0.25;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return entity.isShiftKeyDown();
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel serverLevel) {
         if (entity.isShiftKeyDown()) {
            FourthProtectionManager.dismiss(entity);
         } else if (!this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
            LivingEntity aimedTarget = this.findAimedTarget(entity);
            LivingEntity protectedTarget = aimedTarget == null ? entity : aimedTarget;
            float radius = aimedTarget == null ? (instance.isMastered(entity) ? 8.0F : 5.0F) : this.getTargetBarrierRadius(aimedTarget);
            FourthProtectionManager.activate(serverLevel, entity, protectedTarget, radius, 2400);
            instance.setCoolDown(30, mode);
            instance.addMasteryPoint(entity);
         }
      }
   }

   private float getTargetBarrierRadius(LivingEntity target) {
      AABB box = target.getBoundingBox();
      double halfWidth = box.getXsize() * 0.5;
      double halfHeight = box.getYsize() * 0.5;
      double halfDepth = box.getZsize() * 0.5;
      return (float)(Math.sqrt(halfWidth * halfWidth + halfHeight * halfHeight + halfDepth * halfDepth) + 3.0);
   }

   private LivingEntity findAimedTarget(LivingEntity caster) {
      Vec3 start = caster.getEyePosition(1.0F);
      Vec3 look = caster.getViewVector(1.0F).normalize();
      Vec3 intendedEnd = start.add(look.scale(16.0));
      HitResult blockHit = caster.level().clip(new ClipContext(start, intendedEnd, Block.COLLIDER, Fluid.NONE, caster));
      Vec3 end = blockHit.getType() == Type.MISS ? intendedEnd : blockHit.getLocation();
      AABB searchBounds = caster.getBoundingBox().expandTowards(end.subtract(start)).inflate(1.0);
      EntityHitResult hit = ProjectileUtil.getEntityHitResult(
         caster, start, end, searchBounds, candidate -> this.isValidTarget(caster, candidate), start.distanceToSqr(end)
      );
      return hit != null && hit.getEntity() instanceof LivingEntity living ? living : null;
   }

   private boolean isValidTarget(LivingEntity caster, Entity candidate) {
      return candidate != caster && candidate instanceof LivingEntity living && living.isAlive() && !candidate.isSpectator() && candidate.isPickable();
   }
}
