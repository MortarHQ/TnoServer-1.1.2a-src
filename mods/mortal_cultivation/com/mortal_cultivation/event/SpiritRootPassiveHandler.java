package com.mortal_cultivation.event;

import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public final class SpiritRootPassiveHandler {
   private static final ResourceLocation WIND_SPEED_BONUS_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_root_wind_speed");
   private static final double WIND_SPEED_BONUS = 0.15;
   private static final ResourceLocation OVERLORD_BODY_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "spirit_root_overlord_body_health"
   );
   private static final ResourceLocation OVERLORD_BODY_LUCK_ID = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "spirit_root_overlord_body_luck");
   private static final ResourceLocation OVERLORD_BODY_AURA_REGEN_ID = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "spirit_root_overlord_body_aura_regeneration"
   );
   private static final ResourceLocation OVERLORD_BODY_MAGICULE_REGEN_ID = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "spirit_root_overlord_body_magicule_regeneration"
   );
   private static final int OVERLORD_BODY_RESISTANCE_DURATION = 40;
   private static final int WATER_RECOVERY_INTERVAL = 40;
   private static final float WATER_HEAL_PERCENT = 0.01F;
   private static final double WATER_MAGICULE_PERCENT = 0.015;

   private SpiritRootPassiveHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && player.isAlive()) {
         syncWindMovementBonus(player);
         syncOverlordBodyBonuses(player);
         if (SpiritRootData.hasPrimaryRoot(player, SpiritRootType.WATER) && player.tickCount % 40 == 0) {
            applyWaterRecovery(player);
         }
      }
   }

   private static void syncWindMovementBonus(Player player) {
      AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         boolean hasWindRoot = SpiritRootData.hasPrimaryRoot(player, SpiritRootType.WIND) && !player.isSpectator();
         boolean hasModifier = speed.getModifier(WIND_SPEED_BONUS_ID) != null;
         if (hasWindRoot && !hasModifier) {
            speed.addTransientModifier(new AttributeModifier(WIND_SPEED_BONUS_ID, 0.15, Operation.ADD_MULTIPLIED_TOTAL));
         } else if (!hasWindRoot && hasModifier) {
            speed.removeModifier(WIND_SPEED_BONUS_ID);
         }
      }
   }

   private static void syncOverlordBodyBonuses(Player player) {
      boolean hasOverlordBody = SpiritRootData.hasPrimaryRoot(player, SpiritRootType.OVERLORD_BODY) && !player.isSpectator();
      syncAttributeModifier(player.getAttribute(Attributes.MAX_HEALTH), OVERLORD_BODY_HEALTH_ID, hasOverlordBody, 5.0, Operation.ADD_VALUE);
      syncAttributeModifier(player.getAttribute(Attributes.LUCK), OVERLORD_BODY_LUCK_ID, hasOverlordBody, 3.0, Operation.ADD_VALUE);
      syncAttributeModifier(
         player.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER), OVERLORD_BODY_AURA_REGEN_ID, hasOverlordBody, 0.5, Operation.ADD_MULTIPLIED_TOTAL
      );
      syncAttributeModifier(
         player.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER),
         OVERLORD_BODY_MAGICULE_REGEN_ID,
         hasOverlordBody,
         0.5,
         Operation.ADD_MULTIPLIED_TOTAL
      );
      if (hasOverlordBody) {
         MobEffectInstance resistance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
         if (resistance == null || resistance.getAmplifier() == 0 && resistance.getDuration() < 20) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 0, true, false, true));
         }
      }
   }

   private static void syncAttributeModifier(AttributeInstance attribute, ResourceLocation id, boolean enabled, double amount, Operation operation) {
      if (attribute != null) {
         boolean present = attribute.getModifier(id) != null;
         if (enabled && !present) {
            attribute.addTransientModifier(new AttributeModifier(id, amount, operation));
         } else if (!enabled && present) {
            attribute.removeModifier(id);
         }
      }
   }

   private static void applyWaterRecovery(Player player) {
      if (!player.isSpectator()) {
         if (player.getHealth() < player.getMaxHealth()) {
            player.heal(player.getMaxHealth() * 0.01F);
         }

         double maxMagicule = EnergyHelper.getMaxMagicule(player);
         if (maxMagicule > 0.0) {
            EnergyHelper.gainMagicule(player, maxMagicule * 0.015, GainType.NORMAL);
         }
      }
   }
}
