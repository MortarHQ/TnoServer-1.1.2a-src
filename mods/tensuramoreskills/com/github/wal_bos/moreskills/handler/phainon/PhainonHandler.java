package com.github.wal_bos.moreskills.handler.phainon;

import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.unique.CookSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Added;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class PhainonHandler {
   public static final ResourceLocation CORE_RESISTANCE_DEGRADATION = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "phainon_core_resistance_degradation"
   );
   public static final ResourceLocation CORE_DODGE_NEGATION = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "phainon_core_dodge_negation");

   private PhainonHandler() {
   }

   public static Optional<ManasSkillInstance> getPhainon(LivingEntity entity) {
      return SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
   }

   public static void tick(LivingEntity entity, ManasSkillInstance instance) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (entity.tickCount % 80 == 0) {
            updateCoreAttributes(entity, instance);
            removeBlockedEffects(entity, instance);
            clearOwnedEvil(entity, instance);
         }

         PhainonDelivererActions.tick(entity, instance);
         PhainonSoulscorchEdict.tick(entity, instance);
         PhainonFallToMyName.tick(entity, instance);
         if (entity.tickCount % 20 == 0) {
            if (entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN))) {
               grantOrMessage(entity, instance, PhainonProgress.CoreFlame.WORLDBEARING, 20);
            }

            if (hasTemporaryBuff(entity)) {
               grantOrMessage(entity, instance, PhainonProgress.CoreFlame.TIME, 20);
            }

            updateRomance(entity, instance);
         }

         if (entity instanceof Player player && player.isFallFlying() && entity.tickCount % 10 == 0) {
            Vec3 old = new Vec3(tag.getDouble("PhainonSkyX"), tag.getDouble("PhainonSkyY"), tag.getDouble("PhainonSkyZ"));
            Vec3 now = entity.position();
            if (tag.getBoolean("PhainonSkyTracking")) {
               int gain = (int)Math.floor(old.distanceTo(now));
               if (gain > 0 && gain < 200) {
                  grantOrMessage(entity, instance, PhainonProgress.CoreFlame.SKY, gain);
               }
            }

            tag.putBoolean("PhainonSkyTracking", true);
            tag.putDouble("PhainonSkyX", now.x);
            tag.putDouble("PhainonSkyY", now.y);
            tag.putDouble("PhainonSkyZ", now.z);
         } else {
            tag.putBoolean("PhainonSkyTracking", false);
         }
      }
   }

   public static void sendCoreFlameProgress(LivingEntity entity, ManasSkillInstance instance) {
      if (entity instanceof Player player) {
         PhainonProgress progress = PhainonProgress.get(instance);
         player.sendSystemMessage(Component.literal("Phainon, The Deliverer").withStyle(ChatFormatting.GOLD));
         player.sendSystemMessage(
            Component.literal(
                  "Core Flames: " + progress.getCoreFlameCount() + "/12 | Cycles: " + progress.getCycles() + " | Blaze: " + progress.getBlaze() + "/8"
               )
               .withStyle(ChatFormatting.YELLOW)
         );

         for (PhainonProgress.CoreFlame flame : PhainonProgress.CoreFlame.values()) {
            boolean has = progress.has(flame);
            int value = progress.getProgress(flame);
            player.sendSystemMessage(
               Component.literal((has ? "[CORE] " : "[----] ") + flame.display + ": " + value + "/" + flame.max + " - " + flame.requirement)
                  .withStyle(has ? ChatFormatting.GREEN : ChatFormatting.GRAY)
            );
         }
      }
   }

   public static void grantOrMessage(LivingEntity entity, ManasSkillInstance instance, PhainonProgress.CoreFlame flame, int amount) {
      PhainonProgress progress = PhainonProgress.get(instance);
      boolean gained = progress.add(flame, amount);
      if (gained) {
         clearOwnedEvil(entity, instance);
         entity.sendSystemMessage(Component.literal("Core Flame acquired: " + flame.display).withStyle(ChatFormatting.GOLD));
         updateCoreAttributes(entity, instance);
      }
   }

   public static void grantDirect(LivingEntity entity, ManasSkillInstance instance, PhainonProgress.CoreFlame flame) {
      PhainonProgress progress = PhainonProgress.get(instance);
      if (progress.grant(flame)) {
         clearOwnedEvil(entity, instance);
         entity.sendSystemMessage(Component.literal("Core Flame acquired: " + flame.display).withStyle(ChatFormatting.GOLD));
         updateCoreAttributes(entity, instance);
      }
   }

   public static void grantAllCoreFlames(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);

      for (PhainonProgress.CoreFlame flame : PhainonProgress.CoreFlame.values()) {
         progress.setProgress(flame, flame.max);
         progress.grant(flame);
      }

      clearOwnedEvil(entity, instance);
      updateCoreAttributes(entity, instance);
      entity.sendSystemMessage(Component.literal("All Core Flames have been granted.").withStyle(ChatFormatting.GOLD));
   }

   public static void resetAllCoreFlames(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);

      for (PhainonProgress.CoreFlame flame : PhainonProgress.CoreFlame.values()) {
         progress.setProgress(flame, 0);
      }

      progress.clearCores();
      progress.setBlaze(0);
      updateCoreAttributes(entity, instance);
      entity.sendSystemMessage(Component.literal("All Core Flames have been reset.").withStyle(ChatFormatting.RED));
   }

   public static void updateCoreAttributes(LivingEntity entity, ManasSkillInstance instance) {
      int cores = PhainonProgress.get(instance).getCoreFlameCount();
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, CORE_RESISTANCE_DEGRADATION);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, CORE_DODGE_NEGATION);
      if (cores >= 3) {
         AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, CORE_RESISTANCE_DEGRADATION, 1.0, Operation.ADD_VALUE);
      }

      if (cores >= 6) {
         AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, CORE_DODGE_NEGATION, 100.0, Operation.ADD_VALUE);
      }
   }

   public static void clearCoreAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, CORE_RESISTANCE_DEGRADATION);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, CORE_DODGE_NEGATION);
   }

   public static boolean canStartShatter(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);
      CompoundTag tag = instance.getOrCreateTag();
      return entity.isAlive()
         && PhainonDelivererActions.isActive(instance)
         && progress.getCycles() >= 34
         && tag.getInt("PhainonShatterTicks") <= 0
         && !tag.getBoolean("PhainonFallUnlocked");
   }

   public static void startShatter(LivingEntity entity, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("PhainonShatterTicks", 1200);
      tag.putBoolean("PhainonFallUnlocked", true);
      tag.putInt("PhainonDelivererTicks", Math.max(tag.getInt("PhainonDelivererTicks"), 1200));
      entity.setHealth(Math.max(1.0F, Math.min(entity.getMaxHealth(), entity.getHealth())));
      entity.invulnerableTime = 0;
      entity.sendSystemMessage(Component.literal("Shatter the skies! No fate, no king, no god!").withStyle(ChatFormatting.GOLD));
   }

   public static void cleanupAfterTrueDeath(LivingEntity entity, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putInt("PhainonDelivererTicks", 0);
      tag.putInt("PhainonBlaze", 0);
      tag.putInt("PhainonBlazeHitCooldown", 0);
      tag.putInt("PhainonSoulscorchGuard", 0);
      tag.putInt("PhainonShatterTicks", 0);
      tag.putBoolean("PhainonFallUnlocked", false);
      PhainonDelivererActions.stop(entity, instance);
      PhainonDawnmaker.restore(entity, instance);
   }

   private static boolean hasTemporaryBuff(LivingEntity entity) {
      return entity.getActiveEffects().stream().anyMatch(effect -> effect.getDuration() > 0 && ((MobEffect)effect.getEffect().value()).isBeneficial());
   }

   private static void updateRomance(LivingEntity entity, ManasSkillInstance instance) {
      int count = entity.level()
         .getEntitiesOfClass(
            LivingEntity.class, entity.getBoundingBox().inflate(128.0), target -> target != entity && target.isAlive() && target.isAlliedTo(entity)
         )
         .size();
      PhainonProgress progress = PhainonProgress.get(instance);
      if (!progress.has(PhainonProgress.CoreFlame.ROMANCE)) {
         progress.setProgress(PhainonProgress.CoreFlame.ROMANCE, count);
         if (count >= PhainonProgress.CoreFlame.ROMANCE.max) {
            grantDirect(entity, instance, PhainonProgress.CoreFlame.ROMANCE);
         }
      }
   }

   private static void removeBlockedEffects(LivingEntity entity, ManasSkillInstance instance) {
      for (Holder<MobEffect> effect : getBlockedEffects(instance)) {
         entity.removeEffect(effect);
      }
   }

   private static List<Holder<MobEffect>> getBlockedEffects(ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);
      ArrayList<Holder<MobEffect>> list = new ArrayList<>();
      if (progress.getCoreFlameCount() < 12 && !progress.has(PhainonProgress.CoreFlame.TRICKERY)) {
         if (progress.has(PhainonProgress.CoreFlame.PASSAGE)) {
            addEffect(list, TensuraMobEffects.SPATIAL_BLOCKADE);
         }

         if (progress.has(PhainonProgress.CoreFlame.OCEAN)) {
            addEffect(list, TensuraMobEffects.BLACK_BURN);
         }

         if (progress.has(PhainonProgress.CoreFlame.SKY)) {
            addEffect(list, TensuraMobEffects.INFINITE_IMPRISONMENT);
         }

         if (progress.has(PhainonProgress.CoreFlame.WORLDBEARING)) {
            addEffect(list, TensuraMobEffects.MAGIC_INTERFERENCE);
         }

         if (progress.has(PhainonProgress.CoreFlame.REASON)) {
            addEffect(list, TensuraMobEffects.ANTI_SKILL);
         }

         if (progress.has(PhainonProgress.CoreFlame.ROMANCE)) {
            addEffect(list, TensuraMobEffects.MIND_CONTROL);
         }

         if (progress.has(PhainonProgress.CoreFlame.STRIFE)) {
            addEffect(list, TensuraMobEffects.DISINTEGRATING);
         }

         if (progress.has(PhainonProgress.CoreFlame.DEATH)) {
            addEffect(list, TensuraMobEffects.SOUL_DRAIN);
         }

         return list;
      } else {
         list.addAll(getAbnormalConditionEffects());
         addEffect(list, TensuraMobEffects.SPATIAL_BLOCKADE);
         addEffect(list, TensuraMobEffects.BLACK_BURN);
         addEffect(list, TensuraMobEffects.INFINITE_IMPRISONMENT);
         addEffect(list, TensuraMobEffects.MAGIC_INTERFERENCE);
         addEffect(list, TensuraMobEffects.ANTI_SKILL);
         addEffect(list, TensuraMobEffects.MIND_CONTROL);
         addEffect(list, TensuraMobEffects.DISINTEGRATING);
         addEffect(list, TensuraMobEffects.SOUL_DRAIN);
         addEffect(list, TensuraMobEffects.MOVEMENT_INTERFERENCE);
         addEffect(list, TensuraMobEffects.SLEEP);
         addEffect(list, TensuraMobEffects.FEAR);
         addEffect(list, TensuraMobEffects.CONFUSION);
         addEffect(list, TensuraMobEffects.DROWSINESS);
         addEffect(list, TensuraMobEffects.MAGICULE_POISON);
         addEffect(list, TensuraMobEffects.CORROSION);
         addEffect(list, TensuraMobEffects.FROST);
         addEffect(list, TensuraMobEffects.CHILL);
         addEffect(list, TensuraMobEffects.WEBBED);
         addEffect(list, TensuraMobEffects.INSANITY);
         return list;
      }
   }

   private static List<Holder<MobEffect>> getAbnormalConditionEffects() {
      ArrayList<Holder<MobEffect>> list = new ArrayList<>();
      list.add(MobEffects.HUNGER);
      list.add(MobEffects.POISON);
      list.add(MobEffects.BLINDNESS);
      list.add(MobEffects.CONFUSION);
      list.add(MobEffects.DARKNESS);
      list.add(MobEffects.DIG_SLOWDOWN);
      list.add(MobEffects.MOVEMENT_SLOWDOWN);
      list.add(MobEffects.WEAKNESS);
      addEffect(list, TensuraMobEffects.BURDEN);
      addEffect(list, TensuraMobEffects.FRAGILITY);
      addEffect(list, TensuraMobEffects.CURSE);
      addEffect(list, TensuraMobEffects.FATAL_POISON);
      addEffect(list, TensuraMobEffects.PARALYSIS);
      addEffect(list, TensuraMobEffects.PETRIFICATION);
      addEffect(list, TensuraMobEffects.HYPNOSIS);
      return list;
   }

   private static void addEffect(List<Holder<MobEffect>> list, RegistrySupplier<MobEffect> supplier) {
      Holder<MobEffect> effect = TensuraMobEffects.getReference(supplier);
      if (effect != null && !list.contains(effect)) {
         list.add(effect);
      }
   }

   private static void clearOwnedEvil(LivingEntity entity, ManasSkillInstance instance) {
      PhainonProgress progress = PhainonProgress.get(instance);
      if (progress.has(PhainonProgress.CoreFlame.LAW) || progress.getCoreFlameCount() >= 12) {
         clearSeverance(entity);
         CookSkill.removeCookedHP(entity);
      }

      if (progress.has(PhainonProgress.CoreFlame.TRICKERY) || progress.getCoreFlameCount() >= 12) {
         removeBlockedEffects(entity, instance);
      }
   }

   private static void clearSeverance(LivingEntity entity) {
      IEffect effect = TensuraStorages.getEffectFrom(entity);
      if (effect.getSeveranceAmount() > 0.0F) {
         effect.setSeveranceAmount(0.0F);
         effect.markDirty();
      }
   }

   @SubscribeEvent
   public static void onEffectAdded(Added event) {
      if (event.getEffectSource() instanceof LivingEntity living
         && event.getEffectInstance() != null
         && !((MobEffect)event.getEffectInstance().getEffect().value()).isBeneficial()) {
         getPhainon(living).ifPresent(instance -> grantOrMessage(living, instance, PhainonProgress.CoreFlame.TRICKERY, 1));
      }

      getPhainon(event.getEntity()).ifPresent(instance -> {
         if (event.getEffectInstance() != null && getBlockedEffects(instance).contains(event.getEffectInstance().getEffect())) {
            event.getEntity().removeEffect(event.getEffectInstance().getEffect());
         }
      });
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      getPhainon(target).ifPresent(instance -> {
         CompoundTag tag = instance.getOrCreateTag();
         PhainonProgress progress = PhainonProgress.get(instance);
         if (tag.getInt("PhainonShatterTicks") > 0) {
            event.setCanceled(true);
            target.setHealth(Math.max(target.getHealth(), 1.0F));
            target.invulnerableTime = 0;
         } else {
            float incoming = Math.max(0.0F, event.getAmount());
            float survivalPool = target.getHealth() + target.getAbsorptionAmount();
            boolean fatal = incoming >= survivalPool || isKillCommandDamage(event.getSource());
            if (fatal && canStartShatter(target, instance)) {
               event.setCanceled(true);
               startShatter(target, instance);
            } else {
               if (tag.getInt("PhainonSoulscorchGuard") > 0) {
                  event.setAmount(event.getAmount() * 0.25F);
               }

               if (PhainonDelivererActions.isActive(instance) && tag.getInt("PhainonBlazeHitCooldown") <= 0) {
                  progress.addBlaze(1);
                  tag.putInt("PhainonBlazeHitCooldown", instance.isMastered(target) ? 100 : 200);
               }

               if (isLawDamage(event.getSource(), target)) {
                  grantOrMessage(target, instance, PhainonProgress.CoreFlame.LAW, Math.max(1, (int)event.getAmount()));
                  if (progress.has(PhainonProgress.CoreFlame.LAW)) {
                     clearSeverance(target);
                     CookSkill.removeCookedHP(target);
                     event.setCanceled(true);
                     return;
                  }
               }

               if (progress.has(PhainonProgress.CoreFlame.STRIFE) && target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.DISINTEGRATING))) {
                  event.setCanceled(true);
               } else {
                  incoming = Math.max(0.0F, event.getAmount());
                  survivalPool = target.getHealth() + target.getAbsorptionAmount();
                  if (incoming > 0.0F && incoming < survivalPool) {
                     grantOrMessage(target, instance, PhainonProgress.CoreFlame.EARTH, Math.max(1, (int)incoming));
                  }
               }
            }
         }
      });
      if (event.getSource().getEntity() instanceof LivingEntity living) {
         getPhainon(living).ifPresent(instance -> {
            int cores = PhainonProgress.get(instance).getCoreFlameCount();
            float multiplier = 1.0F + cores * 0.08F;
            if (PhainonDelivererActions.isActive(instance)) {
               multiplier *= 5.0F;
            }

            if (instance.getOrCreateTag().getInt("PhainonShatterTicks") > 0) {
               multiplier *= 10.0F;
            }

            event.setAmount(event.getAmount() * multiplier);
         });
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      getPhainon(entity).ifPresent(instance -> {
         PhainonProgress progress = PhainonProgress.get(instance);
         progress.setProgress(PhainonProgress.CoreFlame.EARTH, 0);
         if (canStartShatter(entity, instance)) {
            event.setCanceled(true);
            startShatter(entity, instance);
         } else if (instance.getOrCreateTag().getInt("PhainonShatterTicks") <= 0) {
            cleanupAfterTrueDeath(entity, instance);
         }
      });
      if (event.getSource().getEntity() instanceof LivingEntity living) {
         getPhainon(living).ifPresent(instance -> {
            grantOrMessage(living, instance, PhainonProgress.CoreFlame.DEATH, 1);
            if (living.getMainHandItem().isEmpty()) {
               grantDirect(living, instance, PhainonProgress.CoreFlame.STRIFE);
            }
         });
      }
   }

   @SubscribeEvent
   public static void onFish(ItemFishedEvent event) {
      getPhainon(event.getEntity()).ifPresent(instance -> grantOrMessage(event.getEntity(), instance, PhainonProgress.CoreFlame.OCEAN, 1));
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      getPhainon(player).ifPresent(instance -> {
         if (!player.isAlive()) {
            cleanupAfterTrueDeath(player, instance);
         } else {
            if (player.tickCount % 20 == 0 && player.getMainHandItem().isEnchanted()) {
               CompoundTag tag = instance.getOrCreateTag();
               int hash = player.getMainHandItem().hashCode();
               if (tag.getInt("PhainonLastEnchantHash") != hash) {
                  tag.putInt("PhainonLastEnchantHash", hash);
                  grantOrMessage(player, instance, PhainonProgress.CoreFlame.REASON, 1);
               }
            }
         }
      });
   }

   private static boolean isLawDamage(DamageSource source, LivingEntity target) {
      if (TensuraDamageHelper.isSeveranceDamage(source, target, true)) {
         return true;
      }

      if (TensuraDamageHelper.isSeveranceDamage(source, target, false)) {
         return true;
      }

      String id = source.getMsgId().toLowerCase(Locale.ROOT);
      return id.contains("cook") || id.contains("cooked");
   }

   private static boolean isKillCommandDamage(DamageSource source) {
      String id = source.getMsgId().toLowerCase(Locale.ROOT);
      return id.contains("out_of_world") || id.contains("felloutofworld") || id.contains("generic_kill") || id.contains("kill");
   }
}
