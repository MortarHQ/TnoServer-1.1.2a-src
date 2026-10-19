package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1304;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1355;
import net.minecraft.class_1429;
import net.minecraft.class_1438;
import net.minecraft.class_1452;
import net.minecraft.class_1472;
import net.minecraft.class_1495;
import net.minecraft.class_1496;
import net.minecraft.class_1498;
import net.minecraft.class_1500;
import net.minecraft.class_1501;
import net.minecraft.class_1536;
import net.minecraft.class_1548;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1914;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3989;
import net.minecraft.class_4048;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_1438.class_4053;

public class EntityFunctions {
   public static Boolean isHorse(class_1297 entity) {
      return entity instanceof class_1496;
   }

   public static boolean isModdedVillager(String entitystring) {
      String type = entitystring.split("\\[")[0];

      for (String moddedvillager : GlobalVariables.moddedvillagers) {
         if (type.equalsIgnoreCase(moddedvillager)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isModdedVillager(class_1297 entity) {
      String entitystring = getEntityString(entity);
      return isModdedVillager(entitystring);
   }

   public static boolean isMilkable(class_1297 entity) {
      if (!(entity instanceof class_1472)
         && !(entity instanceof class_1501)
         && !(entity instanceof class_1452)
         && !(entity instanceof class_1495)
         && !(entity instanceof class_1498)
         && !(entity instanceof class_1500)) {
         return false;
      } else {
         return !(entity instanceof class_1429 animal) ? false : !animal.method_6109();
      }
   }

   public static String getEntityString(class_1297 entity) {
      String entitystring = "";
      class_2960 rl = class_1299.method_5890(entity.method_5864());
      if (rl != null) {
         entitystring = rl.toString();
         if (entitystring.contains(":")) {
            entitystring = entitystring.split(":")[1];
         }

         entitystring = StringFunctions.capitalizeEveryWord(entitystring.replace("_", " ")).replace(" ", "").replace("Entity", "");
      }

      return entitystring;
   }

   public static void nameEntity(class_1297 entity, String name) {
      if (!name.equals("")) {
         entity.method_5665(class_2561.method_43470(name));
      }
   }

   public static void addPotionEffect(class_1297 entity, class_1291 effect, Integer ms) {
      addPotionEffect(entity, class_7923.field_41174.method_47983(effect), ms);
   }

   public static void addPotionEffect(class_1297 entity, class_6880<class_1291> mobEffectHolder, Integer ms) {
      class_1293 freeze = new class_1293(mobEffectHolder, ms / 50);
      class_1309 le = (class_1309)entity;
      le.method_6092(freeze);
   }

   public static void removePotionEffect(class_1297 entity, class_1291 effect) {
      removePotionEffect(entity, class_7923.field_41174.method_47983(effect));
   }

   public static void removePotionEffect(class_1297 entity, class_6880<class_1291> mobEffectHolder) {
      class_1309 le = (class_1309)entity;
      le.method_6016(mobEffectHolder);
   }

   public static void chargeEntity(class_1297 entity) {
      class_1937 world = entity.method_37908();
      if (entity instanceof class_1548) {
         entity.method_5841().method_12778(class_1548.field_7224, true);
      } else if (entity instanceof class_1438) {
         ((class_1438)entity).method_47846(class_4053.field_18110);
      }
   }

   public static void setEntityFlag(class_1297 entity, int flag, boolean set) {
      entity.method_5729(flag, set);
   }

   public static boolean getAbstractHorseEntityFlagResult(class_1496 abstractHorse, int flag) {
      return abstractHorse.method_6730(flag);
   }

   public static void resetMerchantOffers(class_1646 villager) {
      for (class_1914 offer : villager.method_8264()) {
         resetMerchantOffer(offer);
      }
   }

   public static void resetMerchantOffers(class_3989 wanderingTrader) {
      for (class_1914 offer : wanderingTrader.method_8264()) {
         resetMerchantOffer(offer);
      }
   }

   public static void resetMerchantOffer(class_1914 offer) {
      offer.field_9147 = 0;
      offer.field_9144 = Integer.MAX_VALUE;
      offer.field_18677 = 0;
   }

   public static void forceSetHealth(class_1309 livingEntity, float health) {
      livingEntity.method_5841().method_12778(class_1309.field_6247, health);
   }

   public static boolean fishingHookHasCatch(class_1536 fishingHook) {
      return fishingHook.field_23232;
   }

   public static void transferItemsBetweenEntities(class_1297 from, class_1297 to, boolean ignoremainhand) {
      if (from instanceof class_1308 mobfrom) {
         for (class_1304 equipmentslottype : class_1304.values()) {
            if (!ignoremainhand || !equipmentslottype.equals(class_1304.field_6173)) {
               class_1799 itemstack = mobfrom.method_6118(equipmentslottype);
               if (!itemstack.method_7960()) {
                  ((class_1309)to).method_5673(equipmentslottype, itemstack.method_7972());
               }
            }
         }
      }
   }

   public static void transferItemsBetweenEntities(class_1297 from, class_1297 to) {
      transferItemsBetweenEntities(from, to, false);
   }

   public static Boolean doesEntitySurviveThisDamage(class_1657 player, int halfheartdamage) {
      return doesEntitySurviveThisDamage((class_1309)player, halfheartdamage);
   }

   public static Boolean doesEntitySurviveThisDamage(class_1309 entity, int halfheartdamage) {
      class_1937 level = entity.method_37908();
      float newhealth = entity.method_6032() - halfheartdamage;
      if (newhealth > 0.0F) {
         entity.method_5643(level.method_48963().method_48831(), 0.1F);
         entity.method_6033(newhealth);
         return true;
      } else {
         entity.method_5643(level.method_48963().method_48831(), Float.MAX_VALUE);
         return false;
      }
   }

   public static Boolean isEntityFromSpawner(class_1297 entity) {
      return entity == null ? false : entity.method_5752().contains("collective.fromspawner");
   }

   public static void setEntitySize(class_1297 entity, class_4048 entityDimensions, float eyeHight) {
      entity.field_18065 = entityDimensions;
      entity.field_18066 = eyeHight;
   }

   public static class_1355 getGoalSelector(class_1308 mob) {
      return mob.field_6201;
   }

   public static class_1355 getTargetSelector(class_1308 mob) {
      return mob.field_6185;
   }
}
