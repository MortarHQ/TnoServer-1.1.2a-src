package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.random.RandomGenerator;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class WonderOfU extends MoreRelicBase {
   public static boolean inDamageReflect = false;

   public WonderOfU(Properties properties) {
      super(new Properties().rarity(Rarity.EPIC).stacksTo(1));
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("entropy")
                     .stat(
                        StatData.builder("perch")
                           .initialValue(7.0, 10.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("flow_of_calamity")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(16.0, 12.0)
                           .upgradeModifier(UpgradeOperation.ADD, -1.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(5)
                     .build()
               )
               .ability(
                  AbilityData.builder("event_horizon")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(6.0, 10.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(540.0, 420.0)
                           .upgradeModifier(UpgradeOperation.ADD, -30.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .requiredLevel(10)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(13)
               .initialCost(300)
               .step(60)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("entropy").build()).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         IntegerCounter calamityCounter = DataComponentHandler.getAltIntegerCounter(stack);
         int calamityCooldown = calamityCounter.current();
         if (calamityCooldown >= 1) {
            calamityCooldown--;
         }

         stack.set(DataComponentHandler.ALT_INT_COUNTER, calamityCounter.getUpdatedCounter(calamityCooldown));
         MobEffectInstance nullEffect = livingEntity.getEffect(MoreRelics.NULLIFICATION);
         if (nullEffect != null && nullEffect.getDuration() == 1) {
            livingEntity.level()
               .playSound(
                  null,
                  livingEntity.position().x,
                  livingEntity.position().y,
                  livingEntity.position().z,
                  SoundEvents.BEACON_DEACTIVATE,
                  SoundSource.MASTER,
                  1.0F,
                  0.5F
               );
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (!player.level().isClientSide) {
         if (ability.equals("event_horizon")) {
            MobEffectInstance nullification = new MobEffectInstance(
               MoreRelics.NULLIFICATION, (int)(this.getStatValue(stack, "event_horizon", "duration") * 20.0)
            );
            player.addEffect(nullification);
            player.level().playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.WONDER, SoundSource.MASTER, 1.0F, 1.0F);
            this.horizonActivationBlast(player);
            this.setAbilityCooldown(stack, "event_horizon", (int)this.getStatValue(stack, "event_horizon", "cooldown") * 20);
         }
      }
   }

   @Override
   public boolean canEquipMultipleCopies() {
      return false;
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      super.onUnequip(slotContext, newStack, stack);
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem() && livingEntity.hasEffect(MoreRelics.NULLIFICATION)) {
         livingEntity.removeEffect(MoreRelics.NULLIFICATION);
         livingEntity.level()
            .playSound(
               null,
               livingEntity.position().x,
               livingEntity.position().y,
               livingEntity.position().z,
               SoundEvents.BEACON_DEACTIVATE,
               SoundSource.MASTER,
               1.5F,
               0.5F
            );
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void OnPlayerHurt(LivingIncomingDamageEvent event) {
      LivingEntity livingEntity = event.getEntity();
      boolean entityHasNullification = livingEntity.hasEffect(MoreRelics.NULLIFICATION);
      if (event.getSource().getEntity() instanceof LivingEntity applier
         && !applier.level().isClientSide
         && livingEntity instanceof Player player
         && player != applier) {
         if (!inDamageReflect) {
            inDamageReflect = true;
            if (event.getAmount() < 0.5F) {
               inDamageReflect = false;
               return;
            }

            double reflectAmount = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WONDER_OF_U.get()).stream().mapToDouble(stack -> {
               WonderOfU item = (WonderOfU)stack.getItem();
               if (!item.isAbilityUnlocked(stack, "entropy")) {
                  return 0.0;
               }

               item.spreadRelicExperience(player, stack, 5);
               return event.getAmount() * (item.getStatValue(stack, "entropy", "perch") / 100.0);
            }).sum();
            if (reflectAmount > 0.0) {
               if (entityHasNullification) {
                  reflectAmount *= 2.0;
               }

               reflectAmount = Math.clamp(reflectAmount, 0.0, player.getHealth());
               applier.hurt(new DamageSource(livingEntity.damageSources().mobAttack(livingEntity).typeHolder(), player, player), (float)reflectAmount);
            }

            inDamageReflect = false;
         }

         int shouldApplyBadEffect = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WONDER_OF_U.get())
            .stream()
            .mapToInt(
               stack -> {
                  WonderOfU item = (WonderOfU)stack.getItem();
                  if (!item.isAbilityUnlocked(stack, "flow_of_calamity")) {
                     return 0;
                  } else {
                     IntegerCounter calamityCounter = DataComponentHandler.getAltIntegerCounter(stack);
                     int calamityCooldown = calamityCounter.current();
                     if (calamityCooldown <= 0) {
                        stack.set(
                           DataComponentHandler.ALT_INT_COUNTER,
                           calamityCounter.getUpdatedCounter((int)(20.0 * item.getStatValue(stack, "flow_of_calamity", "cooldown")))
                        );
                        return 1;
                     } else {
                        return 0;
                     }
                  }
               }
            )
            .sum();
         if (shouldApplyBadEffect >= 1) {
            applyCalamity(applier);
            if (entityHasNullification) {
               applyCalamity(applier);
            }
         }
      }

      if (entityHasNullification) {
         event.setCanceled(true);
      }
   }

   private static void applyCalamity(LivingEntity target) {
      Map<Holder<MobEffect>, Integer[]> calamityEffects = getCalamityEffects();
      List<Entry<Holder<MobEffect>, Integer[]>> effectList = new ArrayList<>(calamityEffects.entrySet());
      Collections.shuffle(effectList, RandomGenerator.getDefault());

      for (Entry<Holder<MobEffect>, Integer[]> entry : effectList) {
         Holder<MobEffect> effectType = entry.getKey();
         Integer[] data = entry.getValue();
         int duration = data[0];
         int amplifier = data[1];
         if (!target.hasEffect(effectType)) {
            target.addEffect(new MobEffectInstance(effectType, duration, amplifier));
            break;
         }
      }
   }

   private void horizonActivationBlast(Player player) {
      Vec3 center = player.position();
      float radius = 4.0F;
      spawnWonderExplosionParticles(player.level(), player.position(), radius);
      AABB area = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);

      for (Entity detected : player.level().getEntities(player, area)) {
         if (detected instanceof LivingEntity livingEntity && player != detected) {
            Vec3 direction = livingEntity.position().subtract(center).normalize();
            double pushStrength = Math.clamp(3.0 / livingEntity.position().distanceTo(center), 1.0, 2.0);
            MobEffectInstance stun = new MobEffectInstance(EffectRegistry.STUN, 60);
            livingEntity.addEffect(stun);
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(direction.scale(pushStrength)));
         }
      }
   }

   private static Map<Holder<MobEffect>, Integer[]> getCalamityEffects() {
      Map<Holder<MobEffect>, Integer[]> effects = new HashMap<>();
      effects.put(MobEffects.MOVEMENT_SLOWDOWN, new Integer[]{100, 0});
      effects.put(MobEffects.WEAKNESS, new Integer[]{80, 0});
      effects.put(MobEffects.BLINDNESS, new Integer[]{60, 0});
      effects.put(MobEffects.DARKNESS, new Integer[]{120, 0});
      effects.put(MobEffects.HUNGER, new Integer[]{200, 1});
      effects.put(MobEffects.POISON, new Integer[]{160, 1});
      effects.put(MobEffects.WITHER, new Integer[]{120, 0});
      effects.put(MobEffects.UNLUCK, new Integer[]{180, 9});
      effects.put(EffectRegistry.PARALYSIS, new Integer[]{40, 0});
      effects.put(EffectRegistry.ANTI_HEAL, new Integer[]{200, 0});
      effects.put(MoreRelics.CORROSION, new Integer[]{120, 4});
      effects.put(MoreRelics.VULNERABILITY, new Integer[]{240, 0});
      return effects;
   }

   public static void spawnWonderExplosionParticles(Level level, Vec3 position, float radius) {
      int particleCount = 240;
      ServerLevel sevLevel = (ServerLevel)level;
      List<ServerPlayer> players = level.players();

      for (int i = 0; i < particleCount; i++) {
         double theta = Math.random() * 2.0 * Math.PI;
         double phi = Math.acos(2.0 * Math.random() - 1.0);
         double r = Math.cbrt(Math.random()) * radius;
         double x = position.x + r * Math.sin(phi) * Math.cos(theta);
         double y = position.y + r * Math.cos(phi);
         double z = position.z + r * Math.sin(phi) * Math.sin(theta);

         for (ServerPlayer player : players) {
            sevLevel.sendParticles(player, MoreRelics.WONDER_EXPLOSION.get(), true, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }
}
