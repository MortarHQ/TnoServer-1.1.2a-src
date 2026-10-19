package com.github.wal_bos.moreskills.nirvana;

import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class NirvanaPlayerResetUtil {
   private NirvanaPlayerResetUtil() {
   }

   public static void resetPlayerLikeCharacterResetScroll(ServerPlayer player) {
      removeAllEffects(player);
      clearAndResetAllLoadedAttributes(player);
      resetKnownAttributes(player);
      resetVanillaMovementState(player);
      syncAttributes(player);
      player.setHealth(player.getMaxHealth());
      player.getFoodData().setFoodLevel(20);
      player.getFoodData().setSaturation(5.0F);
   }

   private static void removeAllEffects(ServerPlayer player) {
      for (MobEffectInstance effect : new ArrayList(player.getActiveEffects())) {
         player.removeEffect(effect.getEffect());
      }
   }

   private static void clearAndResetAllLoadedAttributes(ServerPlayer player) {
      for (AttributeInstance instance : copiedAttributeInstances(player)) {
         hardResetAttribute(instance, ((Attribute)instance.getAttribute().value()).getDefaultValue());
      }
   }

   private static void resetKnownAttributes(ServerPlayer player) {
      resetAttributeBase(player, Attributes.MAX_HEALTH, 20.0);
      resetAttributeBase(player, Attributes.FOLLOW_RANGE, 32.0);
      resetAttributeBase(player, Attributes.KNOCKBACK_RESISTANCE, 0.0);
      resetAttributeBase(player, Attributes.MOVEMENT_SPEED, 0.1);
      resetAttributeBase(player, Attributes.FLYING_SPEED, 0.4);
      resetAttributeBase(player, Attributes.ATTACK_DAMAGE, 1.0);
      resetAttributeBase(player, Attributes.ATTACK_KNOCKBACK, 0.0);
      resetAttributeBase(player, Attributes.ATTACK_SPEED, 4.0);
      resetAttributeBase(player, Attributes.ARMOR, 0.0);
      resetAttributeBase(player, Attributes.ARMOR_TOUGHNESS, 0.0);
      resetAttributeBase(player, Attributes.LUCK, 0.0);
      resetAttributeBase(player, Attributes.BLOCK_INTERACTION_RANGE, 4.5);
      resetAttributeBase(player, Attributes.ENTITY_INTERACTION_RANGE, 3.0);
      resetAttributeBase(player, Attributes.BLOCK_BREAK_SPEED, 1.0);
      resetAttributeBase(player, Attributes.MINING_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.SNEAKING_SPEED, 0.3);
      resetAttributeBase(player, Attributes.SUBMERGED_MINING_SPEED, 0.2);
      resetAttributeBase(player, Attributes.SWEEPING_DAMAGE_RATIO, 0.0);
      resetAttributeBase(player, Attributes.OXYGEN_BONUS, 0.0);
      resetAttributeBase(player, Attributes.WATER_MOVEMENT_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.MOVEMENT_EFFICIENCY, 0.0);
      resetAttributeBase(player, Attributes.SAFE_FALL_DISTANCE, 3.0);
      resetAttributeBase(player, Attributes.FALL_DAMAGE_MULTIPLIER, 1.0);
      resetAttributeBase(player, Attributes.GRAVITY, 0.08);
      resetAttributeBase(player, Attributes.JUMP_STRENGTH, 0.42);
      resetAttributeBase(player, Attributes.SCALE, 1.0);
      resetAttributeBase(player, Attributes.STEP_HEIGHT, 0.6);
      resetAttributeBase(player, Attributes.BURNING_TIME, 1.0);
      resetAttributeBase(player, Attributes.EXPLOSION_KNOCKBACK_RESISTANCE, 0.0);
      resetAllAttributesFromClass(player, TensuraAttributes.class);
      resetAttributeBase(player, Attributes.MOVEMENT_SPEED, 0.1);
   }

   private static void resetAllAttributesFromClass(ServerPlayer player, Class<?> type) {
      for (Field field : type.getDeclaredFields()) {
         try {
            field.setAccessible(true);
            if (field.get(null) instanceof Holder<?> holder && holder.value() instanceof Attribute) {
               resetAttributeBase(player, (Holder<Attribute>)holder);
            }
         } catch (Throwable var8) {
         }
      }
   }

   private static void resetAttributeBase(ServerPlayer player, Holder<Attribute> holder) {
      try {
         AttributeInstance instance = player.getAttribute(holder);
         if (instance != null) {
            hardResetAttribute(instance, ((Attribute)holder.value()).getDefaultValue());
         }
      } catch (Throwable var3) {
      }
   }

   private static void resetAttributeBase(ServerPlayer player, Holder<Attribute> holder, double value) {
      try {
         AttributeInstance instance = player.getAttribute(holder);
         if (instance != null) {
            hardResetAttribute(instance, value);
         }
      } catch (Throwable var5) {
      }
   }

   private static void hardResetAttribute(AttributeInstance instance, double value) {
      try {
         instance.removeModifier(TensuraRace.DEFAULT_RACE_ID);
      } catch (Throwable var7) {
      }

      try {
         instance.removeModifiers();
      } catch (Throwable var6) {
      }

      try {
         instance.setBaseValue(value);
      } catch (Throwable var5) {
      }

      try {
         instance.removeModifier(TensuraRace.DEFAULT_RACE_ID);
      } catch (Throwable var4) {
      }
   }

   private static void resetVanillaMovementState(ServerPlayer player) {
      player.setSprinting(false);
      player.setShiftKeyDown(false);
      player.fallDistance = 0.0F;
      player.setDeltaMovement(0.0, 0.0, 0.0);
      player.getAbilities().setWalkingSpeed(0.1F);
      player.getAbilities().setFlyingSpeed(0.05F);
      player.onUpdateAbilities();
      player.hurtMarked = true;
   }

   private static List<AttributeInstance> copiedAttributeInstances(ServerPlayer player) {
      List<AttributeInstance> result = new ArrayList<>();

      try {
         for (Field field : AttributeMap.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
               field.setAccessible(true);
               if (field.get(player.getAttributes()) instanceof Map<?, ?> attributes) {
                  for (Object object : attributes.values()) {
                     if (object instanceof AttributeInstance attribute && !result.contains(attribute)) {
                        result.add(attribute);
                     }
                  }
               }
            }
         }
      } catch (Throwable var11) {
      }

      return result;
   }

   private static void syncAttributes(ServerPlayer player) {
      try {
         player.connection.send(new ClientboundUpdateAttributesPacket(player.getId(), player.getAttributes().getSyncableAttributes()));
      } catch (Throwable var2) {
      }
   }
}
