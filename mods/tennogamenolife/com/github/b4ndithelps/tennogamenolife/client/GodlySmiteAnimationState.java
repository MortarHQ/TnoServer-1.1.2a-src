package com.github.b4ndithelps.tennogamenolife.client;

import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public final class GodlySmiteAnimationState {
   public static final String LOCKED_TAG = "GodlySmiteLocked";
   public static final String CHARGE_TICKS_TAG = "GodlySmiteChargeTicks";
   public static final float ARM_ANIMATION_TICKS = 10.0F;
   public static final float WING_ANIMATION_TICKS = 5.0F;

   private GodlySmiteAnimationState() {
   }

   public static boolean isCharging(LivingEntity entity) {
      CompoundTag tag = getTag(entity);
      return tag != null && tag.getBoolean("GodlySmiteLocked");
   }

   public static float getChargeProgress(LivingEntity entity, float partialTick) {
      return getChargeProgress(entity, partialTick, 10.0F);
   }

   public static float getWingChargeProgress(LivingEntity entity, float partialTick) {
      return getChargeProgress(entity, partialTick, 5.0F);
   }

   private static float getChargeProgress(LivingEntity entity, float partialTick, float animationTicks) {
      CompoundTag tag = getTag(entity);
      if (tag != null && tag.getBoolean("GodlySmiteLocked")) {
         float ticks = tag.getInt("GodlySmiteChargeTicks") + Mth.clamp(partialTick, 0.0F, 1.0F);
         return Mth.clamp(ticks / animationTicks, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   private static CompoundTag getTag(LivingEntity entity) {
      if (entity != null && entity.isAlive()) {
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity)
            .getSkill((ManasSkill)TenreincarnationSkill.GODLY_SMITE.get())
            .orElse(null);
         return instance == null ? null : instance.getTag();
      } else {
         return null;
      }
   }
}
