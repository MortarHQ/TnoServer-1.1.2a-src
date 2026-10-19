package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.handler.ronova.RonovaDeathHandler;
import com.github.wal_bos.moreskills.handler.ronova.RonovaPlayerHandler;
import com.github.wal_bos.moreskills.handler.ronova.RonovaSoulHandler;
import com.github.wal_bos.moreskills.handler.ronova.RonovaWorldEraseHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RonovaSkill extends AnimatedShadeSkill {
   private static final ResourceLocation[] ICON_FRAMES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/ronova.png")
   };

   public RonovaSkill() {
      super(SkillType.ULTIMATE, ICON_FRAMES, 500L);
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 4 : mode - 1;
      } else {
         return mode >= 4 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "ronova.spiritual_force";
         case 1 -> "ronova.salvation_lord";
         case 2 -> "ronova.ally_sacrifice";
         case 3 -> "ronova.death_field";
         case 4 -> "ronova.finality";
         default -> super.getModeId(instance, mode);
      };
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      return Component.translatable("tensuramoreskills.skill.mode." + this.getModeId(instance, mode)).withStyle(ChatFormatting.GRAY);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();

      return switch (mode) {
         case 0 -> cfg.spiritualForceCost.get();
         case 1 -> cfg.salvationLordCost.get();
         case 2 -> cfg.allySacrificeCost.get();
         case 3 -> cfg.deathFieldCost.get();
         case 4 -> cfg.finalityCost.get();
         default -> 0.0;
      };
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 3 && instance.getOrCreateTag().getBoolean("RonovaDeathFieldActive");
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      RonovaPlayerHandler.onRonovaToggleOn(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      RonovaPlayerHandler.onRonovaToggleOff(instance, entity);
      instance.getOrCreateTag().putBoolean("RonovaDeathFieldActive", false);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      super.onTick(instance, entity);
      if (instance.isToggled()) {
         RonovaPlayerHandler.onRonovaSkillTick(instance, entity);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!instance.isToggled()) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.toggle_first").withStyle(ChatFormatting.RED), true);
         }

         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 1.0F, 0.6F);
      } else if (mode == 3 || !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         switch (mode) {
            case 0:
               RonovaPlayerHandler.toggleSpiritualForce(instance, entity);
               instance.setCoolDown((Integer)RonovaConfigAccess.cfg().spiritualForceCooldown.get(), mode);
               instance.addMasteryPoint(entity);
               break;
            case 1:
               if (RonovaSoulHandler.tryRescue(entity, true)) {
                  instance.setCoolDown((Integer)RonovaConfigAccess.cfg().salvationLordCooldown.get(), mode);
                  instance.addMasteryPoint(entity);
               }
               break;
            case 2:
               RonovaDeathHandler.allySacrifice(instance, entity);
            case 3:
            default:
               break;
            case 4:
               RonovaWorldEraseHandler.finality(instance, entity);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (!instance.isToggled()) {
         return false;
      } else if (mode != 3) {
         return false;
      } else {
         return heldTicks <= 1 && EnergyHelper.isOutOfEnergy(entity, instance, mode) ? false : RonovaDeathHandler.deathFieldHeld(instance, entity, heldTicks);
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (instance.isToggled()) {
         if (mode == 3) {
            RonovaDeathHandler.deathFieldRelease(instance, entity, heldTicks);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
      RonovaPlayerHandler.onRonovaLearn(instance, entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      RonovaPlayerHandler.onRonovaForget(instance, entity);
   }
}
