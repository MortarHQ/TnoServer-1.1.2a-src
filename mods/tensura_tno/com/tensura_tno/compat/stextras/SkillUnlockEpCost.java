package com.tensura_tno.compat.stextras;

import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class SkillUnlockEpCost {
   public static final double COST_RATIO = 0.01;

   private SkillUnlockEpCost() {
   }

   public static double cost(LivingEntity player) {
      return costForMaxEp(EnergyHelper.getMaxEP(player));
   }

   public static boolean canAfford(LivingEntity player, IExistence existence) {
      return canAfford(existence.getAura(), existence.getMagicule(), cost(player));
   }

   public static boolean consume(ServerPlayer player) {
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      double cost = cost(player);
      double aura = existence.getAura();
      double magicule = existence.getMagicule();
      double currentEp = aura + magicule;
      if (cost <= 0.0) {
         return true;
      } else if (canAfford(aura, magicule, cost) && Double.isFinite(currentEp)) {
         EnergyHelper.multiplyMaxEP(player, 0.99);
         double newMaxEp = EnergyHelper.getMaxEP(player);
         double remainingEp = Math.min(currentEp - cost, newMaxEp);
         existence.setAura(scaledComponent(aura, magicule, aura, remainingEp));
         existence.setMagicule(scaledComponent(aura, magicule, magicule, remainingEp));
         existence.markDirty();
         return true;
      } else {
         return false;
      }
   }

   static double costForMaxEp(double maxEp) {
      return Double.isFinite(maxEp) && !(maxEp <= 0.0) ? maxEp * 0.01 : 0.0;
   }

   static boolean canAfford(double aura, double magicule, double cost) {
      return Double.isFinite(cost) && cost >= 0.0 && Double.isFinite(aura) && Double.isFinite(magicule) && aura + magicule >= cost;
   }

   static double remainingMagicule(double aura, double magicule, double cost) {
      return scaledComponent(aura, magicule, magicule, aura + magicule - cost);
   }

   static double remainingAura(double aura, double magicule, double cost) {
      return scaledComponent(aura, magicule, aura, aura + magicule - cost);
   }

   static double maximumAfterCost(double maxEp) {
      return Double.isFinite(maxEp) && !(maxEp <= 0.0) ? maxEp * 0.99 : maxEp;
   }

   private static double scaledComponent(double aura, double magicule, double component, double targetEp) {
      double currentEp = aura + magicule;
      return Double.isFinite(currentEp) && !(currentEp <= 0.0) && Double.isFinite(targetEp) ? component * (Math.max(0.0, targetEp) / currentEp) : component;
   }
}
