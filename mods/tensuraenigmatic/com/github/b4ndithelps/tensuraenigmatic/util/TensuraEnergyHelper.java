package com.github.b4ndithelps.tensuraenigmatic.util;

import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.LivingEntity;

public class TensuraEnergyHelper {
   public static double getMaxAura(LivingEntity entity) {
      double max = entity.getAttributeValue(TensuraAttributes.MAX_AURA);
      double limited = entity.getAttributeValue(TensuraAttributes.LIMITED_SPIRITUAL_MAX_AURA);
      if (limited > 0.0) {
         max = Math.min(max, limited);
      }

      return max;
   }

   public static void restoreAura(LivingEntity entity, double amount) {
      if (!(amount <= 0.0) && entity.isAlive()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double current = existence.getAura();
         double max = getMaxAura(entity);
         if (current < max) {
            existence.setAura(Math.min(current + amount, max));
            existence.markDirty();
         }
      }
   }

   public static void restoreMagicule(LivingEntity entity, double amount) {
      if (!(amount <= 0.0) && entity.isAlive()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         double current = existence.getMagicule();
         double max = entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         double limited = entity.getAttributeValue(TensuraAttributes.LIMITED_SPIRITUAL_MAX_MAGICULE);
         if (limited > 0.0) {
            max = Math.min(max, limited);
         }

         if (current < max) {
            existence.setMagicule(Math.min(current + amount, max));
            existence.markDirty();
         }
      }
   }

   public static boolean hasAllCommonSpirits(LivingEntity entity) {
      for (Element element : Element.getCommonElemental()) {
         if (TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(element) <= 0) {
            return false;
         }
      }

      return true;
   }

   public static boolean hasAnySpirit(LivingEntity entity) {
      for (Element element : Element.getCommandSuggestElemental()) {
         if (TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(element) > 0) {
            return true;
         }
      }

      return false;
   }

   private TensuraEnergyHelper() {
   }
}
