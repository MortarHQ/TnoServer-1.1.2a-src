package com.mortal_cultivation.ability.skill.unique;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class QiFlightSkill extends LegacySkill {
   private static final float NORMAL_FLIGHT_SPEED = 0.05F;
   private static final float SPRINT_FLIGHT_SPEED = 0.1F;
   private static final double MAGICULE_COST_PER_TICK = 1.5;
   private static final Set<UUID> activeQiFlightPlayers = new HashSet<>();

   public QiFlightSkill() {
      super(SkillType.EXTRA);
   }

   private static void setQiFlightActive(Player player, boolean active) {
      if (active) {
         activeQiFlightPlayers.add(player.getUUID());
      } else {
         activeQiFlightPlayers.remove(player.getUUID());
      }
   }

   public static void clearQiFlightState(Player player) {
      activeQiFlightPlayers.remove(player.getUUID());
   }

   public static boolean isQiFlightActive(Player player) {
      return activeQiFlightPlayers.contains(player.getUUID());
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/unique/qi_flight.png");
   }

   @Override
   public boolean meetEPRequirement(Player entity, double curEP) {
      return false;
   }

   @Override
   public double learningCost() {
      return 500.0;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 1.5;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         if (EnergyHelper.isOutOfMagiculeConsuming(entity, this.magiculeCost(entity, instance)) > 0.0) {
            player.displayClientMessage(
               Component.translatable("skill.mortal_cultivation.qi_flight.out_of_magicule").withStyle(style -> style.withColor(16711680)), true
            );
         } else {
            boolean isCurrentlyFlying = activeQiFlightPlayers.contains(player.getUUID());
            if (!isCurrentlyFlying) {
               player.displayClientMessage(
                  Component.translatable("skill.mortal_cultivation.qi_flight.activated").withStyle(style -> style.withColor(65535)), true
               );
               player.fallDistance = 0.0F;
               player.getAbilities().mayfly = true;
               player.getAbilities().flying = true;
               player.getAbilities().setFlyingSpeed(0.1F);
               player.onUpdateAbilities();
               setQiFlightActive(player, true);
            } else {
               player.displayClientMessage(
                  Component.translatable("skill.mortal_cultivation.qi_flight.deactivated").withStyle(style -> style.withColor(16739179)), true
               );
               player.getAbilities().mayfly = false;
               player.getAbilities().flying = false;
               player.getAbilities().setFlyingSpeed(0.05F);
               player.onUpdateAbilities();
               setQiFlightActive(player, false);
            }

            instance.setCoolDown(3, 0);
            this.addMasteryPoint(instance, entity);
         }
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }
}
