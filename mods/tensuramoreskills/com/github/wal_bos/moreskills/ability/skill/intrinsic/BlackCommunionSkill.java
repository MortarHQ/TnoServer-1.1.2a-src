package com.github.wal_bos.moreskills.ability.skill.intrinsic;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BlackBloodConquestHandler;
import com.github.wal_bos.moreskills.handler.BlackCommunionHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class BlackCommunionSkill extends Skill {
   private static final int MODE_BLACK_COMMUNION = 0;
   private static final int MODE_CONQUEST_MARK = 1;
   private static final int MODE_WORLD_ROOT = 2;
   private static final int MODE_BLOOD_TIDE = 3;
   private static final int MODE_SIEGE_BLOOM = 4;
   private static final int MODE_WORLD_HEART = 5;
   private static final int MODE_HARVEST_NETWORK = 6;
   private static final int MODE_COUNT = 7;

   public BlackCommunionSkill() {
      super(SkillType.INTRINSIC);
   }

   private MoreskillsRaceConfig.BlackCommunion cfg() {
      return MoreskillsRaceConfig.get().BlackCommunion;
   }

   public int getMaxMastery() {
      return (Integer)this.cfg().maxMastery.get();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/intrinsic/black_communion.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 7;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safeMode = Math.floorMod(mode, 7);
      if (reverse) {
         return safeMode <= 0 ? 6 : safeMode - 1;
      } else {
         return safeMode >= 6 ? 0 : safeMode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "black_communion.black_communion";
         case 1 -> "black_communion.conquest_mark";
         case 2 -> "black_communion.world_root";
         case 3 -> "black_communion.blood_tide";
         case 4 -> "black_communion.siege_bloom";
         case 5 -> "black_communion.world_heart";
         case 6 -> "black_communion.harvest_network";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 0.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!BlackCommunionHandler.isCrimsonProgenitor(entity)) {
         message(entity, "Command refused: only a Crimson Progenitor can use Black Communion.", ChatFormatting.DARK_RED);
      } else {
         int safeMode = Math.floorMod(mode, 7);
         if (safeMode == 0) {
            this.pressCommunion(instance, entity, safeMode);
         } else if (entity instanceof ServerPlayer player) {
            if (this.isInfectionCommandEnabled(player)) {
               boolean success = false;
               int cooldown = 0;
               switch (safeMode) {
                  case 1: {
                     BlockPos target = getLookTarget(player, 1024.0);
                     success = BlackBloodConquestHandler.commandMarkConquestTarget(player, target);
                     cooldown = BlackBloodConquestHandler.markTargetCooldownTicks();
                     break;
                  }
                  case 2: {
                     BlockPos target = getLookTarget(player, Math.max(2048.0, ((Integer)this.cfg().conquestRootMaxTargetDistanceBlocks.get()).intValue()));
                     success = BlackBloodConquestHandler.commandRootTunnel(player, target);
                     cooldown = BlackBloodConquestHandler.rootCommandCooldownTicks();
                     break;
                  }
                  case 3:
                     success = BlackBloodConquestHandler.commandBloodTide(player, 48);
                     cooldown = BlackBloodConquestHandler.bloodTideCooldownTicks();
                     break;
                  case 4: {
                     BlockPos target = getLookTarget(player, Math.max(2048.0, ((Integer)this.cfg().conquestRootMaxTargetDistanceBlocks.get()).intValue()));
                     success = BlackBloodConquestHandler.commandBloomEruption(player, target);
                     cooldown = BlackBloodConquestHandler.bloomSiegeCooldownTicks();
                     break;
                  }
                  case 5:
                     success = BlackBloodConquestHandler.commandWorldHeartPulse(player);
                     cooldown = BlackBloodConquestHandler.worldHeartCommandCooldownTicks();
                     break;
                  case 6:
                     success = BlackBloodConquestHandler.commandHarvestBloomNetwork(player);
                     cooldown = BlackBloodConquestHandler.harvestCommandCooldownTicks();
               }

               if (success) {
                  instance.addMasteryPoint(entity);
                  if (cooldown > 0) {
                     instance.setCoolDown(cooldown, safeMode);
                  }
               }
            }
         }
      }
   }

   private void pressCommunion(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (BlackCommunionHandler.isCommunionActive(entity)) {
         BlackCommunionHandler.collapse(entity);
         message(entity, "Command accepted: Black Communion collapsed.", ChatFormatting.DARK_RED);
         instance.addMasteryPoint(entity);
         instance.setCoolDown((Integer)this.cfg().collapseCooldown.get(), mode);
      } else if (!BlackCommunionHandler.start(entity)) {
         message(entity, "Command refused: not enough blood to start Black Communion.", ChatFormatting.DARK_RED);
      } else {
         message(entity, "Command accepted: Black Communion awakened.", ChatFormatting.DARK_RED);
         instance.addMasteryPoint(entity);
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (BlackCommunionHandler.isCrimsonProgenitor(entity)) {
         if (BlackCommunionHandler.isCommunionActive(entity) && entity.tickCount % 100 == 0) {
            instance.addMasteryPoint(entity);
         }
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      BlackCommunionHandler.clearCommunion(entity);
   }

   private boolean isInfectionCommandEnabled(ServerPlayer player) {
      MoreskillsRaceConfig.BlackCommunion cfg = this.cfg();
      if (!(Boolean)cfg.enabled.get()) {
         player.displayClientMessage(Component.literal("Command refused: Black Communion is disabled in config.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else if (!(Boolean)cfg.blockInfectionEnabled.get()) {
         player.displayClientMessage(Component.literal("Command refused: block infection is disabled in config.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else if (!(Boolean)cfg.bloodAltarEnabled.get()) {
         player.displayClientMessage(Component.literal("Command refused: blood altars are disabled in config.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else if (!(Boolean)cfg.altarPassiveSpreadEnabled.get()) {
         player.displayClientMessage(Component.literal("Command refused: altar passive spread is disabled in config.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else if (!(Boolean)cfg.conquestEnabled.get()) {
         player.displayClientMessage(Component.literal("Command refused: conquest is disabled in config.").withStyle(ChatFormatting.DARK_RED), true);
         return false;
      } else {
         return true;
      }
   }

   private static BlockPos getLookTarget(ServerPlayer player, double range) {
      double safeRange = Math.max(16.0, Math.min(8192.0, range));
      Vec3 start = player.getEyePosition();
      Vec3 end = start.add(player.getLookAngle().normalize().scale(safeRange));
      BlockHitResult result = player.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, player));
      return result.getType() == Type.BLOCK ? result.getBlockPos() : BlockPos.containing(end);
   }

   private static void message(LivingEntity entity, String text, ChatFormatting color) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal(text).withStyle(color), true);
      }
   }
}
