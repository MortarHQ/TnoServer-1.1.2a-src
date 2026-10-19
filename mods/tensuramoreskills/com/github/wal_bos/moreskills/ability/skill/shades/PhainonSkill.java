package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.handler.phainon.PhainonAcquirementAnimation;
import com.github.wal_bos.moreskills.handler.phainon.PhainonDawnmaker;
import com.github.wal_bos.moreskills.handler.phainon.PhainonDelivererActions;
import com.github.wal_bos.moreskills.handler.phainon.PhainonEraNova;
import com.github.wal_bos.moreskills.handler.phainon.PhainonFallToMyName;
import com.github.wal_bos.moreskills.handler.phainon.PhainonHandler;
import com.github.wal_bos.moreskills.handler.phainon.PhainonLetThereBeLight;
import com.github.wal_bos.moreskills.handler.phainon.PhainonProgress;
import com.github.wal_bos.moreskills.handler.phainon.PhainonSoulscorchEdict;
import com.github.wal_bos.moreskills.handler.phainon.PhainonStardeathVerdict;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.battlewill.MeleeArts;
import io.github.manasmods.tensura.registry.battlewill.UtilityArts;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PhainonSkill extends Skill {
   public PhainonSkill() {
      super(SkillType.ULTIMATE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 5.0E12;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (!TensuraStorages.getExistenceFrom(entity).isTrueHero()) {
         return false;
      }

      if (EnergyHelper.getMaxMagicule(entity) < 5.0E11) {
         return false;
      }

      Skills skills = SkillAPI.getSkillsFrom(entity);
      return hasMastered(skills, entity, (ManasSkill)UniqueSkills.BERSERKER.get())
         && hasMastered(skills, entity, (ManasSkill)ExtraSkills.FLAME_DOMINATION.get())
         && hasMastered(skills, entity, (ManasSkill)ExtraSkills.HEAT_WAVE.get())
         && hasMastered(skills, entity, (ManasSkill)MeleeArts.AURA_SWORD.get())
         && hasMastered(skills, entity, (ManasSkill)MeleeArts.HEAVY_SLASH.get())
         && hasMastered(skills, entity, (ManasSkill)MeleeArts.OGRE_SWORD_GUILLOTINE.get())
         && hasMastered(skills, entity, (ManasSkill)UtilityArts.VIOLENT_BREAK.get());
   }

   private static boolean hasMastered(Skills skills, LivingEntity entity, ManasSkill skill) {
      Optional<ManasSkillInstance> optional = skills.getSkill(skill);
      return optional.isPresent() && optional.get().isMastered(entity);
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return -1;
   }

   public int getModes(ManasSkillInstance instance) {
      return 8;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int max = this.getModes(instance);
      return reverse ? (mode == 0 ? max - 1 : mode - 1) : (mode == max - 1 ? 0 : mode + 1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "phainon.core_flames";
         case 1 -> "phainon.dawnmaker";
         case 2 -> "phainon.deliverer";
         case 3 -> "phainon.let_there_be_light";
         case 4 -> "phainon.soulscorch_edict";
         case 5 -> "phainon.stardeath_verdict";
         case 6 -> "phainon.era_nova";
         case 7 -> "phainon.fall_to_my_name";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 1 -> 25000.0;
         case 2 -> 500000.0;
         case 3 -> 25000.0;
         case 4 -> 75000.0;
         case 5 -> 150000.0;
         case 6 -> 1000000.0;
         default -> 0.0;
      };
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      PhainonProgress.get(instance).setInitialized();
      SkillHelper.learnSkill(entity, (ManasSkill)UniqueSkills.CHOSEN_ONE.get());
      PhainonAcquirementAnimation.start(entity, instance);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      PhainonAcquirementAnimation.stop(entity, instance, false);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      PhainonDawnmaker.restore(entity, instance);
      PhainonDelivererActions.stop(entity, instance);
      PhainonHandler.clearCoreAttributes(entity);
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      PhainonAcquirementAnimation.tick(entity, instance);
      PhainonHandler.tick(entity, instance);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!PhainonAcquirementAnimation.isActive(instance)) {
            if (mode == 0 || !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               switch (mode) {
                  case 0:
                     PhainonHandler.sendCoreFlameProgress(entity, instance);
                     break;
                  case 1:
                     if (PhainonProgress.get(instance).getCoreFlameCount() < 6) {
                        entity.sendSystemMessage(Component.literal("Dawnmaker requires 6 Core Flames.").withStyle(ChatFormatting.RED));
                        return;
                     }

                     PhainonDawnmaker.toggle(entity, instance);
                     instance.setCoolDown(3, mode);
                     break;
                  case 2:
                     if (PhainonProgress.get(instance).getCoreFlameCount() < 12) {
                        entity.sendSystemMessage(
                           Component.literal("He Who Bears the World must Burn requires all 12 Core Flames.").withStyle(ChatFormatting.RED)
                        );
                        return;
                     }

                     PhainonDelivererActions.start(entity, instance);
                     instance.setCoolDown(instance.isMastered(entity) ? 12000 : 24000, mode);
                     break;
                  case 3:
                     if (!PhainonDelivererActions.isActive(instance)) {
                        entity.sendSystemMessage(Component.literal("The Deliverer state is not active.").withStyle(ChatFormatting.RED));
                        return;
                     }

                     PhainonLetThereBeLight.execute(entity, instance);
                     instance.setCoolDown(200, mode);
                     break;
                  case 4:
                     if (!PhainonDelivererActions.isActive(instance)) {
                        entity.sendSystemMessage(Component.literal("The Deliverer state is not active.").withStyle(ChatFormatting.RED));
                        return;
                     }

                     PhainonSoulscorchEdict.start(entity, instance);
                     instance.setCoolDown(400, mode);
                     break;
                  case 5:
                     if (!PhainonDelivererActions.isActive(instance)) {
                        entity.sendSystemMessage(Component.literal("The Deliverer state is not active.").withStyle(ChatFormatting.RED));
                        return;
                     }

                     PhainonStardeathVerdict.execute(entity, instance);
                     instance.setCoolDown(800, mode);
                     break;
                  case 6:
                     if (PhainonProgress.get(instance).getCoreFlameCount() < 12) {
                        entity.sendSystemMessage(Component.literal("Era Nova requires all 12 Core Flames.").withStyle(ChatFormatting.RED));
                        return;
                     }

                     PhainonEraNova.execute(entity, instance);
                     instance.setCoolDown(200, mode);
                     break;
                  case 7:
                     if (instance.getOrCreateTag().getInt("PhainonShatterTicks") <= 0 && !instance.getOrCreateTag().getBoolean("PhainonFallUnlocked")) {
                        entity.sendSystemMessage(
                           Component.literal("Fall to my name requires Shatter the skies! No fate, no king, no god!").withStyle(ChatFormatting.RED)
                        );
                        return;
                     }

                     PhainonFallToMyName.execute(entity, instance);
               }

               entity.swing(InteractionHand.MAIN_HAND, true);
               instance.addMasteryPoint(entity);
            }
         }
      }
   }

   public static void addRequirementText(Player player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      player.sendSystemMessage(Component.literal("Phainon Requirements").withStyle(ChatFormatting.GOLD));
      sendRequirement(player, TensuraStorages.getExistenceFrom(player).isTrueHero(), "Awakened as True Hero");
      sendRequirement(player, EnergyHelper.getMaxMagicule(player) >= 5000000.0, "5,000,000 Max MP");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)UniqueSkills.BERSERKER.get()), "Berserker");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)ExtraSkills.FLAME_DOMINATION.get()), "Flame Domination");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)ExtraSkills.HEAT_WAVE.get()), "Heat Wave");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)MeleeArts.AURA_SWORD.get()), "Aura Sword");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)MeleeArts.HEAVY_SLASH.get()), "Heavy Slash");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)MeleeArts.OGRE_SWORD_GUILLOTINE.get()), "Ogre-sword Guillotine");
      sendRequirement(player, hasMastered(skills, player, (ManasSkill)UtilityArts.VIOLENT_BREAK.get()), "Violent Break");
   }

   private static void sendRequirement(Player player, boolean done, String name) {
      player.sendSystemMessage(Component.literal((done ? "[DONE] " : "[----] ") + name).withStyle(done ? ChatFormatting.GREEN : ChatFormatting.GRAY));
   }

   public static CompoundTag data(ManasSkillInstance instance) {
      return instance.getOrCreateTag();
   }
}
