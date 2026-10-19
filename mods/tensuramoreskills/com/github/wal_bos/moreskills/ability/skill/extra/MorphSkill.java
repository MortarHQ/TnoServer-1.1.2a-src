package com.github.wal_bos.moreskills.ability.skill.extra;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.MorphSyncHandler;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MorphSkill extends Skill {
   public static final String MORPH_ENTITY = "MorphEntity";
   public static final String MORPH_NAME = "MorphName";
   private static final MoreSkillsConfigs.MorphConfig CONFIG = MoreSkillsConfigs.GRAND.morph;

   public MorphSkill() {
      super(SkillType.EXTRA);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)CONFIG.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)CONFIG.acquirementMagiculeCost.get(), (String)CONFIG.requiredSkillId.get(), (Boolean)CONFIG.requiredSkillMustBeMastered.get()
      );
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return (Double)CONFIG.magiculeCost.get();
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/extra/morph.png");
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "morph.transform";
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      clearMorph(instance, entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (entity.isShiftKeyDown()) {
            if (isMorphed(instance)) {
               clearMorph(instance, entity);
               instance.setCoolDown((Integer)CONFIG.cooldownTicks.get(), mode);
               play(entity, false);
               send(entity, "tensuramoreskills.skill.morph.cleared", ChatFormatting.GRAY);
            }
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            Entity target = ObjectSelectionHelper.getTargetingEntity(entity, (Double)CONFIG.range.get(), false, true);
            if (!(target instanceof LivingEntity livingTarget)) {
               send(entity, "tensuramoreskills.skill.morph.no_target", ChatFormatting.RED);
               playFail(entity);
            } else if (target != entity && (!(target instanceof Player) || (Boolean)CONFIG.allowPlayerMorphs.get())) {
               ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
               if (id == null || id.equals(ResourceLocation.withDefaultNamespace("player"))) {
                  send(entity, "tensuramoreskills.skill.morph.invalid_target", ChatFormatting.RED);
                  playFail(entity);
               } else if (((List)CONFIG.blacklistedEntities.get()).contains(id.toString())) {
                  send(entity, "tensuramoreskills.skill.morph.blacklisted", ChatFormatting.RED);
                  playFail(entity);
               } else {
                  double casterEP = EnergyHelper.getMaxEP(entity);
                  double targetEP = EnergyHelper.getMaxEP(livingTarget);
                  double allowedEP = casterEP * (Double)CONFIG.maxTargetEpPercent.get();
                  if (targetEP > allowedEP) {
                     send(entity, "tensuramoreskills.skill.morph.too_strong", ChatFormatting.RED);
                     playFail(entity);
                  } else {
                     CompoundTag tag = instance.getOrCreateTag();
                     tag.putString("MorphEntity", id.toString());
                     tag.putString("MorphName", target.getDisplayName().getString());
                     instance.markDirty();
                     instance.addMasteryPoint(entity);
                     instance.setCoolDown((Integer)CONFIG.cooldownTicks.get(), mode);
                     entity.swing(InteractionHand.MAIN_HAND, true);
                     play(entity, true);
                     MorphSyncHandler.syncToTrackingAndSelf(entity, id.toString());
                     if (entity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable("tensuramoreskills.skill.morph.success", new Object[]{target.getDisplayName()})
                              .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
                           true
                        );
                     }
                  }
               }
            } else {
               send(entity, "tensuramoreskills.skill.morph.invalid_target", ChatFormatting.RED);
               playFail(entity);
            }
         }
      }
   }

   public static boolean isMorphed(ManasSkillInstance instance) {
      return instance != null && instance.getOrCreateTag().contains("MorphEntity");
   }

   public static String getMorphIdString(ManasSkillInstance instance) {
      if (!isMorphed(instance)) {
         return "";
      }

      String id = instance.getOrCreateTag().getString("MorphEntity");
      return id == null ? "" : id;
   }

   public static ResourceLocation getMorphId(ManasSkillInstance instance) {
      String id = getMorphIdString(instance);
      return id.isBlank() ? null : ResourceLocation.tryParse(id);
   }

   public static EntityType<?> getMorphType(ManasSkillInstance instance) {
      ResourceLocation id = getMorphId(instance);
      if (id == null) {
         return null;
      }

      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
      return type == EntityType.PLAYER ? null : type;
   }

   private static void clearMorph(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("MorphEntity");
      tag.remove("MorphName");
      instance.markDirty();
      MorphSyncHandler.syncToTrackingAndSelf(entity, "");
   }

   private static boolean hasAllRequiredSkills(Player player, List<? extends String> requiredSkillIds, boolean mustBeMastered) {
      if (requiredSkillIds != null && !requiredSkillIds.isEmpty()) {
         for (String requiredSkillId : requiredSkillIds) {
            if (!hasRequiredSkill(player, requiredSkillId, mustBeMastered)) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   private static boolean hasRequiredSkill(Player player, String requiredSkillId, boolean mustBeMastered) {
      if (requiredSkillId != null && !requiredSkillId.isBlank()) {
         ResourceLocation id = ResourceLocation.tryParse(requiredSkillId);
         if (id == null) {
            return false;
         }

         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         return skill == null
            ? false
            : SkillAPI.getSkillsFrom(player).getSkill(skill).map(instance -> !mustBeMastered || instance.isMastered(player)).orElse(false);
      } else {
         return true;
      }
   }

   private static void send(LivingEntity entity, String key, ChatFormatting formatting) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable(key).setStyle(Style.EMPTY.withColor(formatting)), true);
      }
   }

   private static void play(LivingEntity entity, boolean activate) {
      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            activate ? (SoundEvent)TensuraSoundEvents.TRANSFORM_OGRE.get() : (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(),
            SoundSource.PLAYERS,
            1.0F,
            1.0F
         );
   }

   private static void playFail(LivingEntity entity) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }
}
