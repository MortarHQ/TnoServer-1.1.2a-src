package com.mortal_cultivation.ability.skill.intrinsic;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public class SizeTransformationSkill extends LegacySkill {
   private static final ResourceLocation SCALE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "size_transformation_scale");
   private static final ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "size_transformation_damage");
   private static final ResourceLocation REACH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "size_transformation_reach");
   private static final float MIN_SIZE = 0.3F;
   private static final float MAX_SIZE = 10.0F;
   private static final double MAGICULE_COST = 3500.0;
   private static final double SNAP_THRESHOLD = 0.3;
   private static final String SCROLL_USED_TAG = "ScrollUsed";
   private static final double MAX_DAMAGE_BONUS = 30.0;
   private static final double MAX_REACH_BONUS = 2.5;

   public SizeTransformationSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/intrinsic/size_transformation.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putBoolean("ScrollUsed", false);
   }

   @Override
   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("ScrollUsed", true);
         float raceDefaultSize = this.getRaceDefaultSize(player);
         float currentSize = tag.contains("size") ? (float)tag.getDouble("size") : raceDefaultSize;
         float newSize = (float)(currentSize + delta * 0.5);
         if (newSize > 10.0F) {
            newSize = 10.0F;
         } else if (newSize < 0.3F) {
            newSize = 0.3F;
         }

         if (Math.abs(newSize - raceDefaultSize) < 0.3) {
            newSize = raceDefaultSize;
         }

         if (!(Math.abs(currentSize - newSize) < 0.01F)) {
            float finalNewSize = newSize;
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence.getMagicule() < 3500.0) {
               player.displayClientMessage(
                  Component.translatable("mortal_cultivation.skill.size_transformation.insufficient_magicule").withStyle(ChatFormatting.RED), true
               );
            } else {
               existence.setMagicule(existence.getMagicule() - 3500.0);
               existence.markDirty();
               tag.putDouble("size", finalNewSize);
               instance.markDirty();
               this.applySizeModifier(player, finalNewSize);
               Component sizeDesc;
               if (Math.abs(finalNewSize - raceDefaultSize) < 0.01F) {
                  sizeDesc = Component.translatable("mortal_cultivation.skill.size_transformation.original", new Object[]{String.format("%.1f", finalNewSize)});
               } else if (finalNewSize > 5.0F) {
                  sizeDesc = Component.translatable("mortal_cultivation.skill.size_transformation.towering", new Object[]{String.format("%.1f", finalNewSize)});
               } else if (finalNewSize < 1.0F) {
                  sizeDesc = Component.translatable("mortal_cultivation.skill.size_transformation.tiny", new Object[]{String.format("%.1f", finalNewSize)});
               } else {
                  sizeDesc = Component.translatable("mortal_cultivation.skill.size_transformation.balanced", new Object[]{String.format("%.1f", finalNewSize)});
               }

               player.displayClientMessage(sizeDesc.copy().withStyle(ChatFormatting.GOLD), true);
            }
         }
      }
   }

   private float getRaceDefaultSize(Player player) {
      AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
      return scaleAttribute != null ? (float)(scaleAttribute.getBaseValue() * 5.0) : 5.0F;
   }

   private void applySizeModifier(Player player, float targetSize) {
      AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
      if (scaleAttribute != null) {
         scaleAttribute.removeModifier(SCALE_MODIFIER_ID);
         this.removeAttackModifiers(player);
         float raceDefaultSize = this.getRaceDefaultSize(player);
         float sizeChange = targetSize - raceDefaultSize;
         if (!(Math.abs(sizeChange) < 0.01F)) {
            double attributeChange = sizeChange / 5.0;
            scaleAttribute.addTransientModifier(new AttributeModifier(SCALE_MODIFIER_ID, attributeChange, Operation.ADD_VALUE));
            this.applyAttackModifiers(player, targetSize, raceDefaultSize);
         }
      }
   }

   private void applyAttackModifiers(Player player, float currentSize, float raceDefaultSize) {
      if (!(currentSize <= raceDefaultSize)) {
         double sizeRatio = (currentSize - raceDefaultSize) / (10.0F - raceDefaultSize);
         sizeRatio = Math.min(1.0, Math.max(0.0, sizeRatio));
         AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackAttribute != null) {
            double damageBonus = sizeRatio * 30.0;
            AttributeModifier damageModifier = new AttributeModifier(DAMAGE_MODIFIER_ID, damageBonus, Operation.ADD_VALUE);
            attackAttribute.addTransientModifier(damageModifier);
         }

         AttributeInstance reachAttribute = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
         if (reachAttribute != null) {
            double reachBonus = sizeRatio * 2.5;
            AttributeModifier reachModifier = new AttributeModifier(REACH_MODIFIER_ID, reachBonus, Operation.ADD_VALUE);
            reachAttribute.addTransientModifier(reachModifier);
         }
      }
   }

   private void removeAttackModifiers(Player player) {
      AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackAttribute != null) {
         attackAttribute.removeModifier(DAMAGE_MODIFIER_ID);
      }

      AttributeInstance reachAttribute = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
      if (reachAttribute != null) {
         reachAttribute.removeModifier(REACH_MODIFIER_ID);
      }
   }

   private void removeSizeModifier(Player player) {
      AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
      if (scaleAttribute != null) {
         scaleAttribute.removeModifier(SCALE_MODIFIER_ID);
      }

      this.removeAttackModifiers(player);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity instanceof Player) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean scrollUsed = tag.getBoolean("ScrollUsed");
         if (!scrollUsed) {
            Player player = (Player)entity;
            player.displayClientMessage(
               Component.translatable("mortal_cultivation.skill.size_transformation.usage_hint").withStyle(ChatFormatting.YELLOW), true
            );
         }

         tag.putBoolean("ScrollUsed", false);
      }
   }

   @EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
   public static class PlayerEventHandler {
      @SubscribeEvent
      public static void onPlayerDeath(LivingDeathEvent event) {
         if (event.getEntity() instanceof Player player) {
            restoreToRaceDefault(player);
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawn(PlayerRespawnEvent event) {
         Player player = event.getEntity();
         forceCleanupAllModifiers(player);
      }

      @SubscribeEvent
      public static void onPlayerClone(Clone event) {
         if (event.isWasDeath()) {
            Player newPlayer = event.getEntity();
            forceCleanupAllModifiers(newPlayer);
         }
      }

      @SubscribeEvent
      public static void onPlayerLogin(PlayerLoggedInEvent event) {
         Player player = event.getEntity();
         restoreSavedSize(player);
      }

      private static void forceCleanupAllModifiers(Player player) {
         AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
         if (scaleAttribute != null) {
            scaleAttribute.removeModifier(SizeTransformationSkill.SCALE_MODIFIER_ID);
         }

         AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackAttribute != null) {
            attackAttribute.removeModifier(SizeTransformationSkill.DAMAGE_MODIFIER_ID);
         }

         AttributeInstance reachAttribute = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
         if (reachAttribute != null) {
            reachAttribute.removeModifier(SizeTransformationSkill.REACH_MODIFIER_ID);
         }

         try {
            Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get());
            if (optional.isPresent()) {
               ManasSkillInstance instance = optional.get();
               CompoundTag tag = instance.getOrCreateTag();
               float raceDefaultSize = getRaceDefaultSize(player);
               tag.putDouble("size", raceDefaultSize);
               instance.markDirty();
            }
         } catch (Exception var8) {
         }

         player.refreshDimensions();
      }

      private static void restoreToRaceDefault(Player player) {
         AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
         if (scaleAttribute != null) {
            scaleAttribute.removeModifier(SizeTransformationSkill.SCALE_MODIFIER_ID);
         }

         removeAttackModifiersStatic(player);
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get());
         if (optional.isPresent()) {
            ManasSkillInstance instance = optional.get();
            CompoundTag tag = instance.getOrCreateTag();
            float raceDefaultSize = getRaceDefaultSize(player);
            tag.putDouble("size", raceDefaultSize);
            instance.markDirty();
         }

         player.refreshDimensions();
      }

      private static void restoreSavedSize(Player player) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)CultivationSkills.SIZE_TRANSFORMATION.get());
         if (optional.isPresent()) {
            ManasSkillInstance instance = optional.get();
            CompoundTag tag = instance.getOrCreateTag();
            if (tag.contains("size")) {
               float savedSize = (float)tag.getDouble("size");
               float raceDefaultSize = getRaceDefaultSize(player);
               if (Math.abs(savedSize - raceDefaultSize) > 0.01F) {
                  AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
                  if (scaleAttribute != null) {
                     scaleAttribute.removeModifier(SizeTransformationSkill.SCALE_MODIFIER_ID);
                     float sizeChange = savedSize - raceDefaultSize;
                     double attributeChange = sizeChange / 5.0;
                     scaleAttribute.addTransientModifier(new AttributeModifier(SizeTransformationSkill.SCALE_MODIFIER_ID, attributeChange, Operation.ADD_VALUE));
                     applyAttackModifiersStatic(player, savedSize, raceDefaultSize);
                  }
               }
            }
         }
      }

      private static void applyAttackModifiersStatic(Player player, float currentSize, float raceDefaultSize) {
         if (!(currentSize <= raceDefaultSize)) {
            double sizeRatio = (currentSize - raceDefaultSize) / (10.0F - raceDefaultSize);
            sizeRatio = Math.min(1.0, Math.max(0.0, sizeRatio));
            AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
            if (attackAttribute != null) {
               double damageBonus = sizeRatio * 30.0;
               AttributeModifier damageModifier = new AttributeModifier(SizeTransformationSkill.DAMAGE_MODIFIER_ID, damageBonus, Operation.ADD_VALUE);
               attackAttribute.addTransientModifier(damageModifier);
            }

            AttributeInstance reachAttribute = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
            if (reachAttribute != null) {
               double reachBonus = sizeRatio * 2.5;
               AttributeModifier reachModifier = new AttributeModifier(SizeTransformationSkill.REACH_MODIFIER_ID, reachBonus, Operation.ADD_VALUE);
               reachAttribute.addTransientModifier(reachModifier);
            }
         }
      }

      private static void removeAttackModifiersStatic(Player player) {
         AttributeInstance attackAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackAttribute != null) {
            attackAttribute.removeModifier(SizeTransformationSkill.DAMAGE_MODIFIER_ID);
         }

         AttributeInstance reachAttribute = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
         if (reachAttribute != null) {
            reachAttribute.removeModifier(SizeTransformationSkill.REACH_MODIFIER_ID);
         }
      }

      private static float getRaceDefaultSize(Player player) {
         AttributeInstance scaleAttribute = player.getAttribute(Attributes.SCALE);
         return scaleAttribute != null ? (float)(scaleAttribute.getBaseValue() * 5.0) : 5.0F;
      }
   }
}
