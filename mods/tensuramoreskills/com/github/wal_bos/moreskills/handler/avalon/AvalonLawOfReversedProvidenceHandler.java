package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonLawReversedProvidenceVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.LivingPostDamageEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

final class AvalonLawOfReversedProvidenceHandler implements AvalonLawHandler {
   private static final ResourceLocation REVERSED_STRENGTH = RoyalSkillSupport.id("avalon_reversed_strength");
   private static final String WITHER_UNTIL = "AvalonReversedWitherUntil";
   private static final Map<UUID, Set<UUID>> SEEN = new HashMap<>();
   private static final Map<UUID, Set<UUID>> AFFECTED = new HashMap<>();
   private static final ThreadLocal<Boolean> REFLECTING = ThreadLocal.withInitial(() -> false);
   private static boolean initialized;

   public static void init() {
      if (!initialized) {
         initialized = true;
         TensuraEntityEvents.LIVING_POST_DAMAGE.register((LivingPostDamageEvent)(target, source, amount) -> {
            if (!REFLECTING.get()) {
               if (AvalonRoyalLawsHandler.isLawActive(target, 6)) {
                  if (source.getEntity() instanceof LivingEntity attacker && RoyalSkillSupport.validEnemy(target, attacker)) {
                     Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(target).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
                     if (!optional.isEmpty()) {
                        try {
                           REFLECTING.set(true);
                           attacker.invulnerableTime = 0;
                           attacker.hurt(RoyalSkillSupport.source(target, optional.get(), 2, TensuraDamageTypes.REFLECTED), amount * 2.5F);
                        } finally {
                           REFLECTING.set(false);
                        }
                     }
                  }
               }
            }
         });
      }
   }

   @Override
   public String name() {
      return "Law of Reversed Providence";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawReversedProvidenceVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void onActivate(ManasSkillInstance instance, LivingEntity owner) {
      SEEN.put(owner.getUUID(), new HashSet<>());
      AFFECTED.put(owner.getUUID(), new HashSet<>());
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 5 == 0) {
         double radius = (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get();
         LivingEntity selected = RoyalSkillSupport.lookTarget(owner, radius);
         AABB box = owner.getBoundingBox().inflate(radius);

         for (Projectile projectile : owner.level().getEntitiesOfClass(Projectile.class, box)) {
            if (projectile instanceof TensuraProjectile tensuraProjectile) {
               ManasSkillInstance projectileSkill = tensuraProjectile.getSkill();
               if (projectileSkill != null
                  && projectileSkill.is(TensuraSkillTags.MAGIC)
                  && projectile.getOwner() instanceof LivingEntity livingOwner
                  && RoyalSkillSupport.validEnemy(owner, livingOwner)) {
                  Entity destination = selected == null ? livingOwner : selected;
                  Vec3 destinationPos = destination.position().add(0.0, destination.getBbHeight() * 0.5, 0.0);
                  Vec3 velocity = destinationPos.subtract(projectile.position()).normalize().scale(Math.max(1.5, projectile.getDeltaMovement().length()));
                  projectile.setOwner(owner);
                  projectile.setDeltaMovement(velocity);
                  projectile.hurtMarked = true;
               }
            }
         }

         Set<UUID> seen = SEEN.computeIfAbsent(owner.getUUID(), ignored -> new HashSet<>());
         Set<UUID> affected = AFFECTED.computeIfAbsent(owner.getUUID(), ignored -> new HashSet<>());
         Set<UUID> currentlyAffected = new HashSet<>();

         for (LivingEntity enemy : RoyalSkillSupport.enemiesInRange(owner, radius)) {
            currentlyAffected.add(enemy.getUUID());
            affected.add(enemy.getUUID());
            if (seen.add(enemy.getUUID())) {
               halvePositiveEffects(enemy);
            }

            stripStrengthBoost(enemy);
            boolean regeneration = stripRegeneration(enemy);
            AttributeHelper.addPermanentAttribute(enemy, Attributes.ATTACK_DAMAGE, REVERSED_STRENGTH, -0.5, Operation.ADD_MULTIPLIED_TOTAL);
            if (regeneration) {
               enemy.getPersistentData().putLong("AvalonReversedWitherUntil", enemy.level().getGameTime() + 120L);
               RoyalSkillSupport.applyEffect(enemy, owner, TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), 120, 1);
            }

            if (enemy.getPersistentData().getLong("AvalonReversedWitherUntil") >= enemy.level().getGameTime() && owner.tickCount % 20 == 0) {
               enemy.invulnerableTime = 0;
               enemy.hurt(RoyalSkillSupport.source(owner, instance, 2, TensuraDamageTypes.CURSE), Math.max(4.0F, enemy.getMaxHealth() * 0.04F));
            }
         }

         if (owner.level() instanceof ServerLevel level) {
            for (UUID uuid : Set.copyOf(affected)) {
               if (!currentlyAffected.contains(uuid)) {
                  if (level.getEntity(uuid) instanceof LivingEntity living) {
                     AttributeHelper.removeAttribute(living, Attributes.ATTACK_DAMAGE, REVERSED_STRENGTH);
                  }

                  affected.remove(uuid);
                  seen.remove(uuid);
               }
            }
         }
      }
   }

   private static boolean stripStrengthBoost(LivingEntity enemy) {
      boolean removed = false;

      for (MobEffectInstance effect : List.copyOf(enemy.getActiveEffects())) {
         ResourceLocation id = effect.getEffect().unwrapKey().map(key -> key.location()).orElse(null);
         if (effect.getEffect().equals(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN)) || id != null && id.getPath().contains("strength")) {
            enemy.removeEffect(effect.getEffect());
            removed = true;
         }
      }

      return removed;
   }

   private static boolean stripRegeneration(LivingEntity enemy) {
      boolean removed = false;

      for (MobEffectInstance effect : List.copyOf(enemy.getActiveEffects())) {
         ResourceLocation id = effect.getEffect().unwrapKey().map(key -> key.location()).orElse(null);
         if (id != null && (id.getPath().contains("regeneration") || id.getPath().contains("healthcare") || id.getPath().contains("restoration"))) {
            enemy.removeEffect(effect.getEffect());
            removed = true;
         }
      }

      return removed;
   }

   private static void halvePositiveEffects(LivingEntity enemy) {
      for (MobEffectInstance effect : List.copyOf(enemy.getActiveEffects())) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
            int duration = Math.max(1, effect.getDuration() / 2);
            enemy.removeEffect(effect.getEffect());
            enemy.addEffect(
               new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon())
            );
         }
      }
   }

   @Override
   public void onDeactivate(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         for (UUID uuid : AFFECTED.getOrDefault(owner.getUUID(), Set.of())) {
            if (level.getEntity(uuid) instanceof LivingEntity living) {
               AttributeHelper.removeAttribute(living, Attributes.ATTACK_DAMAGE, REVERSED_STRENGTH);
            }
         }
      }

      SEEN.remove(owner.getUUID());
      AFFECTED.remove(owner.getUUID());
   }
}
