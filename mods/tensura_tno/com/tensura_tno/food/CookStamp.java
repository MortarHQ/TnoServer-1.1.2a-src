package com.tensura_tno.food;

import com.tensura_tno.world.TensuraTNOGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class CookStamp {
   private static final String TAG_KEY = "tno_cook_stamp";
   private static final String TAG_BONUS = "bonus";
   private static final String TAG_STAMPED = "stamped";
   private static final String TAG_EFFECT_SCALE = "effect_scale";
   private static final ResourceLocation COOK_SKILL_ID = ResourceLocation.fromNamespaceAndPath("tensura", "cook");

   private CookStamp() {
   }

   private static CompoundTag getCustomNbt(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
      return data != null ? data.copyTag() : new CompoundTag();
   }

   private static void setCustomNbt(ItemStack stack, CompoundTag tag) {
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   private static void stripStamp(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
      if (data != null) {
         CompoundTag tag = data.copyTag();
         if (tag.contains("tno_cook_stamp")) {
            tag.remove("tno_cook_stamp");
            if (tag.isEmpty()) {
               stack.remove(DataComponents.CUSTOM_DATA);
            } else {
               stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
         }
      }
   }

   public static boolean isStamped(ItemStack stack) {
      CompoundTag root = getCustomNbt(stack);
      return root.contains("tno_cook_stamp") && root.getCompound("tno_cook_stamp").getBoolean("stamped");
   }

   public static double getBonus(ItemStack stack) {
      CompoundTag root = getCustomNbt(stack);
      if (!root.contains("tno_cook_stamp")) {
         return 0.0;
      }

      CompoundTag stamp = root.getCompound("tno_cook_stamp");
      return !stamp.getBoolean("stamped") ? 0.0 : stamp.getDouble("bonus");
   }

   public static double getEffectScale(ItemStack stack) {
      CompoundTag root = getCustomNbt(stack);
      if (!root.contains("tno_cook_stamp")) {
         return 0.0;
      } else {
         CompoundTag stamp = root.getCompound("tno_cook_stamp");
         if (!stamp.getBoolean("stamped")) {
            return 0.0;
         } else {
            return stamp.contains("effect_scale") ? stamp.getDouble("effect_scale") : 1.0;
         }
      }
   }

   public static void tryStamp(ItemStack stack, Player player) {
      if (!stack.isEmpty()) {
         if (TensuraTNOGameRules.isCookFoodEpEnabled(player.level())) {
            FoodProperties food = stack.getFoodProperties(player);
            if (food != null) {
               if (food.nutrition() <= 0 && food.saturation() <= 0.0F) {
                  stripStamp(stack);
               } else if (!normalizeStamp(stack)) {
                  double mastery = getCookMastery(player);
                  if (!(mastery < 0.0)) {
                     double multiplier = rollMultiplier(mastery);
                     stamp(stack, multiplier, 1.0);
                  }
               }
            }
         }
      }
   }

   public static void tryStampScaled(ItemStack stack, LivingEntity entity, double effectScale) {
      if (!stack.isEmpty()) {
         if (TensuraTNOGameRules.isCookFoodEpEnabled(entity.level())) {
            FoodProperties food = stack.getFoodProperties(entity);
            if (food != null) {
               if (food.nutrition() <= 0 && food.saturation() <= 0.0F) {
                  stripStamp(stack);
               } else if (!normalizeStamp(stack)) {
                  double multiplier = 1.0;
                  if (entity instanceof Player player) {
                     double mastery = getCookMastery(player);
                     if (mastery >= 0.0) {
                        multiplier = rollMultiplier(mastery);
                     }
                  }

                  stamp(stack, multiplier, effectScale);
               }
            }
         }
      }
   }

   public static void tryStampScaledWithStoveBonus(ItemStack stack, LivingEntity entity, double effectScale) {
      if (!stack.isEmpty()) {
         if (TensuraTNOGameRules.isCookFoodEpEnabled(entity.level())) {
            FoodProperties food = stack.getFoodProperties(entity);
            if (food != null) {
               if (food.nutrition() <= 0 && food.saturation() <= 0.0F) {
                  stripStamp(stack);
               } else if (!normalizeStamp(stack)) {
                  double multiplier = 1.0;
                  if (entity instanceof Player player) {
                     double mastery = getCookMastery(player);
                     if (mastery >= 0.0) {
                        multiplier = rollMultiplier(mastery);
                        multiplier += ThreadLocalRandom.current().nextDouble(0.01, 0.0501);
                     }
                  }

                  stamp(stack, multiplier, effectScale);
               }
            }
         }
      }
   }

   private static void stamp(ItemStack stack, double multiplier, double effectScale) {
      CompoundTag root = getCustomNbt(stack);
      CompoundTag stamp = new CompoundTag();
      stamp.putBoolean("stamped", true);
      stamp.putDouble("bonus", normalizeMultiplier(multiplier));
      stamp.putDouble("effect_scale", effectScale);
      root.put("tno_cook_stamp", stamp);
      setCustomNbt(stack, root);
   }

   private static double normalizeMultiplier(double multiplier) {
      int bonusPct = (int)Math.round((multiplier - 1.0) * 100.0);
      return 1.0 + bonusPct / 100.0;
   }

   private static boolean normalizeStamp(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
      if (data == null) {
         return false;
      }

      CompoundTag root = data.copyTag();
      if (!root.contains("tno_cook_stamp")) {
         return false;
      }

      CompoundTag stamp = root.getCompound("tno_cook_stamp");
      if (!stamp.getBoolean("stamped")) {
         return false;
      }

      if (stamp.contains("bonus")) {
         double oldBonus = stamp.getDouble("bonus");
         double normalizedBonus = normalizeMultiplier(oldBonus);
         if (Double.compare(oldBonus, normalizedBonus) != 0) {
            stamp.putDouble("bonus", normalizedBonus);
            root.put("tno_cook_stamp", stamp);
            setCustomNbt(stack, root);
         }
      }

      return true;
   }

   public static double getCookMastery(Player player) {
      Skills skills = SkillAPI.getSkillsFrom(player);

      for (ManasSkillInstance inst : skills.getLearnedSkills()) {
         ResourceLocation id = inst.getSkillId();
         if (COOK_SKILL_ID.equals(id)) {
            return inst.getMastery();
         }
      }

      return -1.0;
   }

   private static double rollMultiplier(double mastery) {
      ThreadLocalRandom rng = ThreadLocalRandom.current();
      if (mastery >= 100.0) {
         return 1.0 + rng.nextDouble(0.01, 0.021);
      } else if (mastery >= 50.0) {
         return 1.0;
      } else {
         return mastery >= 10.0 ? 1.0 + rng.nextDouble(-0.05, -0.019) : 1.0 + rng.nextDouble(-0.15, -0.059);
      }
   }
}
