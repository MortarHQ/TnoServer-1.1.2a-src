package com.github.manasmods.tensura.capability.race;

import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public final class TensuraPlayerCapability {
   private TensuraPlayerCapability() {
   }

   public static double getBaseEP(Player player) {
      return TensuraStorages.getExistenceFrom(player).getEP();
   }

   public static int getSoulPoints(Player player) {
      return TensuraStorages.getExistenceFrom(player).getSoulPoints();
   }

   public static double getMagicule(Player player) {
      return TensuraStorages.getExistenceFrom(player).getMagicule();
   }

   public static double getBaseMagicule(Player player) {
      AttributeInstance instance = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
      return instance != null ? instance.getBaseValue() : 0.0;
   }

   public static void setMagicule(Player player, double amount) {
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      existence.setMagicule(amount);
      existence.markDirty();
   }
}
